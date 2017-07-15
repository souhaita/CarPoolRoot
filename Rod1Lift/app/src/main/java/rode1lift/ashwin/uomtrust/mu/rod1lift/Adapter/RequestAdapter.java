package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityCarMake;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityCarPlateNum;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityCarSeats;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityProfileName;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverDeleteRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.ViewType;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_1;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_2;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_3;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_4;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_MAKE;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_PASSENGER;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_PLATE_NUM;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_PIC;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class RequestAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    private Context context;
    private List<RequestDTO> requestDTOList;

    boolean confirmDelete = false;

    private RequestAdapter RequestAdapter = this;

    public RequestAdapter(Context context, List<RequestDTO> requestDTOList){
        this.context = context;
        this.requestDTOList = requestDTOList;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(requestDTOList != null )
            return requestDTOList.size();

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

        view = inflater.inflate(R.layout.activity_driver_manage_request_content, null);

        TextView txtFrom = (TextView)view.findViewById(R.id.txtFrom);
        txtFrom.setText(requestDTOList.get(i).getPlaceFrom());

        TextView txtTo = (TextView)view.findViewById(R.id.txtTo);
        txtTo.setText(requestDTOList.get(i).getPlaceTo());

        TextView txtDate = (TextView)view.findViewById(R.id.txtDate);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM HH:mm");
        String date = null;
        try {
            date = format.format(requestDTOList.get(i).getEvenDate());
        }
        catch (Exception e){

        }
        txtDate.setText(date);

        String seats = context.getString(R.string.driver_request_adapter_seats_left);
        TextView txtSeatAvailable = (TextView)view.findViewById(R.id.txtSeatAvailable);
        txtSeatAvailable.setText(requestDTOList.get(i).getSeatAvailable().toString() +" "+seats);

        TextView txtPrice = (TextView)view.findViewById(R.id.txtPrice);
        txtPrice.setText("Rs "+requestDTOList.get(i).getPrice().toString());

        final ViewPager imgCarPic = (ViewPager) view.findViewById(R.id.imgCarPic);

        final PhotoViewPagerAdapter photoViewPagerAdapter = new PhotoViewPagerAdapter(context);
        imgCarPic.setAdapter(photoViewPagerAdapter);

        imgCarPic.setOnTouchListener(new View.OnTouchListener() {
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

            }
        });

        final ImageView imgDelete = (ImageView)view.findViewById(R.id.imgDelete);

        final LinearLayout llDeleteRequest = (LinearLayout)view.findViewById(R.id.llDeleteRequest);
        llDeleteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!confirmDelete){
                    imgDelete.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_bin_open_red));
                    confirmDelete = true;
                }
                else{
                    new AsyncDriverDeleteRequest(context, RequestAdapter, requestDTOList).execute(requestDTOList.get(i));
                    confirmDelete = false;
                }
            }
        });


        llRequestDetails.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if(llDeleteRequest.isShown())
                            imgDelete.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_bin_close_red));
                            confirmDelete = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return true;
            }
        });


        return view;
    }

    public List<RequestDTO> getRequestDTOList() {
        return requestDTOList;
    }

    public void setRequestDTOList(List<RequestDTO> requestDTOList) {
        this.requestDTOList = requestDTOList;
    }
}
