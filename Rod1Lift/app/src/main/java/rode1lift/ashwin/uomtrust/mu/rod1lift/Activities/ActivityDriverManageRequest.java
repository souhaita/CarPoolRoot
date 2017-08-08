package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverFetchRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.ConnectivityHelper;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityDriverManageRequest extends Activity {

    private RequestDTO requestDTO;
    private LinearLayout llMainProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_manage_request_main);

        TextView txtMenuHeader = (TextView)findViewById(R.id.txtMenuHeader);
        txtMenuHeader.setText(getString(R.string.activity_complete_driver_manage_request_header));

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView txtDone = (TextView)findViewById(R.id.txtDone);
        txtDone.setVisibility(View.INVISIBLE);

        requestDTO = new RequestDTO();
        requestDTO.setRequestStatus(RequestStatus.REQUEST_PENDING);

        int userId = Utils.getCurrentAccount(ActivityDriverManageRequest.this);
        Integer carId = new CarDAO(ActivityDriverManageRequest.this).getCarByAccountID(userId).getCarId();

        if(carId != null)
            requestDTO.setCarId(carId);

        final Spinner spinnerRequestStatus = (Spinner)findViewById(R.id.spinnerRequestStatus);
        final ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.driver_manage_request_arrays,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRequestStatus.setAdapter(adapter);

        final FloatingActionMenu fabMenu = (FloatingActionMenu)findViewById(R.id.fabMenu);
        fabMenu.setClosedOnTouchOutside(true);

        FloatingActionButton fabPending = (FloatingActionButton) findViewById(R.id.fabPending);
        fabPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerRequestStatus.setSelection(0);
                fabMenu.close(true);
            }
        });

        FloatingActionButton fabClientAccepted = (FloatingActionButton) findViewById(R.id.fabClientAccepted);
        fabClientAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerRequestStatus.setSelection(1);
                fabMenu.close(true);
            }
        });

        spinnerRequestStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position){
                    case 0:
                        requestDTO.setRequestStatus(RequestStatus.REQUEST_PENDING);
                        break;

                    case 1:
                        requestDTO.setRequestStatus(RequestStatus.PASSENGER_ACCEPTED);
                        break;
                }

                if(ConnectivityHelper.isConnected(ActivityDriverManageRequest.this)) {
                    new AsyncDriverFetchRequest(ActivityDriverManageRequest.this).execute(requestDTO);
                }
                else{
                    Utils.alertError(ActivityDriverManageRequest.this, getString(R.string.error_no_connection));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONSTANT.MANAGE_TRIP_ACTIVITY_DRIVER_REQUEST_PENDING) {
            if(ConnectivityHelper.isConnected(ActivityDriverManageRequest.this)) {
                new AsyncDriverFetchRequest(ActivityDriverManageRequest.this).execute(requestDTO);
            }
            else{
                Utils.alertError(ActivityDriverManageRequest.this, getString(R.string.error_no_connection));
            }
        }

    }

    protected void onPause(){
        super.onPause();
        llMainProfile.setAnimation(null);
    }

    protected  void onResume(){
        super.onResume();
        llMainProfile = (LinearLayout)findViewById(R.id.llMainProfile);
        Utils.animateLayout(llMainProfile);
    }
}
