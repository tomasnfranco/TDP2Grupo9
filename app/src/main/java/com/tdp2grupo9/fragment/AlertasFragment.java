package com.tdp2grupo9.fragment;


import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Alerta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AlertasFragment extends Fragment {

    protected List<Alerta> mAlertas;
    protected ListView mListView;
    protected BuscarAlertasTask buscarAlertasTask;

    protected abstract void cargarListView();

    public class BuscarAlertasTask extends AsyncTask<Void, Void, Boolean> {

        public BuscarAlertasTask() {
            mAlertas = new ArrayList<>();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                List<Alerta> alertaReducidas = new ArrayList<Alerta>();
                Thread.sleep(500);

            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            buscarAlertasTask = null;
            if (mAlertas.isEmpty())
                Toast.makeText(getActivity().getBaseContext(),
                        getString(R.string.mensajes_no_hay_alertas),
                        Toast.LENGTH_LONG).show();

            if (success) {
                cargarListView();
            } else {
                Toast.makeText(getActivity().getBaseContext(),
                        getString(R.string.error_busqueda_alertas),
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            buscarAlertasTask = null;
        }
    }

    public List<Alerta> obtenerListaAlertasMock(){
        List<Alerta> alertas = new ArrayList<>();
        return alertas;
    }
}

