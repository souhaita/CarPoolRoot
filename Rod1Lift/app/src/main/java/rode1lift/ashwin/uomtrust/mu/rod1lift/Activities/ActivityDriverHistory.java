package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverFetchHistory;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.ConnectivityHelper;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityDriverHistory extends Activity {

    private LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view_history_main);

        TextView txtMenuHeader = (TextView)findViewById(R.id.txtMenuHeader);
        txtMenuHeader.setText(getString(R.string.activity_driver_history_header));

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView txtDone = (TextView)findViewById(R.id.txtDone);
        txtDone.setVisibility(View.INVISIBLE);

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleViewHistory);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        if(ConnectivityHelper.isConnected(ActivityDriverHistory.this)) {
            new AsyncDriverFetchHistory(ActivityDriverHistory.this, recyclerView).execute();
        }
        else{
            Utils.alertError(ActivityDriverHistory.this, getString(R.string.error_no_connection));
        }
    }

    protected void onPause(){
        super.onPause();
        llMain.setAnimation(null);
    }

    protected  void onResume(){
        super.onResume();
        llMain = (LinearLayout)findViewById(R.id.llMain);
        Utils.animateLayout(llMain);
    }
}
