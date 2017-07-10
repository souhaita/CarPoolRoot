package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.RequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncCreateOrUpdateRequest extends AsyncTask<RequestDTO, Void ,RequestDTO > {

    private Context context;
    private ProgressDialog progressDialog;
    boolean newRequest;

    public AsyncCreateOrUpdateRequest(final Context context, boolean newRequest) {
        this.context = context;
        this.newRequest = newRequest;
    }

    @Override
    protected void onPreExecute() {
        String message;
        if(newRequest)
            message =  "Creating your request";
        else
            message =  "Updating your request";

        progressDialog = Utils.progressDialogue(context,message);
        progressDialog.show();
    }


    @Override
    protected RequestDTO doInBackground(RequestDTO... params) {
        JSONObject postData = new JSONObject();
        RequestDTO requestDTO = params[0];

        HttpURLConnection httpURLConnection = null;

        try{
            if(requestDTO.getRequestId() != null && requestDTO.getRequestId() >0)
                postData.put("requestId", requestDTO.getRequestId());

            postData.put("placeFrom", requestDTO.getPlaceFrom());
            postData.put("placeTo", requestDTO.getPlaceTo());
            postData.put("requestStatus", requestDTO.getRequestStatus());
            postData.put("accountId", requestDTO.getAccountId());

            if(requestDTO.getDateCreated() != null)
                postData.put("dateCreated", requestDTO.getDateCreated().getTime());

            postData.put("dateUpdated", requestDTO.getDateUpdated().getTime());
            postData.put("eventDate", requestDTO.getEvenDate().getTime());

            httpURLConnection = (HttpURLConnection) new URL(WebService.DRIVER_API_CREATE_UPDATE_REQUEST).openConnection();
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

            JSONObject jsonObject = new JSONObject(builder.toString());
            requestDTO.setRequestId(jsonObject.getInt("requestId"));

            return requestDTO;

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
    protected void onPostExecute(RequestDTO requestDTO){
        super.onPostExecute(requestDTO);

        if(requestDTO != null && requestDTO.getRequestId() > 0) {
            new RequestDAO(context).saveOrUpdateRequest(requestDTO);
        }
        else{
            Utils.showToast(context, context.getString(R.string.error_server));
        }
    }
}
