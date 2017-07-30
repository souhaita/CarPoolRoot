package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityCreateTrip;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverDeleteRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class DriverRequestAdapterPending extends RecyclerView.Adapter {

    private static LayoutInflater inflater = null;

    private Context context;
    private List<RequestObject> requestObjectList;

    List<Boolean> confirmDelete;

    private DriverRequestAdapterPending DriverRequestAdapterPending = this;

    public DriverRequestAdapterPending(Context context, List<RequestObject> requestObjectList){
        this.context = context;
        this.requestObjectList = requestObjectList;

        confirmDelete = new ArrayList<>();
        for(RequestObject r: requestObjectList){
            confirmDelete.add(false);
        }

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_driver_manage_request_pending_content, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int i) {

        final ViewHolder viewHolder = (ViewHolder)holder;

        final RequestDTO requestDTO = requestObjectList.get(i).getRequestDTO();

        viewHolder.txtFrom.setText(requestDTO.getPlaceFrom());

        viewHolder.txtTo.setText(requestDTO.getPlaceTo());

        SimpleDateFormat format = new SimpleDateFormat("dd MMM HH:mm");
        String date = null;
        try {
            date = format.format(requestDTO.getEventDate());
        }
        catch (Exception e){

        }
        viewHolder.txtDate.setText(date);

        String seats;


        if(requestDTO.getSeatAvailable() >1) {
            seats = context.getString(R.string.driver_request_adapter_seats_left);
            viewHolder.txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString() +" "+seats);
        }
        else if (requestDTO.getSeatAvailable() == 1) {
            seats = context.getString(R.string.driver_request_adapter_seat_left);
            viewHolder.txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString() +" "+seats);
        }
        else {
            seats = context.getString(R.string.driver_request_adapter_no_seat_left);
            viewHolder.txtSeatAvailable.setText(seats);
        }

        List<ManageRequestDTO> manageRequestDTOList = requestObjectList.get(i).getManageRequestDTOList();

        int count = 0;
        for(ManageRequestDTO m : manageRequestDTOList){
            count += m.getSeatRequested();
        }

        int unitPrice = requestDTO.getPrice();
        int totalPrice = count * unitPrice;

        String unitSPrice = String.valueOf(unitPrice);
        String totalSPrice = String.valueOf(totalPrice);

        viewHolder.txtPrice.setText("Rs"+totalSPrice+" ("+unitSPrice+"/p)");

        List<byte []> profilePic = new ArrayList<>();
        final List<String> fullName =  new ArrayList<>();
        if(requestObjectList.get(i).getAccountDTOList() != null && requestObjectList.get(i).getAccountDTOList().size() >0){
            for(int x = 0; x < requestObjectList.get(i).getAccountDTOList().size(); x++){
                if(requestObjectList.get(i).getAccountDTOList().get(x).getProfilePicture() != null){
                    profilePic.add(requestObjectList.get(i).getAccountDTOList().get(x).getProfilePicture());
                    fullName.add(requestObjectList.get(i).getAccountDTOList().get(x).getFullName());
                }
            }
        }

        PhotoViewPagerAdapter photoViewPagerAdapter = new PhotoViewPagerAdapter(context, profilePic);
        viewHolder.imgViewPhoto.setAdapter(photoViewPagerAdapter);

        viewHolder.imgViewPhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        final PhotoViewPagerAdapter photoAdapter = photoViewPagerAdapter;

        if(photoViewPagerAdapter.getCount() > 1) {
            viewHolder.txtPassengerFullName.setVisibility(View.VISIBLE);

            final android.os.Handler handler = new android.os.Handler();
            Runnable runnable = new Runnable() {
                int i = photoAdapter.getCount() - 1;

                public void run() {
                    if (i < 0)
                        i = photoAdapter.getCount() - 1;

                    viewHolder.txtPassengerFullName.setText(fullName.get(i));

                    viewHolder.imgViewPhoto.setCurrentItem(i);
                    i--;

                    handler.postDelayed(this, 5000);
                }
            };
            handler.postDelayed(runnable, 0);

        }

        viewHolder.llRequestDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityCreateTrip.class);
                RequestObject requestObject = requestObjectList.get(i);
                intent.putExtra(CONSTANT.REQUEST_OBJECT, requestObject);
                ((Activity)context).startActivityForResult(intent, CONSTANT.MANAGE_TRIP_ACTIVITY_DRIVER_REQUEST_PENDING);
            }
        });


        viewHolder.llDeleteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!confirmDelete.get(i)){
                    viewHolder.imgDelete.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_bin_open_red));
                    confirmDelete.set(i, true);
                }
                else{
                    new AsyncDriverDeleteRequest(context, DriverRequestAdapterPending, requestObjectList).execute(requestDTO);
                }
            }
        });


        viewHolder.llRequestDetails.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_CANCEL:
                        viewHolder.imgDelete.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_bin_close_red));
                        confirmDelete.set(i, false);
                        return false;
                }
                return false;
            }
        });

        Utils.animateList(viewHolder.llMain, context, i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if(requestObjectList != null )
            return requestObjectList.size();
        return 0;
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView txtFrom, txtTo, txtDate, txtPrice, txtSeatAvailable, txtPassengerFullName;
        final ViewPager imgViewPhoto;
        final LinearLayout llRequestDetails,llDeleteRequest,llMain;
        final ImageView imgDelete;

        ViewHolder(final View view) {
            super(view);
            txtSeatAvailable = (TextView)view.findViewById(R.id.txtSeatAvailable);
            txtFrom = (TextView)view.findViewById(R.id.txtFrom);
            txtTo = (TextView)view.findViewById(R.id.txtTo);
            txtDate = (TextView)view.findViewById(R.id.txtDate);
            txtPrice = (TextView)view.findViewById(R.id.txtPrice);
            txtPassengerFullName = (TextView)view.findViewById(R.id.txtPassengerFullName);
            imgViewPhoto = (ViewPager)view.findViewById(R.id.imgViewPhoto);
            llRequestDetails = (LinearLayout)view.findViewById(R.id.llRequestDetails);
            llMain = (LinearLayout)view.findViewById(R.id.llMain);
            imgDelete = (ImageView)view.findViewById(R.id.imgDelete);
            llDeleteRequest = (LinearLayout)view.findViewById(R.id.llDeleteRequest);
        }
    }

    public List<RequestObject> getRequestObjectList() {
        return requestObjectList;
    }

    public void setRequestObjectList(List<RequestObject> requestObjectList) {
        this.requestObjectList = requestObjectList;
    }

    public List<Boolean> getConfirmDelete() {
        return confirmDelete;
    }

    public void setConfirmDelete(List<Boolean> confirmDelete) {
        this.confirmDelete = confirmDelete;
    }
}
