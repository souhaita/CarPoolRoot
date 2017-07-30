package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityLogin;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityMain;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.DeviceDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.DeviceDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncSaveDeviceToken extends AsyncTask<String, Void ,DeviceDTO> {

    private Context context;

    public AsyncSaveDeviceToken(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }


    @Override
    protected DeviceDTO doInBackground(String... params) {
        JSONObject postData = new JSONObject();
        String deviceToken = params[0];
        HttpURLConnection httpURLConnection = null;

        try{
            int userId = Utils.getCurrentAccount(context);
            postData.put("accountId", userId);
            postData.put("deviceToken", deviceToken);

            httpURLConnection = (HttpURLConnection) new URL(WebService.API_SAVE_DEVICE_TOKEN).openConnection();
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

            DeviceDTO deviceDTO = new DeviceDTO();
            deviceDTO.setAccountId(userId);
            deviceDTO.setDeviceToken(deviceToken);

            JSONObject jsonObject = new JSONObject(builder.toString());
            deviceDTO.setDeviceId(jsonObject.getInt("deviceId"));

            return deviceDTO;

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
    protected void onPostExecute(DeviceDTO deviceDTO){
        super.onPostExecute(deviceDTO);

        if(deviceDTO != null && deviceDTO.getDeviceId() != null){
            new DeviceDAO(context).saveOrUpdateDevice(deviceDTO);
        }
    }
}
