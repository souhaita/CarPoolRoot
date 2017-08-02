package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncSendMessage;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.MessageDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;


public class PickerActivitySendMessage extends Activity {

    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker_send_message);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        userId = Utils.getCurrentAccount(PickerActivitySendMessage.this);

        final EditText eTxtMessage =(EditText)findViewById(R.id.eTxtMessage);


        TextView txtDone = (TextView)findViewById(R.id.txtDone);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(eTxtMessage);
            }
        });

        ImageButton imgClear = (ImageButton) findViewById(R.id.imgClear);
        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eTxtMessage.setText("");
            }
        });

        eTxtMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    setData(eTxtMessage);
                }
                return true;
            }
        });
    }

    private void setData(EditText eTxtMessage){
        if(!TextUtils.isEmpty(eTxtMessage.getText().toString())){

            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setAccountId(userId);
            messageDTO.setFromUser(true);
            messageDTO.setMessage(eTxtMessage.getText().toString());

            int otherUserId = getIntent().getIntExtra(CONSTANT.OTHER_USER_ID, -1);
            messageDTO.setOtherUserId(otherUserId);

            new AsyncSendMessage(PickerActivitySendMessage.this).execute(messageDTO);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.animator.slide_in_up, R.animator.slide_out_up);
    }
}
