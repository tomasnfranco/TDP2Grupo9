package com.tdp2grupo9.maps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tdp2grupo9.R;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private double currentLat;
    private double currentLon;
    private String nombreMascota = "";
    private String especieMascota = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentLat = getIntent().getDoubleExtra("latitud", 0.0);
        currentLon = getIntent().getDoubleExtra("longitud", 0.0);
        nombreMascota = getIntent().getStringExtra("nombre");
        especieMascota = getIntent().getStringExtra("especie");
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
        String msg = nombreMascota + " " +getBaseContext().getString(R.string.esta_aca);
        if (especieMascota.equals("Perro"))
            mMap.addMarker(new MarkerOptions().position(new LatLng(currentLat, currentLon)).title(msg).icon(BitmapDescriptorFactory.fromResource(R.drawable.market_perro)));
        else{
            mMap.addMarker(new MarkerOptions().position(new LatLng(currentLat, currentLon)).title(msg).icon(BitmapDescriptorFactory.fromResource(R.drawable.market_gato)));
        }

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(currentLat, currentLon), 16)));
    }

    private void createGuardarUbicacionButton() {
        Button guardarUbicacionButton = (Button) findViewById(R.id.guardar_ubicacion_button);
        guardarUbicacionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

}
