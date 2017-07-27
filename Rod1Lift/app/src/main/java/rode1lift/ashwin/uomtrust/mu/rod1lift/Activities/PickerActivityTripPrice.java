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

import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class PickerActivityTripPrice extends Activity {

    EditText eTxtTripPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_trip_price);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        eTxtTripPrice =(EditText)findViewById(R.id.eTxtTripPrice);

        String tripPrice = getIntent().getStringExtra(CONSTANT.TRIP_PRICE);

        eTxtTripPrice.setText(tripPrice);

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
            Utils.alertError(PickerActivityTripPrice.this, message);

            Utils.vibrate(PickerActivityTripPrice.this);
        }
        else if(Integer.parseInt(eTxtFullName.getText().toString()) >100 || Integer.parseInt(eTxtFullName.getText().toString()) < 10){
            String message = "Price range should be between Rs 10 to Rs 100";
            Utils.alertError(PickerActivityTripPrice.this, message);

            Utils.vibrate(PickerActivityTripPrice.this);
        }
        else{
            Intent intent = getIntent();
            Double tripPriceFlooring;

            Integer tripPrice = Integer.parseInt(eTxtFullName.getText().toString());

            if(tripPrice %5 == 0){
                tripPriceFlooring = (double)tripPrice;
            }
            else {
                tripPriceFlooring = 5 * (Math.ceil(Math.abs((tripPrice+4) / 5)));
            }

            intent.putExtra(CONSTANT.TRIP_PRICE, String.valueOf(tripPriceFlooring.intValue()));
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
