package com.tdp2grupo9.modelo;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Alerta {

    private static final String LOG_TAG = "BSH.Alerta";

    private Integer id;
    private Color color;
    private Especie especie;
    private Edad edad;
    private Sexo sexo;
    private Tamanio tamanio;
    private Raza raza;
    private Castrado castrado;
    private CompatibleCon compatibleCon;
    private Energia energia;
    private PapelesAlDia papelesAlDia;
    private Proteccion proteccion;
    private VacunasAlDia vacunasAlDia;
    private Double latitud;
    private Double longitud;
    private Double distancia;
    private Date fechaCreacion;
    //private Integer tipoPublicacion;
    //private Boolean requiereCuidadosEspeciales;
    //private Boolean necesitaTransito;

    public Alerta() {
        this.resetearAtributos();
    }

    public void resetearAtributos() {
        this.id = 0;
        this.color = new Color();
        this.especie = new Especie();
        this.edad = new Edad();
        this.sexo = new Sexo();
        this.tamanio = new Tamanio();
        this.raza = new Raza();
        this.castrado = new Castrado();
        this.compatibleCon = new CompatibleCon();
        this.energia = new Energia();
        this.papelesAlDia = new PapelesAlDia();
        this.proteccion = new Proteccion();
        this.vacunasAlDia = new VacunasAlDia();
        this.latitud = null;
        this.longitud = null;
        this.distancia = null;
        this.fechaCreacion = null;
    }

    private static List<Alerta> jsonToAlertas(JsonReader reader) throws IOException, JSONException {
        List<Alerta> alertas = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Alerta alerta = new Alerta();
            alerta.jsonToAlerta(reader);
            alertas.add(alerta);
        }
        reader.endArray();
        return alertas;
    }

    private void jsonToAlerta(JsonReader reader) throws JSONException, IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    this.id = reader.nextInt();
                    break;
                case "color":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.color.jsonToColor(reader);
                    }
                    break;
                case "especie":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.especie.jsonToEspecie(reader);
                    }
                    break;
                case "edad":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.edad.jsonToEdad(reader);
                    }
                    break;
                case "sexo":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.sexo.jsonToSexo(reader);
                    }
                    break;
                case "tamanio":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.tamanio.jsonToTamanio(reader);
                    }
                    break;
                case "raza":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.raza.jsonToRaza(reader);
                    }
                    break;
                case "castrado":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.castrado.jsonToCastrado(reader);
                    }
                    break;
                case "compatibleCon":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.compatibleCon.jsonToCompatibleCon(reader);
                    }
                    break;
                case "eneria":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.energia.jsonToEnergia(reader);
                    }
                    break;
                case "papelesAlDia":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.papelesAlDia.jsonToPapelesAlDia(reader);
                    }
                    break;
                case "proteccion":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.proteccion.jsonToProteccion(reader);
                    }
                    break;
                case "vacunasAlDia":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.vacunasAlDia.jsonToVacuna(reader);
                    }
                    break;
                case "latitud":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.latitud = reader.nextDouble();
                    }
                    break;
                case "longitud":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.longitud = reader.nextDouble();
                    }
                    break;
                case "distancia":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.distancia = reader.nextDouble();
                    }
                    break;
                case "fechaCreacion":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.fechaCreacion = Fecha.parseStringToDateTime(reader.nextString());
                    }
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public void guardarAlerta(String token) {
        String METHOD = "guardarAlerta";

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("alerta");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String parametros = "token="+token;
            if (this.color.getId() > 0)
                parametros += "&color=" + this.color.getId();
            if (this.especie.getId() > 0)
                parametros += "&especie=" + this.especie.getId();
            if (this.edad.getId() > 0)
                parametros += "&edad=" + this.edad.getId();
            if (this.sexo.getId() > 0)
                parametros += "&sexo=" + this.sexo.getId();
            if (this.tamanio.getId() > 0)
                parametros += "&tamanio=" + this.tamanio.getId();
            if (this.castrado.getId() > 0)
                parametros += "&castrado=" + this.castrado.getId();
            if (this.raza.getId() > 0)
                parametros += "&raza=" + this.raza.getId();
            if (this.compatibleCon.getId() > 0)
                parametros += "&compatibleCon=" + this.compatibleCon.getId();
            if (this.energia.getId() > 0)
                parametros += "&energia=" + this.energia.getId();
            if (this.papelesAlDia.getId() > 0)
                parametros += "&papelesAlDia=" + this.papelesAlDia.getId();
            if (this.proteccion.getId() > 0)
                parametros += "&proteccion=" + this.proteccion.getId();
            if (this.vacunasAlDia.getId() > 0)
                parametros += "&vacunasAlDia=" + this.vacunasAlDia.getId();
            if (this.latitud != null)
                parametros += "&latitud=" + this.latitud.toString().replace('.', ',');
            if (this.longitud != null)
                parametros += "&longitud=" + this.longitud.toString().replace('.', ',');
            if (this.distancia != null)
                parametros += "&distancia=" + this.distancia.toString().replace('.', ',');

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + parametros);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_CREATED) {
                this.jsonToAlerta(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                Log.d(LOG_TAG, METHOD + " alerta guardada id " + this.id);
            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada" + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        Log.d(LOG_TAG, METHOD + " finalizado.");
    }

    public static Alerta obtenerAlerta(String token, Integer id) {
        String METHOD = "obtenerAlerta";
        Alerta alerta = new Alerta();
        HttpURLConnection urlConnection = null;
        try {
            String atributos = +id+ "?token=" + token;
            Log.e(LOG_TAG, METHOD + " enviado al servidor " + atributos);
            urlConnection = Connection.getHttpUrlConnection("alerta/"+atributos);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                alerta.jsonToAlerta(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                Log.d(LOG_TAG, METHOD + " alerta obtenida id: " + alerta.getId());

            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return alerta;
    }

    public void borrarAlerta(String token) {
        String METHOD = "borrarAlerta";

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("alerta/delete");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String parametros = "token="+token+"&alerta="+this.id;

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + parametros);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_CREATED) {
                this.resetearAtributos();
                Log.d(LOG_TAG, METHOD + " alerta borrada ");
            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada" + urlConnection.getResponseMessage());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        Log.d(LOG_TAG, METHOD + " finalizado.");
    }

    public static List<Alerta> obtenerAlertasDeUsuario(String token, Integer offset, Integer max) {
        String METHOD = "obtenerAlertasDeUsuario";
        List<Alerta> alertas = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        try {
            String atributos = "?token="+token;
            //"&offset="+offset+"max="+max
            Log.e(LOG_TAG, METHOD + " enviado al servidor " + atributos);
            urlConnection = Connection.getHttpUrlConnection("alerta/misAlertas"+atributos);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                alertas = Alerta.jsonToAlertas(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                Log.d(LOG_TAG, METHOD + " misAlertas obtenidas " + alertas.size());
            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return alertas;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public Edad getEdad() {
        return edad;
    }

    public void setEdad(Edad edad) {
        this.edad = edad;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Tamanio getTamanio() {
        return tamanio;
    }

    public void setTamanio(Tamanio tamanio) {
        this.tamanio = tamanio;
    }

    public Raza getRaza() {
        return raza;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    public Castrado getCastrado() {
        return castrado;
    }

    public void setCastrado(Castrado castrado) {
        this.castrado = castrado;
    }

    public CompatibleCon getCompatibleCon() {
        return compatibleCon;
    }

    public void setCompatibleCon(CompatibleCon compatibleCon) {
        this.compatibleCon = compatibleCon;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Proteccion getProteccion() {
        return proteccion;
    }

    public void setProteccion(Proteccion proteccion) {
        this.proteccion = proteccion;
    }

    public PapelesAlDia getPapelesAlDia() {
        return papelesAlDia;
    }

    public void setPapelesAlDia(PapelesAlDia papelesAlDia) {
        this.papelesAlDia = papelesAlDia;
    }

    public VacunasAlDia getVacunasAlDia() {
        return vacunasAlDia;
    }

    public void setVacunasAlDia(VacunasAlDia vacunasAlDia) {
        this.vacunasAlDia = vacunasAlDia;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
