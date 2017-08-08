package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import rode1lift.ashwin.uomtrust.mu.rod1lift.AsyncTask.AsyncSaveDeviceToken;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DAO.AccountDAO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.MessageDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.ENUM.AccountRole;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Firebase.FirebaseNotificationUtils;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

import static rode1lift.ashwin.uomtrust.mu.rod1lift.Constant.CONSTANT.PERMISSION_GPS;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;

    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFrag;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private boolean allowLocationChange = true;

    private ArrayList markerPoints = new ArrayList();

    private boolean fromCurrentPosition = true;

    private String from = null, to = null;
    private MarkerOptions currentPositionMarker;

    private FloatingActionMenu fabMenu;

    private LatLng mLatLng;

    private String currentLocation = null;

    private AccountRole accountRole;

    //FireBase
    private static final String TAG = ActivityMain.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FireBase
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(CONSTANT.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(CONSTANT.TOPIC_GLOBAL);

                    saveFirebaseRegId();

                } else if (intent.getAction().equals(CONSTANT.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String title = intent.getStringExtra(CONSTANT.FIREBASE_TITLE);
                    String message = intent.getStringExtra(CONSTANT.FIREBASE_MESSAGE);

                    CoordinatorLayout clMain = (CoordinatorLayout)findViewById(R.id.clMain);
                    Snackbar snackbar = Snackbar.make(clMain, title+"\n"+message, Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    view.setBackgroundColor(getResources().getColor(R.color.red));

                    TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(getResources().getColor(R.color.white));
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tv.setTextSize(15f);

                    CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view.getLayoutParams();
                    params.gravity = Gravity.TOP;
                    params.height = (int)(toolbar.getHeight()*1.5);
                    view.setLayoutParams(params);
                    snackbar.show();
                }
            }
        };

        saveFirebaseRegId();
        //end


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Integer accountId = Utils.getCurrentAccount(getApplicationContext());

        AccountDTO accountDTO = new AccountDAO(this).getAccountById(accountId);
        accountRole = accountDTO.getAccountRole();

        setProfileDetails(navigationView, accountDTO);

        if(accountRole == AccountRole.PASSENGER) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.passenger_menu);
        }

        getSupportActionBar().setTitle("LIFT");

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFrag.getMapAsync(this);
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void saveFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(CONSTANT.SHARED_PREF, 0);
        String regId = pref.getString(CONSTANT.FIREBASE_REGISTRATION_KEY, null);

        if(regId != null){
            new AsyncSaveDeviceToken(ActivityMain.this).execute(regId);
        }

        Log.e(TAG, "Firebase reg id: " + regId);
    }

    private void setProfileDetails(NavigationView navigationView, AccountDTO accountDTO){
        View view =  navigationView.getHeaderView(0);

        ImageView imgProfilePic = (ImageView) view.findViewById(R.id.imgProfilePic);
        imgProfilePic.setImageBitmap(Utils.convertBlobToBitmap(accountDTO.getProfilePicture()));

        TextView txtFullName = (TextView) view.findViewById(R.id.txtFullName);
        txtFullName.setText(accountDTO.getFullName());

        LinearLayout llMainProfilePic = (LinearLayout)view.findViewById(R.id.llMainProfilePic);
        llMainProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityMain.this, ActivityProfile.class);
                startActivityForResult(intent, CONSTANT.MAIN_ACTIVITY);
            }
        });

        // Utils.animateLayout(llMainProfilePic);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (id == R.id.nav_create_trip) {
            Intent intent = new Intent(ActivityMain.this, ActivityCreateTrip.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_search_trip) {
            Intent intent = new Intent(ActivityMain.this, ActivitySearchTrip.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_chat) {
            Intent intent = new Intent(ActivityMain.this, ActivityChat.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_manage) {
            Intent intent;

            if(accountRole == AccountRole.DRIVER)
                intent = new Intent(ActivityMain.this, ActivityDriverManageRequest.class);
            else
                intent = new Intent(ActivityMain.this, ActivityPassengerManageRequest.class);

            startActivity(intent);
        }
        else if (id == R.id.nav_history) {
            Intent intent;

            if(accountRole == AccountRole.DRIVER)
                intent = new Intent(ActivityMain.this, ActivityDriverHistory.class);
            else
                intent = new Intent(ActivityMain.this,  ActivityPassengerHistory.class);

            startActivity(intent);
        }
        else if (id == R.id.nav_logout) {

            showLogoutPopup();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }

    private void showLogoutPopup(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);
        builder.setTitle(getString(R.string.activity_map_logout_title));
        builder.setMessage(getString(R.string.activity_map_logout_message));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ActivityMain.this, ActivityLogout.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    @Override
    protected void onResume(){
        super.onResume();

        Integer accountId = Utils.getCurrentAccount(getApplicationContext());

        AccountDTO accountDTO = new AccountDAO(this).getAccountById(accountId);

        setProfileDetails(navigationView, accountDTO);

        //FireBase
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(CONSTANT.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(CONSTANT.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        FirebaseNotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                setMapListener();
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
            setMapListener();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); // 5 seconds
        mLocationRequest.setSmallestDisplacement(10); // 10 meters
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location) {

        //Place current location marker
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        mLatLng = new LatLng(lat, lng);
        if(allowLocationChange) {
            allowLocationChange = !allowLocationChange;
            currentMarker();
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 11));
        }

    }

    private void currentMarker(){
        currentLocation = getAddressFromLocation(mLatLng);
        currentPositionMarker = new MarkerOptions();
        currentPositionMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentPositionMarker.position(mLatLng).title(getString(R.string.activity_map_marker_title_main_here)).snippet(currentLocation);
        mGoogleMap.addMarker(currentPositionMarker);
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                String title = getString(R.string.activity_main_gps_permission_title);
                String message = getString(R.string.activity_main_gps_permission_message);
                new AlertDialog.Builder(this)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ActivityMain.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_GPS );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA},
                        PERMISSION_GPS );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_GPS: {
                // If request is cancelled, the result arrays are empty.

                Utils.enableGPS(ActivityMain.this);

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                            mGoogleMap.setMyLocationEnabled(true);
                            setMapListener();
                        }
                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void setMapListener(){
        currentLocation = getAddressFromLocation(mLatLng);
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (markerPoints.size() > 1) {
                    markerPoints.clear();
                    mGoogleMap.clear();

                    currentMarker();
                }

                // Adding new item to the ArrayList
                markerPoints.add(latLng);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(latLng);

                String touchedLocation = getAddressFromLocation(latLng);
                if (markerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                    if(fromCurrentPosition) {
                        to = touchedLocation;
                        from = currentLocation;
                        options.title(getString(R.string.activity_map_marker_title_to)).snippet(touchedLocation);
                    }
                    else {
                        from = touchedLocation;
                        options.title(getString(R.string.activity_map_marker_title_from)).snippet(touchedLocation);
                    }
                    mGoogleMap.addMarker(options);
                }
                else if (markerPoints.size() == 2) {
                    to = touchedLocation;
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    options.title(getString(R.string.activity_map_marker_title_to)).snippet(touchedLocation);
                    mGoogleMap.addMarker(options);
                }
                // Checks, whether start and end locations are captured
                if (fromCurrentPosition || markerPoints.size() >= 2) {
                    LatLng origin, dest;

                    if(fromCurrentPosition){
                        origin = currentPositionMarker.getPosition();
                        dest = (LatLng) markerPoints.get(0);
                    }
                    else{
                        origin = (LatLng) markerPoints.get(0);
                        dest = (LatLng) markerPoints.get(1);
                    }

                    // Getting URL to the Google Directions API
                    String url = getUrl(origin, dest);
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                    //move map camera
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(origin).zoom(12).build();
                    mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        });

        FloatingActionButton fabClearMarker;
        FloatingActionButton fabCreateTrip;
        FloatingActionButton fabSearchTrip;
        FloatingActionButton fabPathFromTwoPoints;
        FloatingActionButton fabPathFromCurrentPosition;
        FloatingActionButton fabLocateMe;

        fabMenu = (FloatingActionMenu)findViewById(R.id.fabMenu);
        fabMenu.setClosedOnTouchOutside(true);

        fabClearMarker = (FloatingActionButton) findViewById(R.id.fabClearMarker);
        fabPathFromCurrentPosition = (FloatingActionButton) findViewById(R.id.fabPathFromCurrentPosition);
        fabPathFromTwoPoints = (FloatingActionButton) findViewById(R.id.fabPathFromTwoPoints);
        fabCreateTrip = (FloatingActionButton) findViewById(R.id.fabCreateTrip);
        fabSearchTrip = (FloatingActionButton) findViewById(R.id.fabSearchTrip);
        fabLocateMe = (FloatingActionButton) findViewById(R.id.fabLocateMe);

        if(accountRole == AccountRole.DRIVER){
            fabCreateTrip.setVisibility(View.VISIBLE);
        }
        else{
            fabSearchTrip.setVisibility(View.VISIBLE);
        }

        fabSearchTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                startActivity_passenger();
            }
        });

        fabLocateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                //markerPoints.clear();
                //mGoogleMap.clear();
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 11));
                mGoogleMap.addMarker(currentPositionMarker);
            }
        });

        //fab menu
        fabClearMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                markerPoints.clear();
                mGoogleMap.clear();
                mGoogleMap.addMarker(currentPositionMarker);
            }
        });

        fabPathFromCurrentPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                markerPoints.clear();
                mGoogleMap.clear();
                mGoogleMap.addMarker(currentPositionMarker);
                fromCurrentPosition = true;
            }
        });

        fabPathFromTwoPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                markerPoints.clear();
                mGoogleMap.clear();
                mGoogleMap.addMarker(currentPositionMarker);
                fromCurrentPosition = false;
            }
        });

        fabCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(markerPoints.size() >0) {
                    fabMenu.close(true);
                    markerPoints.clear();
                    mGoogleMap.clear();
                    mGoogleMap.addMarker(currentPositionMarker);
                    startActivity_driver();
                }
                else{
                    alertError();
                }
            }
        });
    }


    private void startActivity_driver(){
        Intent intent = new Intent(ActivityMain.this, ActivityCreateTrip.class);
        intent.putExtra(CONSTANT.CREATE_TRIP_FROM, from);
        intent.putExtra(CONSTANT.CREATE_TRIP_TO, to);
        startActivity(intent);
    }

    private void startActivity_passenger(){
        Intent intent = new Intent(ActivityMain.this, ActivitySearchTrip.class);
        intent.putExtra(CONSTANT.SEARCH_TRIP_FROM, from);
        intent.putExtra(CONSTANT.SEARCH_TRIP_TO, to);
        startActivity(intent);
    }

    private void alertError(){
        fabMenu.close(true);
        markerPoints.clear();
        mGoogleMap.clear();
        mGoogleMap.addMarker(currentPositionMarker);

        Utils.vibrate(ActivityMain.this);

        String title = getString(R.string.activity_map_marker_create_trip_validation_title);
        String message = getString(R.string.activity_map_marker_create_trip_validation_message);

        final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(ActivityMain.this.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(ActivityMain.this.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity_driver();
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    //ref: https://stackoverflow.com/questions/472313/android-reverse-geocoding-getfromlocation
    public String getAddressFromLocation(final LatLng latLng) {
        Geocoder geocoder = new Geocoder(ActivityMain.this, Locale.getDefault());
        try {
            List<Address> list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (list != null && list.size() > 0) {
                return list.get(0).getLocality();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DataParser parser = new DataParser();
                // Starts parsing data
                routes = parser.parse(jObject);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mGoogleMap.addPolyline(lineOptions);
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    public class DataParser {

        /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
        public List<List<HashMap<String,String>>> parse(JSONObject jObject){

            List<List<HashMap<String, String>>> routes = new ArrayList<>() ;
            JSONArray jRoutes;
            JSONArray jLegs;
            JSONArray jSteps;

            try {

                jRoutes = jObject.getJSONArray("routes");

                /** Traversing all routes */
                for(int i=0;i<jRoutes.length();i++){
                    jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<>();

                    /** Traversing all legs */
                    for(int j=0;j<jLegs.length();j++){
                        jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                        /** Traversing all steps */
                        for(int k=0;k<jSteps.length();k++){
                            String polyline = "";
                            polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                            List<LatLng> list = decodePoly(polyline);

                            /** Traversing all points */
                            for(int l=0;l<list.size();l++){
                                HashMap<String, String> hm = new HashMap<>();
                                hm.put("lat", Double.toString((list.get(l)).latitude) );
                                hm.put("lng", Double.toString((list.get(l)).longitude) );
                                path.add(hm);
                            }
                        }
                        routes.add(path);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return routes;
        }


        /**
         * Method to decode polyline points
         * Courtesy : https://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
         * */
        private List<LatLng> decodePoly(String encoded) {

            List<LatLng> poly = new ArrayList<>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }

            return poly;
        }
    }
}