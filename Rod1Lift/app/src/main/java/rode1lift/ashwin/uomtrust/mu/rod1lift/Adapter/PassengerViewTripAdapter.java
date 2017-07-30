package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityPassengerViewDriverProfile;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncPassengerPayRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class PassengerViewTripAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static LayoutInflater inflater = null;

    private Context context;
    private List<RequestObject> requestObjectList;
    private PassengerViewTripAdapter passengerViewTripAdapter = this;
    private RequestStatus requestStatus = null;

    public PassengerViewTripAdapter(Context context, List<RequestObject> requestObjectList){
        this.context = context;
        this.requestObjectList = requestObjectList;
        requestStatus = requestObjectList.get(0).getManageRequestDTOList().get(0).getRequestStatus();

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;

        if(requestStatus == RequestStatus.PASSENGER_ACCEPTED)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_passenger_manage_request_other_content, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_passenger_manage_request_pending_content, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final viewHolder viewHolder = (viewHolder)holder;
        final RequestDTO requestDTO = requestObjectList.get(position).getRequestDTO();

        viewHolder.txtFrom.setText(requestDTO.getPlaceFrom());
        viewHolder.txtTo.setText(requestDTO.getPlaceTo());
        viewHolder.txtPrice.setText("Rs"+requestDTO.getPrice().toString());
        String seat = context.getString(R.string.passenger_view_history_seat);
        viewHolder.txtSeatRequested.setText(requestDTO.getSeatRequested().toString()+" "+seat);

        SimpleDateFormat format = new SimpleDateFormat("dd MMM HH:mm");
        String date = null;
        try {
            date = format.format(requestDTO.getEventDate());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        viewHolder.txtDate.setText(date);

        List<byte []> images = new ArrayList<>();

        AccountDTO accountDTO = requestObjectList.get(position).getAccountDTOList().get(0);
        images.add(accountDTO.getProfilePicture());

        viewHolder.txtDriverFullName.setText(accountDTO.getFullName());

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

        if(requestStatus == RequestStatus.DRIVER_ACCEPTED) {
            viewHolder.llPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ManageRequestDTO manageRequestDTO = requestObjectList.get(position).getManageRequestDTOList().get(0);
                    showPaymentMenu(manageRequestDTO);
                }
            });
        }

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
                ((Activity)context).startActivityForResult(intent, CONSTANT.PASSENGER_VIEW_TRIP_ACTIVITY);
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
        public TextView txtFrom, txtTo, txtDriverFullName, txtPrice, txtDate, txtSeatRequested;
        public ViewPager imgViewPager;
        public ImageView imgPayment;
        public LinearLayout llPayment, llMain, llRequestDetails;

        public viewHolder(View view) {
            super(view);

            txtFrom = (TextView)view.findViewById(R.id.txtFrom);
            txtTo = (TextView)view.findViewById(R.id.txtTo);
            txtPrice = (TextView)view.findViewById(R.id.txtPrice);
            txtDate = (TextView)view.findViewById(R.id.txtDate);
            txtSeatRequested = (TextView)view.findViewById(R.id.txtSeatRequested);
            imgViewPager = (ViewPager) view.findViewById(R.id.imgViewPager);
            txtDriverFullName = (TextView)view.findViewById(R.id.txtDriverFullName);


            if(requestStatus == RequestStatus.DRIVER_ACCEPTED) {
                imgPayment = (ImageView) view.findViewById(R.id.imgPayment);
                llPayment = (LinearLayout) view.findViewById(R.id.llPayment);
            }

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

    private void showPaymentMenu(final ManageRequestDTO manageRequestDTO) {
        final Dialog menuDialog = new Dialog(context, R.style.WalkthroughTheme);
        menuDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menuDialog.setContentView(R.layout.dilaogue_payment);
        menuDialog.setCanceledOnTouchOutside(true);
        menuDialog.setCancelable(true);

        TextView txtPayPal = (TextView) menuDialog.findViewById(R.id.txtPayPal);
        TextView txtCash = (TextView) menuDialog.findViewById(R.id.txtCash);

        txtPayPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        txtCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncPassengerPayRequest(menuDialog, context, passengerViewTripAdapter, requestObjectList).execute(manageRequestDTO);
            }
        });

        menuDialog.show();
    }
}
