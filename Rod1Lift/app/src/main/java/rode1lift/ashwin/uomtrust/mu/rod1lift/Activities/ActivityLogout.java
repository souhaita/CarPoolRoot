package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDeleteDeviceToken;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityLogout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        LinearLayout llMain = (LinearLayout)findViewById(R.id.llMain);
        Utils.animateLayout(llMain);

        new AsyncDeleteDeviceToken(ActivityLogout.this).execute();
    }
}
