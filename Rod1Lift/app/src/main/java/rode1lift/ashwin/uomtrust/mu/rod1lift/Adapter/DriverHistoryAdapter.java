package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityCreateTrip;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;

/**
 * Created by vgobin on 18-Jul-17.
 */

public class DriverHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RequestObject> requestObjectList;
    private Context context;

    public DriverHistoryAdapter(Context context, List<RequestObject> requestObjectList){
        this.requestObjectList = requestObjectList;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_driver_view_history_content, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        HistoryViewHolder view = (HistoryViewHolder)holder;
        final RequestDTO requestDTO = requestObjectList.get(i).getRequestDTO();

        view.txtFrom.setText(requestDTO.getPlaceFrom());
        view.txtTo.setText(requestDTO.getPlaceTo());

        String seats = null;
        if(requestDTO.getSeatAvailable() >1) {
            seats = context.getString(R.string.driver_request_adapter_seats_left);
            view.txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString() +" "+seats);
        }
        else if (requestDTO.getSeatAvailable() == 1) {
            seats = context.getString(R.string.driver_request_adapter_seat_left);
            view.txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString() +" "+seats);
        }
        else {
            seats = context.getString(R.string.driver_request_adapter_no_seat_left);
            view.txtSeatAvailable.setText(seats);
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

        view.txtPrice.setText("Rs"+totalSPrice+" ("+unitSPrice+"/p)");

        SimpleDateFormat format = new SimpleDateFormat("dd MMM HH:mm");
        String date = null;
        try {
            date = format.format(requestDTO.getEvenDate());
        }
        catch (Exception e){

        }

        view.txtDate.setText(date);

        int[] androidColors = context.getResources().getIntArray(R.array.randomColors);
        int color = androidColors[new Random().nextInt(androidColors.length)];
        view.llHeaderDetails.setBackgroundColor(color);

        List<AccountDTO> accountDTOList = requestObjectList.get(i).getAccountDTOList();
        if(accountDTOList != null && accountDTOList.size() >0) {
            DriverHistoryGridAdapter d = new DriverHistoryGridAdapter(context, accountDTOList);
            view.gvDriverHistory.setAdapter(d);
        }

        view.fabCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityCreateTrip.class);
                intent.putExtra(CONSTANT.CREATE_TRIP_FROM, requestDTO.getPlaceFrom());
                intent.putExtra(CONSTANT.CREATE_TRIP_TO, requestDTO.getPlaceTo());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(requestObjectList != null && requestObjectList.size() >0)
            return requestObjectList.size();
        return 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFrom, txtDate, txtSeatAvailable, txtTo, txtPrice;
        public GridView gvDriverHistory;
        public LinearLayout llHeaderDetails;
        public FloatingActionButton fabCreateTrip;

        public HistoryViewHolder(View view) {
            super(view);

            txtFrom = (TextView) view.findViewById(R.id.txtFrom);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtSeatAvailable = (TextView) view.findViewById(R.id.txtSeatAvailable);
            txtTo = (TextView) view.findViewById(R.id.txtTo);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            gvDriverHistory = (GridView) view.findViewById(R.id.gvDriverHistory);
            llHeaderDetails = (LinearLayout) view.findViewById(R.id.llHeaderDetails);
            fabCreateTrip = (FloatingActionButton) view.findViewById(R.id.fabCreateTrip);

        }
    }
}
