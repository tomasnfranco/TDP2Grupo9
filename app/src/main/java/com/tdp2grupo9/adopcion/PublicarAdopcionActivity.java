package com.tdp2grupo9.adopcion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.maps.MapsActivity;
import com.tdp2grupo9.view.SeleccionAtributosActivity;

/**
 * Created by tfranco on 9/24/2015.
 */
public class PublicarAdopcionActivity extends SeleccionAtributosActivity {

    private static final String PHOTO_INTENT_TYPE = "image/*";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAXIMO_FOTOS_PERMITIDAS = 6;
    private int cantidadFotosCargadas = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_adopcion);
        initializeWidgets();
    }

    private void initializeWidgets() {
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
        createAgregarFotoButton();
        createEliminarFotoButton();
        createMapsButton();

    }

    private void createAgregarFotoButton() {
        View agregarFotoButton = findViewById(R.id.cargar_foto_clickeable);
        View fotosHorizontalScrollView = findViewById(R.id.fotos_horizontal_clickable);
        agregarFotoButton.setOnClickListener(new AgregarFotoListener());
        fotosHorizontalScrollView.setOnClickListener(new AgregarFotoListener());
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
                startActivity(intent);
            }
        });
    }

}
