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

        userId = Utils.getCurrentAccount(PickerActivityProfileName.this);

        final EditText eTxtFullName =(EditText)findViewById(R.id.eTxtFullName);

        final AccountDTO accountDTO = new AccountDAO(PickerActivityProfileName.this).getAccountById(userId);
        eTxtFullName.setText(accountDTO.getFullName());

        TextView txtDone = (TextView)findViewById(R.id.txtDone);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(eTxtFullName, accountDTO);
            }
        });

        ImageButton imgClear = (ImageButton) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTxtFullName.setText("");
            }
        });

        eTxtFullName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setData(eTxtFullName, accountDTO);
                }
                return true;
            }
        });
    }

    private void setData(EditText eTxtFullName, AccountDTO accountDTO){
        if(TextUtils.isEmpty(eTxtFullName.getText().toString())){
            String message = getString(R.string.activity_profile_name_error);
            Utils.alertError(PickerActivityProfileName.this, message);

            Utils.vibrate(PickerActivityProfileName.this);
        }
        else{
            accountDTO.setFullName(eTxtFullName.getText().toString());

            new AccountDAO(PickerActivityProfileName.this).saveOrUpdateAccount(accountDTO);

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
