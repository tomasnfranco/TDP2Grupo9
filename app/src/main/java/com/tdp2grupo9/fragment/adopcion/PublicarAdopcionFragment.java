package com.tdp2grupo9.fragment.adopcion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.drawer.DrawerMenuActivity;
import com.tdp2grupo9.fragment.PublicacionesConMapaFragment;
import com.tdp2grupo9.fragment.SeleccionAtributosFragment;
import com.tdp2grupo9.modelo.Imagen;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.PublicacionAtributos;
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
import com.tdp2grupo9.utils.EdicionPublicacionIntermediary;
import com.tdp2grupo9.view.clickable.EditarPublicacionClickable;
import com.tdp2grupo9.view.foto.FotoPicker;

import java.text.DecimalFormat;


public class PublicarAdopcionFragment extends SeleccionAtributosFragment implements View.OnClickListener {

    private static final String PHOTO_INTENT_TYPE = "image/*";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int DATA_MAPA_REQUEST = 10;


    private EditText nombreDescripcion;
    private EditText videoLink;
    private Checkable cuidadosEspeciales;
    private Checkable requiereHogarTransito;
    private TextView contacto;
    private TextView condicionesAdopcion;
    private Button btnPublicar;
    protected double currentLat = Usuario.getInstancia().getLatitud();
    protected double currentLon = Usuario.getInstancia().getLongitud();
    private String direccion = Usuario.getInstancia().getDireccion();

    private FotoPicker mFotoPicker;

    private PublicarAdopcionTask publicarAdopcionTask;
    private ModificarPublicacionTask mModificarPublicacionTask;
    private Publicacion mPublicacionPrevia;
    private RadioGroup radioGroupPublicaciones;
    private String tipoPublicacion = "";
    private TextView localizacion_mascota;
    private ImageView imagenPosicion;

    public static PublicarAdopcionFragment newInstance() {
        PublicarAdopcionFragment publicarAdopcion = new PublicarAdopcionFragment();
        return publicarAdopcion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mFragmentView = inflater.inflate(R.layout.fragment_publicar_mascotas, container, false);
        inicializarWidgets();
        hideInnecessaryFields();
        initializeSpinners();
        createMapsButton();
        //getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        return mFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (EdicionPublicacionIntermediary.shouldLoadPublicacion) {
            EdicionPublicacionIntermediary.shouldLoadPublicacion = false;
            loadPublicacionData(EdicionPublicacionIntermediary.publicacion);
            mPublicacionPrevia = EdicionPublicacionIntermediary.publicacion;
        }
    }

    private void loadPublicacionData(Publicacion publicacion) {
        loadTipoPublicacion(publicacion);
        loadNombre(publicacion);
        loadEspecie(publicacion);
        loadRaza(publicacion);
        loadSexo(publicacion);
        loadTamanio(publicacion);
        loadEdad(publicacion);
        loadColor(publicacion);
        loadFotos(publicacion);
        loadVideo(publicacion);
        loadCompatibleCon(publicacion);
        loadPapelesAlDia(publicacion);
        loadVacunasAlDia(publicacion);
        loadCastradoPublicacion(publicacion);
        loadProteccion(publicacion);
        loadEnergia(publicacion);
        loadRequiereHogarDeTransito(publicacion);
        loadRequiereCuidadosEspeciales(publicacion);
        loadContacto(publicacion);
        loadCondiciones(publicacion);
    }

    private void loadCondiciones(Publicacion publicacion) {
        condicionesAdopcion.setText(publicacion.getCondiciones());
    }

    private void loadContacto(Publicacion publicacion) {
        contacto.setText(publicacion.getContacto());
    }

    private void loadRequiereCuidadosEspeciales(Publicacion publicacion) {
        cuidadosEspeciales.setChecked(publicacion.getRequiereCuidadosEspeciales());
    }

    private void loadRequiereHogarDeTransito(Publicacion publicacion) {
        requiereHogarTransito.setChecked(publicacion.getNecesitaTransito());
    }

    private void loadEnergia(Publicacion publicacion) {
        loadSpinner(spEnergia, PublicacionAtributos.getInstancia().getEnergia(publicacion.getEnergia()).getValor());
    }

    private void loadProteccion(Publicacion publicacion) {
        loadSpinner(spProteccion, PublicacionAtributos.getInstancia().getProteccion(publicacion.getProteccion()).getValor());
    }

    private void loadCastradoPublicacion(Publicacion publicacion) {
        loadSpinner(spCastrado, PublicacionAtributos.getInstancia().getCastrado(publicacion.getCastrado()).getValor());
    }

    private void loadVacunasAlDia(Publicacion publicacion) {
        loadSpinner(spVacunas, PublicacionAtributos.getInstancia().getVacunasAlDia(publicacion.getVacunasAlDia()).getValor());
    }

    private void loadPapelesAlDia(Publicacion publicacion) {
        loadSpinner(spPapeles, PublicacionAtributos.getInstancia().getPapelesAlDia(publicacion.getPapelesAlDia()).getValor());
    }

    private void loadCompatibleCon(Publicacion publicacion) {
        loadSpinner(spCompatibleCon, PublicacionAtributos.getInstancia().getCompatibleCon(publicacion.getCompatibleCon()).getValor());
    }

    private void loadVideo(Publicacion publicacion) {
        videoLink.setText(publicacion.getVideoLink());
    }

    private void loadFotos(Publicacion publicacion) {
        for (Imagen imagen : publicacion.getImagenes()) {
            mFotoPicker.addFoto(imagen.getBitmap());
        }
    }

    private void loadColor(Publicacion publicacion) {
        loadSpinner(spColor, PublicacionAtributos.getInstancia().getColor(publicacion.getColor()).getValor());
    }

    private void loadEdad(Publicacion publicacion) {
        loadSpinner(spEdad, PublicacionAtributos.getInstancia().getEdad(publicacion.getEdad()).getValor());
    }

    private void loadTamanio(Publicacion publicacion) {
        loadSpinner(spTamanio, PublicacionAtributos.getInstancia().getTamanio(publicacion.getTamanio()).getValor());
    }

    private void loadSexo(Publicacion publicacion) {
        loadSpinner(spSexo, PublicacionAtributos.getInstancia().getSexo(publicacion.getSexo()).getValor());
    }

    private void loadRaza(Publicacion publicacion) {
        loadSpinner(spRaza, PublicacionAtributos.getInstancia().getRaza(publicacion.getRaza()).getValor());
    }


    private void loadEspecie(Publicacion publicacion) {
        loadSpinner(spEspecie, PublicacionAtributos.getInstancia().getEspecie(publicacion.getEspecie()).getValor());
    }

    private void loadSpinner(Spinner spinner, String valorPublicacion) {
        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            if (spinner.getAdapter().getItem(i).toString().equals(valorPublicacion)) {
                spinner.setSelection(i);
                return;
            }
            spinner.setSelection(0);
        }
    }

    private void loadNombre(Publicacion publicacion) {
        nombreDescripcion.setText(publicacion.getNombreMascota());
    }

    private void loadTipoPublicacion(Publicacion publicacion) {
        switch (publicacion.getTipoPublicacion()) {
            case ADOPCION: ((RadioButton) mFragmentView.findViewById(R.id.btn_adopciones)).setChecked(true); break;
            case PERDIDA: ((RadioButton) mFragmentView.findViewById(R.id.btn_perdidos)).setChecked(true); break;
            case ENCONTRADA: ((RadioButton) mFragmentView.findViewById(R.id.btn_encontrados)).setChecked(true); break;
        }
    }


    private void hideInnecessaryFields() {
        mFragmentView.findViewById(R.id.panel_maxima_distancia).setVisibility(View.GONE);
    }

    @Override
    protected void initializeSpinners() {
        createEspecieSpinner();
        createRazaSpinner();
        createSexoSpinner();
        createTamanioSpinner();
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
        cleanSpinner(spCompatibleCon);
        cleanSpinner(spPapeles);
        cleanSpinner(spVacunas);
        cleanSpinner(spCastrado);
        cleanSpinner(spProteccion);
        cleanSpinner(spEnergia);
        currentLat = Usuario.getInstancia().getLatitud();
        currentLon = Usuario.getInstancia().getLongitud();
        localizacion_mascota.setText(Usuario.getInstancia().getDireccion());
    }

    private void inicializarWidgets(){
        nombreDescripcion = (EditText) mFragmentView.findViewById(R.id.nombre_mascota_edit_text);
        videoLink = (EditText) mFragmentView.findViewById(R.id.cargar_video);
        cuidadosEspeciales = (Checkable) mFragmentView.findViewById(R.id.requiere_cuidados_especiales);
        requiereHogarTransito = (Checkable) mFragmentView.findViewById(R.id.requiere_hogar_transito);
        contacto = (EditText) mFragmentView.findViewById(R.id.contacto_edit_text);
        condicionesAdopcion = (EditText) mFragmentView.findViewById(R.id.condiciones_edit_text);
        btnPublicar = (Button) mFragmentView.findViewById(R.id.btn_publicar_adopcion);
        btnPublicar.setOnClickListener(this);

        localizacion_mascota = (TextView) mFragmentView.findViewById(R.id.tv_direccion_mascota);
        localizacion_mascota.setText(Usuario.getInstancia().getDireccion());

        mFotoPicker = (FotoPicker) mFragmentView.findViewById(R.id.foto_picker);
        mFotoPicker.setFragment(this);
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

    @Override
    public void onClick(View v) {
        inicializarWidgets();
        if (v.getId() == R.id.btn_publicar_adopcion){
            if (isValidAttribute()){
                //Publicacion publicacion = new Publicacion();

                if (tipoPublicacion.equals(mFragmentView.getContext().getString(R.string.adopciones)))
                    mPublicacionPrevia.setTipoPublicacion(TipoPublicacion.ADOPCION);

                if (tipoPublicacion.equals(mFragmentView.getContext().getString(R.string.encontrados)))
                    mPublicacionPrevia.setTipoPublicacion(TipoPublicacion.ENCONTRADA);

                if (tipoPublicacion.equals(mFragmentView.getContext().getString(R.string.perdidos)))
                    mPublicacionPrevia.setTipoPublicacion(TipoPublicacion.PERDIDA);

                mPublicacionPrevia.setNombreMascota(nombreDescripcion.getText().toString());
                mPublicacionPrevia.setEspecie(((Especie) spEspecie.getSelectedItem()));
                mPublicacionPrevia.setRaza(((Raza) spRaza.getSelectedItem()));
                mPublicacionPrevia.setColor(((Color) spColor.getSelectedItem()));
                mPublicacionPrevia.setSexo(((Sexo) spSexo.getSelectedItem()));
                mPublicacionPrevia.setTamanio(((Tamanio) spTamanio.getSelectedItem()));
                mPublicacionPrevia.setEdad(((Edad) spEdad.getSelectedItem()));

                mPublicacionPrevia.setCompatibleCon(((CompatibleCon) spCompatibleCon.getSelectedItem()));
                mPublicacionPrevia.setVacunasAlDia(((VacunasAlDia) spVacunas.getSelectedItem()));
                mPublicacionPrevia.setPapelesAlDia(((PapelesAlDia) spPapeles.getSelectedItem()));
                mPublicacionPrevia.setCastrado(((Castrado) spCastrado.getSelectedItem()));
                mPublicacionPrevia.setProteccion(((Proteccion) spProteccion.getSelectedItem()));
                mPublicacionPrevia.setEnergia(((Energia) spEnergia.getSelectedItem()));

                mPublicacionPrevia.setVideoLink("");
                if(!videoLink.getText().toString().isEmpty())
                    mPublicacionPrevia.setVideoLink(videoLink.getText().toString());

                mPublicacionPrevia.setLatitud(currentLat);
                mPublicacionPrevia.setLongitud(currentLon);
                mPublicacionPrevia.setDireccion(direccion);

                mPublicacionPrevia.setNecesitaTransito(requiereHogarTransito.isChecked());
                mPublicacionPrevia.setRequiereCuidadosEspeciales(cuidadosEspeciales.isChecked());

                mPublicacionPrevia.setContacto("");
                if (!contacto.getText().toString().isEmpty())
                    mPublicacionPrevia.setContacto(contacto.getText().toString());

                mPublicacionPrevia.setCondiciones("");
                if (!condicionesAdopcion.getText().toString().isEmpty())
                    mPublicacionPrevia.setCondiciones(condicionesAdopcion.getText().toString());

                mPublicacionPrevia.getImagenes().clear();
                for (Bitmap bitmap : mFotoPicker.getImagesBitmaps()) {
                    mPublicacionPrevia.addImagen(Imagen.resizeDefault(bitmap));
                }

                if (mPublicacionPrevia != null) {
                    mModificarPublicacionTask = new ModificarPublicacionTask(mPublicacionPrevia);
                    mModificarPublicacionTask.execute();
                    //mPublicacionPrevia = null;
                }
                //publicarAdopcionTask = new PublicarAdopcionTask(publicacion);
                //publicarAdopcionTask.execute((Void)null);


            }
        }
    }

    private boolean validateCampoRequeridoSpinner(Spinner spinner, String campoRequeridoString) {
        if (((AtributoPublicacion) spinner.getSelectedItem()).getId() == 0){
            ((TextView) spinner.getSelectedView()).setError(campoRequeridoString);
            return false;
        }
        return true;
    }

    private boolean validateCampoRequeridoEditText(EditText editText, String campoRequeridoString) {
        if (editText.getText().toString().isEmpty()){
            editText.setError(campoRequeridoString);
            return false;
        }
        return true;
    }

    private boolean isValidAttribute(){
        boolean valido = true;
        String campoRequeridoString = getString(R.string.campo_requerido);
        if (tipoPublicacion.isEmpty()) {
            Toast.makeText(mFragmentView.getContext(), mFragmentView.getContext().getString(R.string.error_tipo_publicacion),
                    Toast.LENGTH_LONG).show();
            valido = false; }
        if (!validateCampoRequeridoSpinner(spEspecie, campoRequeridoString)) {valido = false;}
        if (!validateCampoRequeridoSpinner(spRaza, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spSexo, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spTamanio, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spEdad, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spColor, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoEditText(nombreDescripcion, campoRequeridoString)) { valido = false;}

        return valido;
    }

    public void loadImageRequest() {
        Intent intent = new Intent();
        intent.setType(PHOTO_INTENT_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        getActivity().startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                mFotoPicker.addFoto(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if ((requestCode == DATA_MAPA_REQUEST) && (resultCode == Activity.RESULT_OK)){
            currentLon = data.getDoubleExtra("longitud",0.0);
            currentLat = data.getDoubleExtra("latitud", 0.0);
            direccion = data.getStringExtra("direccion");
            localizacion_mascota.setText(direccion);
        }
    }

    public class PublicarAdopcionTask extends AsyncTask<Void, Void, Boolean> {

        Publicacion publicacion;

        PublicarAdopcionTask(Publicacion publicacion) {
            this.publicacion = publicacion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                this.publicacion.guardarPublicacion(Usuario.getInstancia().getToken());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return this.publicacion.getId() > 0;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            publicarAdopcionTask = null;
            if (success) {
                radioGroupPublicaciones.clearCheck();
                nombreDescripcion.setText("");
                spEspecie.setSelection(0);
                spRaza.setSelection(0);
                spSexo.setSelection(0);
                spTamanio.setSelection(0);
                spEdad.setSelection(0);
                spColor.setSelection(0);
                spCompatibleCon.setSelection(0);
                spPapeles.setSelection(0);
                spVacunas.setSelection(0);
                spCastrado.setSelection(0);
                spProteccion.setSelection(0);
                spEnergia.setSelection(0);
                currentLat = Usuario.getInstancia().getLatitud();
                currentLon = Usuario.getInstancia().getLongitud();
                mFotoPicker.empty();
                videoLink.setText("");
                requiereHogarTransito.setChecked(false);
                cuidadosEspeciales.setChecked(false);
                contacto.setText("");
                condicionesAdopcion.setText("");

                /*Toast toast3 = new Toast(getActivity().getApplicationContext());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) mFragmentView.findViewById(R.id.lytLayout));

                ImageView imageViewMsg = (ImageView)layout.findViewById(R.id.imageMensaje);

                toast3.setDuration(Toast.LENGTH_SHORT);
                toast3.setView(layout);
                toast3.show();*/

                navigateToMisPublicaciones();
            } else {
                Toast.makeText(mFragmentView.getContext(), "Error: No se pudo guardar.",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            publicarAdopcionTask = null;
        }
    }

    private void navigateToMisPublicaciones() {
        ((DrawerMenuActivity )getActivity()).navigateToMisPublicaciones(null);
    }

    public String formatDecimal(double number, String formatPattern){

        DecimalFormat df = new DecimalFormat(formatPattern);
        return df.format(number);

    }


    private void createMapsButton() {

        imagenPosicion = (ImageView) mFragmentView.findViewById(R.id.iv_localizacion_mascota);
        imagenPosicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), PublicacionesConMapaFragment.class);
                intent.putExtra("latitud", currentLat);
                intent.putExtra("longitud", currentLon);
                getActivity().startActivityForResult(intent, DATA_MAPA_REQUEST);
            }
        });
    }

    public class ModificarPublicacionTask extends AsyncTask<Void, Void, Boolean> {

        private Publicacion mPublicacion;

        ModificarPublicacionTask(Publicacion publicacion) {
            this.mPublicacion = publicacion;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                mPublicacion.modificarPublicacion(Usuario.getInstancia().getToken());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mModificarPublicacionTask = null;
            navigateToMisPublicaciones();
        }

        @Override
        protected void onCancelled() {
            mModificarPublicacionTask = null;
        }
    }


}


