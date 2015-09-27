package com.tdp2grupo9.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tdp2grupo9.R;
import com.tdp2grupo9.inicio.HomeActivity;
import com.tdp2grupo9.modelo.Usuario;


public class RegisterActivity extends AppCompatActivity {

    private UserRegisterFacebookTask userRegisterFacebookTask = null;
    private View progressView;
    private Button btnRegistrarse;
    private EditText etDireccion;
    private EditText etTelefono;
    private CheckBox autopublicar;
    private CheckBox hogarTransito;
    private Double longitud;
    private Double latitud;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        etDireccion = (EditText) findViewById(R.id.editTextZona);
        etTelefono = (EditText) findViewById(R.id.editTextPhone);
        autopublicar = (CheckBox) findViewById(R.id.checkBoxAutopublicar);
        hogarTransito = (CheckBox) findViewById(R.id.checkBoxOfreceTransito);
        longitud = 222.2222;
        latitud = 222.222;

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: validar los datos
                Toast.makeText(getApplicationContext(),"Aplicacion probada.", Toast.LENGTH_LONG).show();
                Usuario.getInstancia().setDireccion(etDireccion.getText().toString());
                Usuario.getInstancia().setTelefono(etTelefono.getText().toString());
                Usuario.getInstancia().setLatitud(latitud);
                Usuario.getInstancia().setLongitud(longitud);
                Usuario.getInstancia().setAutopublicar(autopublicar.isChecked());
                Usuario.getInstancia().setOfreceTransito(hogarTransito.isChecked());
                userRegisterFacebookTask = new UserRegisterFacebookTask();
                userRegisterFacebookTask.execute((Void) null);
            }
        });

       SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentMap);

        // Getting a reference to the map
        googleMap =supportMapFragment.getMap();

        /// Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);
        initComponent(myLocation);


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title("Latitud: " + latLng.latitude + " : " + "Longitud: " + latLng.longitude);
                latitud = latLng.latitude;
                longitud = latLng.longitude;


                // Clears the previously touched position
                googleMap.clear();

                // Animating to the touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void initComponent(Location myLocation) {
        //set map type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();
        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);
        // Show the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
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
