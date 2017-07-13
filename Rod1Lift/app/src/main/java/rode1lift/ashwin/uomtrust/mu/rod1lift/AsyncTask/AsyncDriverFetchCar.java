package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncDriverFetchCar extends AsyncTask<CarDTO, Void ,CarDTO > {

    private Context context;
    private ProgressDialog progressDialog;

    public AsyncDriverFetchCar(final Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = Utils.progressDialogue(context, "Fetching your car details");
    }


    @Override
    protected CarDTO doInBackground(CarDTO... params) {
        JSONObject postData = new JSONObject();
        CarDTO carDetailsDTO = params[0];
        HttpURLConnection httpURLConnection = null;

        try{
            postData.put("accountId", carDetailsDTO.getAccountId());

            httpURLConnection = (HttpURLConnection) new URL(WebService.API_DRIVER_FETCH_CAR_DETAILS).openConnection();
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

            JSONObject jsonObject = new JSONObject(builder.toString());
            carDetailsDTO.setCarId(jsonObject.getInt("carId"));
            carDetailsDTO.setYear(jsonObject.getInt("year"));
            carDetailsDTO.setAccountId(jsonObject.getInt("accountId"));
            carDetailsDTO.setMake(jsonObject.getString("make"));
            carDetailsDTO.setPlateNum(jsonObject.getString("plateNum"));
            carDetailsDTO.setNumOfPassenger(jsonObject.getInt("numOfPassenger"));
            carDetailsDTO.setModel(jsonObject.getString("model"));

            if(jsonObject.getString("sPicture1") != null) {
                carDetailsDTO.setHasPic1(true);
                carDetailsDTO.setPicture1(Base64.decode(jsonObject.getString("sPicture1"), Base64.DEFAULT));
            }

            if(jsonObject.getString("sPicture2") != null) {
                carDetailsDTO.setHasPic2(true);
                carDetailsDTO.setPicture2(Base64.decode(jsonObject.getString("sPicture2"), Base64.DEFAULT));
            }

            if(jsonObject.getString("sPicture1") != null) {
                carDetailsDTO.setHasPic3(true);
                carDetailsDTO.setPicture3(Base64.decode(jsonObject.getString("sPicture3"), Base64.DEFAULT));
            }

            if(jsonObject.getString("sPicture1") != null) {
                carDetailsDTO.setHasPic4(true);
                carDetailsDTO.setPicture4(Base64.decode(jsonObject.getString("sPicture4"), Base64.DEFAULT));
            }

            return carDetailsDTO;

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            if(progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }

        return null;
    }

    @Override
    protected void onPostExecute(CarDTO carDTO){
        super.onPostExecute(carDTO);

        if(carDTO != null || carDTO.getCarId() != null) {
            new CarDAO(context).saveOrUpdateCar(carDTO);
            Intent intent = new Intent(context, ActivityMain.class);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
        else{
            Utils.showToast(context, context.getString(R.string.error_server));

        }
    }

}
