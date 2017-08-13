package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RatingDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 03-Jun-17.
 */

public class AsyncPassengerRateTrip extends AsyncTask<RatingDTO, Void , Void> {

    public AsyncPassengerRateTrip() {

    }

    @Override
    protected void onPreExecute() {

    }


    @Override
    protected Void doInBackground(RatingDTO... params) {
        JSONObject postData = new JSONObject();
        RatingDTO tripRatingDTO = params[0];

        try{
            postData.put("raterId", tripRatingDTO.getRaterId());
            postData.put("carId", tripRatingDTO.getCarId());
            postData.put("requestId", tripRatingDTO.getRequestId());
            postData.put("comment", tripRatingDTO.getComment());
            postData.put("rating", tripRatingDTO.getRating());

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(WebService.API_PASSENGER_RATE_TRIP).openConnection();
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

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return null;

        }catch (Exception e){
            e.printStackTrace();
        } finally {

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
    }
}
