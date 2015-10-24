package com.tdp2grupo9.fragment.adopcion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.tdp2grupo9.fragment.PublicacionesConMapaFragment;
import com.tdp2grupo9.modelo.Imagen;
import com.tdp2grupo9.modelo.Publicacion;
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
import com.tdp2grupo9.view.foto.FotoPicker;

import java.text.DecimalFormat;


public class PublicarAdopcionFragment extends PublicacionesConMapaFragment implements View.OnClickListener {

    private static final String PHOTO_INTENT_TYPE = "image/*";
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText nombreDescripcion;
    private EditText videoLink;
    private Checkable cuidadosEspeciales;
    private Checkable requiereHogarTransito;
    private TextView contacto;
    private TextView condicionesAdopcion;
    private Button btnPublicar;

    private FotoPicker mFotoPicker;

    private PublicarAdopcionTask publicarAdopcionTask;
    private RadioGroup radioGroupPublicaciones;
    private String tipoPublicacion = "";

    public static PublicarAdopcionFragment newInstance() {
        PublicarAdopcionFragment publicarAdopcion = new PublicarAdopcionFragment();
        return publicarAdopcion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mFragmentView = inflater.inflate(R.layout.fragment_publicar_mascotas, container, false);
        createMap();
        inicializarWidgets();
        initializeGoogleApi();

        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hideInnecessaryFields();

        initializeSpinners();

        return mFragmentView;
    }

    private void hideInnecessaryFields() {
        mFragmentView.findViewById(R.id.maxima_distancia).setVisibility(View.GONE);
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
        tvZona = (TextView) mFragmentView.findViewById(R.id.tv_zona);
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
                Publicacion publicacion = new Publicacion();

                if (tipoPublicacion.equals(mFragmentView.getContext().getString(R.string.adopciones)))
                    publicacion.setTipoPublicacion(TipoPublicacion.ADOPCION);

                if (tipoPublicacion.equals(mFragmentView.getContext().getString(R.string.encontrados)))
                    publicacion.setTipoPublicacion(TipoPublicacion.ENCONTRADA);

                if (tipoPublicacion.equals(mFragmentView.getContext().getString(R.string.perdidos)))
                    publicacion.setTipoPublicacion(TipoPublicacion.PERDIDA);

                publicacion.setNombreMascota(nombreDescripcion.getText().toString());
                publicacion.setEspecie(((Especie) spEspecie.getSelectedItem()));
                publicacion.setRaza(((Raza) spRaza.getSelectedItem()));
                publicacion.setColor(((Color) spColor.getSelectedItem()));
                publicacion.setSexo(((Sexo) spSexo.getSelectedItem()));
                publicacion.setTamanio(((Tamanio) spTamanio.getSelectedItem()));
                publicacion.setEdad(((Edad) spEdad.getSelectedItem()));

                publicacion.setCompatibleCon(((CompatibleCon) spCompatibleCon.getSelectedItem()));
                publicacion.setVacunasAlDia(((VacunasAlDia) spVacunas.getSelectedItem()));
                publicacion.setPapelesAlDia(((PapelesAlDia) spPapeles.getSelectedItem()));
                publicacion.setCastrado(((Castrado) spCastrado.getSelectedItem()));
                publicacion.setProteccion(((Proteccion) spProteccion.getSelectedItem()));
                publicacion.setEnergia(((Energia) spEnergia.getSelectedItem()));

                publicacion.setVideoLink("");
                if(!videoLink.getText().toString().isEmpty())
                    publicacion.setVideoLink(videoLink.getText().toString());

                publicacion.setLatitud(currentLat);
                publicacion.setLongitud(currentLon);
                publicacion.setDireccion(tvZona.getText().toString());

                publicacion.setNecesitaTransito(requiereHogarTransito.isChecked());
                publicacion.setRequiereCuidadosEspeciales(cuidadosEspeciales.isChecked());

                publicacion.setContacto("");
                if (!contacto.getText().toString().isEmpty())
                    publicacion.setContacto(contacto.getText().toString());

                publicacion.setCondiciones("");
                if (!condicionesAdopcion.getText().toString().isEmpty())
                    publicacion.setCondiciones(condicionesAdopcion.getText().toString());

                for (Bitmap bitmap : mFotoPicker.getImagesBitmaps()) {
                    publicacion.addImagen(Imagen.resizeDefault(bitmap));
                }

                publicarAdopcionTask = new PublicarAdopcionTask(publicacion);
                publicarAdopcionTask.execute((Void)null);


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
                Thread.sleep(500);
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
                //TODO borrar imagenes cargadas
                videoLink.setText("");
                requiereHogarTransito.setChecked(false);
                cuidadosEspeciales.setChecked(false);
                contacto.setText("");
                condicionesAdopcion.setText("");

                Toast toast3 = new Toast(getActivity().getApplicationContext());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) mFragmentView.findViewById(R.id.lytLayout));

                ImageView imageViewMsg = (ImageView)layout.findViewById(R.id.imageMensaje);

                toast3.setDuration(Toast.LENGTH_SHORT);
                toast3.setView(layout);
                toast3.show();
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

    public String formatDecimal(double number, String formatPattern){

        DecimalFormat df = new DecimalFormat(formatPattern);
        return df.format(number);

    }


}


