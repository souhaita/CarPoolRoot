package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncCheckAccount;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncCreateAccount;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.Const;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class LoginActivity extends Activity {

    CallbackManager callbackManager;
    AccountDTO accountDTO = new AccountDTO();

    LinearLayout llMain = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        llMain = (LinearLayout)findViewById(R.id.llMain);

        animateLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getFbData(JSONObject object){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            if(checkIfAccountExist(object.getString("email"))) {
                SharedPreferences.Editor editor = getSharedPreferences(Const.appName, MODE_PRIVATE).edit();
                editor.putBoolean(Const.login, true);
                editor.putInt(Const.currentAccountId, accountDTO.getAccountId());
                editor.commit();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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
                accountDTO.setDateCreated(new Date());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private boolean checkIfAccountExist(String email){
        Boolean exist = false;
        try {
            accountDTO.setAccountId(new AsyncCheckAccount(LoginActivity.this).execute(email).get());
            exist = accountDTO.getAccountId() == null? false: true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return exist;
    }

    private void selectUserType() {
        final Dialog dialog = new Dialog(LoginActivity.this, R.style.WalkthroughTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.show_dilaogue_user_type);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        final NumberPicker numberPickeruserType = (NumberPicker)dialog.findViewById(R.id.numberPickerUserType);

        numberPickeruserType.setMinValue(0);
        numberPickeruserType.setMaxValue(2);

        final String[] arrayUnits = new String[]{"Select", "Driver", "Other" };
        numberPickeruserType.setDisplayedValues(arrayUnits);

        numberPickeruserType.setValue(0);

        numberPickeruserType.setWrapSelectorWheel(false);

        TextView txtOk = (TextView)dialog.findViewById(R.id.txtOk);

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int value = numberPickeruserType.getValue();

                if(value == 0){
                   Utils.showToast(LoginActivity.this, getResources().getString(R.string.activity_login_select_user_type_error));
                }

                else if(value == 1){
                    /*accountDTO.setAccountRole(AccountRole.DRIVER);
                    new AccountDAO(LoginActivity.this).saveOrUpdateAccount(accountDTO);

                    Intent intent = new Intent(LoginActivity.this, CompleteDriverRegistration.class);
                    startActivity(intent);
                    finish();*/
                }
                else{
                    dialog.dismiss();

                    accountDTO.setAccountRole(AccountRole.OTHER);
                    Date date = new Date();
                    accountDTO.setDateUpdated(date);
                    accountDTO.setDateCreated(date);
                    new AccountDAO(LoginActivity.this).saveOrUpdateAccount(accountDTO);
                    new AsyncCreateAccount(LoginActivity.this).execute(accountDTO);
                }
            }
        });

        dialog.show();
    }

    private void animateLayout(){
        AnimationDrawable animationDrawable = (AnimationDrawable) llMain.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
    }

    /*private void layout1(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    synchronized(this){
                        wait(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                llMain.setBackground(getResources().getDrawable(R.drawable.background_main));
                                layout2();
                            }
                        });
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }*/


}
