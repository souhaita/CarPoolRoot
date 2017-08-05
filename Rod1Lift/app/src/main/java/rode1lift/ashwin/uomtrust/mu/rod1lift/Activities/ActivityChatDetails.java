package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.ChatDetailAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDownloadMessages;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncSendMessage;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.MessageDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.MessageDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class ActivityChatDetails extends Activity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        TextView txtMenuHeader = (TextView)findViewById(R.id.txtMenuHeader);

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView txtDone = (TextView)findViewById(R.id.txtDone);
        txtDone.setVisibility(View.INVISIBLE);


        int otherUserId = getIntent().getIntExtra(CONSTANT.OTHER_USER_ID, -1);
        String senderFullName = getIntent().getStringExtra(CONSTANT.SENDER_FULL_NAME);
        txtMenuHeader.setText(senderFullName);

        final List<MessageDTO> messageDTOList = new MessageDAO(ActivityChatDetails.this).getMessageByOtherUserId(otherUserId);

        final RecyclerView rvChatDetails = (RecyclerView)findViewById(R.id.rvChatDetails);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityChatDetails.this, LinearLayoutManager.VERTICAL, false);
        rvChatDetails.setLayoutManager(layoutManager);

        final ChatDetailAdapter chatDetailAdapter = new ChatDetailAdapter(ActivityChatDetails.this, messageDTOList);
        rvChatDetails.setAdapter(chatDetailAdapter);
        rvChatDetails.smoothScrollToPosition(messageDTOList.size()-1);

        final EditText eTxtMessage = (EditText)findViewById(R.id.eTxtMessage);

        FloatingActionButton fabSendMessage = (FloatingActionButton)findViewById(R.id.fabSendMessage);
        fabSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = eTxtMessage.getText().toString();
                if(!TextUtils.isEmpty(message)){
                    MessageDTO messageDTO = messageDTOList.get(0);
                    messageDTO.setMessage(message);
                    messageDTO.setFromUser(true);
                    eTxtMessage.setText("");
                    messageDTOList.add(messageDTO);
                    chatDetailAdapter.setMessageDTOList(messageDTOList);
                    chatDetailAdapter.notifyDataSetChanged();
                    rvChatDetails.smoothScrollToPosition(messageDTOList.size()-1);
                    new AsyncSendMessage(ActivityChatDetails.this).execute(messageDTO);
                }
            }
        });


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(CONSTANT.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications

                } else if (intent.getAction().equals(CONSTANT.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String title = intent.getStringExtra(CONSTANT.FIREBASE_TITLE);
                    String message = intent.getStringExtra(CONSTANT.FIREBASE_MESSAGE);


                    if(title.equalsIgnoreCase("chat")) {
                        MessageDTO messageDTO = new MessageDTO();
                        messageDTO.setFromUser(false);
                        messageDTO.setMessage(message);
                        messageDTO.setOtherUserId(messageDTOList.get(0).getOtherUserId());
                        messageDTO.setAccountId(messageDTOList.get(0).getAccountId());

                        messageDTOList.add(messageDTO);
                        chatDetailAdapter.setMessageDTOList(messageDTOList);
                        chatDetailAdapter.notifyDataSetChanged();
                        rvChatDetails.smoothScrollToPosition(messageDTOList.size() - 1);
                    }
                }
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout llMain = (LinearLayout)findViewById(R.id.llMain);
        Utils.animateLayout(llMain);

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(CONSTANT.PUSH_NOTIFICATION));
    }
}
