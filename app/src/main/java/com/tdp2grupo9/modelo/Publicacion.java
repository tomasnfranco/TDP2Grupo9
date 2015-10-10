package com.tdp2grupo9.modelo;

import android.graphics.Bitmap;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.tdp2grupo9.modelo.publicacion.Castrado;
import com.tdp2grupo9.modelo.publicacion.Color;
import com.tdp2grupo9.modelo.publicacion.CompatibleCon;
import com.tdp2grupo9.modelo.publicacion.Edad;
import com.tdp2grupo9.modelo.publicacion.Energia;
import com.tdp2grupo9.modelo.publicacion.Especie;
import com.tdp2grupo9.modelo.publicacion.Imagen;
import com.tdp2grupo9.modelo.publicacion.PapelesAlDia;
import com.tdp2grupo9.modelo.publicacion.Proteccion;
import com.tdp2grupo9.modelo.publicacion.Raza;
import com.tdp2grupo9.modelo.publicacion.Sexo;
import com.tdp2grupo9.modelo.publicacion.Tamanio;
import com.tdp2grupo9.modelo.publicacion.VacunasAlDia;
import com.tdp2grupo9.utils.Connection;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Publicacion {

    private static final String LOG_TAG = "BSH.Publicacion";

    private static Integer TPUBLICACION_MASCOTA_ADOPCION = 1;
    private static Integer TPUBLICACION_PEDIDO_ADOPCION = 2;
    private static Integer TPUBLICACION_MASCOTA_PERDIDA = 3;
    private static Integer TPUBLICACION_MASCOTA_ENCONTRADA = 4;

    private Integer id;
    private Integer tipoPublicacion;
    private String nombreMascota;
    private String condiciones;
    private String videoLink;
    private String contacto;
    private Boolean requiereCuidadosEspeciales;
    private Boolean necesitaTransito;
    private Raza raza;
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
    private List<Imagen> imagenes;
    private Double latitud;
    private Double longitud;

    public Publicacion() {
        this.id = 0;
        this.tipoPublicacion = TPUBLICACION_MASCOTA_ADOPCION;
        this.nombreMascota = "";
        this.condiciones = "";
        this.videoLink = "";
        this.contacto = "";
        this.raza = new Raza();
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
        this.requiereCuidadosEspeciales = null;
        this.necesitaTransito = null;
        this.imagenes = new ArrayList<>();
        this.latitud = 0.0;
        this.longitud = 0.0;
    }

    private static List<Publicacion> jsonToPublicaciones(JsonReader reader) throws IOException, JSONException {
        List<Publicacion> publicaciones = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Publicacion publicacion = new Publicacion();
            publicacion.jsonToPublicacion(reader);
            publicaciones.add(publicacion);
        }
        reader.endArray();
        return publicaciones;
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
                case "id":
                    this.id = reader.nextInt();
                    break;
                case "tipoPublicacion":
                    this.tipoPublicacion = reader.nextInt();
                    break;
                case "nombreMascota":
                    this.nombreMascota = "";
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.nombreMascota = reader.nextString();
                    break;
                case "condiciones":
                    this.condiciones = "";
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.condiciones = reader.nextString();
                    break;
                case "videoLink":
                    this.videoLink = "";
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.videoLink = reader.nextString();
                    break;
                case "latitud":
                    this.latitud = reader.nextDouble();
                    break;
                case "longitud":
                    this.longitud = reader.nextDouble();
                    break;
                case "requiereCuidadosEspeciales":
                    this.requiereCuidadosEspeciales = reader.nextBoolean();
                    break;
                case "necesitaTransito":
                    this.necesitaTransito = reader.nextBoolean();
                    break;
                case "foto":
                	Imagen img = new Imagen();
                	img.setImg(Imagen.bytesFromBase64URL_SAFE(reader.nextString()));
                    this.imagenes.add(img);
                    break;
                case "fotos":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                    	this.imagenes = Imagen.getImagenesfromJson(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public void guardarPublicacion(String token) {
        String METHOD = "guardarPublicacion";

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
                    "&necesitaTransito="+this.necesitaTransito +"&raza="+this.raza.getId()+
                    "&condiciones="+this.condiciones+"&requiereCuidadosEspeciales="+this.requiereCuidadosEspeciales+
                    "&latitud="+this.latitud+"&longitud="+this.longitud+"&videoLink="+this.videoLink);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_CREATED) {

                this.jsonToPublicacion(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));

                Log.d(LOG_TAG, METHOD + " publicacion guardada id " + this.id);
            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada" + urlConnection.getResponseMessage());
            }

        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        Log.d(LOG_TAG, METHOD + " adjuntando " + this.imagenes.size() + " imagenes....");

        for(Imagen bmp: this.imagenes) {
        	bmp.setPublicacionId(this.id);
            bmp.guardarImagen(token);
        }
        
        Log.d(LOG_TAG, METHOD + " finalizado.");

    }

    public static List<Publicacion> buscarPublicaciones(String token, Integer tipoPublicacion, Integer offset,
                                                        Integer max, Publicacion publicacion) {

        String METHOD = "buscarPublicaciones";

        List<Publicacion> publicaciones = new ArrayList<>();

        HttpURLConnection urlConnection = null;
        try {
            String atributos = "?token="+token+"&tipoPublicacion="+tipoPublicacion+
                    "&longitud="+publicacion.getLongitud() + "&latitud="+publicacion.getLatitud();

            //"&offset="+offset+"max="+max

            if (publicacion.getColor().getId() > 0)
                atributos += "&color="+publicacion.getColor().getId();
            if (publicacion.getCastrado().getId() > 0)
                atributos += "&castrado="+publicacion.getCastrado().getId();
            if (publicacion.getEspecie().getId() > 0)
                atributos += "&especie="+publicacion.getEspecie().getId();
            if (publicacion.getCompatibleCon().getId() > 0)
                atributos += "&compatibleCon="+publicacion.getCompatibleCon().getId();
            if (publicacion.getEdad().getId() > 0)
                atributos += "&edad="+publicacion.getEdad().getId();
            if (publicacion.getEnergia().getId() > 0)
                atributos += "&energia="+publicacion.getEnergia().getId();
            if (publicacion.getPapelesAlDia().getId() > 0)
                atributos += "&papelesAlDia="+publicacion.getPapelesAlDia().getId();
            if (publicacion.getProteccion().getId() > 0)
                atributos += "&proteccion="+publicacion.getProteccion().getId();
            if (publicacion.getSexo().getId() > 0)
                atributos += "&sexo="+publicacion.getSexo().getId();
            if (publicacion.getTamanio().getId() > 0)
                atributos += "&tamanio="+publicacion.getTamanio().getId();
            if (publicacion.getVacunasAlDia().getId() > 0)
                atributos += "&vacunasAlDia="+publicacion.getVacunasAlDia().getId();
            if (publicacion.getRequiereCuidadosEspeciales() != null)
                atributos += "&requiereCuidadosEspeciales="+publicacion.getRequiereCuidadosEspeciales();
            if (publicacion.getNecesitaTransito() != null)
                atributos += "&necesitaTransito="+publicacion.getNecesitaTransito();

            Log.e(LOG_TAG, METHOD + " enviado al servidor " + atributos);

            urlConnection = Connection.getHttpUrlConnection("publicacion/buscar"+atributos);

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                publicaciones = Publicacion.jsonToPublicaciones(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                Log.d(LOG_TAG, METHOD + " publicaciones obtenidas " + publicaciones.size());
            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return publicaciones;
    }

    public static Publicacion obtenerPublicacion(String token, Integer id) {

        String METHOD = "obtenerPublicacion";

        Publicacion publicacion = new Publicacion();

        HttpURLConnection urlConnection = null;
        try {
            String atributos = +id+ "?token=" + token;

            Log.e(LOG_TAG, METHOD + " enviado al servidor " + atributos);

            urlConnection = Connection.getHttpUrlConnection("publicacion/"+atributos);

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                publicacion.jsonToPublicacion(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                Log.d(LOG_TAG, METHOD + " publicacion obtenida id: " + publicacion.getId());

            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return publicacion;
    }

    public Integer getId() {
        return id;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public Castrado getCastrado() {
        return castrado;
    }


    public String getCondiciones() {
        return condiciones;
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

    public Raza getRaza() {
        return raza;
    }

    public Tamanio getTamanio() {
        return tamanio;
    }

    public VacunasAlDia getVacunasAlDia() {
        return vacunasAlDia;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public Double getLatitud() { return this.latitud;}

    public Boolean getRequiereCuidadosEspeciales() {
        return this.requiereCuidadosEspeciales;
    }

    public Boolean getNecesitaTransito() {
        return this.necesitaTransito;
    }

    public void addImagen(Bitmap imagen) {
    	Imagen img = new Imagen();
    	img.setImg(Imagen.bytesFromBitmap(imagen));
        this.imagenes.add(img);
    }
    
    public void addImagen(Imagen imagen) {
        this.imagenes.add(imagen);
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
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

    public void setRaza(Raza raza) {
        this.raza = raza;
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

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public void setNecesitaTransito(Boolean necesitaTransito) {
        this.necesitaTransito = necesitaTransito;
    }

    public void setRequiereCuidadosEspeciales(Boolean requiereCuidadosEspeciales) {
        this.requiereCuidadosEspeciales = requiereCuidadosEspeciales;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }


}
