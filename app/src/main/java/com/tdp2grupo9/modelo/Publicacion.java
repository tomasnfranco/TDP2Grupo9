package com.tdp2grupo9.modelo;

import android.graphics.Bitmap;
import android.util.JsonReader;
import android.util.Log;

import com.tdp2grupo9.modelo.publicacion.Castrado;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.CompatibleCon;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.NecesitaTransito;
import com.tdp2grupo9.modelo.publicacion.PapelesAlDia;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.RequiereCuidadosEspeciales;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;
import com.tdp2grupo9.modelo.publicacion.VacunasAlDia;
import com.tdp2grupo9.utils.Connection;

import org.json.JSONException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class Publicacion {

    private Integer tipoPublicacion;
    private String nombreMascota;
    private String condiciones;
    private String videoLink;
    private Color color;
    private Castrado castrado;
    private Especie especie;
    private CompatibleCon compatibleCon;
    private Edad edad;
    private Energia energia;
    private PapelesAlDia papelesAlDia;
    private Proteccion proteccion;
    private Sexo sexo;
    private Tamanio tamanio;
    private VacunasAlDia vacunasAlDia;
    private RequiereCuidadosEspeciales requiereCuidadosEspeciales;
    private NecesitaTransito necesitaTransito;
    private List<Bitmap> imagenes;
    private Double latitud;
    private Double longitud;

    public Publicacion() {
        this.tipoPublicacion = 0;
        this.nombreMascota = "";
        this.condiciones = "";
        this.videoLink = "";
        this.color = new Color();
        this.castrado = new Castrado();
        this.especie = new Especie();
        this.compatibleCon = new CompatibleCon();
        this.edad = new Edad();
        this.energia = new Energia();
        this.papelesAlDia = new PapelesAlDia();
        this.proteccion = new Proteccion();
        this.sexo = new Sexo();
        this.tamanio = new Tamanio();
        this.vacunasAlDia = new VacunasAlDia();
        this.requiereCuidadosEspeciales = new RequiereCuidadosEspeciales();
        this.necesitaTransito = new NecesitaTransito();
        this.imagenes = new ArrayList<>();
        this.latitud = 0.0;
        this.longitud = 0.0;
    }

    private void jsonToPublicacion(JsonReader reader) throws JSONException, IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case Especie.CLAVE:
                    this.especie.jsonToEspecie(reader);
                    break;
                case Color.CLAVE:
                    this.color.jsonToColor(reader);
                    break;
                case Castrado.CLAVE:
                    this.castrado.jsonToCastrado(reader);
                    break;
                case CompatibleCon.CLAVE:
                    this.compatibleCon.jsonToCompatibleCon(reader);
                    break;
                case Edad.CLAVE:
                    this.edad.jsonToEdad(reader);
                    break;
                case Energia.CLAVE:
                    this.energia.jsonToEnergia(reader);
                    break;
                case PapelesAlDia.CLAVE:
                    this.papelesAlDia.jsonToPapelesAlDia(reader);
                    break;
                case Proteccion.CLAVE:
                    this.proteccion.jsonToProteccion(reader);
                    break;
                case Sexo.CLAVE:
                    this.sexo.jsonToSexo(reader);
                    break;
                case Tamanio.CLAVE:
                    this.tamanio.jsonToTamanio(reader);
                    break;
                case VacunasAlDia.CLAVE:
                    this.vacunasAlDia.jsonToVacuna(reader);
                    break;
                case RequiereCuidadosEspeciales.CLAVE:
                    this.requiereCuidadosEspeciales.jsonToRequiereCuidadosEspeciales(reader);
                    break;
                case NecesitaTransito.CLAVE:
                    this.necesitaTransito.jsonToNecesitaTransito(reader);
                    break;
                case "tipoPublicacion":
                    this.tipoPublicacion = reader.nextInt();
                    break;
                case "nombreMascota":
                    this.tipoPublicacion = reader.nextInt();
                    break;
                case "condiciones":
                    this.condiciones = reader.nextString();
                    break;
                case "videoLink":
                    this.videoLink = reader.nextString();
                    break;
                case "latitud":
                    this.latitud = reader.nextDouble();
                    break;
                case "longitud":
                    this.longitud = reader.nextDouble();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public void guardarPublicacion(String token) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("publicacion");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("token="+token+"&tipoPublicacion="+this.tipoPublicacion+ "&especie="+this.especie.getId()+
                    "&nombreMascota="+this.nombreMascota+"&color="+this.color.getId()+
            "&edad="+this.edad.getId()+"&sexo="+this.sexo.getId()+"&tamanio="+this.tamanio.getId()+
            "&castrado="+this.castrado.getId()+"&compatibleCon="+this.compatibleCon.getId()+
            "&energia="+this.energia.getId()+"&papelesAlDia="+this.papelesAlDia.getId()+
            "&proteccion="+this.proteccion.getId()+"&vacunasAlDia="+this.vacunasAlDia.getId()+
                    "&necesitaTransito="+this.necesitaTransito.getId()+
            "&condiciones="+this.condiciones+"&requiereCuidadosEspeciales="+this.requiereCuidadosEspeciales.getId()+
            "&latitud="+this.latitud+"&longitud="+this.longitud+"&videoLink="+this.videoLink);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.i("BuscaSusHuellas", "Publicacion guardada");
            } else {
                Log.e("BuscaSusHuellas", urlConnection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public static List<Publicacion> buscarPublicaciones(String token, Integer tipoPublicacion, Integer offset,
                                                        Integer cantidad, Long latitud, Long longitud) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("usuario/logout");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("token="+token+"&tipoPublicacion="+tipoPublicacion+"&offset="+offset+
                    "cantidad");
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.i("BuscaSusHuellas", "Logout realizado correctamente");
            } else {
                Log.e("BuscaSusHuellas", urlConnection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return new ArrayList<>();
    }

    public Castrado getCastrado() {
        return castrado;
    }

    public Color getColor() {
        return color;
    }

    public Edad getEdad() {
        return edad;
    }

    public Energia getEnergia() {
        return energia;
    }

    public Especie getEspecie() {
        return especie;
    }

    public CompatibleCon getCompatibleCon() {
        return compatibleCon;
    }

    public PapelesAlDia getPapelesAlDia() {
        return papelesAlDia;
    }

    public Proteccion getProteccion() {
        return proteccion;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public Tamanio getTamanio() {
        return tamanio;
    }

    public VacunasAlDia getVacunasAlDia() {
        return vacunasAlDia;
    }

    public List<Bitmap> getImagenes() {
        return imagenes;
    }

    public void addImagen(Bitmap imagen) {
        this.imagenes.add(imagen);
    }

    public void setCastrado(Castrado castrado) {
        this.castrado = castrado;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCompatibleCon(CompatibleCon compatibleCon) {
        this.compatibleCon = compatibleCon;
    }

    public void setEdad(Edad edad) {
        this.edad = edad;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public void setPapelesAlDia(PapelesAlDia papelesAlDia) {
        this.papelesAlDia = papelesAlDia;
    }

    public void setProteccion(Proteccion proteccion) {
        this.proteccion = proteccion;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setTamanio(Tamanio tamanio) {
        this.tamanio = tamanio;
    }

    public void setVacunasAlDia(VacunasAlDia vacunasAlDia) {
        this.vacunasAlDia = vacunasAlDia;
    }

}
