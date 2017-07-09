package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.ProfileAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.ProfileObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.Const;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.CarDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.ViewType;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;

public class ProfileActivity extends AppCompatActivity {

    private List<ProfileObject> profileObjectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);

        prepareDataList();
        ProfileAdapter profileAdapter = new ProfileAdapter(ProfileActivity.this, profileObjectList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleViewProfile);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(profileAdapter);
    }

    private void prepareDataList(){
        SharedPreferences prefs = getSharedPreferences(Const.appName, MODE_PRIVATE);
        Integer userId = prefs.getInt(Const.currentAccountId, -1);

        AccountDTO accountDTO = new AccountDAO(ProfileActivity.this).getAccountById(userId);

        profileObjectList.add(new ProfileObject(ViewType.PROFILE_PICTURE, accountDTO));

        if(accountDTO.getAccountRole() == AccountRole.DRIVER) {

            CarDTO carDTO = new CarDAO(ProfileActivity.this).getCarByAccountID(userId);

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

}
