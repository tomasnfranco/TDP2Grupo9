package com.tdp2grupo9.fragment.adopcion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.PublicacionesFragment;
import com.tdp2grupo9.listview.PublicacionesAdapter;
import com.tdp2grupo9.modelo.Publicacion;
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
import com.tdp2grupo9.utils.TiposEnum;

/**
 * Created by Tomas on 15/10/2015.
 */
public class ResultadosBusquedaFragment extends PublicacionesFragment {

    private View mFragmentView;
    private ImageButton mReturnSearchButton;

    public static ResultadosBusquedaFragment newInstance() {
        ResultadosBusquedaFragment fragment = new ResultadosBusquedaFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("especie", 0);
        arguments.putInt("raza", 0);
        arguments.putInt("sexo", 0);
        arguments.putInt("tamanio", 0);
        arguments.putInt("edad", 0);
        arguments.putInt("proteccion", 0);
        arguments.putInt("color", 0);
        arguments.putInt("energia", 0);
        arguments.putInt("castrado", 0);
        arguments.putInt("compatiblecon", 0);
        arguments.putInt("vacunas", 0);
        arguments.putInt("papeles", 0);
        arguments.putDouble("longitud", -1.0);
        arguments.putDouble("latitud", -1.0);
        arguments.putDouble("distancia", -1.0);
        fragment.setArguments(new Bundle());
        return fragment;
    }

    public static ResultadosBusquedaFragment newInstance(Bundle arguments) {
        ResultadosBusquedaFragment fragment = new ResultadosBusquedaFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_resultados_busqueda, container, false);
        mListView = (ExpandableListView) mFragmentView.findViewById(R.id.list_view_resultados_busqueda);
        configureReturnSearchButton();
        startSearch(getArguments());
        return mFragmentView;
    }

    public void configureReturnSearchButton() {
        mReturnSearchButton = (ImageButton) mFragmentView.findViewById(R.id.return_search_button);
        mReturnSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void startSearch(Bundle arguments) {
        Publicacion publicacion = new Publicacion();
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
        publicacion.setDistancia(getDistanciaPublicacion(arguments.getDouble("distancia", -1.0)));
        publicacion.setLongitud(getLongitudPublicacion(arguments.getDouble("longitud", -1.0)));
        publicacion.setLatitud(getLatitudPublicacion(arguments.getDouble("latitud", -1.0)));

        mBuscarAdopcionesTask = new BuscarAdopcionesTask(publicacion, TiposEnum.BUSQUEDA);
        mBuscarAdopcionesTask.execute((Void) null);
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
        mListView.setAdapter(new PublicacionesAdapter(mFragmentView.getContext(), mPublicaciones ,TiposEnum.BUSQUEDA));
    }

}
