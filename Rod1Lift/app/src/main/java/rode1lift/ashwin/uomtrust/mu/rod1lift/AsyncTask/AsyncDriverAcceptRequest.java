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
import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.DriverRequestAdapterOther;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.DriverRequestAdapterPending;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.ManageRequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.RequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncDriverAcceptRequest extends AsyncTask<RequestDTO, Void ,Boolean > {

    private Context context;
    private ProgressDialog progressDialog;
    private RequestDTO requestDTO;
    private List<RequestObject> requestObjectList;
    private Intent intent;

    private DriverRequestAdapterOther driverRequestAdapterOther;


    public AsyncDriverAcceptRequest(final Context context, DriverRequestAdapterOther driverRequestAdapterOther, List<RequestObject> requestObjectList) {
        this.context = context;
        this.driverRequestAdapterOther = driverRequestAdapterOther;
        this.requestObjectList = requestObjectList;
    }

    @Override
    protected void onPreExecute() {
        String message = context.getString(R.string.async_driver_accept_client_request);
        progressDialog = Utils.progressDialogue(context,message);
        progressDialog.show();
    }


    @Override
    protected Boolean doInBackground(RequestDTO... params) {
        JSONObject postData = new JSONObject();
        requestDTO = params[0];

        HttpURLConnection httpURLConnection = null;

        try{
            if(requestDTO.getManageRequestId() != null)
                postData.put("manageRequestId", requestDTO.getManageRequestId());

            httpURLConnection = (HttpURLConnection) new URL(WebService.API_DRIVER_ACCEPT_CLIENT_REQUEST).openConnection();
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
    protected void onPostExecute(Boolean accepted){
        super.onPostExecute(accepted);

        if(accepted != null && accepted) {

            List<RequestObject> newRequestObjectList = new ArrayList<>();

            for(int x = 0; x < requestObjectList.size(); x++){
                RequestObject r = requestObjectList.get(x);
                if (!r.getManageRequestDTOList().get(0).getManageRequestId().equals(requestDTO.getManageRequestId())) {
                    newRequestObjectList.add(r);
                }
                else{
                    ManageRequestDTO m = r.getManageRequestDTOList().get(0);
                    m.setRequestStatus(RequestStatus.DRIVER_ACCEPTED);
                    new ManageRequestDAO(context).saveOrUpdateManageRequest(m);
                }
            }

            driverRequestAdapterOther.setRequestObjectList(newRequestObjectList);
            driverRequestAdapterOther.notifyDataSetChanged();
        }
        else{
            Utils.alertError(context, context.getString(R.string.error_server));
        }
    }
}
