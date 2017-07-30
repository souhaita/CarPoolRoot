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
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverAcceptRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverDeleteRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.ManageRequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.RequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class DriverRequestUserAcceptedAdapter extends RecyclerView.Adapter {

    private static LayoutInflater inflater = null;

    private Context context;
    private List<RequestObject> requestObjectList;

    List<Boolean> confirmDelete;
    private DriverRequestUserAcceptedAdapter DriverRequestUserAcceptedAdapter = this;

    public DriverRequestUserAcceptedAdapter(Context context, List<RequestObject> requestObjectList){
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_driver_manage_request_other_content, parent, false);
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

        String seats = context.getString(R.string.driver_request_adapter_seats_left);
        viewHolder.txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString() +" "+seats);

        int setReq = requestObjectList.get(i).getManageRequestDTOList().get(0).getSeatRequested();
        String setRequested = context.getString(R.string.driver_request_adapter_seats_requested);
        viewHolder.txtSeatRequested.setText(setReq +" "+setRequested);

        List<byte []> profilePic = new ArrayList<>();

        profilePic.add(requestObjectList.get(i).getAccountDTOList().get(0).getProfilePicture());

        PhotoViewPagerAdapter photoViewPagerAdapter = new PhotoViewPagerAdapter(context, profilePic);
        viewHolder.imgProfilePicture.setAdapter(photoViewPagerAdapter);

        viewHolder.imgProfilePicture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        viewHolder.txtPassengerFullName.setText(requestObjectList.get(i).getAccountDTOList().get(0).getFullName());

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
                    requestDTO.setManageRequestId(requestObjectList.get(i).getManageRequestDTOList().get(0).getManageRequestId());
                    new AsyncDriverDeleteRequest(context, DriverRequestUserAcceptedAdapter, requestObjectList).execute(requestDTO);
                    confirmDelete.set(i, false);
                }
            }
        });

        viewHolder.llAcceptRequest.setOnClickListener(new View.OnClickListener() {
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

                    if(r.getSeatAvailable().intValue() > 0 && serverManageRequest.getSeatRequested().intValue() <= r.getSeatAvailable().intValue()){
                        requestDTO.setManageRequestId(manageRequestId);
                        new AsyncDriverAcceptRequest(context, DriverRequestUserAcceptedAdapter, requestObjectList).execute(requestDTO);
                        confirmDelete.set(i, false);
                    }
                    else{
                        String message = context.getString(R.string.async_driver_accept_client_request_fail);
                        Utils.alertError(context, message);
                    }
                }
                else if(serverManageRequest.getSeatRequested().intValue() <= serverRequest.getSeatAvailable().intValue()){
                    requestDTO.setManageRequestId(manageRequestId);
                    new AsyncDriverAcceptRequest(context, DriverRequestUserAcceptedAdapter, requestObjectList).execute(requestDTO);
                    confirmDelete.set(i, false);
                }
                else{
                    String message = context.getString(R.string.async_driver_accept_client_request_fail);
                    Utils.alertError(context, message);
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

        final TextView txtFrom, txtTo, txtDate, txtSeatRequested, txtSeatAvailable, txtPassengerFullName;
        final ViewPager imgProfilePicture;
        final LinearLayout llRequestDetails,llDeleteRequest, llAcceptRequest, llMain;
        final ImageView imgDelete;

        ViewHolder(final View view) {
            super(view);
            txtSeatAvailable = (TextView)view.findViewById(R.id.txtSeatAvailable);
            txtFrom = (TextView)view.findViewById(R.id.txtFrom);
            txtTo = (TextView)view.findViewById(R.id.txtTo);
            txtDate = (TextView)view.findViewById(R.id.txtDate);
            txtSeatRequested = (TextView)view.findViewById(R.id.txtSeatRequested);
            txtPassengerFullName = (TextView)view.findViewById(R.id.txtPassengerFullName);
            imgProfilePicture = (ViewPager)view.findViewById(R.id.imgProfilePicture);
            llRequestDetails = (LinearLayout)view.findViewById(R.id.llRequestDetails);
            llMain = (LinearLayout)view.findViewById(R.id.llMain);
            llAcceptRequest = (LinearLayout)view.findViewById(R.id.llAcceptRequest);
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
