package com.tdp2grupo9.modelo;

import android.util.JsonReader;
import android.util.Log;

import com.tdp2grupo9.modelo.publicacion.Castrado;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.CompatibleCon;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.PapelesAlDia;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.RequiereCuidadosEspeciales;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;
import com.tdp2grupo9.modelo.publicacion.VacunasAlDia;
import com.tdp2grupo9.utils.Connection;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class PublicacionAtributos {

    private List<Color> colores;
    private List<Castrado> castrados;
    private List<Especie> especies;
    private List<CompatibleCon> compatibilidades;
    private List<Edad> edades;
    private List<Energia> energias;
    private List<PapelesAlDia> papelesAlDia;
    private List<Proteccion> protecciones;
    private List<Sexo> sexos;
    private List<Tamanio> tamanios;
    private List<VacunasAlDia> vacunasAlDia;
    private List<RequiereCuidadosEspeciales> requiereCuidadosEspeciales;

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
        this.requiereCuidadosEspeciales = new ArrayList<>();
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
        this.requiereCuidadosEspeciales.clear();
    }


    private void jsonToAtributos(JsonReader reader) throws JSONException, IOException {
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
                case RequiereCuidadosEspeciales.CLAVE:
                    this.requiereCuidadosEspeciales = RequiereCuidadosEspeciales.getRequiereCuidadosEspecialesfromJson(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public void cargarAtributos(String token){
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("/publicacion/atributos");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("token="+token);
            out.close();

            Log.i("BuscaSusHuellas", "Atributos Publicacion requeridos" + urlConnection.getResponseMessage());

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.i("BuscaSusHuellas", "Atributos Publicacion obtenidos" + urlConnection.getResponseMessage());
                this.jsonToAtributos(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
            } else {
                Log.e("BuscaSusHuellas", urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public List<Castrado> getCastrados() {
        return castrados;
    }

    public List<Color> getColores() {
        return colores;
    }

    public List<CompatibleCon> getCompatibilidades() {
        return compatibilidades;
    }

    public List<Edad> getEdades() {
        return edades;
    }

    public List<Energia> getEnergias() {
        return energias;
    }

    public List<Especie> getEspecies() {
        return especies;
    }

    public List<PapelesAlDia> getPapelesAlDia() {
        return papelesAlDia;
    }

    public List<Proteccion> getProtecciones() {
        return protecciones;
    }

    public List<Sexo> getSexos() {
        return sexos;
    }

    public List<Tamanio> getTamanios() {
        return tamanios;
    }

    public List<VacunasAlDia> getVacunasAlDia() {
        return vacunasAlDia;
    }

    public List<RequiereCuidadosEspeciales> getRequiereCuidadosEspeciales() {
        return requiereCuidadosEspeciales;
    }

}
