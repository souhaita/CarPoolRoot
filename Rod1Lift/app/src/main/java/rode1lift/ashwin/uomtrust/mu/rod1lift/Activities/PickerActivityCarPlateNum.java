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
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class PickerActivityCarPlateNum extends Activity {

    private CarDTO carDTO;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_car_plate_num);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        SharedPreferences prefs = getSharedPreferences(Const.appName, MODE_PRIVATE);
        userId = prefs.getInt(Const.currentAccountId, -1);
        carDTO = new CarDAO(PickerActivityCarPlateNum.this).getCarByAccountID(userId);

        final EditText eTxtPlateNum =(EditText)findViewById(R.id.eTxtPlateNum);
        TextView txtDone = (TextView)findViewById(R.id.txtDone);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValue(eTxtPlateNum);
            }
        });

        ImageButton imgClear = (ImageButton) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTxtPlateNum.setText("");
            }
        });

        eTxtPlateNum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setValue(eTxtPlateNum);
                }
                return true;
            }
        });

        if(carDTO != null && carDTO.getPlateNum() != null)
            eTxtPlateNum.setText(carDTO.getPlateNum());
    }

    private void setValue(EditText eTxtPlateNum){
        if(TextUtils.isEmpty(eTxtPlateNum.getText().toString())){
            String message = getString(R.string.activity_complete_driver_registration_plate_num_error);
            Utils.showToast(PickerActivityCarPlateNum.this, message);

            Utils.vibrate(PickerActivityCarPlateNum.this);
        }
        else {
            if (carDTO != null && carDTO.getCarId() != null) {
                carDTO.setPlateNum(eTxtPlateNum.getText().toString());
            } else {
                carDTO = new CarDTO();
                carDTO.setCarId(-1);
                carDTO.setAccountId(userId);
                carDTO.setPlateNum(eTxtPlateNum.getText().toString());
            }

            new CarDAO(PickerActivityCarPlateNum.this).saveOrUpdateCar(carDTO);

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
