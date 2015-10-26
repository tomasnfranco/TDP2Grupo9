package com.tdp2grupo9.adopcion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.listview.ResultadosBusquedaActivity;
import com.tdp2grupo9.maps.MapsActivity;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.AtributoPublicacion;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Raza;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;
import com.tdp2grupo9.view.SeleccionAtributosActivity;

@Deprecated
public class BuscarMascotaActivity extends SeleccionAtributosActivity implements View.OnClickListener{

    private static final int DATA_MAPA_REQUEST = 10;
    private static final int RESULTADOS_BUSQUEDA = 5 ;
    private Double latitud = Usuario.getInstancia().getLatitud();
    private Double longitud = Usuario.getInstancia().getLongitud();
    private Button btnBuscarMascota;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_buscar_mascota);
        createMapsButton();
        createLimpiarFiltrosButton();
        createBuscarMascotaButton();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == DATA_MAPA_REQUEST) && (resultCode == RESULT_OK)){
            longitud = data.getDoubleExtra("longitud",0.0);
            latitud = data.getDoubleExtra("latitud", 0.0);
        }
    }

    private void createBuscarMascotaButton() {
        btnBuscarMascota = (Button) findViewById(R.id.buscar_mascota_button);
        btnBuscarMascota.setOnClickListener(this);
    }

    private void createMapsButton() {
        View mapsClickable = findViewById(R.id.mapa_clickable);
        mapsClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                startActivityForResult(intent, DATA_MAPA_REQUEST);
            }
        });
    }

    private void createLimpiarFiltrosButton() {
        View limpiarFiltrosClickable = findViewById(R.id.limpiar_filtros_clickable);
        limpiarFiltrosClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanFilters();
            }
        });
    }

    private void cleanFilters() {
        cleanSpinner(spEspecie);
        cleanSpinner(spRaza);
        cleanSpinner(spSexo);
        cleanSpinner(spTamanio);
        cleanSpinner(spEdad);
        cleanSpinner(spColor);
        cleanSpinner(spProteccion);
        cleanSpinner(spEnergia);
        latitud = Usuario.getInstancia().getLatitud();
        longitud = Usuario.getInstancia().getLongitud();
    }

    private void cleanSpinner(Spinner spinner) {
        spinner.setSelection(0);
    }

    private boolean isValidEspecie(){
        if (((AtributoPublicacion) spEspecie.getSelectedItem()).getId() == 0){
            ((TextView) spEspecie.getSelectedView()).setError("Campo Requerido");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (isValidEspecie()){
            Intent intent = new Intent(getApplicationContext(), ResultadosBusquedaActivity.class);
            intent.putExtra("especie",((Especie) spEspecie.getSelectedItem()).getId());
            intent.putExtra("raza",((Raza) spRaza.getSelectedItem()).getId());
            intent.putExtra("color", ((Color) spColor.getSelectedItem()).getId());
            intent.putExtra("sexo",((Sexo) spSexo.getSelectedItem()).getId());
            intent.putExtra("tamanio",((Tamanio) spTamanio.getSelectedItem()).getId());
            intent.putExtra("edad",((Edad) spEdad.getSelectedItem()).getId());
            intent.putExtra("proteccion",((Proteccion) spProteccion.getSelectedItem()).getId());
            intent.putExtra("energia",((Energia) spEnergia.getSelectedItem()).getId());
            intent.putExtra("latitud", latitud);
            intent.putExtra("longitud",longitud);
            startActivity(intent);

        }
    }

}
