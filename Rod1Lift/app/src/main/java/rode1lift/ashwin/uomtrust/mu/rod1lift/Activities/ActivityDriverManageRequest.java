package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityDriverManageRequest extends Activity {

    private ListView listView;
    private RequestDTO requestDTO;

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
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        requestDTO = new RequestDTO();
        requestDTO.setRequestStatus(RequestStatus.REQUEST_PENDING);

        listView = (ListView)findViewById(R.id.sLvManageRequest);

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

        FloatingActionButton fabCarFull = (FloatingActionButton) findViewById(R.id.fabCarFull);
        fabCarFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerRequestStatus.setSelection(1);
                fabMenu.close(true);
            }
        });

        FloatingActionButton fabClientAccepted = (FloatingActionButton) findViewById(R.id.fabClientAccepted);
        fabClientAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerRequestStatus.setSelection(2);
                fabMenu.close(true);
            }
        });

        FloatingActionButton fabDriverAccepted = (FloatingActionButton) findViewById(R.id.fabDriverAccepted);
        fabDriverAccepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerRequestStatus.setSelection(3);
                fabMenu.close(true);
            }
        });

        spinnerRequestStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                new AsyncDriverFetchRequest(ActivityDriverManageRequest.this, listView).execute(requestDTO);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        LinearLayout llMainProfile = (LinearLayout)findViewById(R.id.llMainProfile);
        Utils.animateLayout(llMainProfile);
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONSTANT.MANAGE_TRIP_ACTIVITY_DRIVER_REQUEST_PENDING) {
            new AsyncDriverFetchRequest(ActivityDriverManageRequest.this, listView).execute(requestDTO);
        }

    }
}
