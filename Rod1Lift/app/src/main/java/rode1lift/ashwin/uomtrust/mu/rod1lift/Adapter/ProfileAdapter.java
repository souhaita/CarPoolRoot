package rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityCarMake;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityCarPlateNum;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityCarSeats;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Activities.PickerActivityProfileName;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.ViewType;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_1;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_2;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_3;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_4;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_MAKE;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_PASSENGER;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_PLATE_NUM;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_PIC;

/**
 * Created by Ashwin on 09-Jul-17.
 */

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context = null;
    private List<ProfileObject> profileObjectList;
    private ViewType viewType;
    private Dialog menuDialog;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

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

            view.txtFullName.setText(firstName +" "+lastName);
            view.txtFullName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PickerActivityProfileName.class);
                    ((Activity) context).startActivityForResult(intent, CONSTANT.PROFILE_ACTIVITY_NAME);
                }
            });

            view.imgViewProfile.setImageBitmap(Utils.convertBlobToBitmap(profileObjectList.get(position).getProfilePicture()));

            view.imgViewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMenu(PROFILE_ACTIVITY_PROFILE_PIC);
                }
            });

        }
        else if (viewType == ViewType.CARS_PICTURES){
            view.llProfile.setVisibility(View.GONE);
            view.llData.setVisibility(View.GONE);
            view.imgViewCar.setImageBitmap(Utils.convertBlobToBitmap(profileObjectList.get(position).getCarsPicture()));

            view.imgViewCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (position){
                        case 1:
                            showMenu(PROFILE_ACTIVITY_PROFILE_CAR_1);
                            break;
                        case 2:
                            showMenu(PROFILE_ACTIVITY_PROFILE_CAR_2);
                            break;
                        case 3:
                            showMenu(PROFILE_ACTIVITY_PROFILE_CAR_3);
                            break;
                        case 4:
                            showMenu(PROFILE_ACTIVITY_PROFILE_CAR_4);
                            break;
                    }
                }
            });
        }
        else if(viewType == ViewType.DATA){
            view.llProfile.setVisibility(View.GONE);
            view.llCar.setVisibility(View.GONE);

            String label = profileObjectList.get(position).getLabel();
            String data = profileObjectList.get(position).getData();

            view.txtLabel.setText(label);
            view.txtData.setText(data);

            view.llData.setOnClickListener(new View.OnClickListener() {
                Intent intent = null;

                @Override
                public void onClick(View view) {
                    switch (position){
                        case 5:
                            intent = new Intent(context, PickerActivityCarMake.class);
                            ((Activity)context).startActivityForResult(intent, PROFILE_ACTIVITY_PROFILE_CAR_MAKE);
                            break;
                        case 6:
                            intent = new Intent(context, PickerActivityCarMake.class);
                            ((Activity)context).startActivityForResult(intent, PROFILE_ACTIVITY_PROFILE_CAR_MAKE);
                            break;
                        case 7:
                            intent = new Intent(context, PickerActivityCarPlateNum.class);
                            ((Activity)context).startActivityForResult(intent, PROFILE_ACTIVITY_PROFILE_CAR_PLATE_NUM);
                            break;
                        case 8:
                            intent = new Intent(context, PickerActivityCarSeats.class);
                            ((Activity)context).startActivityForResult(intent, PROFILE_ACTIVITY_PROFILE_CAR_PASSENGER);
                            break;
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return profileObjectList.size();
    }

    public class AllViewHolder extends RecyclerView.ViewHolder {
        public TextView txtLabel, txtData, txtFullName;
        public ImageView imgViewProfile, imgViewCar ;

        public LinearLayout llProfile, llData, llCar;

        public AllViewHolder(View view) {
            super(view);

            txtLabel = (TextView) view.findViewById(R.id.txtLabel);
            txtData = (TextView) view.findViewById(R.id.txtData);

            txtFullName = (TextView) view.findViewById(R.id.txtFullName);

            imgViewProfile = (ImageView) view.findViewById(R.id.imgViewProfile);
            imgViewCar = (ImageView) view.findViewById(R.id.imgViewCar);

            llProfile = (LinearLayout) view.findViewById(R.id.llProfile);
            llData = (LinearLayout) view.findViewById(R.id.llData);
            llCar = (LinearLayout) view.findViewById(R.id.llCar);

        }
    }

    public List<ProfileObject> getProfileObjectList() {
        return profileObjectList;
    }

    public void setProfileObjectList(List<ProfileObject> profileObjectList) {
        this.profileObjectList = profileObjectList;
    }


    private void showMenu(final Integer value) {
        menuDialog = new Dialog(context, R.style.WalkthroughTheme);
        menuDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menuDialog.setContentView(R.layout.dilaogue_camera);
        menuDialog.setCanceledOnTouchOutside(true);
        menuDialog.setCancelable(true);

        TextView txtGallery = (TextView) menuDialog.findViewById(R.id.txtGallery);
        TextView txtCamera = (TextView) menuDialog.findViewById(R.id.txtCamera);

        txtCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.alertError(context, "Not Yet Implemented");
            }
        });

        txtGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Pictures"), value);

                menuDialog.dismiss();
            }
        });

        menuDialog.show();
    }

}
