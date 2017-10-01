package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;

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

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.PassengerHistoryAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
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

public class AsyncPassengerFetchHistory extends AsyncTask<Void, Void ,List<RequestObject >> {

    private Context context;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;

    public AsyncPassengerFetchHistory(final Context context, RecyclerView recyclerView ) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        String message = context.getString(R.string.async_driver_fetch_history);
        progressDialog = Utils.progressDialogue(context,message);
        progressDialog.show();
    }


    @Override
    protected List<RequestObject> doInBackground(Void... params) {
        JSONObject postData = new JSONObject();

        HttpURLConnection httpURLConnection = null;

        try{

            int userId = Utils.getCurrentAccount(context);

            postData.put("accountId", userId);

            httpURLConnection = (HttpURLConnection) new URL(WebService.API_PASSENGER_GET_HISTORY_LIST).openConnection();
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

                newRequestDTO.setSeatRequested(jsonObjectRequest.getInt("seatRequested"));
                newRequestDTO.setEventDate(new Date(jsonObjectRequest.getLong("eventDate")));
                newRequestDTO.setPlaceFrom(jsonObjectRequest.getString("placeFrom"));
                newRequestDTO.setPlaceTo(jsonObjectRequest.getString("placeTo"));
                newRequestDTO.setPrice(jsonObjectRequest.getInt("price"));
                newRequestDTO.setDateCreated(new Date(jsonObjectRequest.getLong("dateCreated")));
                newRequestDTO.setDateUpdated(new Date(jsonObjectRequest.getLong("dateUpdated")));
                newRequestDTO.setCarId(jsonObjectRequest.getInt("carId"));

                if(jsonObjectRequest.has("tripDuration")&& !jsonObjectRequest.isNull("tripDuration"))
                    newRequestDTO.setTripDuration(new Date(jsonObjectRequest.getLong("tripDuration")));



                List<ManageRequestDTO> manageRequestDTOList = new ArrayList<>();
                JSONArray jsonManageRequestMain = jsonObjectList.getJSONArray("manageRequestDTOList");
                for (int y = 0; y < jsonManageRequestMain.length(); y++) {
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

                    if(jsonObjectAccount.has("rating") && !jsonObjectAccount.isNull("rating")) {
                        accountDTO.setRating(jsonObjectAccount.getDouble("rating"));
                        accountDTO.setRatingCount(jsonObjectAccount.getInt("ratingCount"));
                    }

                    accountDTOList.add(accountDTO);
                }

                JSONArray jsonCarMain = jsonObjectList.getJSONArray("carDTOList");
                CarDTO carDTO = new CarDTO();
                for(int y = 0; y<jsonCarMain.length(); y++){
                    JSONObject jsonObject = jsonCarMain.getJSONObject(y);

                    carDTO.setCarId(jsonObject.getInt("carId"));
                    carDTO.setYear(jsonObject.getInt("year"));
                    carDTO.setAccountId(jsonObject.getInt("accountId"));
                    carDTO.setMake(jsonObject.getString("make"));
                    carDTO.setPlateNum(jsonObject.getString("plateNum"));
                    carDTO.setNumOfPassenger(jsonObject.getInt("numOfPassenger"));
                    carDTO.setModel(jsonObject.getString("model"));

                    if(jsonObject.getString("sPicture1") != null) {
                        carDTO.setHasPic1(true);
                        carDTO.setPicture1(Base64.decode(jsonObject.getString("sPicture1"), Base64.DEFAULT));
                    }

                    if(jsonObject.getString("sPicture2") != null) {
                        carDTO.setHasPic2(true);
                        carDTO.setPicture2(Base64.decode(jsonObject.getString("sPicture2"), Base64.DEFAULT));
                    }

                    if(jsonObject.getString("sPicture1") != null) {
                        carDTO.setHasPic3(true);
                        carDTO.setPicture3(Base64.decode(jsonObject.getString("sPicture3"), Base64.DEFAULT));
                    }

                    if(jsonObject.getString("sPicture1") != null) {
                        carDTO.setHasPic4(true);
                        carDTO.setPicture4(Base64.decode(jsonObject.getString("sPicture4"), Base64.DEFAULT));
                    }

                }
                List<CarDTO> carDTOList = new ArrayList<>();
                carDTOList.add(carDTO);

                requestObject.setCarDTO(carDTOList);
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

        if(requestObjectList != null && requestObjectList.size() >0) {
            PassengerHistoryAdapter passengerHistoryAdapter = new PassengerHistoryAdapter(context, requestObjectList);
            recyclerView.setAdapter(passengerHistoryAdapter);
        }

       /* else  {
            Utils.alertError(context, context.getString(R.string.error_server));
        }*/
    }
}
