package com.tdp2grupo9.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Usuario;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Tomas on 18/10/2015.
 */
public abstract class PublicacionesConMapaFragment extends SeleccionAtributosFragment implements GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        com.google.android.gms.location.LocationListener,
        GoogleMap.OnInfoWindowClickListener {

    protected static final String TAG = "Mapper";
    protected static final double SPEED_THRESH = 1;
    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 1000;

    protected Location currentLocation;
    protected double currentLat = Usuario.getInstancia().getLatitud();
    protected double currentLon = Usuario.getInstancia().getLongitud();
    protected GoogleApiClient googleApiClient;
    protected LatLng map_center;
    protected GoogleMap map;
    protected LocationRequest locationRequest;
    protected float currentZoom;
    protected MarkerOptions markerOptions;
    protected TextView tvZona;
    protected static final int numberOptions = 10;
    protected String[] optionArray = new String[numberOptions];
    protected float bearing;
    protected float speed;
    protected float acc = 1.2f;
    protected Circle localCircle;
    protected int zoomOffset = 10;
    protected SharedPreferences prefs;
    protected SharedPreferences.Editor prefsEditor;

    protected void createMap() {
        mMapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.fragment_map_publicacion);
        map = mMapFragment.getMap();
        if(map != null){
            currentZoom = map.getMaxZoomLevel()-zoomOffset;
            map.setOnMapClickListener(this);

        } else {
            Toast.makeText(mFragmentView.getContext(), getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }
    }

    protected void initializeGoogleApi() {
        googleApiClient = new GoogleApiClient.Builder(mFragmentView.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        markerOptions = new MarkerOptions();


        prefs = getActivity().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    @Override
    public void onConnected(Bundle dataBundle) {
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (currentLocation != null){
            currentLat = currentLocation.getLatitude();
            currentLon = currentLocation.getLongitude();
        }

        map_center = new LatLng(currentLat,currentLon);

        if (map != null) {
            initializeMap();
        } else {
            Toast.makeText(mFragmentView.getContext(), getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    private void initializeMap() {
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(map_center, currentZoom));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setRotateGesturesEnabled(false);

        markerOptions.position(map_center);

        map.clear();
        map.animateCamera(CameraUpdateFactory.newLatLng(map_center));
        map.addMarker(markerOptions);
        tvZona.setText(reverseGeocodeLocation(map_center.latitude, map_center.longitude));
    }

    private String reverseGeocodeLocation(double latitude, double longitude) {
        boolean omitCountry = true;
        String returnString = "";

        Geocoder gcoder = new Geocoder(mFragmentView.getContext());

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
                    currentLat = location.getLatitude();
                    currentLon = location.getLongitude();
                }
                country = location.getCountryName();
                if(country == null) {
                    country = "";
                } else {
                    country =  ", "+country;
                }
                raw += location+"\n";
                optionArray[opCount] = location.getAddressLine(0)+", "
                        +location.getAddressLine(1)+country;
                if(opCount == 0){
                    if(omitCountry){
                        returnString = location.getAddressLine(0)+", "
                                +location.getAddressLine(1);
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
            Log.i(TAG,"lat="+currentLat+" lon="+currentLon);


        } catch (IOException e){
            Log.e(TAG, "I/O Failure",e);
        }


        return returnString;

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location newLocation) {

        bearing = newLocation.getBearing();
        speed = newLocation.getSpeed();
        acc = newLocation.getAccuracy();

        currentLat = newLocation.getLatitude();
        currentLon = newLocation.getLongitude();
        LatLng currentLatLng = new LatLng(currentLat, currentLon);


        Bundle locationExtras = newLocation.getExtras();
        int numberSatellites = -1;
        if(locationExtras != null){
            Log.i(TAG, "Extras:" + locationExtras.toString());
            if(locationExtras.containsKey("satellites")){
                numberSatellites = locationExtras.getInt("satellites");
            }
        }

        if(map != null) {

            if(speed < SPEED_THRESH) {
                map.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng));

            } else {

                changeCamera(map, currentLatLng, map.getCameraPosition().zoom,
                        bearing, map.getCameraPosition().tilt, true);
            }
        } else {
            Toast.makeText(mFragmentView.getContext(), getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }
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
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        double lat = marker.getPosition().latitude;
        double lon = marker.getPosition().longitude;

        showStreetView(lat, lon);

        marker.remove();
        localCircle.remove();

    }

    private void showStreetView(double lat, double lon ){
        String uriString = "google.streetview:cbll="+lat+","+lon;
        Intent streetView = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uriString));
        startActivity(streetView);
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
        tvZona.setText(title);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.remove();
        localCircle.remove();
        return true;
    }

    private Circle addCircle(double lat, double lon, float radius,
                             String strokeColor, String fillColor){

        if(map == null){
            Toast.makeText(mFragmentView.getContext(), getString(R.string.nomap_error), Toast.LENGTH_LONG).show();
            return null;
        }

        CircleOptions circleOptions = new CircleOptions()
                .center( new LatLng(lat, lon) )
                .radius( radius )
                .strokeWidth(1)
                .fillColor(android.graphics.Color.parseColor(fillColor))
                .strokeColor(android.graphics.Color.parseColor(strokeColor));

        return map.addCircle(circleOptions);
    }

    @Override
    public void onPause() {

        // Store the current map zoom level
        if(map != null){
            currentZoom = map.getCameraPosition().zoom;
            prefsEditor.putFloat("KEY_ZOOM",currentZoom);
            prefsEditor.commit();
        }
        super.onPause();
        Log.i(TAG, "onPause: Zoom=" + currentZoom);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Restore previous zoom level (default to max zoom level if
        // no prefs stored)

        if (prefs.contains("KEY_ZOOM") && map != null){
            currentZoom = prefs.getFloat("KEY_ZOOM", map.getMaxZoomLevel());
        }
        Log.i(TAG, "onResume: Zoom=" + currentZoom);

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        if (googleApiClient.isConnected()) {
            // LocationServices.FusedLocationApi.removeLocationUpdates(this);
            //locationClient.removeLocationUpdates(this);
        }
        googleApiClient.disconnect();

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onStop();
    }

}