package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.maps.MapFragment;
import com.sdsmdg.harjot.crollerTest.Croller;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncCreateOrUpdateRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncUpdateAccount;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.Const;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class CreateTripActivity extends Activity {


    private TextView txtPrice;
    private AutoCompleteTextView autoFrom;
    private AutoCompleteTextView autoTo;
    private String[] places;
    private TextView txtDate, txtTime;
    private EditText txtContact;

    private Calendar requestDateTime = Calendar.getInstance();
    private RequestDTO requestDTO = new RequestDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        txtPrice = (TextView)findViewById(R.id.txtPrice);
        txtPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateTripActivity.this, PickerActivityTripPrice.class);
                startActivityForResult(intent, Const.CREATE_TRIP_ACTIVITY);
            }
        });

        slider();

        places = getResources().getStringArray(R.array.address_arrays);
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);
        ArrayAdapter<String> adapterTO = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,places);

        autoFrom = (AutoCompleteTextView)findViewById(R.id.autoFrom);
        autoFrom.setAdapter(adapterFrom);

        autoTo = (AutoCompleteTextView)findViewById(R.id.autoTo);
        autoTo.setAdapter(adapterTO);

        txtContact = (EditText) findViewById(R.id.txtContact);

        txtDate = (TextView)findViewById(R.id.txtDate);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Utils.hideKeyboard(CreateTripActivity.this);

                Calendar currentTime = Calendar.getInstance();
                final int mMonth = currentTime.get(Calendar.MONTH);
                final int mYear = currentTime.get(Calendar.YEAR);
                final int mDay = currentTime.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialogue;
                datePickerDialogue = new DatePickerDialog(CreateTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        requestDateTime.set(Calendar.YEAR, year);
                        requestDateTime.set(Calendar.MONTH, monthOfYear);
                        requestDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        txtDate.setText(String.valueOf(dayOfMonth) +" "+ new DateFormatSymbols().getMonths()[monthOfYear-1]);

                        Utils.hideKeyboard(CreateTripActivity.this);

                    }
                }, mYear, mMonth, mDay);

                datePickerDialogue.getDatePicker().setMinDate(currentTime.getTimeInMillis());
                datePickerDialogue.setTitle("Select Date");
                datePickerDialogue.show();
            }
        });

        txtTime = (TextView)findViewById(R.id.txtTime);
        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(CreateTripActivity.this);

                Calendar currentTime = Calendar.getInstance();
                final int mHour = currentTime.get(Calendar.HOUR_OF_DAY);
                final int mMinute = currentTime.get(Calendar.MINUTE);
                final TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(CreateTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String min =  String.valueOf(selectedMinute).length() <2? "0"+String.valueOf(selectedMinute):String.valueOf(selectedMinute);

                        txtTime.setText(String.valueOf(selectedHour)+" : "+min);

                        requestDateTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        requestDateTime.set(Calendar.MINUTE, selectedMinute);

                        Utils.hideKeyboard(CreateTripActivity.this);
                    }
                }, mHour, mMinute, false);

                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertCancel();
            }
        });

        TextView txtDone = (TextView)findViewById(R.id.txtDone);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validForm()){
                    new AsyncCreateOrUpdateRequest(CreateTripActivity.this, true).execute(requestDTO);
                }
            }
        });
    }

    private void alertCancel() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateTripActivity.this);

        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure you want delete this trip?");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        alertCancel();
    }

    private void slider(){
        //https://android-arsenal.com/details/1/5079
        Croller croller = (Croller) findViewById(R.id.croller);
        croller.setIndicatorWidth(12);

        croller.setBackCircleColor(Color.TRANSPARENT);
        croller.setMainCircleColor(Color.TRANSPARENT);


        croller.setMax(20);
        croller.setStartOffset(45);
        croller.setIsContinuous(false);
        croller.setLabelSize(40);
        croller.setLabelColor(Color.WHITE);
        croller.setProgressPrimaryColor(getResources().getColor(R.color.white));
        croller.setIndicatorColor(getResources().getColor(R.color.white));
        croller.setProgressSecondaryColor(getResources().getColor(R.color.black));
        croller.setLabel("PRICE");
        croller.setProgressPrimaryCircleSize(7f);
        croller.setProgressSecondaryCircleSize(5f);

        croller = (Croller) findViewById(R.id.croller);
        croller.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                // use the progress
                txtPrice.setText(String.valueOf(progress*5));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.animator.slide_in_up, R.animator.slide_out_up);
    }

    private boolean validForm(){
        boolean validForm = true;
        boolean validAddressFrom = false;
        boolean validAddressTo= false;

        SharedPreferences prefs = CreateTripActivity.this.getSharedPreferences(Const.appName, MODE_PRIVATE);
        int accountId = prefs.getInt(Const.currentAccountId, 1);

        if(autoFrom.getText() == null){
            Utils.showToast(CreateTripActivity.this, getResources().getString(R.string.create_trip_activity_validation_autocomplete_address));
            validForm = false;
        }
        else if(autoTo.getText() == null ){
            Utils.showToast(CreateTripActivity.this, getResources().getString(R.string.create_trip_activity_validation_autocomplete_address));
            validForm = false;
        }
        else if(txtDate.getText() == null ){
            Utils.showToast(CreateTripActivity.this, getResources().getString(R.string.create_trip_activity_validation_autocomplete_date));
            validForm = false;
        }
        else if(txtTime.getText() == null ){
            Utils.showToast(CreateTripActivity.this, getResources().getString(R.string.create_trip_activity_validation_autocomplete_time));
            validForm = false;
        }
        else if(TextUtils.isEmpty(txtContact.getText().toString())){
            Utils.showToast(CreateTripActivity.this, getResources().getString(R.string.create_trip_activity_validation_autocomplete_contact_detail));
            validForm = false;
        }
        else if(txtContact.getText().toString().length() >8 || txtContact.getText().toString().length() <7){
            Utils.showToast(CreateTripActivity.this, getResources().getString(R.string.create_trip_activity_validation_autocomplete_contact_detail_length));
            validForm = false;
        }
        else{
            AccountDTO accountDTO = new AccountDAO(CreateTripActivity.this).getAccountById(accountId);
            if(accountDTO.getPhoneNum() == null){
                accountDTO.setPhoneNum(Integer.parseInt(txtContact.getText().toString()));
                new AccountDAO(CreateTripActivity.this).saveOrUpdateAccount(accountDTO);
                new AsyncUpdateAccount(CreateTripActivity.this).execute(accountDTO);
            }
            else{
                if(!accountDTO.getPhoneNum().equals(Integer.valueOf(txtContact.getText().toString()))){
                    accountDTO.setPhoneNum(Integer.parseInt(txtContact.getText().toString()));
                    new AccountDAO(CreateTripActivity.this).saveOrUpdateAccount(accountDTO);
                    new AsyncUpdateAccount(CreateTripActivity.this).execute(accountDTO);
                }
            }
        }

        String addressFrom = autoFrom.getText().toString();
        for(int x = 0; x < places.length; x++){
            if(addressFrom.equalsIgnoreCase(places[x])){
                validAddressFrom = true;
            }
        }

        String addressTo = autoTo.getText().toString();
        for(int x = 0; x < places.length; x++){
            if(addressTo.equalsIgnoreCase(places[x])){
                validAddressTo = true;
            }
        }

        if(validForm && !validAddressFrom){
            Utils.showToast(CreateTripActivity.this, getResources().getString(R.string.create_trip_activity_validation_autocomplete_address_no_match));
        }
        else if(validForm && !validAddressTo){
            Utils.showToast(CreateTripActivity.this, getResources().getString(R.string.create_trip_activity_validation_autocomplete_address_no_match));
        }

        if(validForm&&validAddressFrom&&validAddressTo){

            requestDTO.setAccountId(accountId);
            requestDTO.setPlaceFrom(autoFrom.getText().toString());
            requestDTO.setPlaceTo(autoTo.getText().toString());
            requestDTO.setEvenDate(requestDateTime.getTime());
            requestDTO.setRequestStatus(RequestStatus.REQUEST_PENDING);

            Date date = new Date();
            requestDTO.setDateCreated(date);
            requestDTO.setDateUpdated(date);
        }
        else{
            Utils.vibrate(CreateTripActivity.this);
        }

        return validForm&&validAddressFrom&&validAddressTo;
    }
}
