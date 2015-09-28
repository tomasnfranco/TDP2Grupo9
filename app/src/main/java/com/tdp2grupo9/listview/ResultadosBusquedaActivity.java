package com.tdp2grupo9.listview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Raza;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;

import java.util.ArrayList;
import java.util.List;


public class ResultadosBusquedaActivity extends AppCompatActivity {

    private ListView listView;
    private BuscarAdopcionTask buscarAdopcionTask;
    private List<Publicacion> publicaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_listview_busqueda);

        Integer idEspecie = getIntent().getIntExtra("especie", 0);
        Integer idSexo = getIntent().getIntExtra("sexo", 0);
        Integer idEdad = getIntent().getIntExtra("edad", 0);
        Integer idRaza = getIntent().getIntExtra("raza", 0);
        Integer idTamanio = getIntent().getIntExtra("tamanio", 0);
        Integer idEnergia = getIntent().getIntExtra("energia", 0);
        Integer idProteccion = getIntent().getIntExtra("proteccion", 0);
        Integer idColor = getIntent().getIntExtra("color", 0);
        Double longitud = getIntent().getDoubleExtra("longitud", 0.0);
        Double latitud = getIntent().getDoubleExtra("latitud", 0.0);

        Especie especie = new Especie();
        especie.setId(idEspecie);
        Sexo sexo = new Sexo();
        sexo.setId(idSexo);
        Edad edad = new Edad();
        edad.setId(idEdad);
        Raza raza = new Raza();
        raza.setId(idRaza);
        Tamanio tamanio = new Tamanio();
        tamanio.setId(idTamanio);
        Energia energia = new Energia();
        energia.setId(idEnergia);
        Proteccion proteccion = new Proteccion();
        proteccion.setId(idProteccion);
        Color color = new Color();
        color.setId(idColor);

        Publicacion publicacion = new Publicacion();
        publicacion.setEspecie(especie);
        publicacion.setSexo(sexo);
        publicacion.setEdad(edad);
        publicacion.setRaza(raza);
        publicacion.setTamanio(tamanio);
        publicacion.setEnergia(energia);
        publicacion.setProteccion(proteccion);
        publicacion.setColor(color);
        publicacion.setLongitud(longitud);
        publicacion.setLatitud(latitud);

        buscarAdopcionTask = new BuscarAdopcionTask(publicacion);
        buscarAdopcionTask.execute((Void)null);
    }


    private void cargarListView(){
        Publicacion p1 = new Publicacion();
        p1.setNombreMascota("Pepe");
        p1.setRequiereCuidadosEspeciales(true);
        p1.setNecesitaTransito(true);

        Publicacion p2 = new Publicacion();
        p2.setNombreMascota("Sarmiento");
        p2.setRequiereCuidadosEspeciales(false);
        p2.setNecesitaTransito(true);

        publicaciones.add(p1);
        publicaciones.add(p2);

        this.listView = (ListView) findViewById(R.id.listView);
        List<Item> items = new ArrayList<Item>();
        for (Publicacion p : publicaciones){
            Item item = new Item(p, this);
            items.add(item);
        }

        this.listView.setAdapter(new ItemAdapter(this, items));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {

                Item item = (Item) listView.getAdapter().getItem(position);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class BuscarAdopcionTask extends AsyncTask<Void, Void, Boolean> {

        Publicacion publicacion;

        BuscarAdopcionTask(Publicacion publicacion) {
            this.publicacion = publicacion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                publicaciones = this.publicacion.buscarPublicaciones(Usuario.getInstancia().getToken(), 1,0,0, publicacion);

                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            buscarAdopcionTask = null;
            if (success) {
                cargarListView();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            buscarAdopcionTask = null;
        }
    }

}
