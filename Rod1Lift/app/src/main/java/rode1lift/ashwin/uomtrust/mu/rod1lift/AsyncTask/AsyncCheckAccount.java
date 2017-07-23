package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncCheckAccount extends AsyncTask<String, Void , AccountDTO > {

    private Context context;
    private ProgressDialog progressDialog;

    public AsyncCheckAccount(final Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        String message = context.getString(R.string.async_check_account_validating_account);
        progressDialog = Utils.progressDialogue(context, message);
        progressDialog.show();
    }


    @Override
    protected AccountDTO doInBackground(String... params) {
        JSONObject postData = new JSONObject();
        AccountDTO accountDTO  = new AccountDTO();
        accountDTO.setEmail(params[0]);

        try{
            postData.put("email", accountDTO.getEmail());

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(WebService.API_CHECK_ACCOUNT_VIA_EMAIL).openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                httpURLConnection.setRequestProperty("Accept", "application/json; charset=utf-8");
                httpURLConnection.setRequestProperty("accept-charset", "UTF-8");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(postData.toString());
                wr.flush();
                wr.close();

                InputStream responseStream = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));

                final StringBuilder builder = new StringBuilder();
                String inputLine;

                while ((inputLine = reader.readLine()) != null) {
                    builder.append(inputLine).append("\n");
                }

                JSONObject jsonObject = new JSONObject(builder.toString());
                accountDTO.setAccountId(jsonObject.getInt("accountId"));
                accountDTO.setEmail(accountDTO.getEmail());
                accountDTO.setProfilePicture(Base64.decode(jsonObject.getString("sProfilePicture"), Base64.DEFAULT));
                accountDTO.setFullName(jsonObject.getString("fullName"));
                accountDTO.setFacebookId(jsonObject.getString("facebookId"));
                accountDTO.setAccountRole(AccountRole.valueOf(jsonObject.getString("accountRole")));
                accountDTO.setAccountStatus(AccountStatus.valueOf(jsonObject.getString("accountStatus")));
                accountDTO.setDateCreated(new Date(jsonObject.getLong("dateCreated")));
                accountDTO.setDateUpdated(new Date(jsonObject.getLong("dateUpdated")));



                if(jsonObject.has("phoneNum"))
                    accountDTO.setPhoneNum(jsonObject.getInt("phoneNum"));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return accountDTO;

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            progressDialog.dismiss();
        }

        return null;
    }

    @Override
    protected void onPostExecute(AccountDTO accountDTO){
        super.onPostExecute(accountDTO);
        if(accountDTO != null && accountDTO.getAccountId() != null && accountDTO.getAccountId() > 0)
            new AccountDAO(context).saveOrUpdateAccount(accountDTO);
    }
}
