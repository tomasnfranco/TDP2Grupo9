package com.tdp2grupo9.fragment.adopcion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.SeleccionAtributosFragment;
import com.tdp2grupo9.listview.ResultadosBusquedaActivity;
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

/**
 * Created by Tomas on 11/10/2015.
 */
public class BuscarMascotaFragment extends SeleccionAtributosFragment {

    private Double mLatitud = Usuario.getInstancia().getLatitud();
    private Double mLongitud = Usuario.getInstancia().getLongitud();
    private Button mBuscarMascotaButton;

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
        createBuscarMascotaButton();
        createLimpiarFiltrosButton();
        createCrearAlerta();
        hideInnecessaryFields();
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


    private void createCrearAlerta() {
        View crearAlertaClickable = mFragmentView.findViewById(R.id.crear_alerta_clickable);
        crearAlertaClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearAlerta();
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

    private void crearAlerta(){
        //TODO
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

}
