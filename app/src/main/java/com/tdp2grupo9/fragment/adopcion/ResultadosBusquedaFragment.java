package com.tdp2grupo9.fragment.adopcion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.PublicacionesFragment;
import com.tdp2grupo9.adapter.PublicacionesAdapter;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.TipoPublicacion;
import com.tdp2grupo9.modelo.TiposEnum;
import com.tdp2grupo9.modelo.Usuario;
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
import com.tdp2grupo9.tabbed.TabbedFragment;
import com.tdp2grupo9.utils.EjecucionAlertaIntermediary;

/**
 * Created by Tomas on 15/10/2015.
 */
public class ResultadosBusquedaFragment extends PublicacionesFragment {

    private static final int TABBED_FRAGMENT_REQUEST_CODE = 1;

    private View mFragmentView;
    private ImageButton mReturnSearchButton;

    public static ResultadosBusquedaFragment newInstance(Fragment targetFragment) {
        ResultadosBusquedaFragment fragment = new ResultadosBusquedaFragment();
        fragment.setTargetFragment(targetFragment, TABBED_FRAGMENT_REQUEST_CODE);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_resultados_busqueda, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mListView = (ExpandableListView) mFragmentView.findViewById(R.id.list_view_resultados_busqueda);
        configureReturnSearchButton();
        return mFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (EjecucionAlertaIntermediary.shouldEjecutarAlerta) {
            EjecucionAlertaIntermediary.shouldEjecutarAlerta = false;
            startSearch(EjecucionAlertaIntermediary.searchData);
        }
    }

    public void configureReturnSearchButton() {
        mReturnSearchButton = (ImageButton) mFragmentView.findViewById(R.id.return_search_button);
        mReturnSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TabbedFragment) getTargetFragment()).volverABusqueda();
            }
        });
    }

    public void startSearch(Bundle arguments) {
        Publicacion publicacion = new Publicacion();
        String tipoPublicacion = arguments.getString("tipopublicacion");

        publicacion.setTipoPublicacion(TipoPublicacion.getTipoPublicacion(tipoPublicacion));
        publicacion.setEspecie(getEspeciePublicacion(arguments.getInt("especie", 0)));
        publicacion.setRaza(getRazaPublicacion(arguments.getInt("raza", 0)));
        publicacion.setSexo(getSexoPublicacion(arguments.getInt("sexo", 0)));
        publicacion.setTamanio(getTamanioPublicacion(arguments.getInt("tamanio", 0)));
        publicacion.setEdad(getEdadPublicacion(arguments.getInt("edad", 0)));
        publicacion.setColor(getColorPublicacion(arguments.getInt("color", 0)));
        publicacion.setProteccion(getProteccionPublicacion(arguments.getInt("proteccion", 0)));
        publicacion.setEnergia(getEnergiaPublicacion(arguments.getInt("energia", 0)));
        publicacion.setCastrado(getCastradoPublicacion(arguments.getInt("castrado", 0)));
        publicacion.setCompatibleCon(getCompatibleConPublicacion(arguments.getInt("compatiblecon", 0)));
        publicacion.setVacunasAlDia(getVacuntasAlDiaPublicacion(arguments.getInt("vacunas", 0)));
        publicacion.setPapelesAlDia(getPapelesAlDiaPublicacion(arguments.getInt("papeles", 0)));
        publicacion.setDistancia(getDistanciaPublicacion(arguments.getInt("distancia", -1)));
        publicacion.setLongitud(getLongitudPublicacion(arguments.getDouble("longitud", -1.0)));
        publicacion.setLatitud(getLatitudPublicacion(arguments.getDouble("latitud", -1.0)));
        publicacion.setNecesitaTransito(getNecesitaTransito(arguments.getString("necesitaTransito")));

        mBuscarAdopcionesTask = new BuscarAdopcionesTask(publicacion, TiposEnum.BUSQUEDA);
        mBuscarAdopcionesTask.execute((Void) null);
    }

    private Boolean getNecesitaTransito(String requiereTransito){
        if (requiereTransito != null && requiereTransito.equals("No")) return false;
        else if (requiereTransito != null)  return true;
        else return null;
    }

    private Double getLongitudPublicacion(double longitud) {
        if (longitud == -1.0)
            return Usuario.getInstancia().getLongitud();
        return longitud;
    }

    private Double getLatitudPublicacion(double latitud) {
        if (latitud == -1.0)
            return Usuario.getInstancia().getLatitud();
        return latitud;
    }

    private Double getDistanciaPublicacion(double distancia) {
        if (distancia == -1.0)
            return null;
        return distancia;
    }

    private PapelesAlDia getPapelesAlDiaPublicacion(int idPapelesAlDia) {
        return new PapelesAlDia(idPapelesAlDia);
    }

    private VacunasAlDia getVacuntasAlDiaPublicacion(int idVacunasAlDia) {
        return new VacunasAlDia(idVacunasAlDia);
    }

    private CompatibleCon getCompatibleConPublicacion(int idCompatibleCon) {
        return new CompatibleCon(idCompatibleCon);
    }

    private Castrado getCastradoPublicacion(int idCastrado) {
        return new Castrado(idCastrado);
    }

    private Especie getEspeciePublicacion(int idEspecie) {
        return new Especie(idEspecie);
    }

    private Sexo getSexoPublicacion(int idSexo) {
        return new Sexo(idSexo);
    }

    private Edad getEdadPublicacion(int idEdad) {
        return new Edad(idEdad);
    }

    private Raza getRazaPublicacion(int idRaza) {
        return new Raza(idRaza);
    }

    private Tamanio getTamanioPublicacion(int idTamanio) {
        return new Tamanio(idTamanio);
    }

    private Energia getEnergiaPublicacion(int idEnergia) {
        return new Energia(idEnergia);
    }

    private Proteccion getProteccionPublicacion(int idProteccion) {
        return new Proteccion(idProteccion);
    }

    private Color getColorPublicacion(int idColor) {
        return new Color(idColor);
    }

    @Override
    protected void cargarListView(){
        mListView.setAdapter(new PublicacionesAdapter(mFragmentView.getContext(), mPublicaciones ,TiposEnum.BUSQUEDA, getActivity()));
    }

}
