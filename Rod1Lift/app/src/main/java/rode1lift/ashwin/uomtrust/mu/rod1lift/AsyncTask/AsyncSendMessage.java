package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.MessageDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 02-Aug-17.
 */

public class AsyncSendMessage extends AsyncTask<MessageDTO, Void , Void> {

    Context context;

    public AsyncSendMessage(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }


    @Override
    protected Void doInBackground(MessageDTO... params) {
        JSONObject postData = new JSONObject();
        MessageDTO messageDTO = params[0];

        try{
            postData.put("accountId", messageDTO.getAccountId());
            postData.put("otherUserId", messageDTO.getOtherUserId());
            postData.put("message", messageDTO.getMessage());

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(WebService.API_SEND_MESSAGE).openConnection();
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
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
    }
}
