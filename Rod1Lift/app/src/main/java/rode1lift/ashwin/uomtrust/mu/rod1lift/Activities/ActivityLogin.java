package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncCheckAccount;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncCreateAccount;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverFetchCar;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityLogin extends Activity {

    CallbackManager callbackManager;
    AccountDTO accountDTO = new AccountDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        SharedPreferences prefs = getSharedPreferences(CONSTANT.APP_NAME, MODE_PRIVATE);
        Boolean login = prefs.getBoolean(CONSTANT.LOGIN, false);
        if(login){
            Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
            startActivity(intent);
            finish();
        }
        else {
            setContentView(R.layout.activity_login);

            Utils.disconnectFromFacebook();

            LinearLayout llMain = (LinearLayout)findViewById(R.id.llMain);
            Utils.animateLayout(llMain);

            callbackManager = CallbackManager.Factory.create();

            LoginButton loginButton = (LoginButton) findViewById(R.id.btnFbLogin);
            loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {

                                    if (object != null) {
                                        getFbData(object);
                                        if(accountDTO == null || accountDTO.getAccountId() == null || accountDTO.getAccountId() <1)
                                            selectUserType();
                                    } else {
                                        Utils.disconnectFromFacebook();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id, picture.type(large), first_name, last_name, email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                @Override
                public void onCancel() {
                    Utils.disconnectFromFacebook();
                }

                @Override
                public void onError(FacebookException error) {
                    Utils.disconnectFromFacebook();
                }
                //...
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void login(){

        SharedPreferences.Editor editor = getSharedPreferences(CONSTANT.APP_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(CONSTANT.LOGIN, true);
        editor.putInt(CONSTANT.CURRENT_ACCOUNT_ID, accountDTO.getAccountId());
        editor.commit();

        CarDTO carDTO = new CarDAO(ActivityLogin.this).getCarByAccountID(accountDTO.getAccountId());
        if(carDTO != null && carDTO.getCarId() != null) {
            Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
            startActivity(intent);
            finish();
        }
        else{
            carDTO.setAccountId(accountDTO.getAccountId());
            new AsyncDriverFetchCar(ActivityLogin.this).execute(carDTO);
        }
    }

    private void getFbData(JSONObject object){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            if(checkIfAccountExist(object.getString("email"))) {
                login();
            }
            else {

                if (object.has("picture")) {
                    String profilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");

                    if (!TextUtils.isEmpty(profilePicUrl) && !profilePicUrl.equalsIgnoreCase("null")) {
                        URL fb_url = new URL(profilePicUrl);
                        HttpsURLConnection conn1 = (HttpsURLConnection) fb_url.openConnection();
                        HttpsURLConnection.setFollowRedirects(true);
                        conn1.setInstanceFollowRedirects(true);

                        accountDTO.setProfilePicture(Utils.toByteArray(conn1.getInputStream()));
                    }
                }

                if (object.has("email")) {
                    accountDTO.setEmail(object.getString("email"));
                }

                if (object.has("id")) {
                    accountDTO.setFacebookId(object.getString("id"));
                }

                if (object.has("first_name")) {
                    accountDTO.setFirstName(object.getString("first_name"));
                }
                if (object.has("last_name")) {
                    accountDTO.setLastName(object.getString("last_name"));
                }

                accountDTO.setAccountId(-1);
                accountDTO.setAccountStatus(AccountStatus.ACTIVE);

                Date date = new Date();
                accountDTO.setDateUpdated(date);
                accountDTO.setDateCreated(date);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private boolean checkIfAccountExist(String email){
        Boolean exist = false;
        try {
            accountDTO = new AsyncCheckAccount(ActivityLogin.this).execute(email).get();
            exist = accountDTO.getAccountId() == null? false: true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return exist;
    }

    private void selectUserType() {
        final Dialog dialog = new Dialog(ActivityLogin.this, R.style.WalkthroughTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dilaogue_user_type);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        final NumberPicker numberPickeruserType = (NumberPicker)dialog.findViewById(R.id.numberPickerUserType);

        numberPickeruserType.setMinValue(0);
        numberPickeruserType.setMaxValue(2);

        String select = getString(R.string.activity_login_select_type_select);
        String carPooler = getString(R.string.activity_login_select_type_driver);
        String carSeeker = getString(R.string.activity_login_select_type_other);

        final String[] arrayUnits = new String[]{select, carPooler, carSeeker };
        numberPickeruserType.setDisplayedValues(arrayUnits);

        numberPickeruserType.setValue(0);

        numberPickeruserType.setWrapSelectorWheel(false);

        TextView txtOk = (TextView)dialog.findViewById(R.id.txtOk);

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = numberPickeruserType.getValue();

                if(value == 0){
                   Utils.alertError(ActivityLogin.this, getResources().getString(R.string.activity_login_select_user_type_error));
                }

                else if(value == 1){

                    if(dialog != null && dialog.isShowing())
                        dialog.dismiss();

                    accountDTO.setAccountRole(AccountRole.DRIVER);
                    new AccountDAO(ActivityLogin.this).saveOrUpdateAccount(accountDTO);

                    Intent intent = new Intent(ActivityLogin.this, ActivityCompleteDriverRegistration.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    if(dialog != null && dialog.isShowing())
                        dialog.dismiss();

                    accountDTO.setAccountRole(AccountRole.PASSENGER);
                    new AccountDAO(ActivityLogin.this).saveOrUpdateAccount(accountDTO);
                    new AsyncCreateAccount(ActivityLogin.this).execute(accountDTO);
                    finish();
                }
            }
        });

        dialog.show();
    }
}
