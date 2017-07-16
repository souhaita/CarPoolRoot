package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.UserDetailsGridAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverFetchRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityDriverViewUserDetails extends Activity {

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
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
            date = format.format(requestDTO.getEvenDate());
        }
        catch (Exception e){

        }
        txtDate.setText(date);

        String seats = getString(R.string.driver_request_adapter_seats_left);
        TextView txtSeatAvailable = (TextView)findViewById(R.id.txtSeatAvailable);
        txtSeatAvailable.setText(requestDTO.getSeatAvailable().toString() +" "+seats);

        TextView txtPrice = (TextView)findViewById(R.id.txtPrice);
        txtPrice.setText("Rs "+requestDTO.getPrice().toString());

        UserDetailsGridAdapter adapter = new UserDetailsGridAdapter(ActivityDriverViewUserDetails.this,accountDTOList);
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
