package com.tdp2grupo9.adopcion;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.listview.Item;
import com.tdp2grupo9.listview.ItemAdapter;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.PublicacionAtributos;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.Edad;

import java.util.ArrayList;
import java.util.List;

public class UltimasPublicacionesActivity extends Activity {
    private ListView listView;
    private BuscarAdopcionTask buscarAdopcionTask;
    private List<Publicacion> publicaciones;
    private PublicacionAtributos publicacionAtributos;
    private Publicacion publicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_listview_busqueda);

        publicacionAtributos = new PublicacionAtributos();

        publicacion = new Publicacion();
        publicacion.setLongitud(Usuario.getInstancia().getLongitud());
        publicacion.setLatitud(Usuario.getInstancia().getLatitud());

        buscarAdopcionTask = new BuscarAdopcionTask(publicacion);
        buscarAdopcionTask.execute((Void)null);
    }


    private void cargarListView(){
        /*Publicacion p1 = new Publicacion();
        p1.setNombreMascota("Pepe");
        Edad edad = new Edad();
        edad.setId(2);
        p1.setRequiereCuidadosEspeciales(true);
        p1.setNecesitaTransito(true);
        p1.setEdad(edad);

        Publicacion p2 = new Publicacion();
        p2.setNombreMascota("Sarmiento");
        p2.setRequiereCuidadosEspeciales(false);
        p2.setNecesitaTransito(true);
        p2.setCondiciones("Tiene condiciones");
        publicaciones = new ArrayList<Publicacion>();
        publicaciones.add(p1);
        publicaciones.add(p2);*/

        this.listView = (ListView) findViewById(R.id.listView);
        List<Item> items = new ArrayList<Item>();
        for (Publicacion p : publicaciones){
            Item item = new Item(p, this, publicacionAtributos);
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
                publicaciones = Publicacion.buscarPublicaciones(Usuario.getInstancia().getToken(), 1,0,0, publicacion);
                publicacionAtributos.cargarAtributos(Usuario.getInstancia().getToken());
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
