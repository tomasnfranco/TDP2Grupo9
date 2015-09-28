package com.tdp2grupo9.maps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tdp2grupo9.R;
import com.tdp2grupo9.adopcion.PublicarAdopcionActivity;
import com.tdp2grupo9.modelo.Usuario;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private double currentLat = Usuario.getInstancia().getLatitud();
    private double currentLon = Usuario.getInstancia().getLongitud();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        createGuardarUbicacionButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        mMap.addMarker(new MarkerOptions().position(new LatLng(currentLon, currentLat)).title("").icon(BitmapDescriptorFactory.fromResource(R.drawable.market_mascota)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(3));
        mMap.setOnMapClickListener(this);
    }

    private void createGuardarUbicacionButton() {
        Button guardarUbicacionButton = (Button) findViewById(R.id.guardar_ubicacion_button);
        guardarUbicacionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("longitud", currentLon);
                data.putExtra("latitud", currentLat);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latLng);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.market_mascota));
        markerOptions.title("Latitud: " + latLng.latitude + " : " + "Longitud: " + latLng.longitude);

        currentLat = latLng.latitude;
        currentLon = latLng.longitude;

        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.addMarker(markerOptions);
    }
}
