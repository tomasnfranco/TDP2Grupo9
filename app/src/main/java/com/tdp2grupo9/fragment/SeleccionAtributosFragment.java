package com.tdp2grupo9.fragment;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;
import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.PublicacionAtributos;
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
import com.tdp2grupo9.view.AtributosPublicacionArrayAdapter;

import java.util.List;

public abstract class SeleccionAtributosFragment extends Fragment {

    private ObtenerAtributosTask obtenerAtributosTask;
    protected PublicacionAtributos publicacionAtributos;
    protected Spinner spEspecie;
    protected Spinner spRaza;
    protected Spinner spSexo;
    protected Spinner spTamanio;
    protected Spinner spEdad;
    protected Spinner spColor;
    protected Spinner spCompatibleCon;
    protected Spinner spPapeles;
    protected Spinner spVacunas;
    protected Spinner spCastrado;
    protected Spinner spProteccion;
    protected Spinner spEnergia;
    protected GoogleMap map;
    protected View mFragmentView;

    protected void obtenerAtributos() {
        publicacionAtributos = new PublicacionAtributos();
        obtenerAtributosTask = new ObtenerAtributosTask(this.publicacionAtributos);
        obtenerAtributosTask.execute((Void) null);
    }

    private Spinner createSpinner(List<AtributoPublicacion> atributos, AtributoPublicacion atributoPublicacion, int id) {
        atributoPublicacion.setValor(atributoPublicacion.getName());
        atributos.add(0, atributoPublicacion);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(mFragmentView.getContext(), android.R.layout.simple_spinner_item, atributos);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) mFragmentView.findViewById(id);
        spinner.setAdapter(atributosArrayAdapter);
        return spinner;
    }

    protected void createEspecieSpinner() {
        spEspecie = createSpinner(publicacionAtributos.getEspecies(), new Especie(), R.id.especie_spinner);
    }

    protected void createRazaSpinner() {
        spRaza = createSpinner(publicacionAtributos.getRazas(), new Raza(), R.id.raza_spinner);
    }

    protected void createSexoSpinner() {
        spSexo = createSpinner(publicacionAtributos.getSexos(), new Sexo(), R.id.sexo_spinner);
    }

    protected void createTamanioSpinner() {
        spTamanio = createSpinner(publicacionAtributos.getTamanios(), new Tamanio(), R.id.tamanio_spinner);
    }

    protected void createEdadSpinner() {
        spEdad = createSpinner(publicacionAtributos.getEdades(), new Edad(), R.id.edad_spinner);
    }

    protected void createColorSpinner() {
        spColor = createSpinner(publicacionAtributos.getColores(), new Color(), R.id.color_spinner);
    }

    protected void createCompatibleConSpinner() {
        spCompatibleCon = createSpinner(publicacionAtributos.getCompatibilidades(), new CompatibleCon(), R.id.compatible_con_spinner);
    }

    protected void createPapelesDiaSpinner() {
        spPapeles = createSpinner(publicacionAtributos.getPapelesAlDia(), new PapelesAlDia(), R.id.papeles_dia_spinner);
    }

    protected void createVacunasDiaSpinner() {
        spVacunas = createSpinner(publicacionAtributos.getVacunasAlDia(), new VacunasAlDia(), R.id.vacunas_dia_spinner);
    }

    protected void createCastradorSpinner() {
        spCastrado = createSpinner(publicacionAtributos.getCastrados(), new Castrado(), R.id.castrado_spinner);
    }

    protected void createProteccionSpinner() {
        spProteccion = createSpinner(publicacionAtributos.getProtecciones(), new Proteccion(), R.id.proteccion_spinner);
    }

    protected void createEnergiaSpinner() {
        spEnergia = createSpinner(publicacionAtributos.getEnergias(), new Energia(), R.id.energia_spinner);
    }

    protected abstract void initializeSpinners();

    public class ObtenerAtributosTask extends AsyncTask<Void, Void, Boolean> {

        PublicacionAtributos publicacionAtributos;
        ObtenerAtributosTask(PublicacionAtributos publicacionAtributos) {
            this.publicacionAtributos = publicacionAtributos;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                this.publicacionAtributos.cargarAtributos(Usuario.getInstancia().getToken());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            obtenerAtributosTask = null;
            if (success) {
                initializeSpinners();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            obtenerAtributosTask = null;
        }
    }

}
