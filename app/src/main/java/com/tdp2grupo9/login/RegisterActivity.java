package com.tdp2grupo9.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tdp2grupo9.R;
import com.tdp2grupo9.inicio.HomeActivity;
import com.tdp2grupo9.modelo.Usuario;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private UserRegisterFacebookTask userRegisterFacebookTask = null;
    private Button btnRegistrarse;
    private EditText etDireccion;
    private EditText etTelefono;
    private CheckBox autopublicar;
    private CheckBox hogarTransito;
    private LatLng map_center;
    private GoogleMap googleMap;
    private Location currentLocation;
    private double currentLat = -34.5976786;
    private double currentLon = -58.4430195;

    private int zoomOffset = 5;
    private float currentZoom;
    LocationRequest locationRequest;

    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    private GoogleApiClient googleApiClient;

    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 1000;
    private static final double SPEED_THRESH = 1;
    private float bearing;
    private float speed;
    private float acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        etDireccion = (EditText) findViewById(R.id.editTextZona);
        etTelefono = (EditText) findViewById(R.id.editTextPhone);
        autopublicar = (CheckBox) findViewById(R.id.checkBoxAutopublicar);
        hogarTransito = (CheckBox) findViewById(R.id.checkBoxOfreceTransito);


        btnRegistrarse.setOnClickListener(this);

       SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentMap);

        googleMap =supportMapFragment.getMap();

        if(googleMap != null){

            currentZoom = googleMap.getMaxZoomLevel()-zoomOffset;
            googleMap.setOnMapClickListener(this);

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

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegistrarse){

            if (isValidTelefono() && isValidDireccion()) {
                Usuario.getInstancia().setDireccion(etDireccion.getText().toString());
                Usuario.getInstancia().setTelefono(etTelefono.getText().toString());
                Usuario.getInstancia().setLatitud(currentLat);
                Usuario.getInstancia().setLongitud(currentLon);
                Usuario.getInstancia().setAutopublicar(autopublicar.isChecked());
                Usuario.getInstancia().setOfreceTransito(hogarTransito.isChecked());
                userRegisterFacebookTask = new UserRegisterFacebookTask();
                userRegisterFacebookTask.execute((Void) null);
            }
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latLng);
        markerOptions.title("Latitud: " + latLng.latitude + " : " + "Longitud: " + latLng.longitude);
        currentLat = latLng.latitude;
        currentLon = latLng.longitude;

        googleMap.clear();
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onConnected(Bundle bundle) {

        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (currentLocation != null){
            currentLat = currentLocation.getLatitude();
            currentLon = currentLocation.getLongitude();
        }

        map_center = new LatLng(currentLat,currentLon);

        if(googleMap != null) {
            initializeMap();
        } else {
            Toast.makeText(this, getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
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

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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
            //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    protected void onPause() {

        if(googleMap != null){
            currentZoom = googleMap.getCameraPosition().zoom;
            prefsEditor.putFloat("KEY_ZOOM",currentZoom);
            prefsEditor.commit();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.contains("KEY_ZOOM") && googleMap != null){
            currentZoom = prefs.getFloat("KEY_ZOOM", googleMap.getMaxZoomLevel());
        }

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
        }
        googleApiClient.disconnect();

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onStop();
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
            if(locationExtras.containsKey("satellites")){
                numberSatellites = locationExtras.getInt("satellites");
            }
        }

        if(googleMap != null) {

            if(speed < SPEED_THRESH) {

                googleMap.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng));

            } else {

                //changeCamera(googleMap, currentLatLng, googleMap.getCameraPosition().zoom,
                  //      bearing, googleMap.getCameraPosition().tilt, true);
            }

        } else {
            Toast.makeText(this, getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void initializeMap(){
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(map_center,currentZoom));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private boolean isValidDireccion(){
        if (etDireccion.getText().toString().isEmpty() || etDireccion.getText().toString().length() < 4
                || etDireccion.getText().toString().length() > 200){
            etDireccion.setError("Direccion invalida.");
            return false;
        }else return true;
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
