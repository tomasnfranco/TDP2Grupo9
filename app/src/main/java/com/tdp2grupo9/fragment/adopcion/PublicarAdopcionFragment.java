package com.tdp2grupo9.fragment.adopcion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.SeleccionAtributosFragment;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.AtributoPublicacion;
import com.tdp2grupo9.modelo.publicacion.Castrado;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.CompatibleCon;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.PapelesAlDia;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Raza;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;
import com.tdp2grupo9.modelo.publicacion.VacunasAlDia;
import com.tdp2grupo9.view.foto.FotoPicker;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class PublicarAdopcionFragment extends SeleccionAtributosFragment implements View.OnClickListener, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        com.google.android.gms.location.LocationListener,
        GoogleMap.OnInfoWindowClickListener {

    private static final String PHOTO_INTENT_TYPE = "image/*";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int DATA_MAPA_REQUEST = 10;

    private Double latitud = Usuario.getInstancia().getLatitud();
    private Double longitud = Usuario.getInstancia().getLongitud();
    private Map<Integer, ImageView> photosMap;

    private int cantidadFotosCargadas = 0;
    private EditText nombreDescripcion;
    private EditText videoLink;
    private Checkable cuidadosEspeciales;
    private Checkable requiereHogarTransito;
    private TextView contacto;
    private TextView condicionesAdopcion;
    private Button btnPublicar;

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
    private FotoPicker mFotoPicker;

    private double lon;
    private double lat;
    static final int numberOptions = 10;
    String [] optionArray = new String[numberOptions];

    LocationRequest locationRequest;

    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;
    private GoogleApiClient googleApiClient;
    private MarkerOptions markerOptions;

    private PublicarAdopcionTask publicarAdopcionTask;
    private TextView tvZona;

    public static PublicarAdopcionFragment newInstance() {
        PublicarAdopcionFragment publicarAdopcion = new PublicarAdopcionFragment();
        return publicarAdopcion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mFragmentView = inflater.inflate(R.layout.fragment_publicar_mascotas, container, false);
        inicializarWidgets();
        map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.fragment_map_publicacion)).getMap();

        if(map != null){
            currentZoom = map.getMaxZoomLevel()-zoomOffset;
            map.setOnMapClickListener(this);

        } else {
            Toast.makeText(mFragmentView.getContext(), getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }

        googleApiClient = new GoogleApiClient.Builder(mFragmentView.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        prefs = getActivity().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();

        markerOptions = new MarkerOptions();
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        obtenerAtributos();
        return mFragmentView;
    }

    @Override
    protected void initializeSpinners() {
        createEspecieSpinner();
        createRazaSpinner();
        createSexoSpinner();
        createTamanioSpinner();
        ;
        createEdadSpinner();
        createColorSpinner();
        createCompatibleConSpinner();
        createPapelesDiaSpinner();
        createVacunasDiaSpinner();
        createCastradorSpinner();
        createProteccionSpinner();
        createEnergiaSpinner();
    }

    private void inicializarWidgets(){
        nombreDescripcion = (EditText) mFragmentView.findViewById(R.id.nombre_mascota_edit_text);
        videoLink = (EditText) mFragmentView.findViewById(R.id.cargar_video);
        cuidadosEspeciales = (Checkable) mFragmentView.findViewById(R.id.requiere_cuidados_especiales);
        requiereHogarTransito = (Checkable) mFragmentView.findViewById(R.id.requiere_hogar_transito);
        contacto = (EditText) mFragmentView.findViewById(R.id.contacto_edit_text);
        condicionesAdopcion = (EditText) mFragmentView.findViewById(R.id.condiciones_edit_text);
        btnPublicar = (Button) mFragmentView.findViewById(R.id.btn_publicar_adopcion);
        btnPublicar.setOnClickListener(this);
        tvZona = (TextView) mFragmentView.findViewById(R.id.tv_zona);
        mFotoPicker = (FotoPicker) mFragmentView.findViewById(R.id.foto_picker);
        mFotoPicker.setFragment(this);
    }

    @Override
    public void onClick(View v) {
        inicializarWidgets();
        if (v.getId() == R.id.btn_publicar_adopcion){
            if (isValidAttribute()){
                Publicacion publicacion = new Publicacion();

                publicacion.setNombreMascota(nombreDescripcion.getText().toString());
                publicacion.setEspecie(((Especie) spEspecie.getSelectedItem()));
                publicacion.setRaza(((Raza) spRaza.getSelectedItem()));
                publicacion.setColor(((Color) spColor.getSelectedItem()));
                publicacion.setSexo(((Sexo) spSexo.getSelectedItem()));
                publicacion.setTamanio(((Tamanio) spTamanio.getSelectedItem()));
                publicacion.setEdad(((Edad) spEdad.getSelectedItem()));
                publicacion.setCompatibleCon(((CompatibleCon) spCompatibleCon.getSelectedItem()));
                publicacion.setVacunasAlDia(((VacunasAlDia) spVacunas.getSelectedItem()));
                publicacion.setPapelesAlDia(((PapelesAlDia) spPapeles.getSelectedItem()));
                publicacion.setCastrado(((Castrado) spCastrado.getSelectedItem()));
                publicacion.setProteccion(((Proteccion) spProteccion.getSelectedItem()));
                publicacion.setEnergia(((Energia) spEnergia.getSelectedItem()));

                publicacion.setVideoLink("");
                if(!videoLink.getText().toString().isEmpty())
                    publicacion.setVideoLink(videoLink.getText().toString());

                publicacion.setLatitud(latitud);
                publicacion.setLongitud(longitud);

                publicacion.setNecesitaTransito(requiereHogarTransito.isChecked());
                publicacion.setRequiereCuidadosEspeciales(cuidadosEspeciales.isChecked());

                publicacion.setContacto("");
                if (!contacto.getText().toString().isEmpty())
                    publicacion.setContacto(contacto.getText().toString());

                publicacion.setCondiciones("");
                if (!condicionesAdopcion.getText().toString().isEmpty())
                    publicacion.setCondiciones(condicionesAdopcion.getText().toString());

                for (Bitmap bitmap : mFotoPicker.getImagesBitmaps()) {
                    publicacion.addImagen(bitmap);
                }

                publicarAdopcionTask = new PublicarAdopcionTask(publicacion);
                publicarAdopcionTask.execute((Void)null);


            }
        }
    }

    private boolean validateCampoRequeridoSpinner(Spinner spinner, String campoRequeridoString) {
        if (((AtributoPublicacion) spinner.getSelectedItem()).getId() == 0){
            ((TextView) spinner.getSelectedView()).setError(campoRequeridoString);
            return false;
        }
        return true;
    }

    private boolean validateCampoRequeridoEditText(EditText editText, String campoRequeridoString) {
        if (editText.getText().toString().isEmpty()){
            editText.setError(campoRequeridoString);
            return false;
        }
        return true;
    }

    private boolean isValidAttribute(){
        boolean valido = true;
        String campoRequeridoString = getString(R.string.campo_requerido);

        if (!validateCampoRequeridoSpinner(spEspecie, campoRequeridoString)) {valido = false;}
        if (!validateCampoRequeridoSpinner(spRaza, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spSexo, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spTamanio, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spEdad, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spColor, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spCompatibleCon, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spPapeles, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spVacunas, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spCastrado, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spProteccion, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spEnergia, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoEditText(nombreDescripcion, campoRequeridoString)) { valido = false;}

        return valido;
    }

    public void loadImageRequest() {
        Intent intent = new Intent();
        intent.setType(PHOTO_INTENT_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                mFotoPicker.addFoto(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class PublicarAdopcionTask extends AsyncTask<Void, Void, Boolean> {

        Publicacion publicacion;

        PublicarAdopcionTask(Publicacion publicacion) {
            this.publicacion = publicacion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                this.publicacion.guardarPublicacion(Usuario.getInstancia().getToken());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            publicarAdopcionTask = null;
            if (success) {
                nombreDescripcion.setText("");
                spEspecie.setSelection(0);
                spRaza.setSelection(0);
                spSexo.setSelection(0);
                spTamanio.setSelection(0);
                spEdad.setSelection(0);
                spColor.setSelection(0);
                spCompatibleCon.setSelection(0);
                spPapeles.setSelection(0);
                spVacunas.setSelection(0);
                spCastrado.setSelection(0);
                spProteccion.setSelection(0);
                spEnergia.setSelection(0);
                latitud = Usuario.getInstancia().getLatitud();
                longitud = Usuario.getInstancia().getLongitud();
                videoLink.setText("");
                requiereHogarTransito.setChecked(false);
                cuidadosEspeciales.setChecked(false);
                contacto.setText("");
                condicionesAdopcion.setText("");

                Toast toast3 = new Toast(getActivity().getApplicationContext());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) mFragmentView.findViewById(R.id.lytLayout));

                TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);

                toast3.setDuration(Toast.LENGTH_SHORT);
                toast3.setView(layout);
                toast3.show();


            } else {
                //TODO: mensaje indicando por que no se pudo publicar
            }
        }

        @Override
        protected void onCancelled() {
            publicarAdopcionTask = null;
        }
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
            Toast.makeText(mFragmentView.getContext(), getString(R.string.nomap_error),
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
        tvZona.setText(reverseGeocodeLocation(map_center.latitude,map_center.longitude));
    }

    private void startTracking(){
        googleApiClient.connect();
        Toast.makeText(mFragmentView.getContext(), "Location tracking started", Toast.LENGTH_SHORT).show();

    }

    private void stopTracking(){
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

        }
        googleApiClient.disconnect();
        Toast.makeText(mFragmentView.getContext(), "Location tracking halted", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(mFragmentView.getContext(), getString(R.string.nomap_error),
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
            Toast.makeText(mFragmentView.getContext(), getString(R.string.nomap_error),
                    Toast.LENGTH_LONG).show();
        }
    }

    private String reverseGeocodeLocation(double latitude, double longitude){
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
        tvZona.setText(title);

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


}


