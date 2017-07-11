package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.widget.Toast;


import com.chauthai.swipereveallayout.SwipeRevealLayout;

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


import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityDriverManageRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.RequestAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.RequestDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.RequestStatus;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncDriverFetchRequest extends AsyncTask<RequestDTO, Void ,List<RequestDTO >> {

    private Context context;
    private ProgressDialog progressDialog;
    //private SwipeMenuListView swipeMenuListView;
    private SwipeRevealLayout swipeRevealLayout;

    public AsyncDriverFetchRequest(final Context context, /*SwipeMenuListView swipeMenuListView*/SwipeRevealLayout swipeRevealLayout) {
        this.context = context;
        //this.swipeMenuListView = swipeMenuListView;
        this.swipeRevealLayout = swipeRevealLayout;
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
                url = WebService.DRIVER_API_GET_PENDING_REQUEST_LIST;
            else
                url = WebService.DRIVER_API_OTHER_REQUEST_LIST;


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

            /*swipeMenuListView.setAdapter(requestAdapter);

            SwipeMenuCreator creator = new SwipeMenuCreator() {

                @Override
                public void create(SwipeMenu menu) {
                    //create an action that will be showed on swiping an item in the list
                    SwipeMenuItem item1 = new SwipeMenuItem(getApplicationContext());
                    item1.setBackground(new ColorDrawable(Color.DKGRAY));
                    item1.setWidth(200);
                    item1.setTitle("Action 1");
                    item1.setTitleSize(18);
                    item1.setTitleColor(Color.WHITE);
                    menu.addMenuItem(item1);


                    SwipeMenuItem item2 = new SwipeMenuItem(getApplicationContext());
                    item2.setBackground(new ColorDrawable(Color.RED));
                    item2.setWidth(200);
                    item2.setTitle("Action 2");
                    item2.setTitleSize(18);
                    item2.setTitleColor(Color.WHITE);
                    menu.addMenuItem(item2);
                }
            };

            swipeMenuListView.setMenuCreator(creator);

            swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                    switch (index) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "Action 1 for " , Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "Action 2 for " , Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return false;
                }});

            swipeMenuListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

                @Override
                public void onSwipeStart(int position) {
                    // swipe start
                }

                @Override
                public void onSwipeEnd(int position) {
                    // swipe end
                }
            });*/


            RequestDAO requestDAO = new RequestDAO(context);
            for(RequestDTO requestDTO : requestDTOList){
                requestDAO.saveOrUpdateRequest(requestDTO);
            }
        }

        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
