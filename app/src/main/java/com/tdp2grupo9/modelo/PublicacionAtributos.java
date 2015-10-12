package com.tdp2grupo9.modelo;

import android.util.JsonReader;
import android.util.Log;

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

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class PublicacionAtributos {

    private static final String LOG_TAG = "BSH.PubliAtributos";

    private List<AtributoPublicacion> colores;
    private List<AtributoPublicacion> castrados;
    private List<AtributoPublicacion> especies;
    private List<AtributoPublicacion> compatibilidades;
    private List<AtributoPublicacion> edades;
    private List<AtributoPublicacion> energias;
    private List<AtributoPublicacion> papelesAlDia;
    private List<AtributoPublicacion> protecciones;
    private List<AtributoPublicacion> sexos;
    private List<AtributoPublicacion> tamanios;
    private List<AtributoPublicacion> vacunasAlDia;
    private List<AtributoPublicacion> razas;

    public PublicacionAtributos() {
        this.colores = new ArrayList<>();
        this.castrados = new ArrayList<>();
        this.especies = new ArrayList<>();
        this.compatibilidades = new ArrayList<>();
        this.edades = new ArrayList<>();
        this.energias = new ArrayList<>();
        this.papelesAlDia = new ArrayList<>();
        this.protecciones = new ArrayList<>();
        this.sexos = new ArrayList<>();
        this.tamanios = new ArrayList<>();
        this.vacunasAlDia = new ArrayList<>();
        this.razas = new ArrayList<>();
    }

    private void resetearAtributos() {
        this.colores.clear();
        this.castrados.clear();
        this.especies.clear();
        this.compatibilidades.clear();
        this.edades.clear();
        this.energias.clear();
        this.papelesAlDia.clear();
        this.protecciones.clear();
        this.sexos.clear();
        this.tamanios.clear();
        this.vacunasAlDia.clear();
        this.razas.clear();
    }


    private void jsonToAtributos(JsonReader reader) throws JSONException, IOException {
        reader.setLenient(true);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case Especie.CLAVE:
                    this.especies = Especie.getEspeciesfromJson(reader);
                    break;
                case Color.CLAVE:
                    this.colores = Color.getColoresfromJson(reader);
                    break;
                case Castrado.CLAVE:
                    this.castrados = Castrado.getCastradosfromJson(reader);
                    break;
                case CompatibleCon.CLAVE:
                    this.compatibilidades = CompatibleCon.getCompatibilidadesfromJson(reader);
                    break;
                case Edad.CLAVE:
                    this.edades = Edad.getEdadesfromJson(reader);
                    break;
                case Energia.CLAVE:
                    this.energias = Energia.getEnergiasfromJson(reader);
                    break;
                case PapelesAlDia.CLAVE:
                    this.papelesAlDia = PapelesAlDia.getPapelesAlDiafromJson(reader);
                    break;
                case Proteccion.CLAVE:
                    this.protecciones = Proteccion.getProteccionesfromJson(reader);
                    break;
                case Sexo.CLAVE:
                    this.sexos = Sexo.getSexosfromJson(reader);
                    break;
                case Tamanio.CLAVE:
                    this.tamanios = Tamanio.getTamaniosfromJson(reader);
                    break;
                case VacunasAlDia.CLAVE:
                    this.vacunasAlDia = VacunasAlDia.getVacunasAlDiafromJson(reader);
                    break;
                case Raza.CLAVE:
                    this.razas = Raza.getRazasfromJson(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public void cargarAtributos(String token){
        String METHOD = "cargarAtributos";

        Log.d(LOG_TAG, METHOD + " token " + token);

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("publicacion/atributos?token="+token);

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                this.jsonToAtributos(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                Log.d(LOG_TAG, METHOD + " atributos cargados correctamente.");
            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada. " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public List<AtributoPublicacion> getCastrados() {
        return castrados;
    }

    public List<AtributoPublicacion> getColores() {
        return colores;
    }

    public List<AtributoPublicacion> getCompatibilidades() {
        return compatibilidades;
    }

    public List<AtributoPublicacion> getEdades() {
        return edades;
    }

    public List<AtributoPublicacion> getEnergias() {
        return energias;
    }

    public List<AtributoPublicacion> getEspecies() {
        return especies;
    }

    public List<AtributoPublicacion> getPapelesAlDia() {
        return papelesAlDia;
    }

    public List<AtributoPublicacion> getProtecciones() {
        return protecciones;
    }

    public List<AtributoPublicacion> getSexos() {
        return sexos;
    }

    public List<AtributoPublicacion> getTamanios() {
        return tamanios;
    }

    public List<AtributoPublicacion> getVacunasAlDia() {
        return vacunasAlDia;
    }

    public List<AtributoPublicacion> getRazas() {
        return razas;
    }
}
