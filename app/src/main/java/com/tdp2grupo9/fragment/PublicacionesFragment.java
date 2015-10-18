package com.tdp2grupo9.fragment;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.utils.TiposEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Tomas on 15/10/2015.
 */
public abstract class PublicacionesFragment extends Fragment {

    protected List<Publicacion> mPublicaciones;
    protected ExpandableListView mListView;
    protected BuscarAdopcionesTask mBuscarAdopcionesTask;

    protected abstract void cargarListView();

    public class BuscarAdopcionesTask extends AsyncTask<Void, Void, Boolean> {

        private Publicacion mPublicacion;
        private TiposEnum tipo;

        public BuscarAdopcionesTask(Publicacion publicacion, TiposEnum tipo) {
            this.mPublicacion = publicacion;
            this.tipo = tipo;
            mPublicaciones = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                List<Publicacion> publicacionesReducidas = new ArrayList<Publicacion>();

                if (tipo == TiposEnum.BUSQUEDA || tipo == TiposEnum.RECIENTES)
                    publicacionesReducidas = Publicacion.buscarPublicaciones(Usuario.getInstancia().getToken(), 1, 0, 0, mPublicacion);
                else if (tipo == TiposEnum.MIS_PUBLICACIONES)
                    publicacionesReducidas = Usuario.getInstancia().obtenerMisPublicaciones(0, 0);
                else if (tipo == TiposEnum.MIS_POSTULACIONES)
                    publicacionesReducidas = Usuario.getInstancia().obtenerMisPostulaciones(0,0);

                for (Publicacion publicacionReducida : publicacionesReducidas){
                    mPublicaciones.add(Publicacion.obtenerPublicacion(Usuario.getInstancia().getToken(), publicacionReducida.getId()));
                }

                Thread.sleep(500);

            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mBuscarAdopcionesTask = null;
            if (mPublicaciones.isEmpty())
                Toast.makeText(getActivity().getBaseContext(),
                        getString(R.string.mensaje_no_hay_publicaciones),
                        Toast.LENGTH_LONG).show();

            if (success) {
                cargarListView();
            } else {
                Toast.makeText(getActivity().getBaseContext(),
                        getString(R.string.error_busqueda_publicaciones),
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mBuscarAdopcionesTask = null;
        }
    }

    //TODO: hecho solo para ver los resultados de la busqueda, eliminar en produccion
    protected List<Publicacion> getPublicacionesMock() {
        List<Publicacion> publicaciones = new ArrayList<Publicacion>();

        Publicacion p1 = new Publicacion();
        p1.setNombreMascota("Firulais");
        p1.setRequiereCuidadosEspeciales(true);
        p1.setNecesitaTransito(true);
        p1.setCondiciones("Tengo que ir a visitarlo una vez al mes");
        p1.setFechaPublicacion(new Date(2015, 10, 15));
        p1.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero1));

        Publicacion p2 = new Publicacion();
        p2.setNombreMascota("Chuletas");
        p2.setRequiereCuidadosEspeciales(true);
        p2.setNecesitaTransito(true);
        p2.setCondiciones("Tengo que ir a visitarlo una vez al mes");
        p2.setFechaPublicacion(new Date(2015, 9, 15));
        p2.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero2));

        Publicacion p3 = new Publicacion();
        p3.setNombreMascota("Ayudante de Santa");
        p3.setRequiereCuidadosEspeciales(true);
        p3.setNecesitaTransito(false);
        p3.setFechaPublicacion(new Date(2015, 7, 1));
        p3.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero3));

        Publicacion p4 = new Publicacion();
        p4.setNombreMascota("Huesos");
        p4.setRequiereCuidadosEspeciales(false);
        p4.setNecesitaTransito(true);
        p4.setFechaPublicacion(new Date(2015, 7, 5));
        p4.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero4));

        Publicacion p5 = new Publicacion();
        p5.setNombreMascota("Bobby");
        p5.setRequiereCuidadosEspeciales(false);
        p5.setNecesitaTransito(false);
        p5.setFechaPublicacion(new Date(2015, 9, 15));
        p5.addImagen(BitmapFactory.decodeResource(getResources(), R.drawable.ovejero5));

        Publicacion p6 = new Publicacion();
        p6.setNombreMascota("Max");
        p6.setRequiereCuidadosEspeciales(true);
        p6.setNecesitaTransito(false);
        p6.setFechaPublicacion(new Date(2015, 1, 15));
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

}
