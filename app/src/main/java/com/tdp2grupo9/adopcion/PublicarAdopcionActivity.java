package com.tdp2grupo9.adopcion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

/**
 * Created by tfranco on 9/24/2015.
 */
public class PublicarAdopcionActivity extends SeleccionAtributosActivity implements View.OnClickListener {

    private static final String PHOTO_INTENT_TYPE = "image/*";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAXIMO_FOTOS_PERMITIDAS = 6;
    private static final int DATA_MAPA_REQUEST = 10;

    private Double latitud = Usuario.getInstancia().getLatitud();
    private Double longitud = Usuario.getInstancia().getLongitud();

    private int cantidadFotosCargadas = 0;
    private EditText nombre_descripcion;
    private EditText videoLink;
    private CheckBox cuidadosEspeciales;
    private CheckBox requiereHogarTransito;
    private TextView contacto;
    private TextView condicionesAdopcion;
    private Button btnPublicar;

    private PublicarAdopcionTask publicarAdopcionTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_adopcion);
        instanciarWindgets();
        btnPublicar = (Button) findViewById(R.id.btn_publicar_adopcion);
        btnPublicar.setOnClickListener(this);
        createAgregarFotoButton();
        createEliminarFotoButton();
        createMapsButton();

    }

    private void instanciarWindgets(){
        nombre_descripcion = (EditText) findViewById(R.id.nombre_mascota_edit_text);
        videoLink = (EditText) findViewById(R.id.cargar_video);
        cuidadosEspeciales = (CheckBox) findViewById(R.id.requiere_cuidados_especiales);
        requiereHogarTransito = (CheckBox) findViewById(R.id.requiere_hogar_transito_box);
        contacto = (EditText) findViewById(R.id.contacto_edit_text);
        condicionesAdopcion = (EditText) findViewById(R.id.condiciones_edit_text);
    }


    private void createAgregarFotoButton() {
        View agregarFotoButton = findViewById(R.id.cargar_foto_clickeable);
        View fotosHorizontalScrollView = findViewById(R.id.fotos_horizontal_clickable);
        agregarFotoButton.setOnClickListener(new AgregarFotoListener());
        fotosHorizontalScrollView.setOnClickListener(new AgregarFotoListener());
    }

    @Override
    public void onClick(View v) {
        instanciarWindgets();
        if (v.getId() == R.id.btn_publicar_adopcion){
            if (isValidAttribute()){
                Publicacion publicacion = new Publicacion();

                publicacion.setNombreMascota(nombre_descripcion.getText().toString());
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

                Integer[] idFotos = {R.id.foto_uno, R.id.foto_dos, R.id.foto_tres, R.id.foto_cuatro, R.id.foto_cinco, R.id.foto_seis};

                for (int i=0; i < cantidadFotosCargadas; i++){
                    publicacion.addImagen(((BitmapDrawable) ((ImageView) findViewById(idFotos[i])).getDrawable()).getBitmap());
                }

                publicarAdopcionTask = new PublicarAdopcionTask(publicacion);
                publicarAdopcionTask.execute((Void)null);


            }
        }
    }

    private boolean isValidAttribute(){
        Boolean valido = true;

        if (((AtributoPublicacion) spEspecie.getSelectedItem()).getId() == 0){
            ((TextView) spEspecie.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spRaza.getSelectedItem()).getId() == 0){
            ((TextView) spRaza.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spSexo.getSelectedItem()).getId() == 0){
            ((TextView) spSexo.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spTamanio.getSelectedItem()).getId() == 0){
            ((TextView) spTamanio.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spEdad.getSelectedItem()).getId() == 0){
            ((TextView) spEdad.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spColor.getSelectedItem()).getId() == 0){
            ((TextView) spColor.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spCompatibleCon.getSelectedItem()).getId() == 0){
            ((TextView) spCompatibleCon.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spPapeles.getSelectedItem()).getId() == 0){
            ((TextView) spPapeles.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spVacunas.getSelectedItem()).getId() == 0){
            ((TextView) spVacunas.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spCastrado.getSelectedItem()).getId() == 0){
            ((TextView) spCastrado.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spProteccion.getSelectedItem()).getId() == 0){
            ((TextView) spProteccion.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (((AtributoPublicacion) spEnergia.getSelectedItem()).getId() == 0){
            ((TextView) spEnergia.getSelectedView()).setError("Campo Requerido");
            valido = false;
        }
        if (nombre_descripcion.getText().toString().isEmpty()){
            nombre_descripcion.setError("Campo Requerido");
            valido = false;
        }
        return valido;
    }

    private class AgregarFotoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (cantidadFotosCargadas < MAXIMO_FOTOS_PERMITIDAS) {
                Intent intent = new Intent();
                intent.setType(PHOTO_INTENT_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
            else {
                Toast.makeText(getApplicationContext(), getString(R.string.maximo_fotos_alcanzado), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void createEliminarFotoButton() {
        View eliminarFotoButton = findViewById(R.id.eliminar_foto_clickeable);
        eliminarFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantidadFotosCargadas > 0) {
                    getImageViewAEliminar().setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
                    --cantidadFotosCargadas;
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_hay_fotos), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                getImageViewACargar().setImageBitmap(bitmap);
                ++cantidadFotosCargadas;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ((requestCode == DATA_MAPA_REQUEST) && (resultCode == RESULT_OK)){
            longitud = data.getDoubleExtra("longitud",0.0);
            latitud = data.getDoubleExtra("latitud", 0.0);
        }
    }

    public ImageView getImageViewACargar() {
        switch (cantidadFotosCargadas) {
            case 0: return (ImageView) findViewById(R.id.foto_uno);
            case 1: return (ImageView) findViewById(R.id.foto_dos);
            case 2: return (ImageView) findViewById(R.id.foto_tres);
            case 3: return (ImageView) findViewById(R.id.foto_cuatro);
            case 4: return (ImageView) findViewById(R.id.foto_cinco);
            case 5: return (ImageView) findViewById(R.id.foto_seis);
        }
        return null;
    }

    public ImageView getImageViewAEliminar() {
        switch (cantidadFotosCargadas) {
            case 1: return (ImageView) findViewById(R.id.foto_uno);
            case 2: return (ImageView) findViewById(R.id.foto_dos);
            case 3: return (ImageView) findViewById(R.id.foto_tres);
            case 4: return (ImageView) findViewById(R.id.foto_cuatro);
            case 5: return (ImageView) findViewById(R.id.foto_cinco);
            case 6: return (ImageView) findViewById(R.id.foto_seis);
        }
        return null;
    }

    private void createMapsButton() {
        ImageButton mapsButton = (ImageButton) findViewById(R.id.mapa_button);
        mapsButton.setOnClickListener(new View.OnClickListener() {
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
                nombre_descripcion.setText("");
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

                TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);

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
