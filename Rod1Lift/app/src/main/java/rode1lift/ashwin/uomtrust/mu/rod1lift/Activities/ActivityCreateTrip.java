package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sdsmdg.harjot.crollerTest.Croller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverCreateOrUpdateRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncUpdateAccount;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityCreateTrip extends Activity {


    private TextView txtPrice;
    private AutoCompleteTextView autoFrom;
    private AutoCompleteTextView autoTo;
    private TextView txtDate, txtTime;
    private EditText txtContact, txtSeatAvailable;
    private Croller croller;

    private Calendar requestDateTime = Calendar.getInstance();
    private RequestDTO requestDTO = new RequestDTO();

    private int accountId;
    private AccountDTO accountDTO;
    private CarDTO carDTO;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    public static String API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        API_KEY = getString(R.string.google_places_api_key);

        SharedPreferences prefs = ActivityCreateTrip.this.getSharedPreferences(CONSTANT.APP_NAME, MODE_PRIVATE);
        accountId = prefs.getInt(CONSTANT.CURRENT_ACCOUNT_ID, 1);

        accountDTO = new AccountDAO(ActivityCreateTrip.this).getAccountById(accountId);

        txtPrice = (TextView)findViewById(R.id.txtPrice);
        txtPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCreateTrip.this, PickerActivityTripPrice.class);
                intent.putExtra(CONSTANT.TRIP_PRICE, txtPrice.getText().toString());
                startActivityForResult(intent, CONSTANT.CREATE_TRIP_ACTIVITY);
            }
        });

        slider();

        autoFrom = (AutoCompleteTextView)findViewById(R.id.autoFrom);
        autoFrom.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.simple_list_places));

        autoTo = (AutoCompleteTextView)findViewById(R.id.autoTo);
        autoTo.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.simple_list_places));

        txtContact = (EditText) findViewById(R.id.txtContact);
        if(accountDTO != null && accountDTO.getPhoneNum() != null && accountDTO.getPhoneNum().toString().length() >=6)
            txtContact.setText(accountDTO.getPhoneNum().toString());

        txtSeatAvailable = (EditText) findViewById(R.id.txtSeatAvailable);
        carDTO = new CarDAO(ActivityCreateTrip.this).getCarByAccountID(accountId);
        if(carDTO != null && carDTO.getNumOfPassenger() != null)
            txtSeatAvailable.setText(carDTO.getNumOfPassenger().toString());

        txtDate = (TextView)findViewById(R.id.txtDate);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Utils.hideKeyboard(ActivityCreateTrip.this);

                Calendar currentTime = Calendar.getInstance();
                final int mMonth = currentTime.get(Calendar.MONTH);
                final int mYear = currentTime.get(Calendar.YEAR);
                final int mDay = currentTime.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialogue;
                datePickerDialogue = new DatePickerDialog(ActivityCreateTrip.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        requestDateTime.set(Calendar.YEAR, year);
                        requestDateTime.set(Calendar.MONTH, monthOfYear);
                        requestDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        txtDate.setText(String.valueOf(dayOfMonth) +" "+ new DateFormatSymbols().getMonths()[monthOfYear-1]);

                        Utils.hideKeyboard(ActivityCreateTrip.this);

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
                Utils.hideKeyboard(ActivityCreateTrip.this);

                Calendar currentTime = Calendar.getInstance();
                final int mHour = currentTime.get(Calendar.HOUR_OF_DAY);
                final int mMinute = currentTime.get(Calendar.MINUTE);
                final TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(ActivityCreateTrip.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String min =  String.valueOf(selectedMinute).length() <2? "0"+String.valueOf(selectedMinute):String.valueOf(selectedMinute);

                        txtTime.setText(String.valueOf(selectedHour)+" : "+min);

                        requestDateTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        requestDateTime.set(Calendar.MINUTE, selectedMinute);

                        Utils.hideKeyboard(ActivityCreateTrip.this);
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
                    new AsyncDriverCreateOrUpdateRequest(ActivityCreateTrip.this, true).execute(requestDTO);
                }
            }
        });
    }

    private void alertCancel() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ActivityCreateTrip.this);

        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure you want delete this trip?");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
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
        croller = (Croller) findViewById(R.id.croller);
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
                txtPrice.setText(String.valueOf(progress*5));
            }
        });
    }

    private boolean validForm(){
        boolean validForm = true;

        if(TextUtils.isEmpty(autoFrom.getText())){
            Utils.alertError(ActivityCreateTrip.this, getResources().getString(R.string.create_trip_activity_validation_autocomplete_address));
            return false;
        }
        else if(TextUtils.isEmpty(autoTo.getText())){
            Utils.alertError(ActivityCreateTrip.this, getResources().getString(R.string.create_trip_activity_validation_autocomplete_address));
            return false;
        }
        else if(TextUtils.isEmpty(txtDate.getText()) ){
            Utils.alertError(ActivityCreateTrip.this, getResources().getString(R.string.create_trip_activity_validation_date));
            return false;
        }
        else if(TextUtils.isEmpty(txtTime.getText())){
            Utils.alertError(ActivityCreateTrip.this, getResources().getString(R.string.create_trip_activity_validation_time));
            return false;
        }
        else if(TextUtils.isEmpty(txtSeatAvailable.getText().toString())){
            Utils.alertError(ActivityCreateTrip.this, getResources().getString(R.string.create_trip_activity_validation_seat_available));
            return false;
        }
        else if(TextUtils.isEmpty(txtContact.getText().toString())){
            Utils.alertError(ActivityCreateTrip.this, getResources().getString(R.string.create_trip_activity_validation_contact_detail));
            return false;
        }
        else if(txtContact.getText().toString().length() >8 || txtContact.getText().toString().length() <7){
            Utils.alertError(ActivityCreateTrip.this, getResources().getString(R.string.create_trip_activity_validation_contact_detail_length));
            return false;
        }

        int numOfPassenger = carDTO.getNumOfPassenger();
        int numOfPassengerEntered = Integer.parseInt(txtSeatAvailable.getText().toString());
        if( numOfPassengerEntered> numOfPassenger || numOfPassengerEntered <1){
            String message = getResources().getString(R.string.create_trip_activity_validation_seat_available_length) +" - "+numOfPassenger;
            Utils.alertError(ActivityCreateTrip.this, message);
            return false;
        }


        if(validForm){
            if(accountDTO != null && accountDTO.getPhoneNum() == null){
                accountDTO.setPhoneNum(Integer.parseInt(txtContact.getText().toString()));
                new AccountDAO(ActivityCreateTrip.this).saveOrUpdateAccount(accountDTO);
                new AsyncUpdateAccount(ActivityCreateTrip.this).execute(accountDTO);
            }
            else if(!accountDTO.getPhoneNum().equals(Integer.valueOf(txtContact.getText().toString()))){
                accountDTO.setPhoneNum(Integer.parseInt(txtContact.getText().toString()));
                new AccountDAO(ActivityCreateTrip.this).saveOrUpdateAccount(accountDTO);
                new AsyncUpdateAccount(ActivityCreateTrip.this).execute(accountDTO);
            }

            requestDTO.setAccountId(accountId);
            requestDTO.setPlaceFrom(autoFrom.getText().toString());
            requestDTO.setPlaceTo(autoTo.getText().toString());
            requestDTO.setEvenDate(requestDateTime.getTime());
            requestDTO.setRequestStatus(RequestStatus.REQUEST_PENDING);
            requestDTO.setPrice(Integer.parseInt(txtPrice.getText().toString()));
            requestDTO.setSeatAvailable(Integer.parseInt(txtSeatAvailable.getText().toString()));

            Date date = new Date();
            requestDTO.setDateCreated(date);
            requestDTO.setDateUpdated(date);
        }
        else{
            Utils.vibrate(ActivityCreateTrip.this);
        }

        return validForm;
    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONSTANT.CREATE_TRIP_ACTIVITY) {
            String tripPrice = data.getStringExtra(CONSTANT.TRIP_PRICE);

            txtPrice.setText(tripPrice);
            croller.setProgress(Integer.parseInt(tripPrice)/5);
        }
    }

    // ref: https://examples.javacodegeeks.com/android/android-google-places-autocomplete-api-example/
    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY );
            sb.append("&components=country:mu");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (Exception e) {
            return resultList;
        }  finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                String place = predsJsonArray.getJSONObject(i).getString("description");
                String[] parts = place.split(",");
                resultList.add(parts[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return (String)resultList.get(index);
        }

        @Override
        public android.widget.Filter getFilter() {
            android.widget.Filter filter = new android.widget.Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }
}
