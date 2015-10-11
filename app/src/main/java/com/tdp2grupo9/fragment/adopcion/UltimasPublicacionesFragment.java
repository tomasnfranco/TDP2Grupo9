package com.tdp2grupo9.fragment.adopcion;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.listview.Item;
import com.tdp2grupo9.listview.PublicacionesAdapter;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.PublicacionAtributos;
import com.tdp2grupo9.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emmanuelfls371 on 06/10/2015.
 */
public class UltimasPublicacionesFragment extends Fragment {

    private ListView listView;
    private BuscarAdopcionTask buscarAdopcionTask;
    private List<Publicacion> publicaciones;
    private PublicacionAtributos publicacionAtributos;
    private Publicacion publicacion;
    private Context context;
    private View viewContext;

    public static UltimasPublicacionesFragment newInstance() {
        return new UltimasPublicacionesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_ultimas_publicaciones, container, false);
        this.context = v.getContext();
        viewContext = v;
        publicacionAtributos = new PublicacionAtributos();
        publicacion = new Publicacion();
        publicacion.setLongitud(Usuario.getInstancia().getLongitud());
        publicacion.setLatitud(Usuario.getInstancia().getLatitud());
        buscarAdopcionTask = new BuscarAdopcionTask(publicacion);
        buscarAdopcionTask.execute((Void)null);
        cargarListView();
        return v;
    }

    private void cargarListView(){
        listView = (ListView) viewContext.findViewById(R.id.list_view_ultimas_publicaciones);
        listView.setAdapter(new PublicacionesAdapter(context, getPublicacionesMock()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long arg) {
                Item item = (Item) listView.getAdapter().getItem(position);
            }
        });
    }

    //TODO: hecho solo para ver los resultados de la busqueda, eliminar en produccion
    private List<Publicacion> getPublicacionesMock() {
        List<Publicacion> publicaciones = new ArrayList<Publicacion>();

        Publicacion p1 = new Publicacion();
        p1.setNombreMascota("Firulais");
        p1.setRequiereCuidadosEspeciales(true);
        p1.setNecesitaTransito(true);
        p1.setCondiciones("Tengo que ir a visitarlo una vez al mes");
        p1.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero1));

        Publicacion p2 = new Publicacion();
        p2.setNombreMascota("Chuletas");
        p2.setRequiereCuidadosEspeciales(true);
        p2.setNecesitaTransito(true);
        p2.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero2));

        Publicacion p3 = new Publicacion();
        p3.setNombreMascota("Ayudante de Santa");
        p3.setRequiereCuidadosEspeciales(true);
        p3.setNecesitaTransito(false);
        p3.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero3));

        Publicacion p4 = new Publicacion();
        p4.setNombreMascota("Huesos");
        p4.setRequiereCuidadosEspeciales(false);
        p4.setNecesitaTransito(true);
        p4.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero4));

        Publicacion p5 = new Publicacion();
        p5.setNombreMascota("Bobby");
        p5.setRequiereCuidadosEspeciales(false);
        p5.setNecesitaTransito(false);
        p5.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero5));

        Publicacion p6 = new Publicacion();
        p6.setNombreMascota("Max");
        p6.setRequiereCuidadosEspeciales(true);
        p6.setNecesitaTransito(false);
        p6.setCondiciones("Tiene que ser adoptado por una familia con hijos");
        p6.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero6));

        publicaciones.add(p1);
        publicaciones.add(p2);
        publicaciones.add(p3);
        publicaciones.add(p4);
        publicaciones.add(p5);
        publicaciones.add(p6);

        return publicaciones;
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
