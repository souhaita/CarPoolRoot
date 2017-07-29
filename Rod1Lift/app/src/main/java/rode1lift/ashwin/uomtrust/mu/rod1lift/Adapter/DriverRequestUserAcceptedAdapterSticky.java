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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

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

public class DriverRequestUserAcceptedAdapterSticky extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter {

    private static LayoutInflater inflater = null;

    private Context context;
    private List<RequestObject> requestObjectList;

    public DriverRequestUserAcceptedAdapterSticky(Context context, List<RequestObject> requestObjectList){
        this.context = context;
        this.requestObjectList = requestObjectList;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_driver_manage_request_other_content, parent, false);
        return new ContentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        ContentViewHolder contentViewHolder = (ContentViewHolder)holder;
        List<byte []> profilePic = new ArrayList<>();
        if(requestObjectList.get(i).getAccountDTOList() != null && requestObjectList.get(i).getAccountDTOList().size() >0){
            for(int x = 0; x < requestObjectList.get(i).getAccountDTOList().size(); x++){
                if(requestObjectList.get(i).getAccountDTOList().get(x).getProfilePicture() != null){
                    profilePic.add(requestObjectList.get(i).getAccountDTOList().get(x).getProfilePicture());
                }
            }
        }

        PhotoViewPagerAdapter photoViewPagerAdapter = new PhotoViewPagerAdapter(context, profilePic);
        contentViewHolder.imgProfilePicture.setAdapter(photoViewPagerAdapter);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_header, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderViewHolder header = (HeaderViewHolder)holder;

        final RequestDTO requestDTO = requestObjectList.get(position).getRequestDTO();

        header.txtFrom.setText(requestDTO.getPlaceFrom());

        header.txtTo.setText(requestDTO.getPlaceTo());

        SimpleDateFormat format = new SimpleDateFormat("dd MMM HH:mm");
        String date = null;
        try {
            date = format.format(requestDTO.getEvenDate());
        }
        catch (Exception e){

        }
        header.txtDate.setText(date);

        String seats = context.getString(R.string.driver_request_adapter_seats_left);
        header.txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString() +" "+seats);

        header.txtPrice.setText("Rs "+requestDTO.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        if(requestObjectList != null )
            return requestObjectList.size();
        return 0;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        final TextView txtFrom, txtTo, txtDate, txtPrice, txtSeatAvailable;
        HeaderViewHolder(final View view) {
            super(view);
            txtFrom = (TextView)view.findViewById(R.id.txtFrom);
            txtTo = (TextView)view.findViewById(R.id.txtTo);
            txtDate = (TextView)view.findViewById(R.id.txtDate);
            txtSeatAvailable = (TextView)view.findViewById(R.id.txtSeatAvailable);
            txtPrice = (TextView)view.findViewById(R.id.txtPrice);
        }
    }

    private class ContentViewHolder extends RecyclerView.ViewHolder {
        ViewPager imgProfilePicture;
        ContentViewHolder(final View v) {
            super(v);
            imgProfilePicture = (ViewPager)v.findViewById(R.id.imgProfilePicture);
        }
    }

    public List<RequestObject> getRequestObjectList() {
        return requestObjectList;
    }

    public void setRequestObjectList(List<RequestObject> requestObjectList) {
        this.requestObjectList = requestObjectList;
    }
}
