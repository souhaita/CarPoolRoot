package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.ActivityChatDetails;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.MessageDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.MessageDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class ChatDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MessageDTO> messageDTOList;
    private int otherUserId;
    private Context context;

    public ChatDetailAdapter(Context context, List<MessageDTO> messageDTOList){
        this.messageDTOList = messageDTOList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_details_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder)holder;

        viewHolder.txtFromUser.setVisibility(View.INVISIBLE);
        viewHolder.txtOtherUser.setVisibility(View.INVISIBLE);

        if(messageDTOList.get(position).isFromUser()) {
            viewHolder.txtFromUser.setVisibility(View.VISIBLE);
            viewHolder.txtFromUser.setText(messageDTOList.get(position).getMessage());
        }
        else{
            viewHolder.txtOtherUser.setVisibility(View.VISIBLE);
            viewHolder.txtOtherUser.setText(messageDTOList.get(position).getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageDTOList.size();
    }

    @Override
    public long getItemId(int position) {
        return messageDTOList.get(position).getMessageId();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFromUser, txtOtherUser;
        public LinearLayout llMain;

        public ViewHolder(View view) {
            super(view);

            txtOtherUser = (TextView) view.findViewById(R.id.txtOtherUser);
            txtFromUser = (TextView) view.findViewById(R.id.txtFromUser);

            llMain = (LinearLayout) view.findViewById(R.id.llMain);
        }
    }

    public List<MessageDTO> getMessageDTOList() {
        return messageDTOList;
    }

    public void setMessageDTOList(List<MessageDTO> messageDTOList) {
        this.messageDTOList = messageDTOList;
    }

    public int getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(int otherUserId) {
        this.otherUserId = otherUserId;
    }

}
