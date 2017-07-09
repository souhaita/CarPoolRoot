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

import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.ViewType;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context = null;
    List<ProfileObject> profileObjectList;
    ViewType viewType;
    ViewType previousViewType;

    public ProfileAdapter(Context context, List<ProfileObject> profileObjectList){
        this.context = context;
        this.profileObjectList = profileObjectList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_profile_content, parent, false);
        return new AllViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        viewType = profileObjectList.get(position).getViewType();

        AllViewHolder view = (AllViewHolder)holder;

        view.llCar.setVisibility(View.VISIBLE);
        view.llData.setVisibility(View.VISIBLE);
        view.llProfile.setVisibility(View.VISIBLE);

        if(viewType == ViewType.PROFILE_PICTURE){
            view.llData.setVisibility(View.GONE);
            view.llCar.setVisibility(View.GONE);

            String firstName = profileObjectList.get(position).getLabel();
            String lastName = profileObjectList.get(position).getData();

            view.txtNameData.setText(firstName);
            view.txtNameLabel.setText(lastName);

            view.imgViewProfile.setImageBitmap(Utils.convertBlobToBitmap(profileObjectList.get(position).getProfilePicture()));
        }
        else if (viewType == ViewType.CARS_PICTURES){
            view.llProfile.setVisibility(View.GONE);
            view.llData.setVisibility(View.GONE);
            view.imgViewCar.setImageBitmap(Utils.convertBlobToBitmap(profileObjectList.get(position).getCarsPicture()));

        }
        else if(viewType == ViewType.DATA){
            view.llProfile.setVisibility(View.GONE);
            view.llCar.setVisibility(View.GONE);

            String label = profileObjectList.get(position).getLabel();
            String data = profileObjectList.get(position).getData();

            view.txtLabel.setText(label);
            view.txtData.setText(data);
        }
    }

    @Override
    public int getItemCount() {
        return profileObjectList.size();
    }

    public class AllViewHolder extends RecyclerView.ViewHolder {
        public TextView txtLabel, txtData, txtNameLabel, txtNameData;
        public ImageView imgViewProfile, imgViewCar ;

        public LinearLayout llProfile, llData, llCar;

        public AllViewHolder(View view) {
            super(view);

            txtLabel = (TextView) view.findViewById(R.id.txtLabel);
            txtData = (TextView) view.findViewById(R.id.txtData);

            txtNameLabel = (TextView) view.findViewById(R.id.txtNameLabel);
            txtNameData = (TextView) view.findViewById(R.id.txtNameData);

            imgViewProfile = (ImageView) view.findViewById(R.id.imgViewProfile);
            imgViewCar = (ImageView) view.findViewById(R.id.imgViewCar);

            llProfile = (LinearLayout) view.findViewById(R.id.llProfile);
            llData = (LinearLayout) view.findViewById(R.id.llData);
            llCar = (LinearLayout) view.findViewById(R.id.llCar);

        }
    }
}
