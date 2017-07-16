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
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class PickerActivityCarMake extends Activity {

    private String[] arrayModel;
    private CarDTO carDTO;
    private Integer userId;
    private String[] arrayCarMake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_car_details);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final NumberPicker pickerMake = (NumberPicker) findViewById(R.id.pickerMake);
        arrayCarMake =  getResources().getStringArray(R.array.carMake);

        userId = Utils.getCurrentAccount(PickerActivityCarMake.this);
        carDTO = new CarDAO(PickerActivityCarMake.this).getCarByAccountID(userId);

        pickerMake.setMinValue(0);
        pickerMake.setMaxValue(arrayCarMake.length-1);
        pickerMake.setDisplayedValues(arrayCarMake);
        pickerMake.setValue(0);
        pickerMake.setWrapSelectorWheel(true);


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
                finish();
            }
        });

        ImageView confirm = (ImageButton) findViewById(R.id.imgConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(carDTO != null && carDTO.getCarId() != null){
                    carDTO.setModel(arrayModel[pickerModel.getValue()]);
                    carDTO.setMake(arrayCarMake[pickerMake.getValue()]);
                    carDTO.setYear(pickerYear.getValue());
                }
                else{
                    carDTO = new CarDTO();
                    carDTO.setCarId(-1);
                    carDTO.setAccountId(userId);
                    carDTO.setModel(arrayModel[pickerModel.getValue()]);
                    carDTO.setYear(pickerYear.getValue());
                    carDTO.setNumOfPassenger(4);
                    carDTO.setMake(arrayCarMake[pickerMake.getValue()]);
                    carDTO.setHasPic1(false);
                    carDTO.setHasPic2(false);
                    carDTO.setHasPic3(false);
                    carDTO.setHasPic4(false);
                }

                new CarDAO(PickerActivityCarMake.this).saveOrUpdateCar(carDTO);

                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        if(carDTO != null && carDTO.getCarId() != null){
            int makeValue = 0;
            int modelValue = 0;

            carMakeLoop:
            for(int x = 0; x<arrayCarMake.length; x++){
                if(arrayCarMake[x].equals(carDTO.getMake())){
                    makeValue = x;
                    break carMakeLoop;
                }
            }
            pickerMake.setValue(makeValue);

            switch (makeValue){
                case 0:
                    arrayModel = getResources().getStringArray(R.array.carModelToyota);
                    break;
                case 1:
                    arrayModel = getResources().getStringArray(R.array.carModelNissan);
                    break;
                case 2:
                    arrayModel = getResources().getStringArray(R.array.carModelKia);
                    break;
                default:
                    arrayModel = getResources().getStringArray(R.array.carModelHonda);
                    break;
            }

            carModelLoop:
            for(int x = 0; x<arrayModel.length; x++){
                if(arrayModel[x].equals(carDTO.getModel())){
                    modelValue = x;
                    break carModelLoop;
                }
            }

            pickerModel.setMinValue(0);
            pickerModel.setMaxValue(arrayModel.length-1);
            pickerModel.setDisplayedValues(arrayModel);
            pickerModel.setWrapSelectorWheel(true);
            pickerModel.setValue(modelValue);

            pickerYear.setValue(carDTO.getYear());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.animator.slide_in_up, R.animator.slide_out_up);
    }
}
