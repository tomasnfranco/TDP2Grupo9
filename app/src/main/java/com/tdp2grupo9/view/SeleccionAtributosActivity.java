package com.tdp2grupo9.view;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.tdp2grupo9.R;
import com.tdp2grupo9.interpeter.AtributosJSONInterpeter;
import com.tdp2grupo9.modelo.PublicacionAtributos;
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

import java.util.List;


public class SeleccionAtributosActivity extends Activity {

    private ObtenerAtributosTask obtenerAtributosTask;
    protected AtributosJSONInterpeter atributosInterpeter;
    protected PublicacionAtributos publicacionAtributos;
    protected Spinner spEspecie;
    protected Spinner spRaza;
    protected Spinner spSexo;
    protected Spinner spTamanio;
    protected Spinner spEdad;
    protected Spinner spColor;
    protected Spinner spCompatibleCon;
    protected Spinner spPapeles;
    protected Spinner spVacunas;
    protected Spinner spCastrado;
    protected Spinner spProteccion;
    protected Spinner spEnergia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        atributosInterpeter = new AtributosJSONInterpeter(this);
        publicacionAtributos = new PublicacionAtributos();
        obtenerAtributosTask = new ObtenerAtributosTask(this.publicacionAtributos);
        obtenerAtributosTask.execute((Void) null);

    }

    protected void createEspecieSpinner() {
        List<AtributoPublicacion> especies = this.publicacionAtributos.getEspecies();
        Especie especie = new Especie();
        especie.setValor(especie.getName());
        especies.add(0, especie);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, especies);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEspecie = (Spinner) findViewById(R.id.especie_spinner);
        spEspecie.setAdapter(atributosArrayAdapter);
    }

    protected void createRazaSpinner() {
        List<AtributoPublicacion> razas = this.publicacionAtributos.getRazas();
        Raza raza = new Raza();
        raza.setValor(raza.getName());
        razas.add(0, raza);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, razas);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRaza = (Spinner) findViewById(R.id.raza_spinner);
        spRaza.setAdapter(atributosArrayAdapter);
    }

    protected void createSexoSpinner() {
        List<AtributoPublicacion> sexos = this.publicacionAtributos.getSexos();
        Sexo sexo = new Sexo();
        sexo.setValor(sexo.getName());
        sexos.add(0, sexo);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, sexos);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSexo = (Spinner) findViewById(R.id.sexo_spinner);
        spSexo.setAdapter(atributosArrayAdapter);
    }

    protected void createTamanioSpinner() {
        List<AtributoPublicacion> tamanios = this.publicacionAtributos.getTamanios();
        Tamanio tamanio = new Tamanio();
        tamanio.setValor(tamanio.getName());
        tamanios.add(0, tamanio);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, tamanios);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTamanio = (Spinner) findViewById(R.id.tamanio_spinner);
        spTamanio.setAdapter(atributosArrayAdapter);
    }

    protected void createEdadSpinner() {
        List<AtributoPublicacion> edades = this.publicacionAtributos.getEdades();
        Edad edad = new Edad();
        edad.setValor(edad.getName());
        edades.add(0, edad);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, edades);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEdad = (Spinner) findViewById(R.id.edad_spinner);
        spEdad.setAdapter(atributosArrayAdapter);
    }

    protected void createColorSpinner() {
        List<AtributoPublicacion> colores = this.publicacionAtributos.getColores();
        Color color = new Color();
        color.setValor(color.getName());
        colores.add(0, color);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, colores);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColor = (Spinner) findViewById(R.id.color_spinner);
        spColor.setAdapter(atributosArrayAdapter);
    }

    protected void createCompatibleConSpinner() {
        List<AtributoPublicacion> compatibilidades = this.publicacionAtributos.getCompatibilidades();
        CompatibleCon compatibleCon = new CompatibleCon();
        compatibleCon.setValor(compatibleCon.getName());
        compatibilidades.add(0, compatibleCon);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, compatibilidades);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCompatibleCon = (Spinner) findViewById(R.id.compatible_con_spinner);
        spCompatibleCon.setAdapter(atributosArrayAdapter);
    }

    protected void createPapelesDiaSpinner() {
        List<AtributoPublicacion> papelesAlDias = this.publicacionAtributos.getPapelesAlDia();
        PapelesAlDia papelesAlDia = new PapelesAlDia();
        papelesAlDia.setValor(papelesAlDia.getName());
        papelesAlDias.add(0, papelesAlDia);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, papelesAlDias);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPapeles = (Spinner) findViewById(R.id.papeles_dia_spinner);
        spPapeles.setAdapter(atributosArrayAdapter);
    }

    protected void createVacunasDiaSpinner() {
        List<AtributoPublicacion> vacunasAldias = this.publicacionAtributos.getVacunasAlDia();
        VacunasAlDia vacunasAldia = new VacunasAlDia();
        vacunasAldia.setValor(vacunasAldia.getName());
        vacunasAldias.add(0, vacunasAldia);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, vacunasAldias);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVacunas = (Spinner) findViewById(R.id.vacunas_dia_spinner);
        spVacunas.setAdapter(atributosArrayAdapter);
    }

    protected void createCastradorSpinner() {
        List<AtributoPublicacion> castrados = this.publicacionAtributos.getCastrados();
        Castrado castrado = new Castrado();
        castrado.setValor(castrado.getName());
        castrados.add(0, castrado);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, castrados);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCastrado = (Spinner) findViewById(R.id.castrado_spinner);
        spCastrado.setAdapter(atributosArrayAdapter);
    }

    protected void createProteccionSpinner() {
        List<AtributoPublicacion> protecciones = this.publicacionAtributos.getProtecciones();
        Proteccion proteccion = new Proteccion();
        proteccion.setValor(proteccion.getName());
        protecciones.add(0, proteccion);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, protecciones);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProteccion = (Spinner) findViewById(R.id.proteccion_spinner);
        spProteccion.setAdapter(atributosArrayAdapter);
    }

    protected void createEnergiaSpinner() {
        List<AtributoPublicacion> energias = this.publicacionAtributos.getEnergias();
        Energia energia = new Energia();
        energia.setValor(energia.getName());
        energias.add(0, energia);
        AtributosPublicacionArrayAdapter atributosArrayAdapter = new AtributosPublicacionArrayAdapter(this, android.R.layout.simple_spinner_item, energias);
        atributosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEnergia = (Spinner) findViewById(R.id.energia_spinner);
        spEnergia.setAdapter(atributosArrayAdapter);
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
    }

    public class ObtenerAtributosTask extends AsyncTask<Void, Void, Boolean> {

        PublicacionAtributos publicacionAtributos;
        ObtenerAtributosTask(PublicacionAtributos publicacionAtributos) {
            this.publicacionAtributos = publicacionAtributos;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                this.publicacionAtributos.cargarAtributos(Usuario.getInstancia().getToken());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            obtenerAtributosTask = null;
            if (success) {
                initializeWidgets();
            } else {
            }
        }

        @Override
        protected void onCancelled() {
            obtenerAtributosTask = null;
        }
    }

}

