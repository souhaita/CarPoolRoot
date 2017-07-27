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

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityPassengerViewDriverProfile;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityPhoneNumber;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncPassengerAcceptRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncPassengerDeleteRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class PassengerSearchTripAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static LayoutInflater inflater = null;

    private Context context;
    private List<RequestObject> requestObjectList;

    List<Boolean> confirmDelete;

    private PassengerSearchTripAdapter passengerSearchTripAdapter = this;

    public PassengerSearchTripAdapter(Context context, List<RequestObject> requestObjectList){
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_passenger_search_trip_content, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final viewHolder viewHolder = (viewHolder)holder;
        final RequestDTO requestDTO = requestObjectList.get(position).getRequestDTO();

        viewHolder.txtFrom.setText(requestDTO.getPlaceFrom());
        viewHolder.txtTo.setText(requestDTO.getPlaceTo());
        viewHolder.txtPrice.setText(requestDTO.getPrice().toString());
        viewHolder.txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString());

        SimpleDateFormat format = new SimpleDateFormat("dd MMM HH:mm");
        String date = null;
        try {
            date = format.format(requestDTO.getEvenDate());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        viewHolder.txtDate.setText(date);

        List<byte []> images = new ArrayList<>();

        AccountDTO accountDTO = requestObjectList.get(position).getAccountDTOList().get(0);
        images.add(accountDTO.getProfilePicture());

        CarDTO carDTO = requestObjectList.get(position).getCarDTO().get(0);

        if(carDTO.isHasPic1() && carDTO.getPicture1() != null)
            images.add(carDTO.getPicture1());

        if(carDTO.isHasPic2() && carDTO.getPicture2() != null)
            images.add(carDTO.getPicture2());

        if(carDTO.isHasPic3() && carDTO.getPicture3() != null)
            images.add(carDTO.getPicture3());

        if(carDTO.isHasPic4() && carDTO.getPicture4() != null)
            images.add(carDTO.getPicture4());

        final PhotoViewPagerAdapter photoViewPagerAdapter = new PhotoViewPagerAdapter(context,images);
        viewHolder.imgViewPager.setAdapter(photoViewPagerAdapter);

        viewHolder.imgViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        if(photoViewPagerAdapter.getCount() > 1) {
            final android.os.Handler handler = new android.os.Handler();
            Runnable runnable = new Runnable() {
                int i = photoViewPagerAdapter.getCount() - 1;

                public void run() {

                    if (i < 0)
                        i = photoViewPagerAdapter.getCount() - 1;

                    viewHolder.imgViewPager.setCurrentItem(i);
                    i--;

                    handler.postDelayed(this, 5000);
                }
            };
            handler.postDelayed(runnable, 0);
        }

        viewHolder.llAcceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = Utils.getCurrentAccount(context);
                AccountDTO account = new AccountDAO(context).getAccountById(userId);

                if(account.getPhoneNum() != null && account.getPhoneNum() >0) {
                    requestDTO.setSeatRequested(1);
                    new AsyncPassengerAcceptRequest(context, passengerSearchTripAdapter, requestObjectList).execute(requestDTO);
                }
                else{
                    Intent intent = new Intent(context, PickerActivityPhoneNumber.class);
                    ((Activity)context).startActivityForResult(intent, CONSTANT.PROFILE_ACTIVITY_PHONE_NUMBER);
                }
            }
        });

        viewHolder.llDeleteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!confirmDelete.get(position)){
                    viewHolder.imgDelete.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_bin_open_red));
                    confirmDelete.set(position, true);
                }
                else{
                    new AsyncPassengerDeleteRequest(context, passengerSearchTripAdapter, requestObjectList).execute(requestDTO);
                }
            }
        });

        viewHolder.llRequestDetails.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_CANCEL:
                        viewHolder.imgDelete.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_bin_close_red));
                        confirmDelete.set(position, false);
                        return false;
                }
                return false;
            }
        });

        viewHolder.llRequestDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityPassengerViewDriverProfile.class);
                RequestObject requestObject = requestObjectList.get(position);

                AccountDTO account = requestObject.getAccountDTOList().get(0);
                CarDTO car = requestObject.getCarDTO().get(0);

                Utils.savePictureToFolder(account.getProfilePicture(), CONSTANT.PROFILE_PICTURE_PATH, account.getAccountId().toString(), false);

                if(car.getPicture1() != null)
                    Utils.savePictureToFolder(car.getPicture1(), car.getCarId().toString(), "1", true);

                if(car.getPicture2() != null)
                    Utils.savePictureToFolder(car.getPicture2(), car.getCarId().toString(), "2", true);

                if(car.getPicture3() != null)
                    Utils.savePictureToFolder(car.getPicture3(), car.getCarId().toString(), "3", true);

                if(car.getPicture4() != null)
                    Utils.savePictureToFolder(car.getPicture4(), car.getCarId().toString(), "4", true);

                requestObject.getAccountDTOList().get(0).setProfilePicture(null);
                requestObject.getCarDTO().get(0).setPicture1(null);
                requestObject.getCarDTO().get(0).setPicture2(null);
                requestObject.getCarDTO().get(0).setPicture3(null);
                requestObject.getCarDTO().get(0).setPicture4(null);

                intent.putExtra(CONSTANT.REQUEST_OBJECT, requestObject);
                ((Activity)context).startActivityForResult(intent, CONSTANT.SEARCH_TRIP_ACTIVITY);
            }
        });

        Utils.animateList(viewHolder.llMain, context, position);
    }

    @Override
    public int getItemCount() {
        if(requestObjectList != null && requestObjectList.size() >0)
            return requestObjectList.size();
        return 0;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView txtFrom, txtTo, txtFullName, txtPrice, txtDate, txtSeatAvailable;
        public ViewPager imgViewPager;
        public ImageView imgDelete, imgAccept;
        public LinearLayout llAcceptRequest, llDeleteRequest, llMain, llRequestDetails;

        public viewHolder(View view) {
            super(view);

            txtFrom = (TextView)view.findViewById(R.id.txtFrom);
            txtTo = (TextView)view.findViewById(R.id.txtTo);
            txtPrice = (TextView)view.findViewById(R.id.txtPrice);
            txtDate = (TextView)view.findViewById(R.id.txtDate);
            txtSeatAvailable = (TextView)view.findViewById(R.id.txtSeatAvailable);
            imgViewPager = (ViewPager) view.findViewById(R.id.imgViewPager);

            imgAccept = (ImageView)view.findViewById(R.id.imgAccept);
            imgDelete = (ImageView)view.findViewById(R.id.imgDelete);

            llAcceptRequest = (LinearLayout)view.findViewById(R.id.llAcceptRequest);
            llDeleteRequest = (LinearLayout)view.findViewById(R.id.llDeleteRequest);
            llMain = (LinearLayout)view.findViewById(R.id.llMain);
            llRequestDetails = (LinearLayout)view.findViewById(R.id.llRequestDetails);
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

    public PassengerSearchTripAdapter getPassengerSearchTripAdapter() {
        return passengerSearchTripAdapter;
    }

    public void setPassengerSearchTripAdapter(PassengerSearchTripAdapter passengerSearchTripAdapter) {
        this.passengerSearchTripAdapter = passengerSearchTripAdapter;
    }
}
