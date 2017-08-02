package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.MessageDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context = null;
    private List<MessageDTO> messageDTOList;

    public ChatListAdapter(Context context, List<MessageDTO> messageDTOList){
        this.context = context;
        this.messageDTOList = messageDTOList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.txtContent.setText(messageDTOList.get(position).getMessage());
        viewHolder.txtFullName.setText(messageDTOList.get(position).getMessage());
        byte [] imgProfile = Utils.getPictures(messageDTOList.get(position).getOtherUserId().toString(), CONSTANT.PROFILE_PICTURE_PATH,false);
        viewHolder.imgProfile.setImageBitmap(Utils.convertBlobToBitmap(imgProfile));

        viewHolder.llMainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent()
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageDTOList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtContent, txtFullName;
        public ImageView imgProfile ;

        public LinearLayout llMainContent;

        public ViewHolder(View view) {
            super(view);

            txtContent = (TextView) view.findViewById(R.id.txtContent);
            txtFullName = (TextView) view.findViewById(R.id.txtFullName);

            llMainContent = (LinearLayout) view.findViewById(R.id.llMainContent);

            imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        }
    }
}
