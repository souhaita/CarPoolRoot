package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.UserDetailsGridAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityDriverTripDetails extends Activity {

    private LinearLayout llMainDriverViewUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view_user_details_main);

        TextView txtMenuHeader = (TextView)findViewById(R.id.txtMenuHeader);
        txtMenuHeader.setText(getString(R.string.activity_driver_view_user_details));

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView txtDone = (TextView)findViewById(R.id.txtDone);
        txtDone.setVisibility(View.INVISIBLE);

        RequestObject requestObject = (RequestObject) getIntent().getSerializableExtra(CONSTANT.REQUEST_OBJECT);
        List<AccountDTO> accountDTOList = requestObject.getAccountDTOList();

        final RequestDTO requestDTO = requestObject.getRequestDTO();

        TextView txtFrom = (TextView)findViewById(R.id.txtFrom);
        txtFrom.setText(requestDTO.getPlaceFrom());

        TextView txtTo = (TextView)findViewById(R.id.txtTo);
        txtTo.setText(requestDTO.getPlaceTo());

        TextView txtDate = (TextView)findViewById(R.id.txtDate);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM HH:mm");
        String date = null;
        try {
            date = format.format(requestDTO.getEventDate());
        }
        catch (Exception e){

        }
        txtDate.setText(date);

        String seats;

        TextView txtSeatAvailable = (TextView)findViewById(R.id.txtSeatAvailable);

        if(requestDTO.getSeatAvailable() >1) {
            seats = getString(R.string.driver_request_adapter_seats_left);
            txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString() +" "+seats);
        }
        else if (requestDTO.getSeatAvailable() == 1) {
            seats = getString(R.string.driver_request_adapter_seat_left);
            txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString() +" "+seats);
        }
        else {
            seats = getString(R.string.driver_request_adapter_no_seat_left);
            txtSeatAvailable.setText(seats);
        }

        List<ManageRequestDTO> manageRequestDTOList = requestObject.getManageRequestDTOList();

        List<AccountDTO> filteredAccountDTOList = new ArrayList<>();

        int count = 0;
        for(ManageRequestDTO m : manageRequestDTOList){
            count += m.getSeatRequested();

            innerLoop:
            for(AccountDTO a : accountDTOList){
                if(a.getAccountId() == m.getAccountId() && m.getRequestStatus() == RequestStatus.DRIVER_ACCEPTED) {
                    filteredAccountDTOList.add(a);
                    break innerLoop;
                }
            }
        }

        int unitPrice = requestDTO.getPrice();
        int totalPrice = count * unitPrice;

        String unitSPrice = String.valueOf(unitPrice);
        String totalSPrice = String.valueOf(totalPrice);

        TextView txtPrice = (TextView)findViewById(R.id.txtPrice);
        txtPrice.setText("Rs"+totalSPrice+" ("+unitSPrice+"/p)");
        
        UserDetailsGridAdapter adapter = new UserDetailsGridAdapter(ActivityDriverTripDetails.this,filteredAccountDTOList);
        GridView gridView = (GridView)findViewById(R.id.gvUserDetails);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    protected void onPause(){
        super.onPause();
        llMainDriverViewUserDetails.setAnimation(null);
    }

    protected  void onResume(){
        super.onResume();
        llMainDriverViewUserDetails = (LinearLayout)findViewById(R.id.llMainDriverViewUserDetails);
        Utils.animateLayout(llMainDriverViewUserDetails);
    }
}
