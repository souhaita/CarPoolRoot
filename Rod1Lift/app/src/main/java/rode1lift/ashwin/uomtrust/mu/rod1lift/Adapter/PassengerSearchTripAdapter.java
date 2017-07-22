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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityCreateTrip;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverDeleteRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final viewHolder view = (viewHolder)holder;
        RequestDTO requestDTO = requestObjectList.get(position).getRequestDTO();

        view.txtFrom.setText(requestDTO.getPlaceFrom());
        view.txtTo.setText(requestDTO.getPlaceTo());
        view.txtPrice.setText(requestDTO.getPrice().toString());
        view.txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString());

        SimpleDateFormat format = new SimpleDateFormat("dd MMM HH:mm");
        String date = null;
        try {
            date = format.format(requestDTO.getEvenDate());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        view.txtDate.setText(date);

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
        view.imgViewPager.setAdapter(photoViewPagerAdapter);

        view.imgViewPager.setOnTouchListener(new View.OnTouchListener() {
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

                    view.imgViewPager.setCurrentItem(i);
                    i--;

                    handler.postDelayed(this, 5000);
                }
            };
            handler.postDelayed(runnable, 0);
        }

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

        public viewHolder(View view) {
            super(view);

            txtFrom = (TextView)view.findViewById(R.id.txtFrom);
            txtTo = (TextView)view.findViewById(R.id.txtTo);
            txtPrice = (TextView)view.findViewById(R.id.txtPrice);
            txtDate = (TextView)view.findViewById(R.id.txtDate);
            txtSeatAvailable = (TextView)view.findViewById(R.id.txtSeatAvailable);
            imgViewPager = (ViewPager) view.findViewById(R.id.imgViewPager);
        }
    }
}
