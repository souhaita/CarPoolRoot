package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.PassengerViewDriverProfileAdapter;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Adapter.ProfileObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncPassengerAcceptRequest;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.CarDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.RequestObject;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.ViewType;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

public class ActivityPassengerViewDriverProfile extends Activity {

    private List<ProfileObject> profileObjectList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PassengerViewDriverProfileAdapter profileAdapter;
    private LinearLayout llMainProfile;
    private RequestDTO requestDTO;

    private FloatingActionMenu fabMenu;
    private FloatingActionButton fabSeat1;
    private FloatingActionButton fabSeat2;
    private FloatingActionButton fabSeat3;
    private FloatingActionButton fabSeat4;
    private FloatingActionButton fabSeat5;

    private AccountDTO accountDTO;

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
        txtMenuHeader.setText(getString(R.string.title_activity_passenger_view_driver_profile));

        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        TextView txtDone = (TextView)findViewById(R.id.txtDone);
        txtDone.setVisibility(View.INVISIBLE);

        fabMenu = (FloatingActionMenu)findViewById(R.id.fabMenu);
        fabMenu.setVisibility(View.VISIBLE);

        fabSeat1 = (FloatingActionButton)findViewById(R.id.fabSeat1);
        fabSeat2 = (FloatingActionButton)findViewById(R.id.fabSeat2);
        fabSeat3 = (FloatingActionButton)findViewById(R.id.fabSeat3);
        fabSeat4 = (FloatingActionButton)findViewById(R.id.fabSeat4);
        fabSeat5 = (FloatingActionButton)findViewById(R.id.fabSeat5);

        int userId = Utils.getCurrentAccount(ActivityPassengerViewDriverProfile.this);
        final AccountDTO account = new AccountDAO(ActivityPassengerViewDriverProfile.this).getAccountById(userId);

        fabSeat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                requestDTO.setSeatRequested(1);

                if(account.getPhoneNum() != null && account.getPhoneNum() >0) {
                    new AsyncPassengerAcceptRequest(ActivityPassengerViewDriverProfile.this, null, null).execute(requestDTO);
                }
                else{
                    Intent intent = new Intent(ActivityPassengerViewDriverProfile.this, PickerActivityPhoneNumber.class);
                    startActivityForResult(intent, CONSTANT.PROFILE_ACTIVITY_PHONE_NUMBER);
                }

            }
        });

        fabSeat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                requestDTO.setSeatRequested(2);

                if(account.getPhoneNum() != null && account.getPhoneNum() >0) {
                    new AsyncPassengerAcceptRequest(ActivityPassengerViewDriverProfile.this, null, null).execute(requestDTO);
                }
                else{
                    Intent intent = new Intent(ActivityPassengerViewDriverProfile.this, PickerActivityPhoneNumber.class);
                    startActivityForResult(intent, CONSTANT.PROFILE_ACTIVITY_PHONE_NUMBER);
                }
            }
        });

        fabSeat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                requestDTO.setSeatRequested(3);

                if(account.getPhoneNum() != null && account.getPhoneNum() >0) {
                    new AsyncPassengerAcceptRequest(ActivityPassengerViewDriverProfile.this, null, null).execute(requestDTO);
                }
                else{
                    Intent intent = new Intent(ActivityPassengerViewDriverProfile.this, PickerActivityPhoneNumber.class);
                    startActivityForResult(intent, CONSTANT.PROFILE_ACTIVITY_PHONE_NUMBER);
                }
            }
        });

        fabSeat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                requestDTO.setSeatRequested(4);

                if(account.getPhoneNum() != null && account.getPhoneNum() >0) {
                    new AsyncPassengerAcceptRequest(ActivityPassengerViewDriverProfile.this, null, null).execute(requestDTO);
                }
                else{
                    Intent intent = new Intent(ActivityPassengerViewDriverProfile.this, PickerActivityPhoneNumber.class);
                    startActivityForResult(intent, CONSTANT.PROFILE_ACTIVITY_PHONE_NUMBER);
                }
            }
        });

        fabSeat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                requestDTO.setSeatRequested(5);

                if(account.getPhoneNum() != null && account.getPhoneNum() >0) {
                    new AsyncPassengerAcceptRequest(ActivityPassengerViewDriverProfile.this, null, null).execute(requestDTO);
                }
                else{
                    Intent intent = new Intent(ActivityPassengerViewDriverProfile.this, PickerActivityPhoneNumber.class);
                    startActivityForResult(intent, CONSTANT.PROFILE_ACTIVITY_PHONE_NUMBER);
                }
            }
        });

        if(requestDTO.getSeatAvailable() != null)
            setAvailableSeatNum();
        else{
            fabMenu.setVisibility(View.GONE);
        }

        FloatingActionButton fabCall = (FloatingActionButton)findViewById(R.id.fabCall);
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);

                Integer phoneNumber = account.getPhoneNum();
                if (phoneNumber != null) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+phoneNumber.toString()));
                    if (ActivityCompat.checkSelfPermission(ActivityPassengerViewDriverProfile.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ActivityPassengerViewDriverProfile.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
                        return;
                    }
                    startActivity(callIntent);
                }
            }
        });

        FloatingActionButton fabMessage = (FloatingActionButton)findViewById(R.id.fabMessage);
        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);

                Intent intent = new Intent(ActivityPassengerViewDriverProfile.this, PickerActivitySendMessage.class);
                intent.putExtra(CONSTANT.OTHER_USER_ID, requestDTO.getAccountId());
                startActivity(intent);
            }
        });

    }

    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONSTANT.PROFILE_ACTIVITY_PHONE_NUMBER) {
            new AsyncPassengerAcceptRequest(ActivityPassengerViewDriverProfile.this, null, null).execute(requestDTO);
        }
    }

    private void setAvailableSeatNum(){
        int seatAvailable = requestDTO.getSeatAvailable();

        switch (seatAvailable){
            case 1:
                fabSeat5.setVisibility(View.GONE);
                fabSeat4.setVisibility(View.GONE);
                fabSeat3.setVisibility(View.GONE);
                fabSeat2.setVisibility(View.GONE);
                break;

            case 2:
                fabSeat5.setVisibility(View.GONE);
                fabSeat4.setVisibility(View.GONE);
                fabSeat3.setVisibility(View.GONE);
                break;

            case 3:
                fabSeat5.setVisibility(View.GONE);
                fabSeat4.setVisibility(View.GONE);
                break;

            case 4:
                fabSeat5.setVisibility(View.GONE);
                break;

            default:
                fabSeat5.setVisibility(View.VISIBLE);
                fabSeat4.setVisibility(View.VISIBLE);
                fabSeat3.setVisibility(View.VISIBLE);
                fabSeat2.setVisibility(View.VISIBLE);
                fabSeat1.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void prepareDataList(){
        RequestObject requestObject = (RequestObject)getIntent().getSerializableExtra(CONSTANT.REQUEST_OBJECT);

        requestDTO = requestObject.getRequestDTO();

        accountDTO = requestObject.getAccountDTOList().get(0);

        byte [] profile = Utils.getPictures(null, accountDTO.getAccountId().toString(), false);
        accountDTO.setProfilePicture(profile);

        profileObjectList.add(new ProfileObject(ViewType.PROFILE_PICTURE, accountDTO));

        CarDTO carDTO = requestObject.getCarDTO().get(0);

        if (carDTO.isHasPic1()) {
            byte [] car1 = Utils.getPictures(carDTO.getCarId().toString(), "1", true);
            profileObjectList.add(new ProfileObject(ViewType.CARS_PICTURES, car1));
        }

        if (carDTO.isHasPic2()) {
            byte [] car2 = Utils.getPictures(carDTO.getCarId().toString(), "2", true);
            profileObjectList.add(new ProfileObject(ViewType.CARS_PICTURES, car2));
        }

        if (carDTO.isHasPic3()) {
            byte [] car3 = Utils.getPictures(carDTO.getCarId().toString(), "3", true);
            profileObjectList.add(new ProfileObject(ViewType.CARS_PICTURES, car3));
        }

        if (carDTO.isHasPic4()) {
            byte [] car4 = Utils.getPictures(carDTO.getCarId().toString(), "4", true);
            profileObjectList.add(new ProfileObject(ViewType.CARS_PICTURES, car4));
        }

        String carDetails = getString(R.string.activity_complete_driver_registration_car_details);
        String year = getString(R.string.activity_complete_driver_registration_year);
        String plateNum = getString(R.string.activity_complete_driver_registration_plate_num);
        String numOfPassenger = getString(R.string.activity_complete_driver_registration_number_of_passenger);

        profileObjectList.add(new ProfileObject(ViewType.DATA, carDetails, carDTO.getMake() + " " + carDTO.getModel()));
        profileObjectList.add(new ProfileObject(ViewType.DATA, year, String.valueOf(carDTO.getYear())));
        profileObjectList.add(new ProfileObject(ViewType.DATA, plateNum, carDTO.getPlateNum()));
        profileObjectList.add(new ProfileObject(ViewType.DATA, numOfPassenger, String.valueOf(carDTO.getNumOfPassenger())));
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