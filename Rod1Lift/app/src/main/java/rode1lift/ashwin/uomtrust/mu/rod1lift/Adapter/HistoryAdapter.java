package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;

/**
 * Created by vgobin on 18-Jul-17.
 */

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RequestObject> requestObjectList;
    private static LayoutInflater inflater = null;

    public HistoryAdapter(Context context, List<RequestObject> requestObjectList){
        this.requestObjectList = requestObjectList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_driver_view_history_content, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HistoryViewHolder view = (HistoryViewHolder)holder;
        view.txtDate.setText("POSITION "+position);
    }

    @Override
    public int getItemCount() {
        if(requestObjectList != null && requestObjectList.size() >0)
            return requestObjectList.size()*10;
        return 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDate;


        public HistoryViewHolder(View view) {
            super(view);

            txtDate = (TextView) view.findViewById(R.id.txtDate);

        }
    }

   /* @Override
    public int getCount() {
        if(requestObjectList != null && requestObjectList.size() >0)
            return requestObjectList.size()*10;
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.activity_driver_view_history_content, null);
        TextView txtDate = (TextView)convertView.findViewById(R.id.txtDate);
        txtDate.setText("TEST CARD" + position);
        return convertView;
    }*/
}
