package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by vgobin on 18-Jul-17.
 */

public class PassengerHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RequestObject> requestObjectList;
    private Context context;

    public PassengerHistoryAdapter(Context context, List<RequestObject> requestObjectList){
        this.requestObjectList = requestObjectList;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_passenger_view_history_content, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        HistoryViewHolder view = (HistoryViewHolder)holder;
        final RequestDTO requestDTO = requestObjectList.get(i).getRequestDTO();

        view.txtFrom.setText(requestDTO.getPlaceFrom());
        view.txtTo.setText(requestDTO.getPlaceTo());

        String seats = context.getString(R.string.passenger_view_history_seat);

        view.txtSeatRequested.setText(requestDTO.getSeatRequested().toString() +" "+seats);

        view.txtPrice.setText("Rs"+requestDTO.getPrice().toString());

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

        CarDTO carDTO = requestObjectList.get(i).getCarDTO().get(0);
        List<byte []> carImages = new ArrayList<>();

        if(carDTO.getPicture1() != null)
            carImages.add(carDTO.getPicture1());

        if(carDTO.getPicture2() != null)
            carImages.add(carDTO.getPicture2());

        if(carDTO.getPicture3() != null)
            carImages.add(carDTO.getPicture3());

        if(carDTO.getPicture4() != null)
            carImages.add(carDTO.getPicture4());

        if(carImages != null && carImages.size() >0) {
            PassengerHistoryGridAdapter d = new PassengerHistoryGridAdapter(context, carImages);
            view.gvPassengerHistory.setAdapter(d);
        }

        AccountDTO accountDTO = requestObjectList.get(i).getAccountDTOList().get(0);
        view.imgProfilePic.setImageBitmap(Utils.convertBlobToBitmap(accountDTO.getProfilePicture()));
    }

    @Override
    public int getItemCount() {
        if(requestObjectList != null && requestObjectList.size() >0)
            return requestObjectList.size();
        return 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFrom, txtDate, txtSeatRequested, txtTo, txtPrice;
        public GridView gvPassengerHistory;
        public LinearLayout llHeaderDetails;
        public ImageView imgProfilePic;

        public HistoryViewHolder(View view) {
            super(view);

            txtFrom = (TextView) view.findViewById(R.id.txtFrom);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtSeatRequested = (TextView) view.findViewById(R.id.txtSeatRequested);
            txtTo = (TextView) view.findViewById(R.id.txtTo);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            gvPassengerHistory = (GridView) view.findViewById(R.id.gvPassengerHistory);
            llHeaderDetails = (LinearLayout) view.findViewById(R.id.llHeaderDetails);
            imgProfilePic = (ImageView) view.findViewById(R.id.imgProfilePic);
        }
    }
}
