package com.tdp2grupo9.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    protected View viewMain;
    protected GoogleMap map;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_publicar_mascotas, container, false);
        viewMain = v;
        publicacionAtributos = new PublicacionAtributos();
        obtenerAtributosTask = new ObtenerAtributosTask(this.publicacionAtributos);
        obtenerAtributosTask.execute((Void) null);

       return v;
    }

    private void createSpinner(List<AtributoPublicacion> atributos, AtributoPublicacion atributoPublicacion, Spinner spinner, int id) {
        atributoPublicacion.setValor(atributoPublicacion.getName());
        atributos.add(0, atributoPublicacion);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(viewMain.getContext(), android.R.layout.simple_spinner_item, atributos);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) viewMain.findViewById(id);
        spinner.setAdapter(atributosArrayAdapter);
    }

    protected void createEspecieSpinner() {
        createSpinner(publicacionAtributos.getEspecies(), new Especie(), spEspecie, R.id.especie_spinner);
    }

    protected void createRazaSpinner() {
        createSpinner(publicacionAtributos.getRazas(), new Raza(), spRaza, R.id.raza_spinner);
    }

    protected void createSexoSpinner() {
        createSpinner(publicacionAtributos.getSexos(), new Sexo(), spSexo, R.id.sexo_spinner);
    }

    protected void createTamanioSpinner() {
        createSpinner(publicacionAtributos.getTamanios(), new Tamanio(), spTamanio, R.id.tamanio_spinner);
    }

    protected void createEdadSpinner() {
        createSpinner(publicacionAtributos.getEdades(), new Edad(), spEdad, R.id.edad_spinner);
    }

    protected void createColorSpinner() {
        createSpinner(publicacionAtributos.getColores(), new Color(), spColor, R.id.color_spinner);
    }

    protected void createCompatibleConSpinner() {
        createSpinner(publicacionAtributos.getCompatibilidades(), new CompatibleCon(), spCompatibleCon, R.id.compatible_con_spinner);
    }

    protected void createPapelesDiaSpinner() {
        createSpinner(publicacionAtributos.getPapelesAlDia(), new PapelesAlDia(), spPapeles, R.id.papeles_dia_spinner);
    }

    protected void createVacunasDiaSpinner() {
        createSpinner(publicacionAtributos.getVacunasAlDia(), new VacunasAlDia(), spVacunas, R.id.vacunas_dia_spinner);
    }

    protected void createCastradorSpinner() {
        createSpinner(publicacionAtributos.getCastrados(), new Castrado(), spCastrado, R.id.castrado_spinner);
    }

    protected void createProteccionSpinner() {
        createSpinner(publicacionAtributos.getProtecciones(), new Proteccion(), spProteccion, R.id.proteccion_spinner);
    }

    protected void createEnergiaSpinner() {
        createSpinner(publicacionAtributos.getEnergias(), new Energia(), spEnergia, R.id.energia_spinner);
    }

    protected abstract void initializeWidgets();

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
                initializeWidgets();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            obtenerAtributosTask = null;
        }
    }

}
