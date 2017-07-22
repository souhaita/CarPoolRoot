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

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncUpdateAccount extends AsyncTask<AccountDTO, Void ,AccountDTO> {

    private Context context;
    private ProgressDialog progressDialog;

    public AsyncUpdateAccount(final Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        String message = context.getString(R.string.async_driver_update_account_details);
        progressDialog = Utils.progressDialogue(context, message);
        progressDialog.show();
    }


    @Override
    protected AccountDTO doInBackground(AccountDTO... params) {
        JSONObject postData = new JSONObject();
        AccountDTO accountDTO = params[0];
        HttpURLConnection httpURLConnection = null;

        try{
            postData.put("accountId", accountDTO.getAccountId());
            postData.put("fullName", accountDTO.getFullName());
            postData.put("email", accountDTO.getEmail());
            postData.put("accountRole", accountDTO.getAccountRole().getValue());
            postData.put("accountStatus", accountDTO.getAccountStatus().getValue());

            if(accountDTO.getFacebookId() != null)
                postData.put("facebookId", accountDTO.getFacebookId());

            if(accountDTO.getDateCreated() != null)
                postData.put("dateCreated", accountDTO.getDateCreated().getTime());

            if(accountDTO.getDateUpdated() != null)
                postData.put("dateUpdated", accountDTO.getDateUpdated().getTime());

            if(accountDTO.getProfilePicture() != null)
                postData.put("sProfilePicture", Base64.encodeToString(accountDTO.getProfilePicture(), Base64.DEFAULT));


            httpURLConnection = (HttpURLConnection) new URL(WebService.API_CREATE_ACCOUNT).openConnection();
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

            return accountDTO;

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            if(progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }

        return null;
    }

    @Override
    protected void onPostExecute(AccountDTO accountDTO){
        super.onPostExecute(accountDTO);

        if(accountDTO == null && accountDTO.getAccountId() < 0)
            Utils.alertError(context, context.getString(R.string.error_server));
    }
}
