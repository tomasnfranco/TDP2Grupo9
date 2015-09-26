package com.tdp2grupo9.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tdp2grupo9.R;
import com.tdp2grupo9.interpeter.AtributosJSONInterpeter;

/**
 * Created by Tomás on 26/09/2015.
 */
public class SeleccionAtributosActivity extends Activity {

    protected AtributosJSONInterpeter atributosInterpeter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        atributosInterpeter = new AtributosJSONInterpeter(this);
    }

    protected void createSpinner(int spinnerId, String key, String atributoKey) {
        Spinner spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, atributosInterpeter.getAtributos(key, atributoKey));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    protected void createEspecieSpinner() {
        createSpinner(R.id.especie_spinner, AtributosJSONInterpeter.ESPECIE_KEY, AtributosJSONInterpeter.TIPO_KEY);
    }

    protected void createRazaSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.raza_spinner);
        String objects[] = {"Raza"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, objects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    protected void createSexoSpinner() {
        createSpinner(R.id.sexo_spinner, AtributosJSONInterpeter.SEXO_KEY, AtributosJSONInterpeter.TIPO_KEY);
    }

    protected void createTamanioSpinner() {
        createSpinner(R.id.tamanio_spinner, AtributosJSONInterpeter.TAMANIO_KEY, AtributosJSONInterpeter.TIPO_KEY);
    }

    protected void createEdadSpinner() {
        createSpinner(R.id.edad_spinner, AtributosJSONInterpeter.EDAD_KEY, AtributosJSONInterpeter.NOMBRE_KEY);
    }

    protected void createColorSpinner() {
        createSpinner(R.id.color_spinner, AtributosJSONInterpeter.COLOR_KEY, AtributosJSONInterpeter.NOMBRE_KEY);
    }

    protected void createCompatibleNiniosSpinner() {
        createSpinner(R.id.compatible_ninios_spinner, AtributosJSONInterpeter.COMPATIBLECON_KEY, AtributosJSONInterpeter.COMPATIBLE_CON_KEY);
    }

    protected void createPapelesDiaSpinner() {
        createSpinner(R.id.papeles_dia_spinner, AtributosJSONInterpeter.PAPELESALDIA_KEY, AtributosJSONInterpeter.TIPO_KEY);
    }

    protected void createVacunasDiaSpinner() {
        createSpinner(R.id.vacunas_dia_spinner, AtributosJSONInterpeter.VACUNASALDIA_KEY, AtributosJSONInterpeter.TIPO_KEY);
    }

    protected void createCastradorSpinner() {
        createSpinner(R.id.castrado_spinner, AtributosJSONInterpeter.CASTRADO_KEY, AtributosJSONInterpeter.TIPO_KEY);
    }

    protected void createProteccionSpinner() {
        createSpinner(R.id.proteccion_spinner, AtributosJSONInterpeter.PROTECCION_KEY, AtributosJSONInterpeter.TIPO_KEY);
    }

    protected void createEnergiaSpinner() {
        createSpinner(R.id.energia_spinner, AtributosJSONInterpeter.ENERGIA_KEY, AtributosJSONInterpeter.TIPO_KEY);
    }

}

