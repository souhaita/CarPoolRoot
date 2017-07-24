package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.Activity;
import android.app.Dialog;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivitySearchTrip;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.PassengerSearchTripAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.PassengerViewTripAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncPassengerPayRequest extends AsyncTask<ManageRequestDTO, Void ,Boolean> {

    private Context context;
    private ProgressDialog progressDialog;
    private List<RequestObject> requestObjectList = null;
    private ManageRequestDTO manageRequestDTO;
    private Dialog menuDialog;
    private PassengerViewTripAdapter passengerViewTripAdapter = null;

    public AsyncPassengerPayRequest(Dialog menuDialog, final Context context, PassengerViewTripAdapter passengerViewTripAdapter, List<RequestObject> requestObjectList) {
        this.context = context;
        this.passengerViewTripAdapter = passengerViewTripAdapter;
        this.requestObjectList = requestObjectList;
        this.menuDialog = menuDialog;
    }

    @Override
    protected void onPreExecute() {
        String message = context.getString(R.string.async_passenger_accept_pay_trip);
        progressDialog = Utils.progressDialogue(context,message);
        progressDialog.show();
    }


    @Override
    protected Boolean doInBackground(ManageRequestDTO... params) {
        JSONObject postData = new JSONObject();
        manageRequestDTO = params[0];

        HttpURLConnection httpURLConnection = null;

        try{
            postData.put("manageRequestId", manageRequestDTO.getManageRequestId());

            httpURLConnection = (HttpURLConnection) new URL(WebService.API_PASSENGER_PAY_REQUEST).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpURLConnection.setRequestProperty("Accept", "application/json;charset=UTF-8");
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

            return builder.toString().contains("true");

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
    protected void onPostExecute(Boolean result){
        super.onPostExecute(result);
        if(result != null && result) {
            if(passengerViewTripAdapter != null) {
                List<RequestObject> newRequestObjectList = new ArrayList<>();
                for (RequestObject r : requestObjectList) {
                    if (!r.getRequestDTO().getRequestId().equals(manageRequestDTO.getRequestId()))
                        newRequestObjectList.add(r);
                }

                passengerViewTripAdapter.setRequestObjectList(newRequestObjectList);
                passengerViewTripAdapter.notifyDataSetChanged();
                menuDialog.dismiss();
            }
            else{
                Utils.alertError(context, context.getString(R.string.error_server));
            }
        }
        else{
            Utils.alertError(context, context.getString(R.string.error_server));
        }
    }
}
