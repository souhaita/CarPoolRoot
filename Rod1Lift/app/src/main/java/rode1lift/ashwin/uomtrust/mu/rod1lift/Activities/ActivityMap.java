package rode1lift.ashwin.uomtrust.mu.rod1lift.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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

import rode1lift.ashwin.uomtrust.mu.rod1lift.DTO.AccountDTO;
import rode1lift.ashwin.uomtrust.mu.rod1lift.R;
import rode1lift.ashwin.uomtrust.mu.rod1lift.Utils.Utils;

/**
 * Created by Ashwin on 04-Jul-17.
 */

public class ActivityMap extends Fragment implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final long INTERVAL_IN_SECOND = 5;
    private static final long MIN_DISTANCE_IN_METER = 1;
    private MapView mMapView;
    private GoogleMap googleMap;

    private LocationRequest locationRequest;
    private LocationClient locationClient;

    private static final int PERMISSION_GPS = 1;

    private Double mLat = null;
    private Double mLng = null;

    private ArrayList markerPoints= new ArrayList();

    private FloatingActionButton fabClearMarker;
    private FloatingActionButton fabCreateTrip;
    private FloatingActionButton fabPathFromTwoPoints;
    private FloatingActionButton fabPathFromCurrentPosition;

    private FloatingActionMenu fabMenu;

    private boolean fromCurrentPosition = true;

    private String from = null, to = null;

    private MarkerOptions currentPositionMarker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_map, container, false);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        locationManager();

        fabMenu = (FloatingActionMenu)v.findViewById(R.id.fabMenu);
        fabMenu.setClosedOnTouchOutside(true);

        fabClearMarker = (FloatingActionButton) v.findViewById(R.id.fabClearMarker);
        fabPathFromCurrentPosition = (FloatingActionButton) v.findViewById(R.id.fabPathFromCurrentPosition);
        fabPathFromTwoPoints = (FloatingActionButton) v.findViewById(R.id.fabPathFromTwoPoints);
        fabCreateTrip = (FloatingActionButton) v.findViewById(R.id.fabCreateTrip);

        mMapView.getMap().setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                googleMap = mMapView.getMap();

                getLocation();
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(false);

                LatLng latLng = new LatLng(mLat, mLng);
                final String currentLocation = getAddressFromLocation(latLng);

                currentPositionMarker = new MarkerOptions();
                currentPositionMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                currentPositionMarker.position(latLng).title(getString(R.string.activity_map_marker_title_main_here)).snippet(currentLocation);
                googleMap.addMarker(currentPositionMarker);

                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                        if (markerPoints.size() > 1) {
                            markerPoints.clear();
                            googleMap.clear();
                            googleMap.addMarker(currentPositionMarker);
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
                            googleMap.addMarker(options);
                        }
                        else if (markerPoints.size() == 2) {
                            to = touchedLocation;
                            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            options.title(getString(R.string.activity_map_marker_title_to)).snippet(touchedLocation);
                            googleMap.addMarker(options);
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

                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(origin);
                            builder.include(dest);
                            LatLngBounds bounds = builder.build();

                            //googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 25));
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(origin).zoom(10).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }


                    }
                });


                //fab menu
                fabClearMarker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fabMenu.close(true);
                        markerPoints.clear();
                        googleMap.clear();
                        googleMap.addMarker(currentPositionMarker);
                    }
                });

                fabPathFromCurrentPosition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fabMenu.close(true);
                        markerPoints.clear();
                        googleMap.clear();
                        googleMap.addMarker(currentPositionMarker);
                        fromCurrentPosition = true;
                    }
                });

                fabPathFromTwoPoints.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fabMenu.close(true);
                        markerPoints.clear();
                        googleMap.clear();
                        googleMap.addMarker(currentPositionMarker);
                        fromCurrentPosition = false;
                    }
                });

                fabCreateTrip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(markerPoints.size() >0) {
                            fabMenu.close(true);
                            markerPoints.clear();
                            googleMap.clear();
                            googleMap.addMarker(currentPositionMarker);
                            startActivity();
                        }
                        else{
                            alertError();
                        }
                    }
                });
            }
        });

        return v;
    }

    private void startActivity(){
        Intent intent = new Intent(getActivity(), ActivityCreateTrip.class);
        intent.putExtra("from", from);
        intent.putExtra("to", to);
        startActivity(intent);
    }

    private void alertError(){
        fabMenu.close(true);
        markerPoints.clear();
        googleMap.clear();
        googleMap.addMarker(currentPositionMarker);

        Utils.vibrate(getActivity());

        String title = getString(R.string.activity_map_marker_create_trip_validation_title);
        String message = getString(R.string.activity_map_marker_create_trip_validation_message);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(getActivity().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton(getActivity().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity();
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    //ref: https://stackoverflow.com/questions/472313/android-reverse-geocoding-getfromlocation
    public String getAddressFromLocation(final LatLng latLng) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
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
                googleMap.addPolyline(lineOptions);
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


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onConnected(Bundle bundle) {
        getLocation();
    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker Title").snippet("Marker Description"));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_GPS);
        }
        else {

            //Utils.turnGPSOn(getActivity());

            if(locationClient != null && locationClient.getLastLocation() != null){
                mLat = locationClient.getLastLocation().getLatitude();
                mLng = locationClient.getLastLocation().getLongitude();
            }
            else{
                mLat = -20.241749;
                mLng = 57.489728;
                locationClient.requestLocationUpdates(locationRequest, this);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_GPS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Utils.turnGPSOn(getActivity());

                } else {

                }
                return;
            }
        }
    }

    private void locationManager(){
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(INTERVAL_IN_SECOND);
        locationRequest.setSmallestDisplacement(MIN_DISTANCE_IN_METER);

        locationClient = new LocationClient(this.getActivity(), this, this);
        locationClient.connect();
    }
}
