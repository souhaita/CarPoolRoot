package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityLogin;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityLogout;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.DeviceDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.DeviceDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncDeleteDeviceToken extends AsyncTask<Void, Void ,Void> {

    private Context context;
    private ProgressDialog progressDialog;
    private DeviceDTO deviceDTO;

    public AsyncDeleteDeviceToken(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }


    @Override
    protected Void doInBackground(Void... params) {
        JSONObject postData = new JSONObject();
        HttpURLConnection httpURLConnection = null;

        try{
            int userId = Utils.getCurrentAccount(context);
            deviceDTO = new DeviceDAO(context).getDeviceAccountID(userId);

            if(deviceDTO != null && deviceDTO.getDeviceId() != null)
                postData.put("deviceId", deviceDTO.getDeviceId());

            httpURLConnection = (HttpURLConnection) new URL(WebService.API_DELETE_DEVICE_TOKEN).openConnection();
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

            return null;

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);

        try {
            Utils.clearSharedPref(context);

            new DeviceDAO(context).deleteDeviceByID(deviceDTO.getDeviceId());

            Intent intent = new Intent(context,ActivityLogin.class);
            context.startActivity(intent);
            ((Activity)context).finish();

            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
