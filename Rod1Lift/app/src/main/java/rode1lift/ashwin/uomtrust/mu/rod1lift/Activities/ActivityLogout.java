package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.FacebookSdk;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityLogout extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_logout);

        TextView txtYes = (TextView)findViewById(R.id.txtYes);
        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.disconnectFromFacebook();

                Utils.clearSharedPref(ActivityLogout.this);

                Intent intent = new Intent(ActivityLogout.this,ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        });

        TextView txtNo = (TextView)findViewById(R.id.txtNo);
        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
