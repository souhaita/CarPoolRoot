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
import android.widget.TextView;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.Const;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class PickerActivityTripPrice extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_trip_price);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        SharedPreferences prefs = getSharedPreferences(Const.appName, MODE_PRIVATE);
        Integer userId = prefs.getInt(Const.currentAccountId, -1);

        final EditText eTxtTripPrice =(EditText)findViewById(R.id.eTxtTripPrice);

        eTxtTripPrice.setText("");

        TextView txtDone = (TextView)findViewById(R.id.txtDone);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(eTxtTripPrice);
            }
        });

        ImageButton imgClear = (ImageButton) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTxtTripPrice.setText("");
            }
        });

        eTxtTripPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setData(eTxtTripPrice);
                }
                return true;
            }
        });
    }

    private void setData(EditText eTxtFullName){
        if(TextUtils.isEmpty(eTxtFullName.getText().toString())){
            String message = "Trip price can not be null";
            Utils.showToast(PickerActivityTripPrice.this, message);

            Utils.vibrate(PickerActivityTripPrice.this);
        }
        else{

            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.animator.slide_in_up, R.animator.slide_out_up);
    }
}
