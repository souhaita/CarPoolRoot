package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityCreateTrip;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverAcceptRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverDeleteRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.ManageRequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.RequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class DriverRequestAdapterOther extends BaseAdapter {

    private static LayoutInflater inflater = null;

    private Context context;
    private List<RequestObject> requestObjectList;

    List<Boolean> confirmDelete;
    private DriverRequestAdapterOther DriverRequestAdapterOther = this;
    private RequestStatus requestStatus;

    public DriverRequestAdapterOther(Context context, List<RequestObject> requestObjectList, RequestStatus requestStatus){
        this.context = context;
        this.requestObjectList = requestObjectList;
        this.requestStatus = requestStatus;

        confirmDelete = new ArrayList<>();
        for(RequestObject r: requestObjectList){
            confirmDelete.add(false);
        }

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(requestObjectList != null )
            return requestObjectList.size();

        return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_driver_manage_request_other_content, null);

        final RequestDTO requestDTO = requestObjectList.get(i).getRequestDTO();

        TextView txtFrom = (TextView)view.findViewById(R.id.txtFrom);
        txtFrom.setText(requestDTO.getPlaceFrom());

        TextView txtTo = (TextView)view.findViewById(R.id.txtTo);
        txtTo.setText(requestDTO.getPlaceTo());

        TextView txtDate = (TextView)view.findViewById(R.id.txtDate);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM HH:mm");
        String date = null;
        try {
            date = format.format(requestDTO.getEvenDate());
        }
        catch (Exception e){

        }
        txtDate.setText(date);

        String seats = context.getString(R.string.driver_request_adapter_seats_left);
        TextView txtSeatAvailable = (TextView)view.findViewById(R.id.txtSeatAvailable);
        txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString() +" "+seats);

        TextView txtPrice = (TextView)view.findViewById(R.id.txtPrice);
        txtPrice.setText("Rs "+requestDTO.getPrice().toString());

        final ViewPager imgCarPic = (ViewPager) view.findViewById(R.id.imgCarPic);

        List<byte []> profilePic = new ArrayList<>();
        if(requestObjectList.get(i).getAccountDTOList() != null && requestObjectList.get(i).getAccountDTOList().size() >0){
            for(int x = 0; x < requestObjectList.get(i).getAccountDTOList().size(); x++){
                if(requestObjectList.get(i).getAccountDTOList().get(x).getProfilePicture() != null){
                    profilePic.add(requestObjectList.get(i).getAccountDTOList().get(x).getProfilePicture());
                }
            }
        }

        PhotoViewPagerAdapter photoViewPagerAdapter = new PhotoViewPagerAdapter(context, profilePic);
        imgCarPic.setAdapter(photoViewPagerAdapter);

        imgCarPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        final PhotoViewPagerAdapter photoAdapter = photoViewPagerAdapter;

        if(photoViewPagerAdapter.getCount() > 1) {
            final android.os.Handler handler = new android.os.Handler();
            Runnable runnable = new Runnable() {
                int i = photoAdapter.getCount() - 1;

                public void run() {

                    if (i < 0)
                        i = photoAdapter.getCount() - 1;

                    imgCarPic.setCurrentItem(i);
                    i--;

                    handler.postDelayed(this, 5000);
                }
            };
            handler.postDelayed(runnable, 0);
        }


        LinearLayout llRequestDetails = (LinearLayout)view.findViewById(R.id.llRequestDetails);
        llRequestDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityCreateTrip.class);
                RequestObject requestObject = requestObjectList.get(i);
                intent.putExtra(CONSTANT.REQUEST_OBJECT, requestObject);
                ((Activity)context).startActivityForResult(intent, CONSTANT.MANAGE_TRIP_ACTIVITY_DRIVER_REQUEST_PENDING);
            }
        });

        final ImageView imgDelete = (ImageView)view.findViewById(R.id.imgDelete);

        LinearLayout llDeleteRequest = (LinearLayout)view.findViewById(R.id.llDeleteRequest);
        llDeleteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!confirmDelete.get(i)){
                    imgDelete.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_bin_open_red));
                    confirmDelete.set(i, true);
                }
                else{
                    requestDTO.setManageRequestId(requestObjectList.get(i).getManageRequestDTOList().get(0).getManageRequestId());
                    new AsyncDriverDeleteRequest(context, DriverRequestAdapterOther, requestObjectList).execute(requestDTO);
                    confirmDelete.set(i, false);
                }
            }
        });

        LinearLayout llAcceptRequest = (LinearLayout)view.findViewById(R.id.llAcceptRequest);
        llAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer manageRequestId = requestObjectList.get(i).getManageRequestDTOList().get(0).getManageRequestId();
                ManageRequestDTO serverManageRequest = requestObjectList.get(i).getManageRequestDTOList().get(0);

                RequestDTO serverRequest = requestObjectList.get(i).getRequestDTO();

                ManageRequestDAO manageRequestDAO = new ManageRequestDAO(context);
                ManageRequestDTO manageRequestDTO = manageRequestDAO.getManageRequest(manageRequestId);

                if(manageRequestDTO != null && manageRequestDTO.getManageRequestId() != null){
                    RequestDAO requestDAO = new RequestDAO(context);
                    RequestDTO r = requestDAO.getRequestByID(manageRequestDTO.getRequestId());

                    if(r.getSeatAvailable().intValue() > 0 && r.getSeatAvailable().intValue() <= serverManageRequest.getSeatRequested().intValue()){
                        requestDTO.setManageRequestId(manageRequestId);
                        new AsyncDriverAcceptRequest(context, DriverRequestAdapterOther, requestObjectList).execute(requestDTO);
                        confirmDelete.set(i, false);
                    }
                    else{
                        String message = context.getString(R.string.async_driver_accept_client_request_fail);
                        Utils.alertError(context, message);
                    }
                }
                else if(serverManageRequest.getSeatRequested().intValue() <= serverRequest.getSeatAvailable().intValue()){
                    requestDTO.setManageRequestId(manageRequestId);
                    new AsyncDriverAcceptRequest(context, DriverRequestAdapterOther, requestObjectList).execute(requestDTO);
                    confirmDelete.set(i, false);
                }
                else{
                    String message = context.getString(R.string.async_driver_accept_client_request_fail);
                    Utils.alertError(context, message);
                }
            }
        });


        llRequestDetails.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_CANCEL:
                        imgDelete.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_bin_close_red));
                        confirmDelete.set(i, false);
                        return false;
                }
                return false;
            }
        });


        LinearLayout llMain = (LinearLayout) view.findViewById(R.id.llMain);
        Utils.animateList(llMain, context, i);

        return view;
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
