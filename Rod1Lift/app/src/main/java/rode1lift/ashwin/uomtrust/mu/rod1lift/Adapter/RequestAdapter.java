package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityCarMake;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityCarPlateNum;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityCarSeats;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityProfileName;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = inflater.inflate(R.layout.activity_driver_manage_request_content, null);

        TextView txtFrom = (TextView)view.findViewById(R.id.txtFrom);
        txtFrom.setText(requestDTOList.get(i).getPlaceFrom());

        TextView txtTo = (TextView)view.findViewById(R.id.txtTo);
        txtTo.setText(requestDTOList.get(i).getPlaceTo());

        TextView txtDate = (TextView)view.findViewById(R.id.txtDate);
        txtDate.setText(requestDTOList.get(i).getEvenDate().toString());

       /* TextView txtPrice = (TextView)view.findViewById(R.id.txtPrice);
        txtPrice.setText(requestDTOList.get(i).getPrice().toString());

        TextView txtSeatAvailable = (TextView)view.findViewById(R.id.txtSeatAvailable);
        txtSeatAvailable.setText(requestDTOList.get(i).getSeatAvailable().toString());*/


        LinearLayout llMain = (LinearLayout)view.findViewById(R.id.llMain);
        llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
