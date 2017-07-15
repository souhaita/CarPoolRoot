package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.content.Context;
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

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverDeleteRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class DriverRequestAdapterPending extends BaseAdapter {

    private static LayoutInflater inflater = null;

    private Context context;
    private List<RequestObject> requestObjectList;

    boolean confirmDelete = false;

    private DriverRequestAdapterPending DriverRequestAdapterPending = this;

    public DriverRequestAdapterPending(Context context, List<RequestObject> requestObjectList){
        this.context = context;
        this.requestObjectList = requestObjectList;

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
        view = inflater.inflate(R.layout.activity_driver_manage_request_pending_content, null);

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

        PhotoViewPagerAdapter photoViewPagerAdapter = new PhotoViewPagerAdapter(context, profilePic, true);
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
                    new AsyncDriverDeleteRequest(context, DriverRequestAdapterPending, requestObjectList).execute(requestDTO);
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

    public List<RequestObject> getRequestObjectList() {
        return requestObjectList;
    }

    public void setRequestObjectList(List<RequestObject> requestObjectList) {
        this.requestObjectList = requestObjectList;
    }
}
