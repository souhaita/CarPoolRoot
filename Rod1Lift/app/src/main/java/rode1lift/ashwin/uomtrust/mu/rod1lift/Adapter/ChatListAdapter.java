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

import rode1lift.ashwin.uomtrust.mu.rod1lift.R;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context = null;
    private List<ProfileObject> profileObjectList;

    public ChatListAdapter(Context context, List<ProfileObject> profileObjectList){
        this.context = context;
        this.profileObjectList = profileObjectList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return profileObjectList.size();
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
