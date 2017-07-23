
package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncPassengerFetchRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivitySearchTripResults extends Activity {

    private RecyclerView rcTripResults;
    private RequestDTO requestDTO;
    private LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip_results);

        llMain = (LinearLayout)findViewById(R.id.llMain);

        TextView txtMenuHeader = (TextView)findViewById(R.id.txtMenuHeader);
        txtMenuHeader.setText(getString(R.string.activity_search_trip_results_header));

        rcTripResults = (RecyclerView)findViewById(R.id.rcTripResults);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ActivitySearchTripResults.this, LinearLayoutManager.VERTICAL, false);
        rcTripResults.setLayoutManager(mLayoutManager);

        requestDTO = new RequestDTO();
        requestDTO.setRequestStatus(RequestStatus.REQUEST_PENDING);
        new AsyncPassengerFetchRequest(ActivitySearchTripResults.this, rcTripResults).execute(requestDTO);

        TextView txtDone = (TextView)findViewById(R.id.txtDone);
        txtDone.setVisibility(View.INVISIBLE);

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONSTANT.SEARCH_TRIP_ACTIVITY) {
            new AsyncPassengerFetchRequest(ActivitySearchTripResults.this, rcTripResults).execute(requestDTO);
        }

    }

    @Override
    public void onBackPressed() {

    }

    protected void onPause(){
        super.onPause();
        llMain.setAnimation(null);
    }

    protected void onResume(){
        super.onResume();
        Utils.animateLayout(llMain);
    }
}
