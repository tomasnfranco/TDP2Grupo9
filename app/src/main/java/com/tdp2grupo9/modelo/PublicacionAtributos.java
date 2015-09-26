package com.tdp2grupo9.modelo;

import android.util.Log;

import com.tdp2grupo9.modelo.publicacion.Castrado;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.CompatibleCon;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.PapelesAlDia;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;
import com.tdp2grupo9.modelo.publicacion.VacunasAlDia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PublicacionAtributos {

    private static final String SERVERURL = "http://10.0.3.2:8080/api/"; //TODO: pasar a un .config o algo

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
    }

    private HttpURLConnection getHttpUrlConnection(String controller_action) throws IOException {
        return (HttpURLConnection) new URL(SERVERURL + controller_action).openConnection();
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
    }

    private void jsonToAtributos(JSONObject json) throws JSONException {
        JSONArray jsonarray = json.getJSONArray("color");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            colores.add(new Color(childJSONObject.getInt("id"), childJSONObject.getString("nombre")));
        }
        jsonarray = json.getJSONArray("castrado");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            castrados.add(new Castrado(childJSONObject.getInt("id"), childJSONObject.getString("tipo")));
        }
        jsonarray = json.getJSONArray("compatibleCon");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            compatibilidades.add(new CompatibleCon(childJSONObject.getInt("id"), childJSONObject.getString("compatibleCon")));
        }
        jsonarray = json.getJSONArray("edad");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            edades.add(new Edad(childJSONObject.getInt("id"), childJSONObject.getString("nombre")));
        }
        jsonarray = json.getJSONArray("energia");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            energias.add(new Energia(childJSONObject.getInt("id"), childJSONObject.getString("tipo")));
        }
        jsonarray = json.getJSONArray("especie");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            especies.add(new Especie(childJSONObject.getInt("id"), childJSONObject.getString("tipo")));
        }
        jsonarray = json.getJSONArray("papelesAlDia");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            papelesAlDia.add(new PapelesAlDia(childJSONObject.getInt("id"), childJSONObject.getString("tipo")));
        }
        jsonarray = json.getJSONArray("proteccion");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            protecciones.add(new Proteccion(childJSONObject.getInt("id"), childJSONObject.getString("tipo")));
        }
        jsonarray = json.getJSONArray("sexo");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            sexos.add(new Sexo(childJSONObject.getInt("id"), childJSONObject.getString("tipo")));
        }
        jsonarray = json.getJSONArray("tamanio");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            tamanios.add(new Tamanio(childJSONObject.getInt("id"), childJSONObject.getString("tipo")));
        }
        jsonarray = json.getJSONArray("vacunasAlDia");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject childJSONObject = jsonarray.getJSONObject(i);
            vacunasAlDia.add(new VacunasAlDia(childJSONObject.getInt("id"), childJSONObject.getString("tipo")));
        }
    }

    public void cargarAtributos(String token){
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = this.getHttpUrlConnection("/publicacion/atributos");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("token="+token);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.i("BuscaSusHuellas", "Atributos mascota" + urlConnection.getResponseMessage());
                this.jsonToAtributos(new JSONObject(urlConnection.getResponseMessage()));
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

}
