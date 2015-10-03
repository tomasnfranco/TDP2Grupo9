package com.tdp2grupo9.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tdp2grupo9.R;
import com.tdp2grupo9.inicio.HomeActivity;
import com.tdp2grupo9.modelo.Usuario;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        com.google.android.gms.location.LocationListener,
        GoogleMap.OnInfoWindowClickListener {

    private UserRegisterFacebookTask userRegisterFacebookTask = null;
    private Button btnRegistrarse;
    private EditText etTelefono;
    private TextView tvDireccion;
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 1000;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final double SPEED_THRESH = 1;

    private static final String TAG = "Mapper";
    private Location currentLocation;
    private double currentLat = -34.5976786;
    private double currentLon = -58.4430195;
    private GoogleMap map;
    private LatLng map_center;
    private int zoomOffset = 5;
    private float currentZoom;
    private float bearing;
    private float speed;
    private float acc = 1.2f;
    private Circle localCircle;

    private double lon;
    private double lat;
    static final int numberOptions = 10;
    String [] optionArray = new String[numberOptions];

    LocationRequest locationRequest;

    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    private GoogleApiClient googleApiClient;
    private MarkerOptions markerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        tvDireccion = (TextView) findViewById(R.id.tvDireccion);
        etTelefono = (EditText) findViewById(R.id.editTextPhone);
        btnRegistrarse.setOnClickListener(this);

        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.fragmentMap)).getMap();

        if(map != null){
            currentZoom = map.getMaxZoomLevel()-zoomOffset;
            map.setOnMapClickListener(this);

        } else {
            Toast.makeText(this, getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        prefs = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();

        markerOptions = new MarkerOptions();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {

        // Store the current map zoom level
        if(map != null){
            currentZoom = map.getCameraPosition().zoom;
            prefsEditor.putFloat("KEY_ZOOM",currentZoom);
            prefsEditor.commit();
        }
        super.onPause();
        Log.i(TAG,"onPause: Zoom="+currentZoom);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Restore previous zoom level (default to max zoom level if
        // no prefs stored)

        if (prefs.contains("KEY_ZOOM") && map != null){
            currentZoom = prefs.getFloat("KEY_ZOOM", map.getMaxZoomLevel());
        }
        Log.i(TAG,"onResume: Zoom="+currentZoom);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }


    @Override
    protected void onStop() {
        if (googleApiClient.isConnected()) {
            // LocationServices.FusedLocationApi.removeLocationUpdates(this);
            //locationClient.removeLocationUpdates(this);
        }
        googleApiClient.disconnect();

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onStop();
    }


    @Override
    public void onConnected(Bundle dataBundle) {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (currentLocation != null){
            currentLat = currentLocation.getLatitude();
            currentLon = currentLocation.getLongitude();
        }

        map_center = new LatLng(currentLat,currentLon);

        if(map != null) {
            initializeMap();
        } else {
            Toast.makeText(this, getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    private void initializeMap(){

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(map_center, currentZoom));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setRotateGesturesEnabled(false);

        markerOptions.position(map_center);

        map.clear();
        map.animateCamera(CameraUpdateFactory.newLatLng(map_center));
        map.addMarker(markerOptions);
        tvDireccion.setText(reverseGeocodeLocation(map_center.latitude,map_center.longitude));
    }

    private void startTracking(){
        googleApiClient.connect();
        Toast.makeText(this, "Location tracking started", Toast.LENGTH_SHORT).show();

    }

    private void stopTracking(){
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

        }
        googleApiClient.disconnect();
        Toast.makeText(this, "Location tracking halted", Toast.LENGTH_SHORT).show();
    }

    private void addMapMarker (double lat, double lon, float markerColor,
                               String title, String snippet){

        if(map != null){
            Marker marker = map.addMarker(new MarkerOptions()
                            .title(title)
                            .snippet(snippet)
                            .position(new LatLng(lat,lon))
                            .icon(BitmapDescriptorFactory.defaultMarker(markerColor))
            );
            marker.setDraggable(false);
            marker.showInfoWindow();
        } else {
            Toast.makeText(this, getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }

    }

    public String formatDecimal(double number, String formatPattern){

        DecimalFormat df = new DecimalFormat(formatPattern);
        return df.format(number);

    }


    private void changeCamera(GoogleMap map, LatLng center, float zoom,
                              float bearing, float tilt, boolean animate) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(center)         // Sets the center of the map
                .zoom(zoom)             // Sets the zoom
                .bearing(bearing)       // Sets the bearing of the camera
                .tilt(tilt)             // Sets the tilt of the camera relative to nadir
                .build();               // Creates a CameraPosition from the builder


        if(animate){
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onLocationChanged(Location newLocation) {

        bearing = newLocation.getBearing();
        speed = newLocation.getSpeed();
        acc = newLocation.getAccuracy();

        double lat = newLocation.getLatitude();
        double lon = newLocation.getLongitude();
        LatLng currentLatLng = new LatLng(lat, lon);


        Bundle locationExtras = newLocation.getExtras();
        int numberSatellites = -1;
        if(locationExtras != null){
            Log.i(TAG, "Extras:"+locationExtras.toString());
            if(locationExtras.containsKey("satellites")){
                numberSatellites = locationExtras.getInt("satellites");
            }
        }

        Log.i(TAG,"Lat="+formatDecimal(lat,"0.00000")
                +" Lon="+formatDecimal(lon,"0.00000")
                +" Bearing="+formatDecimal(bearing, "0.0")
                +" deg Speed="+formatDecimal(speed, "0.0")+" m/s"
                +" Accuracy="+formatDecimal(acc, "0.0")+" m"
                +" Sats="+numberSatellites);

        if(map != null) {

            if(speed < SPEED_THRESH) {
                map.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng));

            } else {

                changeCamera(map, currentLatLng, map.getCameraPosition().zoom,
                        bearing, map.getCameraPosition().tilt, true);
            }
        } else {
            Toast.makeText(this, getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }
    }

    private String reverseGeocodeLocation(double latitude, double longitude){
        boolean omitCountry = true;
        String returnString = "";

        Geocoder gcoder = new Geocoder(this);

        try{
            List<Address> results = null;
            if(Geocoder.isPresent()){
                results = gcoder.getFromLocation(latitude, longitude, 1);
            } else {
                Log.i(TAG,"No geocoder accessible on this device");
                return null;
            }
            Iterator<Address> locations = results.iterator();
            String raw = "\nRaw String:\n";
            String country;
            int opCount = 0;
            while(locations.hasNext()){
                Address location = locations.next();
                if(opCount==0 && location != null){
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                }
                country = location.getCountryName();
                if(country == null) {
                    country = "";
                } else {
                    country =  ", "+country;
                }
                raw += location+"\n";
                optionArray[opCount] = location.getAddressLine(0)+", "
                        +location.getAddressLine(1)+country+"\n";
                if(opCount == 0){
                    if(omitCountry){
                        returnString = location.getAddressLine(0)+", "
                                +location.getAddressLine(1)+"\n";
                    } else {
                        returnString = optionArray[opCount];
                    }
                }
                opCount ++;
            }
            Log.i(TAG, raw);
            Log.i(TAG,"\nOptions:\n");
            for(int i=0; i<opCount; i++){
                Log.i(TAG,"("+(i+1)+") "+optionArray[i]);
            }
            Log.i(TAG,"lat="+lat+" lon="+lon);


        } catch (IOException e){
            Log.e(TAG, "I/O Failure",e);
        }


        return returnString;

    }



    @Override
    public void onMapClick(LatLng latLng) {

        markerOptions.position(latLng);
        currentLat = latLng.latitude;
        currentLon = latLng.longitude;

        map.clear();
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        map.addMarker(markerOptions);

        String title = reverseGeocodeLocation(currentLat, currentLon);
        tvDireccion.setText(title);

    }

    private Circle addCircle(double lat, double lon, float radius,
                             String strokeColor, String fillColor){

        if(map == null){
            Toast.makeText(this, getString(R.string.nomap_error), Toast.LENGTH_LONG).show();
            return null;
        }

        CircleOptions circleOptions = new CircleOptions()
                .center( new LatLng(lat, lon) )
                .radius( radius )
                .strokeWidth(1)
                .fillColor(Color.parseColor(fillColor))
                .strokeColor(Color.parseColor(strokeColor));

        return map.addCircle(circleOptions);

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.remove();
        localCircle.remove();
        return true;
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;

        showStreetView(lat,lon);

        marker.remove();
        localCircle.remove();

    }

    private void showStreetView(double lat, double lon ){
        String uriString = "google.streetview:cbll="+lat+","+lon;
        Intent streetView = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uriString));
        startActivity(streetView);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }




    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegistrarse){

            if (isValidTelefono()) {
                Usuario.getInstancia().setDireccion(tvDireccion.getText().toString());
                Usuario.getInstancia().setTelefono(etTelefono.getText().toString());
                Usuario.getInstancia().setLatitud(currentLat);
                Usuario.getInstancia().setLongitud(currentLon);
                userRegisterFacebookTask = new UserRegisterFacebookTask();
                userRegisterFacebookTask.execute((Void) null);
            }
        }
    }

    private boolean isValidTelefono(){
        if (etTelefono.getText().toString().isEmpty() || etTelefono.getText().toString().length() < 8
                || etTelefono.getText().toString().length() > 15){
            etTelefono.setError("Telefono invalido");
            return false;
        }else return true;
    }

    public class UserRegisterFacebookTask  extends AsyncTask<Void, Void, Boolean> {

        UserRegisterFacebookTask () {

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Usuario.getInstancia().registrarConFacebook();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            userRegisterFacebookTask = null;
            if (success) {
                if (Usuario.getInstancia().isLogueado()) {
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
                finish();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            userRegisterFacebookTask = null;
        }
    }
}
