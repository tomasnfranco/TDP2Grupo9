package com.tdp2grupo9.adopcion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.tdp2grupo9.R;
import com.tdp2grupo9.maps.MapsActivity;
import com.tdp2grupo9.view.SeleccionAtributosActivity;

/**
 * Created by tfranco on 9/24/2015.
 */
public class PublicarAdopcionActivity extends SeleccionAtributosActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_adopcion);
        initializeWidgets();
    }

    private void initializeWidgets() {
        createEspecieSpinner();
        createRazaSpinner();
        createSexoSpinner();
        createTamanioSpinner();;
        createEdadSpinner();
        createColorSpinner();
        createCompatibleNiniosSpinner();
        createPapelesDiaSpinner();
        createVacunasDiaSpinner();
        createCastradorSpinner();
        createProteccionSpinner();
        createEnergiaSpinner();
        createMapsButton();
    }

    private void createMapsButton() {
        ImageButton mapsButton = (ImageButton) findViewById(R.id.mapa_button);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }

}
