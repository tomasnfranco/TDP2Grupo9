package com.tdp2grupo9.adopcion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.listview.ResultadosBusquedaActivity;
import com.tdp2grupo9.maps.MapsActivity;
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
import com.tdp2grupo9.view.SeleccionAtributosActivity;

public class BuscarMascotaActivity extends SeleccionAtributosActivity implements View.OnClickListener{


    private static final int DATA_MAPA_REQUEST = 10;
    private static final int RESULTADOS_BUSQUEDA = 5 ;
    private Double latitud = Usuario.getInstancia().getLatitud();
    private Double longitud = Usuario.getInstancia().getLongitud();
    private Button btnBuscarMascota;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_mascota);
        btnBuscarMascota = (Button) findViewById(R.id.btn_buscar_mascota);
        btnBuscarMascota.setOnClickListener(this);
        createMapsButton();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == DATA_MAPA_REQUEST) && (resultCode == RESULT_OK)){
            longitud = data.getDoubleExtra("longitud",0.0);
            latitud = data.getDoubleExtra("latitud", 0.0);
        }
    }

    private void createMapsButton() {
        ImageButton mapsButton = (ImageButton) findViewById(R.id.mapa_button);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                startActivityForResult(intent, DATA_MAPA_REQUEST);
            }
        });
    }

    private boolean isvalidEspecie(){
        if (((AtributoPublicacion) spEspecie.getSelectedItem()).getId() == 0){
            ((TextView) spEspecie.getSelectedView()).setError("Campo Requerido");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (isvalidEspecie()){
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
