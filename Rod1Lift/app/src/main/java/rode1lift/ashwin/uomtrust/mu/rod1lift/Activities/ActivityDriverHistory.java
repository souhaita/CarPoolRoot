package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.HistoryAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverFetchHistory;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;


public class ActivityDriverHistory extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view_history_main);

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleViewHistory);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        List<RequestObject> requestObjectListView = new ArrayList<>();
        try {
            requestObjectListView = new AsyncDriverFetchHistory(ActivityDriverHistory.this).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        HistoryAdapter historyAdapter = new HistoryAdapter(ActivityDriverHistory.this, requestObjectListView);

        recyclerView.setAdapter(historyAdapter);

    }
}
