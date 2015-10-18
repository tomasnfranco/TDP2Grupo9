package com.tdp2grupo9.fragment.adopcion;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.SeleccionAtributosFragment;
import com.tdp2grupo9.listview.ResultadosBusquedaActivity;
import com.tdp2grupo9.modelo.Alerta;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.modelo.publicacion.AtributoPublicacion;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Raza;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Tomas on 11/10/2015.
 */
public class BuscarMascotaFragment extends SeleccionAtributosFragment {

    private Double mLatitud = Usuario.getInstancia().getLatitud();
    private Double mLongitud = Usuario.getInstancia().getLongitud();
    private Button mBuscarMascotaButton;
    private Spinner mMaximasDistanciasSpinner;
    private LinkedHashMap<String, Integer> mMaximasDistanciasMap;
    private CrearAlertaTask crearAlertaTask;

    public static BuscarMascotaFragment newInstance() {
        return new BuscarMascotaFragment();
    }

    @Override
    protected void initializeSpinners() {
        createEspecieSpinner();
        createRazaSpinner();
        createSexoSpinner();
        createTamanioSpinner();;
        createEdadSpinner();
        createColorSpinner();
        createCompatibleConSpinner();
        createPapelesDiaSpinner();
        createVacunasDiaSpinner();
        createCastradorSpinner();
        createProteccionSpinner();
        createEnergiaSpinner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mFragmentView = inflater.inflate(R.layout.fragment_buscar_mascota, container, false);
        createMap();
        createBuscarMascotaButton();
        createLimpiarFiltrosButton();
        createMaximaDistanciaSpinner();
        createCrearAlerta();
        hideInnecessaryFields();
        obtenerAtributos();
        return mFragmentView;
    }

    private void createMap() {
        mMapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.fragment_map_publicacion);
        map = mMapFragment.getMap();
    }

    private void createBuscarMascotaButton() {
        mBuscarMascotaButton = (Button) mFragmentView.findViewById(R.id.buscar_mascota_button);
        mBuscarMascotaButton.setOnClickListener(new BuscarMascotaOnClickListener());
    }

    private void createLimpiarFiltrosButton() {
        View limpiarFiltrosClickable = mFragmentView.findViewById(R.id.limpiar_filtros_clickable);
        limpiarFiltrosClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanFilters();
            }
        });
    }

    /**
     * @return distancia en kilometros elegida por el usuario y null si no eligio ninguna
     */
    private Integer getDistanciaElegida() {
        return mMaximasDistanciasMap.get(mMaximasDistanciasSpinner.getSelectedItem());
    }

    private void createMaximaDistanciaSpinner() {
        createMaximaDistanciasMap();
        String[] distancias = createMaximaDistanciaMapKeySet();
        mMaximasDistanciasSpinner = (Spinner) mFragmentView.findViewById(R.id.maxima_distancia);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(mFragmentView.getContext(), android.R.layout.simple_spinner_item, distancias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMaximasDistanciasSpinner.setAdapter(adapter);

        mMaximasDistanciasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String string;
                System.out.println("DISTANCIA ELEGIDA" + getDistanciaElegida());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private String[] createMaximaDistanciaMapKeySet() {
        Set<String> keySet = mMaximasDistanciasMap.keySet();
        String[] distanciasString = new String[keySet.size()+1];
        distanciasString[0] = mFragmentView.getResources().getString(R.string.maxima_distancia_mi_localizacion);
        Iterator<String> iterator = keySet.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            distanciasString[i] = iterator.next();
            ++i;
        }
        return distanciasString;
    }

    private void createMaximaDistanciasMap() {
        mMaximasDistanciasMap = new LinkedHashMap<String, Integer>();
        int[] distancias = mFragmentView.getResources().getIntArray(R.array.distancias);
        for (int i = 0; i < distancias.length; i++) {
            int distancia = distancias[i];
            mMaximasDistanciasMap.put(getHastaDistanciaString(distancia), distancia);
        }
    }

    private String getHastaDistanciaString(int distancia) {
        return mFragmentView.getResources().getString(R.string.distancia_hasta_km).replace("%", Integer.toString(distancia));
    }


    private void createCrearAlerta() {
        View crearAlertaClickable = mFragmentView.findViewById(R.id.crear_alerta_clickable);
        crearAlertaClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearAlertaTask = new CrearAlertaTask(Usuario.getInstancia().getToken());
                crearAlertaTask.execute((Void)null);
            }
        });
    }

    private void hideInnecessaryFields() {
        mFragmentView.findViewById(R.id.nombre_mascota_edit_text).setVisibility(View.GONE);
        mFragmentView.findViewById(R.id.requiere_hogar_transito).setVisibility(View.GONE);
        mFragmentView.findViewById(R.id.requiere_cuidados_especiales).setVisibility(View.GONE);
        mFragmentView.findViewById(R.id.contacto_edit_text).setVisibility(View.GONE);
        mFragmentView.findViewById(R.id.condiciones_edit_text).setVisibility(View.GONE);
    }

    private void cleanSpinner(Spinner spinner) {
        spinner.setSelection(0);
    }


    private void cleanFilters() {
        cleanSpinner(spEspecie);
        cleanSpinner(spRaza);
        cleanSpinner(spSexo);
        cleanSpinner(spTamanio);
        cleanSpinner(spEdad);
        cleanSpinner(spColor);
        cleanSpinner(spProteccion);
        cleanSpinner(spEnergia);
        cleanSpinner(spCastrado);
        cleanSpinner(spCompatibleCon);
        cleanSpinner(spVacunas);
        cleanSpinner(spPapeles);
        mLatitud = Usuario.getInstancia().getLatitud();
        mLongitud = Usuario.getInstancia().getLongitud();
    }

    private boolean isValidEspecie(){
        if (((AtributoPublicacion) spEspecie.getSelectedItem()).getId() == 0){
            ((TextView) spEspecie.getSelectedView()).setError("Campo Requerido");
            return false;
        }
        return true;
    }

    private class BuscarMascotaOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (!isValidEspecie()){
                return;
            }
            Intent intent = new Intent(getActivity().getBaseContext(), ResultadosBusquedaActivity.class);
            intent.putExtra("especie",((Especie) spEspecie.getSelectedItem()).getId());
            intent.putExtra("raza",((Raza) spRaza.getSelectedItem()).getId());
            intent.putExtra("color", ((Color) spColor.getSelectedItem()).getId());
            intent.putExtra("sexo",((Sexo) spSexo.getSelectedItem()).getId());
            intent.putExtra("tamanio",((Tamanio) spTamanio.getSelectedItem()).getId());
            intent.putExtra("edad",((Edad) spEdad.getSelectedItem()).getId());
            intent.putExtra("proteccion",((Proteccion) spProteccion.getSelectedItem()).getId());
            intent.putExtra("energia",((Energia) spEnergia.getSelectedItem()).getId());
            intent.putExtra("latitud", mLatitud);
            intent.putExtra("longitud",mLongitud);
            startActivity(intent);
      }
    }


    public class CrearAlertaTask extends AsyncTask<Void, Void, Boolean> {

        String token;

        CrearAlertaTask(String token) {
            this.token = token;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Alerta alerta = new Alerta();
            alerta.guardarAlerta(token);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {
            crearAlertaTask = null;
        }
    }




}
