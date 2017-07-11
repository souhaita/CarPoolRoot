package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.RequestAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverFetchRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.RequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityDriverManageRequest extends Activity {

    //private SwipeMenuListView sLvManageRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_manage_request_main);

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

        //sLvManageRequest = (SwipeMenuListView)findViewById(R.id.sLvManageRequest);

        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setRequestStatus(RequestStatus.REQUEST_PENDING);
        //new AsyncDriverFetchRequest(ActivityDriverManageRequest.this, sLvManageRequest).execute(requestDTO);

        SwipeRevealLayout swipeRevealLayout = (SwipeRevealLayout)findViewById(R.id.sLvManageRequest);

        //https://github.com/daimajia/AndroidSwipeLayout

        //https://github.com/chthai64/SwipeRevealLayout

        new AsyncDriverFetchRequest(ActivityDriverManageRequest.this, swipeRevealLayout).execute(requestDTO);
    }
}
