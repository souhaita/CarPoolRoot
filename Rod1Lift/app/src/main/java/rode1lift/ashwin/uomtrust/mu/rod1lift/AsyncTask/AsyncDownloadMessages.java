package rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.ChatListAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.MessageDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.MessageDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.WebService.WebService;

/**
 * Created by Ashwin on 02-Aug-17.
 */

public class AsyncDownloadMessages extends AsyncTask<Void, Void , List<MessageDTO>> {

    Context context;

    public AsyncDownloadMessages(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }


    @Override
    protected List<MessageDTO> doInBackground(Void... params) {
        JSONObject postData = new JSONObject();
        List<MessageDTO> messageDTOList = new ArrayList<>();
        HttpURLConnection httpURLConnection = null;

        try{
            int userId = Utils.getCurrentAccount(context);
            postData.put("accountId", userId);

            httpURLConnection = (HttpURLConnection) new URL(WebService.API_DOWNLOAD_MESSAGE).openConnection();
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

            JSONArray jsonMessageList =  new JSONArray(builder.toString());

            for(int x = 0; x < jsonMessageList.length(); x++) {
                JSONObject jsonObject = jsonMessageList.getJSONObject(x);

                MessageDTO messageDTO = new MessageDTO();

                int otherUserId = jsonObject.getInt("otherUserId");
                int serverAccountId = jsonObject.getInt("accountId");

                // messages received
                if(serverAccountId != userId){
                    messageDTO.setFromUser(false);
                    otherUserId = jsonObject.getInt("accountId");

                    try {
                        byte[] profilePicture = Base64.decode(jsonObject.getString("sProfilePicture"), Base64.DEFAULT);
                        Utils.savePictureToFolder(profilePicture, CONSTANT.PROFILE_PICTURE_PATH, String.valueOf(serverAccountId), false);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
                else{
                    messageDTO.setFromUser(true);
                }

                messageDTO.setSenderFullName(jsonObject.getString("senderFullName"));
                messageDTO.setMessageId(jsonObject.getInt("messageId"));
                messageDTO.setOtherUserId(otherUserId);
                messageDTO.setMessage(jsonObject.getString("message"));
                messageDTO.setAccountId(userId);
                messageDTOList.add(messageDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return messageDTOList;
    }

    @Override
    protected void onPostExecute(List<MessageDTO> messageDTOList){
        super.onPostExecute(messageDTOList);

        if(messageDTOList != null && messageDTOList.size() >0){
            for(MessageDTO m : messageDTOList) {
                new MessageDAO(context).saveorUpdate(m);
            }
        }

        RecyclerView rvChatList = (RecyclerView) ((Activity)context).findViewById(R.id.rvChatList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvChatList.setLayoutManager(layoutManager);

        List<MessageDTO> messageDTOs = new MessageDAO(context).getMessageList();
        ChatListAdapter chatListAdapter = new ChatListAdapter(context, messageDTOs);

        rvChatList.setAdapter(chatListAdapter);
    }
}
