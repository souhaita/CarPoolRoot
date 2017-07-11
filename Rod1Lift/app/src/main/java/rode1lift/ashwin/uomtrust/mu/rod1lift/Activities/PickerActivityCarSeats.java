package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;


public class PickerActivityCarSeats extends Activity {

    private CarDTO carDTO;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_car_seats);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final NumberPicker pickerNumPassenger = (NumberPicker) findViewById(R.id.pickerNumPassenger);

        SharedPreferences prefs = getSharedPreferences(CONSTANT.APP_NAME, MODE_PRIVATE);
        userId = prefs.getInt(CONSTANT.CURRENT_ACCOUNT_ID, -1);
        carDTO = new CarDAO(PickerActivityCarSeats.this).getCarByAccountID(userId);

        pickerNumPassenger.setMinValue(1);
        pickerNumPassenger.setMaxValue(5);
        pickerNumPassenger.setValue(1);
        pickerNumPassenger.setWrapSelectorWheel(true);

        pickerNumPassenger.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

            }
        });


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

                if(carDTO != null && carDTO.getCarId() != null){
                    carDTO.setNumOfPassenger(pickerNumPassenger.getValue());
                }
                else{
                    carDTO = new CarDTO();
                    carDTO.setCarId(-1);
                    carDTO.setAccountId(userId);
                    carDTO.setNumOfPassenger(pickerNumPassenger.getValue());
                }

                new CarDAO(PickerActivityCarSeats.this).saveOrUpdateCar(carDTO);

                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        if(carDTO != null && carDTO.getCarId() != null){
            pickerNumPassenger.setValue(carDTO.getNumOfPassenger());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.animator.slide_in_up, R.animator.slide_out_up);
    }
}
