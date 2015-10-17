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
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Raza;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;

/**
 * Created by Tomas on 15/10/2015.
 */
public class ResultadosBusquedaFragment extends PublicacionesFragment {

    private View mFragmentView;
    private ImageButton mReturnSearchButton;

    //TODO constructor pensado solo para mockear, se puede eliminar en produccion
    public static ResultadosBusquedaFragment newInstance() {
        ResultadosBusquedaFragment fragment = new ResultadosBusquedaFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("especie", 0);
        arguments.putInt("sexo", 0);
        arguments.putInt("edad", 0);
        arguments.putInt("raza", 0);
        arguments.putInt("tamanio", 0);
        arguments.putInt("energia", 0);
        arguments.putInt("proteccion", 0);
        arguments.putInt("color", 0);
        arguments.putDouble("longitud", 0.0);
        arguments.putDouble("latitud", 0.0);
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
        publicacion.setSexo(getSexoPublicacion(arguments.getInt("sexo", 0)));
        publicacion.setEdad(getEdadPublicacion(arguments.getInt("edad", 0)));
        publicacion.setRaza(getRazaPublicacion(arguments.getInt("raza", 0)));
        publicacion.setTamanio(getTamanioPublicacion(arguments.getInt("tamanio", 0)));
        publicacion.setEnergia(getEnergiaPublicacion(arguments.getInt("energia", 0)));
        publicacion.setProteccion(getProteccionPublicacion(arguments.getInt("proteccion", 0)));
        publicacion.setColor(getColorPublicacion(arguments.getInt("color", 0)));
        publicacion.setLongitud(arguments.getDouble("longitud", 0.0));
        publicacion.setLatitud(arguments.getDouble("latitud", 0.0));

        mBuscarAdopcionesTask = new BuscarAdopcionesTask(publicacion, Tipos.BUSQUEDA);
        mBuscarAdopcionesTask.execute((Void) null);
    }

    private Especie getEspeciePublicacion(int idEspecie) {
        Especie especie = new Especie();
        especie.setId(idEspecie);
        return especie;
    }

    private Sexo getSexoPublicacion(int idSexo) {
        Sexo sexo = new Sexo();
        sexo.setId(idSexo);
        return sexo;
    }

    private Edad getEdadPublicacion(int idEdad) {
        Edad edad = new Edad();
        edad.setId(idEdad);
        return edad;
    }

    private Raza getRazaPublicacion(int idRaza) {
        Raza raza = new Raza();
        raza.setId(idRaza);
        return raza;
    }

    private Tamanio getTamanioPublicacion(int idTamanio) {
        Tamanio tamanio = new Tamanio();
        tamanio.setId(idTamanio);
        return tamanio;
    }

    private Energia getEnergiaPublicacion(int idEnergia) {
        Energia energia = new Energia();
        energia.setId(idEnergia);
        return energia;
    }

    private Proteccion getProteccionPublicacion(int idProteccion) {
        Proteccion proteccion = new Proteccion();
        proteccion.setId(idProteccion);
        return proteccion;
    }

    private Color getColorPublicacion(int idColor) {
        Color color = new Color();
        color.setId(idColor);
        return color;
    }

    @Override
    protected void cargarListView(){
        mListView.setAdapter(new PublicacionesAdapter(mFragmentView.getContext(), getPublicacionesMock()));
    }

}
