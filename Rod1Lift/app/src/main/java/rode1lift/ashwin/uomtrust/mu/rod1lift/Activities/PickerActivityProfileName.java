package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.Const;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class PickerActivityProfileName extends Activity {

    private CarDTO carDTO;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_profile_name);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        SharedPreferences prefs = getSharedPreferences(Const.appName, MODE_PRIVATE);
        userId = prefs.getInt(Const.currentAccountId, -1);

        final EditText eTxtFullName =(EditText)findViewById(R.id.eTxtFullName);

        final AccountDTO accountDTO = new AccountDAO(PickerActivityProfileName.this).getAccountById(userId);
        eTxtFullName.setText(accountDTO.getFirstName() + " "+ accountDTO.getLastName());


        ImageView close = (ImageButton) findViewById(R.id.imgClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView confirm = (ImageButton) findViewById(R.id.imgConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(eTxtFullName.getText().toString())){
                    String message = getString(R.string.activity_complete_driver_registration_plate_num_error);
                    Utils.showToast(PickerActivityProfileName.this, message);

                    Utils.vibrate(PickerActivityProfileName.this);
                }
                else{

                    String[] splited = eTxtFullName.getText().toString().split("\\s+");
                    accountDTO.setFirstName(splited[0]);
                    accountDTO.setLastName(splited[1]);

                    new AccountDAO(PickerActivityProfileName.this).saveOrUpdateAccount(accountDTO);

                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.animator.slide_in_up, R.animator.slide_out_up);
    }
}
