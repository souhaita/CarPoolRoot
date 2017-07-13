package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.RequestAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.RequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncDriverFetchRequest extends AsyncTask<RequestDTO, Void ,List<RequestDTO >> {

    private Context context;
    private ProgressDialog progressDialog;
    private ListView listView;

    public AsyncDriverFetchRequest(final Context context, ListView listView ) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        String message;
        message =  "Fetching requests";

        progressDialog = Utils.progressDialogue(context,message);
        progressDialog.show();
    }


    @Override
    protected List<RequestDTO> doInBackground(RequestDTO... params) {
        JSONObject postData = new JSONObject();

        HttpURLConnection httpURLConnection = null;

        try{

            SharedPreferences prefs = context.getSharedPreferences(CONSTANT.APP_NAME, MODE_PRIVATE);
            int userId = prefs.getInt(CONSTANT.CURRENT_ACCOUNT_ID, 1);

            RequestDTO requestDTO = params[0];
            if(requestDTO != null) {
                postData.put("requestStatus", requestDTO.getRequestStatus().getValue());
                postData.put("accountId", userId);
            }

            String url;

            if(requestDTO.getRequestStatus().equals(RequestStatus.REQUEST_PENDING))
                url = WebService.API_DRIVER_GET_PENDING_REQUEST_LIST;
            else
                url = WebService.API_DRIVER_OTHER_REQUEST_LIST;


            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
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

            JSONArray json;

            json = new JSONArray(builder.toString());
            List<RequestDTO> newRequestDTOList = new ArrayList<>();

            for(int x = 0; x<json.length(); x++){
                JSONObject jsonObject = json.getJSONObject(x);
                RequestDTO newRequestDTO = new RequestDTO();

                newRequestDTO.setAccountId(jsonObject.getInt("accountId"));
                newRequestDTO.setRequestId(jsonObject.getInt("requestId"));
                newRequestDTO.setSeatAvailable(jsonObject.getInt("seatAvailable"));
                newRequestDTO.setRequestStatus(requestDTO.getRequestStatus());
                newRequestDTO.setEvenDate(new Date(jsonObject.getLong("eventDate")));
                newRequestDTO.setPlaceFrom(jsonObject.getString("placeFrom"));
                newRequestDTO.setPlaceTo(jsonObject.getString("placeTo"));
                newRequestDTO.setPrice(jsonObject.getInt("price"));
                newRequestDTO.setDateCreated(new Date(jsonObject.getLong("dateCreated")));
                newRequestDTO.setDateUpdated(new Date(jsonObject.getLong("dateUpdated")));
                newRequestDTO.setPrice(jsonObject.getInt("price"));

                newRequestDTOList.add(newRequestDTO);
            }

            return newRequestDTOList;

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
    protected void onPostExecute(List<RequestDTO> requestDTOList){
        super.onPostExecute(requestDTOList);

        if(requestDTOList != null && requestDTOList.size() >0) {
            final RequestAdapter requestAdapter = new RequestAdapter(context, requestDTOList);
            listView.setAdapter(requestAdapter);

            RequestDAO requestDAO = new RequestDAO(context);
            for(RequestDTO requestDTO : requestDTOList){
                requestDAO.saveOrUpdateRequest(requestDTO);
            }
        }

        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
