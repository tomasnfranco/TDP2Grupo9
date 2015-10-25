package com.tdp2grupo9.fragment.adopcion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.drawer.DrawerMenuActivity;
import com.tdp2grupo9.fragment.PublicacionesConMapaFragment;
import com.tdp2grupo9.modelo.Alerta;
import com.tdp2grupo9.modelo.TipoPublicacion;
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
import com.tdp2grupo9.tabbed.TabbedFragment;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Tomas on 11/10/2015.
 */
public class BuscarMascotaFragment extends PublicacionesConMapaFragment {

    private static final int TABBED_FRAGMENT_REQUEST_CODE = 1;
    private Double mLatitud = Usuario.getInstancia().getLatitud();
    private Double mLongitud = Usuario.getInstancia().getLongitud();
    private Button mBuscarMascotaButton;
    private Spinner mMaximasDistanciasSpinner;
    private LinkedHashMap<String, Integer> mMaximasDistanciasMap;
    private CrearAlertaTask crearAlertaTask;
    private RadioGroup radioGroupPublicaciones;
    private String tipoPublicacion = "";

    public static BuscarMascotaFragment newInstance(Fragment targetFragment) {
        BuscarMascotaFragment fragment = new BuscarMascotaFragment();
        fragment.setTargetFragment(targetFragment, TABBED_FRAGMENT_REQUEST_CODE);
        return fragment;
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
    protected void cleanFilters() {
        radioGroupPublicaciones.clearCheck();
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
        initializeSpinners();
        initializeTipoPublicaciones();
        return mFragmentView;
    }

    private void initializeTipoPublicaciones(){

        radioGroupPublicaciones = (RadioGroup) mFragmentView.findViewById(R.id.radio_group_tipo_publicacion);
        radioGroupPublicaciones.clearCheck();

        radioGroupPublicaciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    tipoPublicacion = rb.getText().toString();
                }
            }
        });
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

    private boolean validateCampoRequeridoSpinner(Spinner spinner, String campoRequeridoString) {
        if (((AtributoPublicacion) spinner.getSelectedItem()).getId() == 0){
            ((TextView) spinner.getSelectedView()).setError(campoRequeridoString);
            return false;
        }
        return true;
    }

    private boolean isValidAlerta(){
        boolean valido = true;
        String campoRequeridoString = getString(R.string.campo_requerido);

        if (tipoPublicacion.isEmpty()) {
            Toast.makeText(mFragmentView.getContext(), mFragmentView.getContext().getString(R.string.error_tipo_publicacion),
                    Toast.LENGTH_LONG).show();
            valido = false; }

        if (!validateCampoRequeridoSpinner(spEspecie, campoRequeridoString)) {valido = false;}

        if (!valido) {
            Toast.makeText(mFragmentView.getContext(), "Error: Debe completar todos los campos requeridos.",
                    Toast.LENGTH_LONG).show();
        }

        return valido;
    }


    private boolean isValidBusqueda(){
        boolean valido = true;
        String campoRequeridoString = getString(R.string.campo_requerido);

        if (tipoPublicacion.isEmpty()) {
            Toast.makeText(mFragmentView.getContext(), mFragmentView.getContext().getString(R.string.error_tipo_publicacion),
                    Toast.LENGTH_LONG).show();
            valido = false; }

        if (!validateCampoRequeridoSpinner(spEspecie, campoRequeridoString)) {valido = false;}

        if (!valido) {
            Toast.makeText(mFragmentView.getContext(), "Error: Debe completar todos los campos requeridos.",
                    Toast.LENGTH_LONG).show();
        }

        return valido;
    }

    private class BuscarMascotaOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (!isValidBusqueda()){
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("tipopublicacion",tipoPublicacion);
            bundle.putInt("especie", ((Especie) spEspecie.getSelectedItem()).getId());
            bundle.putInt("raza", ((Raza) spRaza.getSelectedItem()).getId());
            bundle.putInt("sexo", ((Sexo) spSexo.getSelectedItem()).getId());
            bundle.putInt("tamanio", ((Tamanio) spTamanio.getSelectedItem()).getId());
            bundle.putInt("edad", ((Edad) spEdad.getSelectedItem()).getId());
            bundle.putInt("color", ((Color) spColor.getSelectedItem()).getId());
            bundle.putInt("proteccion", ((Proteccion) spProteccion.getSelectedItem()).getId());
            bundle.putInt("energia", ((Energia) spEnergia.getSelectedItem()).getId());
            bundle.putInt("castrado", ((Castrado) spCastrado.getSelectedItem()).getId());
            bundle.putInt("compatiblecon", ((CompatibleCon) spCompatibleCon.getSelectedItem()).getId());
            bundle.putInt("vacunas", ((VacunasAlDia) spVacunas.getSelectedItem()).getId());
            bundle.putInt("papeles", ((PapelesAlDia) spPapeles.getSelectedItem()).getId());
            Integer distancia = getDistanciaElegida();
            if (distancia == null)
                bundle.putInt("distancia", -1);
            else
                bundle.putInt("distancia", distancia);
            bundle.putDouble("latitud", mLatitud);
            bundle.putDouble("longitud", mLongitud);
            ((TabbedFragment) getTargetFragment()).showBuscarMascotaResults(bundle);
      }
    }


    private void createCrearAlerta() {
        View crearAlertaClickable = mFragmentView.findViewById(R.id.crear_alerta_clickable);
        crearAlertaClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (!isValidAlerta()){
                return;
            }
            Alerta alerta = new Alerta();
            alerta.setNombre("Alerta");
            alerta.setTipoPublicacion(TipoPublicacion.getTipoPublicacion(tipoPublicacion));
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
                Thread.sleep(200);
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
                ((DrawerMenuActivity) getActivity()).navigateToMisNotificaciones();
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
