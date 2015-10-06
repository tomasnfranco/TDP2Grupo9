package com.tdp2grupo9.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.tdp2grupo9.view.SeleccionAtributosFragment;

import java.util.HashMap;
import java.util.Map;


public class PublicarAdopcionFragment extends SeleccionAtributosFragment implements View.OnClickListener {
    private static final String PHOTO_INTENT_TYPE = "image/*";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAXIMO_FOTOS_PERMITIDAS = 6;
    private static final int DATA_MAPA_REQUEST = 10;

    private Double latitud = Usuario.getInstancia().getLatitud();
    private Double longitud = Usuario.getInstancia().getLongitud();
    private Map<Integer, ImageView> photosMap;

    private int cantidadFotosCargadas = 0;
    private EditText nombreDescripcion;
    private EditText videoLink;
    private Checkable cuidadosEspeciales;
    private Checkable requiereHogarTransito;
    private TextView contacto;
    private TextView condicionesAdopcion;
    private Button btnPublicar;

    private PublicarAdopcionTask publicarAdopcionTask;

    public static PublicarAdopcionFragment newInstance(int page, String title) {
        PublicarAdopcionFragment publicarAdopcion = new PublicarAdopcionFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        publicarAdopcion.setArguments(args);
        return publicarAdopcion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        inicializarWidgets();
        createAgregarFotoButton();
        createEliminarFotoButton();
        return viewMain;
    }


    private void inicializarWidgets(){
        nombreDescripcion = (EditText) viewMain.findViewById(R.id.nombre_mascota_edit_text);
        videoLink = (EditText) viewMain.findViewById(R.id.cargar_video);
        cuidadosEspeciales = (Checkable) viewMain.findViewById(R.id.requiere_cuidados_especiales);
        requiereHogarTransito = (Checkable) viewMain.findViewById(R.id.requiere_hogar_transito);
        contacto = (EditText) viewMain.findViewById(R.id.contacto_edit_text);
        condicionesAdopcion = (EditText) viewMain.findViewById(R.id.condiciones_edit_text);
        btnPublicar = (Button) viewMain.findViewById(R.id.btn_publicar_adopcion);
        btnPublicar.setOnClickListener(this);
        initializePhotosScrollView();
    }

    private void initializePhotosScrollView() {
        createPhotosMap();
        photosMap.get(0).setImageDrawable(getResources().getDrawable(R.drawable.ic_camera));
        for (int i = 1; i < MAXIMO_FOTOS_PERMITIDAS; i++) {
            photosMap.get(i).setVisibility(View.INVISIBLE);
        }
    }

    private void createPhotosMap() {
        photosMap = new HashMap<Integer, ImageView>();
        photosMap.put(0, (ImageView) viewMain.findViewById(R.id.foto_uno));
        photosMap.put(1, (ImageView) viewMain.findViewById(R.id.foto_dos));
        photosMap.put(2, (ImageView) viewMain.findViewById(R.id.foto_tres));
        photosMap.put(3, (ImageView) viewMain.findViewById(R.id.foto_cuatro));
        photosMap.put(4, (ImageView) viewMain.findViewById(R.id.foto_cinco));
        photosMap.put(5, (ImageView) viewMain.findViewById(R.id.foto_seis));
    }


    private void createAgregarFotoButton() {
        View cargarFotoTextView = viewMain.findViewById(R.id.cargar_foto_text_view);
        View fotosHorizontalScrollView = viewMain.findViewById(R.id.fotos_horizontal_clickable);
        cargarFotoTextView.setOnClickListener(new AgregarFotoListener());
        fotosHorizontalScrollView.setOnClickListener(new AgregarFotoListener());
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

                Integer[] idFotos = {R.id.foto_uno, R.id.foto_dos, R.id.foto_tres, R.id.foto_cuatro, R.id.foto_cinco, R.id.foto_seis};

                for (int i=0; i < cantidadFotosCargadas; i++){
                    publicacion.addImagen(((BitmapDrawable) ((ImageView) viewMain.findViewById(idFotos[i])).getDrawable()).getBitmap());
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
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.maximo_fotos_alcanzado), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void createEliminarFotoButton() {
        View eliminarFotoButton = viewMain.findViewById(R.id.eliminar_foto_clickeable);
        eliminarFotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cantidadFotosCargadas > 0) {
                    deleteLastPhoto();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.no_hay_fotos), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                loadPhotoWithBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadPhotoWithBitmap(Bitmap bitmap) {
        photosMap.get(cantidadFotosCargadas).setImageBitmap(bitmap);
        ++cantidadFotosCargadas;
        if (cantidadFotosCargadas < MAXIMO_FOTOS_PERMITIDAS) {
            ImageView nextPhotoHolder = photosMap.get(cantidadFotosCargadas);
            nextPhotoHolder.setVisibility(View.VISIBLE);
            nextPhotoHolder.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera));
        }
    }

    private void deleteLastPhoto() {
        --cantidadFotosCargadas;
        photosMap.get(cantidadFotosCargadas).setImageDrawable(getResources().getDrawable(R.drawable.ic_camera));
        if ((cantidadFotosCargadas+1) < MAXIMO_FOTOS_PERMITIDAS) {
            photosMap.get(cantidadFotosCargadas+1).setVisibility(View.INVISIBLE);
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

                Toast toast3 = new Toast(getActivity().getApplicationContext());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) viewMain.findViewById(R.id.lytLayout));

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
