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
                if (tipo == TiposEnum.BUSQUEDA || tipo == TiposEnum.RECIENTES)
                    mPublicaciones = Publicacion.buscarPublicaciones(Usuario.getInstancia().getToken(), 0, 0, mPublicacion);
                else if (tipo == TiposEnum.MIS_PUBLICACIONES)
                    mPublicaciones = Usuario.getInstancia().obtenerMisPublicaciones(0, 0);
                else if (tipo == TiposEnum.MIS_POSTULACIONES)
                    mPublicaciones = Usuario.getInstancia().obtenerMisPostulaciones(0, 0);

                Thread.sleep(200);

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

}
