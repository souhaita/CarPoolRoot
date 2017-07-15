package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

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
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.RequestAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.RequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncDriverDeleteRequest extends AsyncTask<RequestDTO, Void ,Boolean > {

    private Context context;
    private ProgressDialog progressDialog;
    private RequestDTO requestDTO;
    private RequestAdapter requestAdapter;
    private List<RequestDTO> requestDTOList;

    public AsyncDriverDeleteRequest(final Context context, RequestAdapter requestAdapter, List<RequestDTO> requestDTOList) {
        this.context = context;
        this.requestAdapter = requestAdapter;
        this.requestDTOList = requestDTOList;
    }

    @Override
    protected void onPreExecute() {
        String message = context.getString(R.string.async_driver_delete_trip_delete);
        progressDialog = Utils.progressDialogue(context,message);
        progressDialog.show();
    }


    @Override
    protected Boolean doInBackground(RequestDTO... params) {
        JSONObject postData = new JSONObject();
        requestDTO = params[0];

        HttpURLConnection httpURLConnection = null;

        try{
            postData.put("requestId", requestDTO.getRequestId());

            httpURLConnection = (HttpURLConnection) new URL(WebService.API_DRIVER_DELETE_REQUEST).openConnection();
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
    protected void onPostExecute(Boolean deleted){
        super.onPostExecute(deleted);

        if(deleted != null && deleted) {
            new RequestDAO(context).deleteRequest(requestDTO.getRequestId());

            requestDTOList.remove(requestDTO);
            requestAdapter.setRequestDTOList(requestDTOList);
            requestAdapter.notifyDataSetChanged();
        }
        else{
            Utils.alertError(context, context.getString(R.string.error_server));
        }
    }
}
