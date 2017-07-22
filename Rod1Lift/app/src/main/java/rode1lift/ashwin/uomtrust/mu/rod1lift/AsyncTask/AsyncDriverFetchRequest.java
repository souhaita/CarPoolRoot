package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
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


import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.DriverRequestUserAcceptedAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.DriverRequestAdapterPending;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.ManageRequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.RequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.ManageRequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncDriverFetchRequest extends AsyncTask<RequestDTO, Void ,List<RequestObject >> {

    private Context context;
    private ProgressDialog progressDialog;
    private ListView listView;
    private RequestStatus requestStatus;

    public AsyncDriverFetchRequest(final Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        String message = context.getString(R.string.async_driver_fetch_trip_details);
        progressDialog = Utils.progressDialogue(context,message);
        progressDialog.show();
    }


    @Override
    protected List<RequestObject> doInBackground(RequestDTO... params) {
        JSONObject postData = new JSONObject();

        HttpURLConnection httpURLConnection = null;

        try{

            int userId = Utils.getCurrentAccount(context);

            RequestDTO requestDTO = params[0];
            if(requestDTO != null) {
                requestStatus = requestDTO.getRequestStatus();
                postData.put("accountId", userId);

                if(requestDTO.getCarId() != null)
                    postData.put("carId", requestDTO.getCarId());
            }

            String url;

            if(requestDTO.getRequestStatus().equals(RequestStatus.REQUEST_PENDING))
                url = WebService.API_DRIVER_GET_PENDING_REQUEST_LIST;
            else
                url = WebService.API_DRIVER_GET_USER_ACCEPTED_REQUEST_LIST;


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

            JSONArray jsonMain = new JSONArray(builder.toString());

            List<RequestObject> requestObjectList = new ArrayList<>();

            for(int x = 0; x<jsonMain.length(); x++){
                JSONObject jsonObjectList = jsonMain.getJSONObject(x);
                JSONObject jsonObjectRequest = jsonObjectList.getJSONObject("requestDTO");

                RequestObject requestObject = new RequestObject();

                RequestDTO newRequestDTO = new RequestDTO();
                newRequestDTO.setAccountId(jsonObjectRequest.getInt("accountId"));
                newRequestDTO.setRequestId(jsonObjectRequest.getInt("requestId"));
                newRequestDTO.setSeatAvailable(jsonObjectRequest.getInt("seatAvailable"));
                newRequestDTO.setRequestStatus(requestDTO.getRequestStatus());
                newRequestDTO.setEvenDate(new Date(jsonObjectRequest.getLong("eventDate")));
                newRequestDTO.setPlaceFrom(jsonObjectRequest.getString("placeFrom"));
                newRequestDTO.setPlaceTo(jsonObjectRequest.getString("placeTo"));
                newRequestDTO.setPrice(jsonObjectRequest.getInt("price"));
                newRequestDTO.setDateCreated(new Date(jsonObjectRequest.getLong("dateCreated")));
                newRequestDTO.setDateUpdated(new Date(jsonObjectRequest.getLong("dateUpdated")));
                newRequestDTO.setCarId(jsonObjectRequest.getInt("carId"));


                List<ManageRequestDTO> manageRequestDTOList = new ArrayList<>();
                JSONArray jsonManageRequestMain = jsonObjectList.getJSONArray("manageRequestDTOList");
                for(int y = 0; y<jsonManageRequestMain.length(); y++){
                    JSONObject jsonObjectManageRequest = jsonManageRequestMain.getJSONObject(y);

                    ManageRequestDTO manageRequestDTO = new ManageRequestDTO();
                    manageRequestDTO.setManageRequestId(jsonObjectManageRequest.getInt("manageRequestId"));
                    manageRequestDTO.setSeatRequested(jsonObjectManageRequest.getInt("seatRequested"));
                    manageRequestDTO.setAccountId(jsonObjectManageRequest.getInt("accountId"));
                    manageRequestDTO.setCarId(jsonObjectManageRequest.getInt("carId"));
                    manageRequestDTO.setDateCreated(new Date(jsonObjectManageRequest.getLong("dateCreated")));
                    manageRequestDTO.setDateUpdated(new Date(jsonObjectManageRequest.getLong("dateUpdated")));
                    manageRequestDTO.setRequestId(jsonObjectManageRequest.getInt("requestId"));
                    RequestStatus r = RequestStatus.valueOf(jsonObjectManageRequest.getString("requestStatus"));
                    manageRequestDTO.setRequestStatus(r);

                    manageRequestDTOList.add(manageRequestDTO);
                }


                List<AccountDTO> accountDTOList = new ArrayList<>();
                JSONArray jsonAccountMain = jsonObjectList.getJSONArray("accountDTOList");
                for(int y = 0; y<jsonAccountMain.length(); y++){
                    JSONObject jsonObjectAccount = jsonAccountMain.getJSONObject(y);

                    AccountDTO accountDTO = new AccountDTO();
                    accountDTO.setAccountId(jsonObjectAccount.getInt("accountId"));
                    accountDTO.setFullName(jsonObjectAccount.getString("fullName"));
                    accountDTO.setPhoneNum(jsonObjectAccount.getInt("phoneNum"));
                    accountDTO.setProfilePicture(Base64.decode(jsonObjectAccount.getString("sProfilePicture"), Base64.DEFAULT));

                    accountDTOList.add(accountDTO);
                }

                requestObject.setRequestDTO(newRequestDTO);
                requestObject.setManageRequestDTOList(manageRequestDTOList);
                requestObject.setAccountDTOList(accountDTOList);

                requestObjectList.add(requestObject);
            }

            return requestObjectList;

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
    protected void onPostExecute(List<RequestObject> requestObjectList){
        super.onPostExecute(requestObjectList);

        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

        if(requestStatus == RequestStatus.REQUEST_PENDING) {
            List<RequestDTO> requestDTOList = new ArrayList<>();
            List<ManageRequestDTO> manageRequestDTOList = new ArrayList<>();
            List<AccountDTO> accountDTOList = new ArrayList<>();

            for(int x = 0; x<requestObjectList.size(); x++) {
                requestDTOList.add(requestObjectList.get(x).getRequestDTO());

                if(requestObjectList.get(x).getManageRequestDTOList() != null && requestObjectList.get(x).getManageRequestDTOList().size() >0)
                    manageRequestDTOList.addAll(requestObjectList.get(x).getManageRequestDTOList());

                if(requestObjectList.get(x).getAccountDTOList() != null && requestObjectList.get(x).getAccountDTOList().size()>0)
                    accountDTOList.addAll(requestObjectList.get(x).getAccountDTOList());
            }

            RequestDAO requestDAO = new RequestDAO(context);
            for(RequestDTO requestDTO : requestDTOList){
                requestDAO.saveOrUpdateRequest(requestDTO);
            }

            ManageRequestDAO manageRequestDAO = new ManageRequestDAO(context);
            for(ManageRequestDTO manageRequestDTO : manageRequestDTOList){
                manageRequestDAO.saveOrUpdateManageRequest(manageRequestDTO);
            }

            final DriverRequestAdapterPending driverRequestAdapterPending = new DriverRequestAdapterPending(context, requestObjectList);
            listView.setAdapter(null);
            listView.setAdapter(driverRequestAdapterPending);
        }

        else if (requestStatus == RequestStatus.PASSENGER_ACCEPTED) {
            final DriverRequestUserAcceptedAdapter driverRequestUserAcceptedAdapter = new DriverRequestUserAcceptedAdapter(context, requestObjectList);
            listView.setAdapter(null);
            listView.setAdapter(driverRequestUserAcceptedAdapter);
        }

        else  {
            Utils.alertError(context, context.getString(R.string.error_server));
        }
    }
}
