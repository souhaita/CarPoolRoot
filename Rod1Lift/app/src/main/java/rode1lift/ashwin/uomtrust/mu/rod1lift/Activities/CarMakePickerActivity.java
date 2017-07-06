package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;

import rode1lift.ashwin.uomtrust.mu.rod1lift.R;


public class CarMakePickerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_car_details);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final NumberPicker pickerMake = (NumberPicker) findViewById(R.id.pickerMake);
        final String[] arrayUnits =  getResources().getStringArray(R.array.carMake);

        pickerMake.setMinValue(0);
        pickerMake.setMaxValue(arrayUnits.length-1);
        pickerMake.setDisplayedValues(arrayUnits);
        pickerMake.setValue(0);
        pickerMake.setWrapSelectorWheel(true);


        String[] arrayModel;
        final NumberPicker pickerModel = (NumberPicker) findViewById(R.id.pickerModel);
        arrayModel = getResources().getStringArray(R.array.carModelToyota);
        pickerModel.setMinValue(0);
        pickerModel.setMaxValue(arrayModel.length-1);
        pickerModel.setDisplayedValues(arrayModel);
        pickerModel.setWrapSelectorWheel(true);
        pickerModel.setValue(1);


        pickerMake.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                String[] arrayModel;

                if(pickerMake.getValue() == 0){
                    arrayModel = getResources().getStringArray(R.array.carModelToyota);
                }
                else if(pickerMake.getValue() == 1){
                    arrayModel = getResources().getStringArray(R.array.carModelNissan);
                }
                else if(pickerMake.getValue() == 2){
                    arrayModel = getResources().getStringArray(R.array.carModelKia);
                }
                else {
                    arrayModel = getResources().getStringArray(R.array.carModelHonda);
                }

                pickerModel.setMinValue(0);
                pickerModel.setMaxValue(arrayModel.length-1);
                pickerModel.setDisplayedValues(arrayModel);
                pickerModel.setWrapSelectorWheel(true);
                pickerModel.setValue(1);


            }
        });

        final NumberPicker pickerYear = (NumberPicker) findViewById(R.id.pickerYear);
        pickerYear.setMinValue(2000);
        pickerYear.setMaxValue(2017);
        pickerYear.setValue(0);
        pickerYear.setWrapSelectorWheel(false);


        ImageView close = (ImageButton) findViewById(R.id.imgClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.animator.slide_out_up, R.animator.slide_in_up);
                finish();
            }
        });

        ImageView confirm = (ImageButton) findViewById(R.id.imgConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();


                /*intent.putExtra("userDTO", userDTO);
                intent.putExtra("txtGender", txtGender);
                setResult(RESULT_OK, intent);
                finish();*/
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.animator.slide_in_up, R.animator.slide_out_up);
    }
}
