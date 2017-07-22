package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.PassengerViewDriverProfileAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.ProfileAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.ProfileObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncDriverUpdateCar;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncUpdateAccount;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
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
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_CAR_YEAR;
import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PROFILE_ACTIVITY_PROFILE_PIC;

public class ActivityPassengerViewDriverProfile extends Activity {

    private List<ProfileObject> profileObjectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PassengerViewDriverProfileAdapter profileAdapter;
    private LinearLayout llMainProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        prepareDataList();
        profileAdapter = new PassengerViewDriverProfileAdapter(ActivityPassengerViewDriverProfile.this, profileObjectList);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewProfile);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ActivityPassengerViewDriverProfile.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(profileAdapter);

        TextView txtMenuHeader = (TextView)findViewById(R.id.txtMenuHeader);
        txtMenuHeader.setText(getString(R.string.title_activity_profile));

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView txtDone = (TextView)findViewById(R.id.txtDone);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void prepareDataList(){
        RequestObject requestObject = (RequestObject)getIntent().getSerializableExtra(CONSTANT.REQUEST_OBJECT);

        AccountDTO accountDTO = requestObject.getAccountDTOList().get(0);

        profileObjectList.add(new ProfileObject(ViewType.PROFILE_PICTURE, accountDTO));

        if(accountDTO.getAccountRole() == AccountRole.DRIVER) {

            CarDTO carDTO = requestObject.getCarDTO().get(0);

            if (carDTO.isHasPic1())
                profileObjectList.add(new ProfileObject(ViewType.CARS_PICTURES, carDTO.getPicture1()));

            if (carDTO.isHasPic2())
                profileObjectList.add(new ProfileObject(ViewType.CARS_PICTURES, carDTO.getPicture2()));

            if (carDTO.isHasPic3())
                profileObjectList.add(new ProfileObject(ViewType.CARS_PICTURES, carDTO.getPicture3()));

            if (carDTO.isHasPic4())
                profileObjectList.add(new ProfileObject(ViewType.CARS_PICTURES, carDTO.getPicture4()));


            String carDetails = getString(R.string.activity_complete_driver_registration_car_details);
            String year = getString(R.string.activity_complete_driver_registration_year);
            String plateNum = getString(R.string.activity_complete_driver_registration_plate_num);
            String numOfPassenger = getString(R.string.activity_complete_driver_registration_number_of_passenger);

            profileObjectList.add(new ProfileObject(ViewType.DATA, carDetails, carDTO.getMake() + " " + carDTO.getModel()));
            profileObjectList.add(new ProfileObject(ViewType.DATA, year, String.valueOf(carDTO.getYear())));
            profileObjectList.add(new ProfileObject(ViewType.DATA, plateNum, carDTO.getPlateNum()));
            profileObjectList.add(new ProfileObject(ViewType.DATA, numOfPassenger, String.valueOf(carDTO.getNumOfPassenger())));
        }
    }

    protected void onPause(){
        super.onPause();
        llMainProfile.setAnimation(null);
    }

    protected  void onResume(){
        super.onResume();
        llMainProfile = (LinearLayout)findViewById(R.id.llMainProfile);
        Utils.animateLayout(llMainProfile);
    }

}
