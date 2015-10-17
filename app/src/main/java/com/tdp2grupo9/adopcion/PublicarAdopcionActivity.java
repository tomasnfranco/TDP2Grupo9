package com.tdp2grupo9.adopcion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.maps.MapsActivity;
import com.tdp2grupo9.modelo.Publicacion;
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
import com.tdp2grupo9.view.SeleccionAtributosActivity;
import com.tdp2grupo9.view.foto.FotoPicker;

/**
 * Created by tfranco on 9/24/2015.
 */
public class PublicarAdopcionActivity extends SeleccionAtributosActivity implements View.OnClickListener {

    private static final String PHOTO_INTENT_TYPE = "image/*";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int DATA_MAPA_REQUEST = 10;

    private Double latitud = Usuario.getInstancia().getLatitud();
    private Double longitud = Usuario.getInstancia().getLongitud();

    private EditText nombreDescripcion;
    private EditText videoLink;
    private Checkable cuidadosEspeciales;
    private Checkable requiereHogarTransito;
    private TextView contacto;
    private TextView condicionesAdopcion;
    private Button btnPublicar;

    private PublicarAdopcionTask publicarAdopcionTask;
    private FotoPicker mFotoPicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_adopcion);
        inicializarWidgets();
        createMapsButton();
    }

    private void inicializarWidgets(){
        nombreDescripcion = (EditText) findViewById(R.id.nombre_mascota_edit_text);
        videoLink = (EditText) findViewById(R.id.cargar_video);
        cuidadosEspeciales = (Checkable) findViewById(R.id.requiere_cuidados_especiales);
        requiereHogarTransito = (Checkable) findViewById(R.id.requiere_hogar_transito);
        contacto = (EditText) findViewById(R.id.contacto_edit_text);
        condicionesAdopcion = (EditText) findViewById(R.id.condiciones_edit_text);
        btnPublicar = (Button) findViewById(R.id.btn_publicar_adopcion);
        btnPublicar.setOnClickListener(this);
        mFotoPicker = (FotoPicker) findViewById(R.id.foto_picker);
    }

    @Override
    public void onClick(View v) {
        inicializarWidgets();
        if (v.getId() == R.id.btn_publicar_adopcion){
            if (isValidAttribute()){
                Publicacion publicacion = new Publicacion();

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

                publicacion.setLatitud(latitud);
                publicacion.setLongitud(longitud);

                publicacion.setNecesitaTransito(requiereHogarTransito.isChecked());
                publicacion.setRequiereCuidadosEspeciales(cuidadosEspeciales.isChecked());

                publicacion.setContacto("");
                if (!contacto.getText().toString().isEmpty())
                    publicacion.setContacto(contacto.getText().toString());

                publicacion.setCondiciones("");
                if (!condicionesAdopcion.getText().toString().isEmpty())
                    publicacion.setCondiciones(condicionesAdopcion.getText().toString());

                for (Bitmap bitmap : mFotoPicker.getImagesBitmaps()) {
                    publicacion.addImagen(bitmap);
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

        if (!validateCampoRequeridoSpinner(spEspecie, campoRequeridoString)) {valido = false;}
        if (!validateCampoRequeridoSpinner(spRaza, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spSexo, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spTamanio, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spEdad, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spColor, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spCompatibleCon, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spPapeles, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spVacunas, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spCastrado, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spProteccion, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoSpinner(spEnergia, campoRequeridoString)) { valido = false;}
        if (!validateCampoRequeridoEditText(nombreDescripcion, campoRequeridoString)) { valido = false;}

        return valido;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mFotoPicker.addFoto(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ((requestCode == DATA_MAPA_REQUEST) && (resultCode == RESULT_OK)){
            longitud = data.getDoubleExtra("longitud",0.0);
            latitud = data.getDoubleExtra("latitud", 0.0);
        }
    }

    private void createMapsButton() {
        View mapsClickable = findViewById(R.id.mapa_clickable);
        mapsClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                startActivityForResult(intent, DATA_MAPA_REQUEST);
            }
        });
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
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            publicarAdopcionTask = null;
            if (success) {
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
                latitud = Usuario.getInstancia().getLatitud();
                longitud = Usuario.getInstancia().getLongitud();
                videoLink.setText("");
                requiereHogarTransito.setChecked(false);
                cuidadosEspeciales.setChecked(false);
                contacto.setText("");
                condicionesAdopcion.setText("");

                Toast toast3 = new Toast(getApplicationContext());

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.lytLayout));

                ImageView txtMsg = (ImageView)layout.findViewById(R.id.imageMensaje);

                toast3.setDuration(Toast.LENGTH_SHORT);
                toast3.setView(layout);
                toast3.show();


            } else {
                //TODO: mensaje indicando por que no se pudo publicar
            }
        }

        @Override
        protected void onCancelled() {
            publicarAdopcionTask = null;
        }
    }


}
