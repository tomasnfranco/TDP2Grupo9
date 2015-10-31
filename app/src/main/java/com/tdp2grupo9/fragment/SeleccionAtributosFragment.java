package com.tdp2grupo9.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Spinner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.tdp2grupo9.R;
import com.tdp2grupo9.adapter.AtributosPublicacionArrayAdapter;
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class SeleccionAtributosFragment extends Fragment {

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
    protected MapFragment mMapFragment;
    protected View mFragmentView;

    private Spinner createSpinner(List<AtributoPublicacion> atributos, AtributoPublicacion atributoPublicacion, int id) {
        try {
            Field resourceField = R.string.class.getDeclaredField(atributoPublicacion.getName());
            int resourceId = resourceField.getInt(resourceField);
            atributoPublicacion.setValor(mFragmentView.getContext().getString(resourceId));
        } catch (Exception e) {
            atributoPublicacion.setValor(atributoPublicacion.getName());
        }

        List<AtributoPublicacion> atts = new ArrayList<>();
        atts.addAll(atributos);
        atts.add(0, atributoPublicacion);

        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(mFragmentView.getContext(), android.R.layout.simple_spinner_item, atts);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) mFragmentView.findViewById(id);
        spinner.setAdapter(atributosArrayAdapter);
        return spinner;
    }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    protected void createEspecieSpinner() {
        spEspecie = createSpinner(PublicacionAtributos.getInstancia().getEspecies(), new Especie(), R.id.especie_spinner);
    }

    protected void createRazaSpinner() {
        spRaza = createSpinner(PublicacionAtributos.getInstancia().getRazas(), new Raza(), R.id.raza_spinner);
    }

    protected void createSexoSpinner() {
        spSexo = createSpinner(PublicacionAtributos.getInstancia().getSexos(), new Sexo(), R.id.sexo_spinner);
    }

    protected void createTamanioSpinner() {
        spTamanio = createSpinner(PublicacionAtributos.getInstancia().getTamanios(), new Tamanio(), R.id.tamanio_spinner);
    }

    protected void createEdadSpinner() {
        spEdad = createSpinner(PublicacionAtributos.getInstancia().getEdades(), new Edad(), R.id.edad_spinner);
    }

    protected void createColorSpinner() {
        spColor = createSpinner(PublicacionAtributos.getInstancia().getColores(), new Color(), R.id.color_spinner);
    }

    protected void createCompatibleConSpinner() {
        spCompatibleCon = createSpinner(PublicacionAtributos.getInstancia().getCompatibilidades(), new CompatibleCon(), R.id.compatible_con_spinner);
    }

    protected void createPapelesDiaSpinner() {
        spPapeles = createSpinner(PublicacionAtributos.getInstancia().getPapelesAlDia(), new PapelesAlDia(), R.id.papeles_dia_spinner);
    }

    protected void createVacunasDiaSpinner() {
        spVacunas = createSpinner(PublicacionAtributos.getInstancia().getVacunasAlDia(), new VacunasAlDia(), R.id.vacunas_dia_spinner);
    }

    protected void createCastradorSpinner() {
        spCastrado = createSpinner(PublicacionAtributos.getInstancia().getCastrados(), new Castrado(), R.id.castrado_spinner);
    }

    protected void createProteccionSpinner() {
        spProteccion = createSpinner(PublicacionAtributos.getInstancia().getProtecciones(), new Proteccion(), R.id.proteccion_spinner);
    }

    protected void createEnergiaSpinner() {
        spEnergia = createSpinner(PublicacionAtributos.getInstancia().getEnergias(), new Energia(), R.id.energia_spinner);
    }

    protected abstract void initializeSpinners();

    protected void cleanSpinner(Spinner spinner) {
        spinner.setSelection(0);
    }

    protected abstract void cleanFilters();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMapFragment != null) {
            getActivity()
                    .getFragmentManager()
                    .beginTransaction()
                    .remove(mMapFragment)
                    .commit();
            map = null;
            mMapFragment = null;
        }
    }


}