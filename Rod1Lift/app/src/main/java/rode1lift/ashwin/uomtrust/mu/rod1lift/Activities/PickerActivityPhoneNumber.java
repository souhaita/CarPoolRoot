package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
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

import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class PickerActivityPhoneNumber extends Activity {

    private Integer userId;
    private EditText eTxtPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_phone_number);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        userId = Utils.getCurrentAccount(PickerActivityPhoneNumber.this);

        eTxtPhoneNumber =(EditText)findViewById(R.id.eTxtPhoneNumber);

        final AccountDTO accountDTO = new AccountDAO(PickerActivityPhoneNumber.this).getAccountById(userId);
        eTxtPhoneNumber.setText(accountDTO.getPhoneNum().toString());

        TextView txtDone = (TextView)findViewById(R.id.txtDone);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(eTxtPhoneNumber, accountDTO);
            }
        });

        ImageButton imgClear = (ImageButton) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTxtPhoneNumber.setText("");
            }
        });

        eTxtPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setData(eTxtPhoneNumber, accountDTO);
                }
                return true;
            }
        });
    }

    private void setData(EditText eTxtFullName, AccountDTO accountDTO){
        if(TextUtils.isEmpty(eTxtFullName.getText().toString())){
            String message = getString(R.string.activity_create_trip_validation_contact_detail);
            Utils.alertError(PickerActivityPhoneNumber.this, message);

            Utils.vibrate(PickerActivityPhoneNumber.this);
        }
        else{
            String phoneNum = eTxtPhoneNumber.getText().toString();

            if(phoneNum.length() >=9 || phoneNum.length() <=6){
                Utils.alertError(PickerActivityPhoneNumber.this, getResources().getString(R.string.activity_create_trip_validation_contact_detail_length));
            }
            else {
                accountDTO.setPhoneNum(Integer.parseInt(phoneNum));
                new AccountDAO(PickerActivityPhoneNumber.this).saveOrUpdateAccount(accountDTO);

                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.animator.slide_in_up, R.animator.slide_out_up);
    }
}
