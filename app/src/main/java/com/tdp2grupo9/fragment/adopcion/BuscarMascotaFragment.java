package com.tdp2grupo9.fragment.adopcion;

import android.content.ComponentCallbacks;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.PublicacionesConMapaFragment;
import com.tdp2grupo9.listview.ResultadosBusquedaActivity;
import com.tdp2grupo9.modelo.Alerta;
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

import java.util.Date;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Tomas on 11/10/2015.
 */
public class BuscarMascotaFragment extends PublicacionesConMapaFragment {

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
        createLocalizacionTextView();
        hideInnecessaryFields();
        initializeGoogleApi();
        obtenerAtributos();
        return mFragmentView;
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

    private void createLocalizacionTextView() {
        tvZona = (TextView) mFragmentView.findViewById(R.id.tv_zona);
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
        cleanSpinner(mMaximasDistanciasSpinner);
        mLatitud = Usuario.getInstancia().getLatitud();
        mLongitud = Usuario.getInstancia().getLongitud();
    }

    private boolean validateCampoRequeridoSpinner(Spinner spinner, String campoRequeridoString) {
        if (((AtributoPublicacion) spinner.getSelectedItem()).getId() == 0){
            ((TextView) spinner.getSelectedView()).setError(campoRequeridoString);
            return false;
        }
        return true;
    }

    private boolean isValidAttribute(){
        boolean valido = true;
        String campoRequeridoString = getString(R.string.campo_requerido);

        if (!validateCampoRequeridoSpinner(spEspecie, campoRequeridoString)) {valido = false;}
        if (!validateCampoRequeridoSpinner(spRaza, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spSexo, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spTamanio, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spEdad, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spColor, campoRequeridoString)) { valido = false;}

        if (!valido) {
            Toast.makeText(mFragmentView.getContext(), "Error: Debe completar todos los campos requeridos.",
                    Toast.LENGTH_LONG).show();
        }

        return valido;
    }



    private class BuscarMascotaOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (!isValidAttribute()){
                return;
            }
            Intent intent = new Intent(getActivity().getBaseContext(), ResultadosBusquedaActivity.class);
            intent.putExtra("especie",((Especie) spEspecie.getSelectedItem()).getId());
            intent.putExtra("raza",((Raza) spRaza.getSelectedItem()).getId());
            intent.putExtra("sexo",((Sexo) spSexo.getSelectedItem()).getId());
            intent.putExtra("tamanio",((Tamanio) spTamanio.getSelectedItem()).getId());
            intent.putExtra("edad",((Edad) spEdad.getSelectedItem()).getId());
            intent.putExtra("color", ((Color) spColor.getSelectedItem()).getId());
            intent.putExtra("proteccion",((Proteccion) spProteccion.getSelectedItem()).getId());
            intent.putExtra("energia",((Energia) spEnergia.getSelectedItem()).getId());
            intent.putExtra("castrado",((Castrado) spCastrado.getSelectedItem()).getId());
            intent.putExtra("compatiblecon",((CompatibleCon) spCompatibleCon.getSelectedItem()).getId());
            intent.putExtra("vacunas",((VacunasAlDia) spVacunas.getSelectedItem()).getId());
            intent.putExtra("papeles",((PapelesAlDia) spPapeles.getSelectedItem()).getId());
            intent.putExtra("distancia",getDistanciaElegida());
            intent.putExtra("latitud", mLatitud);
            intent.putExtra("longitud",mLongitud);
            startActivity(intent);
      }
    }


    private void createCrearAlerta() {
        View crearAlertaClickable = mFragmentView.findViewById(R.id.crear_alerta_clickable);
        crearAlertaClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (!isValidAttribute()){
                return;
            }
            Alerta alerta = new Alerta();
            alerta.setNombre("Alerta");
            alerta.setEspecie((Especie) spEspecie.getSelectedItem());
            alerta.setRaza((Raza) spRaza.getSelectedItem());
            alerta.setSexo((Sexo) spSexo.getSelectedItem());
            alerta.setTamanio((Tamanio) spTamanio.getSelectedItem());
            alerta.setEdad((Edad) spEdad.getSelectedItem());
            alerta.setColor((Color) spColor.getSelectedItem());
            alerta.setProteccion((Proteccion) spProteccion.getSelectedItem());
            alerta.setEnergia((Energia) spEnergia.getSelectedItem());
            alerta.setCastrado((Castrado) spCastrado.getSelectedItem());
            alerta.setCompatibleCon((CompatibleCon) spCompatibleCon.getSelectedItem());
            alerta.setVacunasAlDia((VacunasAlDia) spVacunas.getSelectedItem());
            alerta.setPapelesAlDia((PapelesAlDia) spPapeles.getSelectedItem());
            alerta.setDistancia(getDistanciaElegida());
            alerta.setLatitud(mLatitud);
            alerta.setLongitud(mLongitud);
            crearAlertaTask = new CrearAlertaTask(alerta);
            crearAlertaTask.execute((Void)null);
            }
        });
    }


    public class CrearAlertaTask extends AsyncTask<Void, Void, Boolean> {

        private Alerta alerta;

        CrearAlertaTask(Alerta alerta) {
            this.alerta = alerta;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                this.alerta.guardarAlerta(Usuario.getInstancia().getToken());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }
            return this.alerta.getId() > 0;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            crearAlertaTask = null;
            if (success) {
                Toast.makeText(mFragmentView.getContext(), "Alerta creada",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(mFragmentView.getContext(), "Error: No se pudo crear.",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            crearAlertaTask = null;
        }
    }




}
