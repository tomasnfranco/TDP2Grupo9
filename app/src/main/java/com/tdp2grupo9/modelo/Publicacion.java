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
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Publicacion {

    private static final String LOG_TAG = "BSH.Publicacion";

    private Integer id;
    private TipoPublicacion tipoPublicacion;
    private Integer postulanteConcretadoId;
    private Integer postulanteTransitoId;
    private Integer publicadorId;
    private String direccion;
    private String direccionTransito;
    private String publicadorNombre;
    private String concretadoNombre;
    private String transitoNombre;
    private String nombreMascota;
    private String condiciones;
    private String videoLink;
    private String contacto;
    private Boolean concreatada;
    private Boolean enTransito;
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
    private Double latitud;
    private Double longitud;
    private Double distancia;
    private Double latitudTransito;
    private Double longitudTransito;

    private List<Integer> mensajesIds;
    private List<Integer> quierenAdoptarIds;
    private List<Integer> ofrecenTransitoIds;

    private Date fechaPublicacion;
    private List<Imagen> imagenes;
    private List<Mensaje> mensajes;
    private List<Postulante> quierenAdoptar;
    private List<Postulante> ofrecenTransito;

    public Publicacion() {
        this.id = 0;
        this.publicadorId = 0;
        this.tipoPublicacion = null;
        this.direccion = "";
        this.direccionTransito = "";
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
        this.concreatada = false;
        this.enTransito = false;
        this.postulanteConcretadoId = 0;
        this.latitud = 0.0;
        this.longitud = 0.0;
        this.latitudTransito = 0.0;
        this.longitudTransito = 0.0;
        this.distancia = null;
        this.fechaPublicacion = null;
        this.imagenes = new ArrayList<>();
        this.mensajes = new ArrayList<>();
        this.mensajesIds = new ArrayList<>();
        this.quierenAdoptarIds = new ArrayList<>();
        this.ofrecenTransitoIds = new ArrayList<>();
        this.quierenAdoptar = new ArrayList<>();
        this.ofrecenTransito = new ArrayList<>();
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
                case Raza.CLAVE:
                    this.raza.jsonToRaza(reader);
                    break;
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
                case "publicadorId":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.publicadorId = reader.nextInt();
                    break;
                case "direccion":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.direccion = reader.nextString();
                    break;
                case "direccionTransito":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.direccionTransito = reader.nextString();
                    break;
                case "publicadorNombre":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.publicadorNombre = reader.nextString();
                    break;
                case "concretadoNombre":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.concretadoNombre = reader.nextString();
                    break;
                case "transitoNombre":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.transitoNombre = reader.nextString();
                    break;
                case "concretado":
                    this.concreatada = false;
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String nameconcret = reader.nextName();
                            switch (nameconcret) {
                                case "id":
                                    this.postulanteConcretadoId = reader.nextInt();
                                    this.concreatada = true;
                                    break;
                                default:
                                    reader.skipValue();
                                    break;
                            }
                        }
                        reader.endObject();
                    }
                    break;
                case "transito":
                    this.enTransito = false;
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        reader.beginObject();
                        while (reader.hasNext()) {
                            String nameconcret = reader.nextName();
                            switch (nameconcret) {
                                case "id":
                                    this.postulanteTransitoId = reader.nextInt();
                                    this.enTransito = true;
                                    break;
                                default:
                                    reader.skipValue();
                                    break;
                            }
                        }
                        reader.endObject();
                    }
                    break;
                case "tipoPublicacion":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.tipoPublicacion = TipoPublicacion.getTipoPublicacion(reader.nextInt());
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
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.latitud = reader.nextDouble();
                    break;
                case "longitud":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.longitud = reader.nextDouble();
                    break;
                /*case "latitudTransito":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.latitudTransito = reader.nextDouble();
                    break;
                case "longitudTransito":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.longitudTransito = reader.nextDouble();
                    break;*/
                case "distancia":
                    this.distancia = reader.nextDouble();
                    break;
                case "requiereCuidadosEspeciales":
                    this.requiereCuidadosEspeciales = reader.nextBoolean();
                    break;
                case "necesitaTransito":
                    this.necesitaTransito = reader.nextBoolean();
                    break;
                case "foto":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        Imagen img = new Imagen();
                        img.setImg(Imagen.bytesFromBase64DEFAULT(reader.nextString()));
                        this.imagenes.add(img);
                    }
                    break;
                case "fotos":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                    	this.imagenes = Imagen.getImagenesfromJson(reader);
                    break;
                case "fecha":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.fechaPublicacion = Fecha.parseStringToDateTime(reader.nextString());
                    break;
                case "fechaPublicacion":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else
                        this.fechaPublicacion = Fecha.parseStringToDateTime(reader.nextString());
                    break;
                case "preguntas":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String namepreg = reader.nextName();
                                switch (namepreg) {
                                    case "id":
                                        this.mensajesIds.add(reader.nextInt());
                                        break;
                                    default:
                                        reader.skipValue();
                                        break;
                                }
                            }
                            reader.endObject();
                        }
                        reader.endArray();
                    }
                    break;
                case "quierenAdoptar":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String namepost = reader.nextName();
                                switch (namepost) {
                                    case "id":
                                        this.quierenAdoptarIds.add(reader.nextInt());
                                        break;
                                    default:
                                        reader.skipValue();
                                        break;
                                }
                            }
                            reader.endObject();
                        }
                        reader.endArray();
                    }
                    break;
                case "ofrecenTransito":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        reader.beginArray();
                        while (reader.hasNext()) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String namepost = reader.nextName();
                                switch (namepost) {
                                    case "id":
                                        this.ofrecenTransitoIds.add(reader.nextInt());
                                        break;
                                    default:
                                        reader.skipValue();
                                        break;
                                }
                            }
                            reader.endObject();
                        }
                        reader.endArray();
                    }
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

            String atributos = "token="+token+"&tipoPublicacion="+tipoPublicacion.getValue()+
                    "&longitud="+this.getLongitud() + "&latitud="+this.getLatitud() +
                    "&nombreMascota="+this.nombreMascota+"&condiciones="+this.condiciones+"&videoLink="+this.videoLink;

            if (!this.getDireccion().isEmpty())
                atributos += "&direccion="+this.getDireccion();
            if (this.getRaza().getId() > 0)
                atributos += "&raza="+this.getRaza().getId();
            if (this.getColor().getId() > 0)
                atributos += "&color="+this.getColor().getId();
            if (this.getCastrado().getId() > 0)
                atributos += "&castrado="+this.getCastrado().getId();
            if (this.getEspecie().getId() > 0)
                atributos += "&especie="+this.getEspecie().getId();
            if (this.getCompatibleCon().getId() > 0)
                atributos += "&compatibleCon="+this.getCompatibleCon().getId();
            if (this.getEdad().getId() > 0)
                atributos += "&edad="+this.getEdad().getId();
            if (this.getEnergia().getId() > 0)
                atributos += "&energia="+this.getEnergia().getId();
            if (this.getPapelesAlDia().getId() > 0)
                atributos += "&papelesAlDia="+this.getPapelesAlDia().getId();
            if (this.getProteccion().getId() > 0)
                atributos += "&proteccion="+this.getProteccion().getId();
            if (this.getSexo().getId() > 0)
                atributos += "&sexo="+this.getSexo().getId();
            if (this.getTamanio().getId() > 0)
                atributos += "&tamanio="+this.getTamanio().getId();
            if (this.getVacunasAlDia().getId() > 0)
                atributos += "&vacunasAlDia="+this.getVacunasAlDia().getId();
            if (this.getRequiereCuidadosEspeciales() != null)
                atributos += "&requiereCuidadosEspeciales="+this.getRequiereCuidadosEspeciales();
            if (this.getNecesitaTransito() != null)
                atributos += "&necesitaTransito="+this.getNecesitaTransito();

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(atributos);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + atributos);
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

        if (this.getId() > 0) {
            Log.d(LOG_TAG, METHOD + " adjuntando " + this.imagenes.size() + " imagenes....");
            for (Imagen bmp : this.imagenes) {
                bmp.setPublicacionId(this.id);
                bmp.guardarImagen(token);
            }
            Log.d(LOG_TAG, METHOD + " finalizado.");
        }

    }

    public void modificarPublicacion(String token) {
        String METHOD = "modificarPublicacion";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("publicacion/update");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String atributos = "token="+token+"&publicacion="+id+"&tipoPublicacion="+tipoPublicacion.getValue()+
                    "&longitud="+this.getLongitud() + "&latitud="+this.getLatitud() +
                    "&nombreMascota="+this.nombreMascota+"&condiciones="+this.condiciones+"&videoLink="+this.videoLink;

            if (!this.getDireccion().isEmpty())
                atributos += "&direccion="+this.getDireccion();
            if (this.getRaza().getId() > 0)
                atributos += "&raza="+this.getRaza().getId();
            if (this.getColor().getId() > 0)
                atributos += "&color="+this.getColor().getId();
            if (this.getCastrado().getId() > 0)
                atributos += "&castrado="+this.getCastrado().getId();
            if (this.getEspecie().getId() > 0)
                atributos += "&especie="+this.getEspecie().getId();
            if (this.getCompatibleCon().getId() > 0)
                atributos += "&compatibleCon="+this.getCompatibleCon().getId();
            if (this.getEdad().getId() > 0)
                atributos += "&edad="+this.getEdad().getId();
            if (this.getEnergia().getId() > 0)
                atributos += "&energia="+this.getEnergia().getId();
            if (this.getPapelesAlDia().getId() > 0)
                atributos += "&papelesAlDia="+this.getPapelesAlDia().getId();
            if (this.getProteccion().getId() > 0)
                atributos += "&proteccion="+this.getProteccion().getId();
            if (this.getSexo().getId() > 0)
                atributos += "&sexo="+this.getSexo().getId();
            if (this.getTamanio().getId() > 0)
                atributos += "&tamanio="+this.getTamanio().getId();
            if (this.getVacunasAlDia().getId() > 0)
                atributos += "&vacunasAlDia="+this.getVacunasAlDia().getId();
            if (this.getRequiereCuidadosEspeciales() != null)
                atributos += "&requiereCuidadosEspeciales="+this.getRequiereCuidadosEspeciales();
            if (this.getNecesitaTransito() != null)
                atributos += "&necesitaTransito="+this.getNecesitaTransito();

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(atributos);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + atributos);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.d(LOG_TAG, METHOD + " publicacion modificada id " + this.id);

            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada" + urlConnection.getResponseMessage());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public void cancelarPublicacion(String token) {
        String METHOD = "cancelar";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("publicacion/delete");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "token="+token+"&publicacion="+this.getId();
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + parametros);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_NO_CONTENT) {
                this.concreatada = true;
                Log.d(LOG_TAG, METHOD + " postulacion cancelada ");
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

        if (publicacion.getId() > 0) {
            publicacion.setMensajes(Mensaje.buscarMensajes(token, publicacion));

            for (Integer postulanteId: publicacion.getQuierenAdoptarIds()) {
                publicacion.addPostulanteAdopcion(Postulante.obtenerPostulante(token, postulanteId));
            }

            for (Integer postulanteId: publicacion.getOfrecenTransitoIds()) {
                publicacion.addPostulanteTransito(Postulante.obtenerPostulante(token, postulanteId));
            }

        }

        return publicacion;
    }

    public static List<Publicacion> buscarPublicaciones(String token, Integer offset,
                                                        Integer max, Publicacion publicacionBusqueda) {
        String METHOD = "buscarPublicaciones";
        List<Publicacion> publicaciones = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        try {
            String atributos = "?token="+token+
                    "&longitud="+publicacionBusqueda.getLongitud()+
                    "&latitud="+publicacionBusqueda.getLatitud();

            //"&offset="+offset+"max="+max
            if (publicacionBusqueda.getTipoPublicacion() != null)
                atributos += "&tipoPublicacion="+publicacionBusqueda.getTipoPublicacion().getValue();
            if (publicacionBusqueda.getDistancia() != null)
                atributos += "&distancia="+publicacionBusqueda.getDistancia();
            if (publicacionBusqueda.getRaza().getId() > 0)
                atributos += "&raza="+publicacionBusqueda.getRaza().getId();
            if (publicacionBusqueda.getColor().getId() > 0)
                atributos += "&color="+publicacionBusqueda.getColor().getId();
            if (publicacionBusqueda.getCastrado().getId() > 0)
                atributos += "&castrado="+publicacionBusqueda.getCastrado().getId();
            if (publicacionBusqueda.getEspecie().getId() > 0)
                atributos += "&especie="+publicacionBusqueda.getEspecie().getId();
            if (publicacionBusqueda.getCompatibleCon().getId() > 0)
                atributos += "&compatibleCon="+publicacionBusqueda.getCompatibleCon().getId();
            if (publicacionBusqueda.getEdad().getId() > 0)
                atributos += "&edad="+publicacionBusqueda.getEdad().getId();
            if (publicacionBusqueda.getEnergia().getId() > 0)
                atributos += "&energia="+publicacionBusqueda.getEnergia().getId();
            if (publicacionBusqueda.getPapelesAlDia().getId() > 0)
                atributos += "&papelesAlDia="+publicacionBusqueda.getPapelesAlDia().getId();
            if (publicacionBusqueda.getProteccion().getId() > 0)
                atributos += "&proteccion="+publicacionBusqueda.getProteccion().getId();
            if (publicacionBusqueda.getSexo().getId() > 0)
                atributos += "&sexo="+publicacionBusqueda.getSexo().getId();
            if (publicacionBusqueda.getTamanio().getId() > 0)
                atributos += "&tamanio="+publicacionBusqueda.getTamanio().getId();
            if (publicacionBusqueda.getVacunasAlDia().getId() > 0)
                atributos += "&vacunasAlDia="+publicacionBusqueda.getVacunasAlDia().getId();
            if (publicacionBusqueda.getRequiereCuidadosEspeciales() != null)
                atributos += "&requiereCuidadosEspeciales="+publicacionBusqueda.getRequiereCuidadosEspeciales();
            if (publicacionBusqueda.getNecesitaTransito() != null)
                atributos += "&necesitaTransito="+publicacionBusqueda.getNecesitaTransito();

            Log.e(LOG_TAG, METHOD + " enviado al servidor " + atributos);
            urlConnection = Connection.getHttpUrlConnection("publicacion/buscar"+atributos);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                publicaciones = Publicacion.jsonToPublicaciones(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));

                for (Publicacion publicacion: publicaciones) {
                    publicacion.setMensajes(Mensaje.buscarMensajes(token, publicacion));

                    for (Integer postulanteId: publicacion.getQuierenAdoptarIds()) {
                        publicacion.addPostulanteAdopcion(Postulante.obtenerPostulante(token, postulanteId));
                    }

                    for (Integer postulanteId: publicacion.getOfrecenTransitoIds()) {
                        publicacion.addPostulanteTransito(Postulante.obtenerPostulante(token, postulanteId));
                    }
                }

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

    public static List<Publicacion> obtenerPublicacionesDeUsuario(String token, Integer offset, Integer max) {
        String METHOD = "obtenerPublicacionesDeUsuario";
        List<Publicacion> publicaciones = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        try {
            String atributos = "?token="+token;
            //"&offset="+offset+"max="+max
            Log.e(LOG_TAG, METHOD + " enviado al servidor " + atributos);
            urlConnection = Connection.getHttpUrlConnection("publicacion/misPublicaciones"+atributos);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                publicaciones = Publicacion.jsonToPublicaciones(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));

                for (Publicacion publicacion: publicaciones) {
                    publicacion.setMensajes(Mensaje.buscarMensajes(token, publicacion));

                    for (Integer postulanteId: publicacion.getQuierenAdoptarIds()) {
                        publicacion.addPostulanteAdopcion(Postulante.obtenerPostulante(token, postulanteId));
                    }

                    for (Integer postulanteId: publicacion.getOfrecenTransitoIds()) {
                        publicacion.addPostulanteTransito(Postulante.obtenerPostulante(token, postulanteId));
                    }
                }
                Log.d(LOG_TAG, METHOD + " misPublicaciones obtenidas " + publicaciones.size());
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

    public static List<Publicacion> obtenerTodasLasPostulacionesDeUsuario(String token, Integer offset, Integer max) {
        List<Publicacion> adopcionesReclamos = Publicacion.obtenerPostulacionesDeUsuario(token, offset, max);
        List<Publicacion> transitos = Publicacion.obtenerTransitosDeUsuario(token, offset, max);
        return new ArrayList<Publicacion>(new HashSet<Publicacion>(adopcionesReclamos));
    }

    public static List<Publicacion> obtenerPostulacionesDeUsuario(String token, Integer offset, Integer max) {
        String METHOD = "obtenerPostulacionesDeUsuario";
        List<Publicacion> publicaciones = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        try {
            String atributos = "?token="+token;
            //"&offset="+offset+"max="+max
            Log.e(LOG_TAG, METHOD + " enviado al servidor " + atributos);
            urlConnection = Connection.getHttpUrlConnection("publicacion/misPostulaciones"+atributos);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                publicaciones = Publicacion.jsonToPublicaciones(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));

                for (Publicacion publicacion: publicaciones) {
                    publicacion.setMensajes(Mensaje.buscarMensajes(token, publicacion));

                    for (Integer postulanteId: publicacion.getQuierenAdoptarIds()) {
                        publicacion.addPostulanteAdopcion(Postulante.obtenerPostulante(token, postulanteId));
                    }

                    for (Integer postulanteId: publicacion.getOfrecenTransitoIds()) {
                        publicacion.addPostulanteTransito(Postulante.obtenerPostulante(token, postulanteId));
                    }
                }
                Log.d(LOG_TAG, METHOD + " misPostulaciones obtenidas " + publicaciones.size());
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

    public static List<Publicacion> obtenerTransitosDeUsuario(String token, Integer offset, Integer max) {
        String METHOD = "obtenerTransitosDeUsuario";
        List<Publicacion> publicaciones = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        try {
            String atributos = "?token="+token;
            //"&offset="+offset+"max="+max
            Log.e(LOG_TAG, METHOD + " enviado al servidor " + atributos);
            urlConnection = Connection.getHttpUrlConnection("publicacion/misTransitos"+atributos);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                publicaciones = Publicacion.jsonToPublicaciones(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));

                for (Publicacion publicacion: publicaciones) {
                    publicacion.setMensajes(Mensaje.buscarMensajes(token, publicacion));

                    for (Integer postulanteId: publicacion.getQuierenAdoptarIds()) {
                        publicacion.addPostulanteAdopcion(Postulante.obtenerPostulante(token, postulanteId));
                    }

                    for (Integer postulanteId: publicacion.getOfrecenTransitoIds()) {
                        publicacion.addPostulanteTransito(Postulante.obtenerPostulante(token, postulanteId));
                    }
                }
                Log.d(LOG_TAG, METHOD + " misTransitos obtenidos " + publicaciones.size());
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

    public static void ofrezcoTransito(String token, Integer publicacionId) {
        String METHOD = "ofrezcoTransito";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("publicacion/ofrezcoTransito");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "token="+token+"&publicacion="+publicacionId;
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + parametros);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.d(LOG_TAG, METHOD + " postulacion aceptada ");
            } else if (HttpResult == HttpURLConnection.HTTP_FORBIDDEN) {
                Log.d(LOG_TAG, METHOD + " no esta autorizado para adoptar ");
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                Log.d(LOG_TAG, METHOD + " no se encuentra la publicacion ");
            } else if (HttpResult == HttpURLConnection.HTTP_BAD_METHOD) {
                Log.d(LOG_TAG, METHOD + " ya ha ofrecido transito en esta publicacion ");
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

    public static void cancelarTransito(String token, Integer publicacionId) {
        String METHOD = "cancelarTransito";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("publicacion/cancelarTransito");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "token="+token+"&publicacion="+publicacionId;
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + parametros);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.d(LOG_TAG, METHOD + " cancelacion de transito aceptada ");
            } else if (HttpResult == HttpURLConnection.HTTP_FORBIDDEN) {
                Log.d(LOG_TAG, METHOD + " no esta autorizado para cancelar el transito ");
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                Log.d(LOG_TAG, METHOD + " no se encuentra la publicacion ");
            } else if (HttpResult == HttpURLConnection.HTTP_BAD_METHOD) {
                Log.d(LOG_TAG, METHOD + " no ha ofrecido transito en esta publicacion ");
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

    public static void concretarTransito(String token, Integer publicacionId, Integer ofrecioTransitoId) {
        String METHOD = "concretarTransito";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("publicacion/concretarTransito");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "token="+token+"&publicacion="+publicacionId+"&ofrecioTransito="+ofrecioTransitoId;
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + parametros);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.d(LOG_TAG, METHOD + " transito concretado ");
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                Log.d(LOG_TAG, METHOD + " no se encuentra la publicacion ");
            } else if (HttpResult == HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d(LOG_TAG, METHOD + " no se encuentra el usuario que ofrecio transito ");
            } else if (HttpResult == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.d(LOG_TAG, METHOD + " no esta autorizado a concretar transito. El token no es valido. ");
            } else if (HttpResult == HttpURLConnection.HTTP_FORBIDDEN) {
                Log.d(LOG_TAG, METHOD + " el usuario que ofrece transito no se encuentra entre los que ofrecieron ");
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

    public static void quieroAdoptar(String token, Integer publicacionId) {
        String METHOD = "quieroAdoptar";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("publicacion/quieroAdoptar");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "token="+token+"&publicacion="+publicacionId;
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + parametros);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.d(LOG_TAG, METHOD + " postulacion aceptada ");
            } else if (HttpResult == HttpURLConnection.HTTP_FORBIDDEN) {
                Log.d(LOG_TAG, METHOD + " no esta autorizado para adoptar ");
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                Log.d(LOG_TAG, METHOD + " no se encuentra la publicacion ");
            } else if (HttpResult == HttpURLConnection.HTTP_BAD_METHOD) {
                Log.d(LOG_TAG, METHOD + " ya se ha postulado en esta publicacion ");
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

    public static void cancelarQuieroAdoptar(String token, Integer publicacionId) {
        String METHOD = "cancelarQuieroAdoptar";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("publicacion/cancelarPostulacion");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "token="+token+"&publicacion="+publicacionId;
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + parametros);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.d(LOG_TAG, METHOD + " cancelacion de postulacion aceptada ");
            } else if (HttpResult == HttpURLConnection.HTTP_FORBIDDEN) {
                Log.d(LOG_TAG, METHOD + " no esta autorizado para cancelar la postulacion ");
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                Log.d(LOG_TAG, METHOD + " no se encuentra la publicacion ");
            } else if (HttpResult == HttpURLConnection.HTTP_BAD_METHOD) {
                Log.d(LOG_TAG, METHOD + " no ha querido adoptar en esta publicacion ");
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

    public static void concretarAdopcion(String token, Integer publicacionId, Integer postulanteId) {
        String METHOD = "concretarAdopcion";
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("publicacion/concretarAdopcion");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "token="+token+"&publicacion="+publicacionId+"&quiereAdoptar="+postulanteId;
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();
            Log.d(LOG_TAG, METHOD + " url= " + parametros);
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                Log.d(LOG_TAG, METHOD + " adopcion concretada ");
            } else if (HttpResult == HttpURLConnection.HTTP_NOT_FOUND) {
                Log.d(LOG_TAG, METHOD + " no se encuentra la publicacion ");
            } else if (HttpResult == HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d(LOG_TAG, METHOD + " falta elegir el usuario adoptante ");
            } else if (HttpResult == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.d(LOG_TAG, METHOD + " no esta autorizado a concretar esta publicacion. El token no es valido. ");
            } else if (HttpResult == HttpURLConnection.HTTP_FORBIDDEN) {
                Log.d(LOG_TAG, METHOD + " el postulante no esta postulado en la publicacion ");
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

    public Integer getId() {
        return id;
    }

    public TipoPublicacion getTipoPublicacion() {
        return this.tipoPublicacion;
    }

    public String getNombreMascota() {
        return this.nombreMascota;
    }

    public Castrado getCastrado() {
        return this.castrado;
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

    public List<Integer> getMensajesIds() {
        return mensajesIds;
    }

    public List<Integer> getQuierenAdoptarIds() {
        return quierenAdoptarIds;
    }

    public List<Postulante> getQuierenAdoptar() {
        return quierenAdoptar;
    }

    public List<Integer> getOfrecenTransitoIds() {
        return ofrecenTransitoIds;
    }

    public List<Postulante> getOfrecenTransito() {
        return ofrecenTransito;
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

    public Boolean getConcreatada() {
        return this.concreatada;
    }

    public Integer getPostulanteConcretadoId() {
        return this.postulanteConcretadoId;
    }

    public Integer getPublicadorId() {
        return publicadorId;
    }

    public String getPublicadorNombre() {
        return publicadorNombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public Double getDistancia() {
        if (this.distancia != null) {
            try {
                DecimalFormat df = new DecimalFormat("#,##");
                df.setRoundingMode(RoundingMode.CEILING);
                return Double.parseDouble(df.format(this.distancia));
            } catch (NumberFormatException e) {
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.CEILING);
                return Double.parseDouble(df.format(this.distancia));
            }
        }
        return null;
    }

    public String getContacto() {
        return contacto;
    }

    private void addMensaje(Mensaje mensaje) {
        this.mensajes.add(mensaje);
    }

    private void addPostulanteAdopcion(Postulante postulante) {
        this.quierenAdoptar.add(postulante);
    }

    private void addPostulanteTransito(Postulante postulante) {
        this.ofrecenTransito.add(postulante);
    }

    public void addImagen(Bitmap imagen) {
    	Imagen img = new Imagen();
    	img.setBitmap(imagen);
        this.imagenes.add(img);
    }

    public void addImagen(Imagen imagen) {
        this.imagenes.add(imagen);
    }

    public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
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

    public void setPublicadorId(Integer publicadorId) {
        this.publicadorId = publicadorId;
    }

    public void setPublicadorNombre(String publicadorNombre) {
        this.publicadorNombre = publicadorNombre;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean isPostuladoAdopcion(Integer usuarioId) {
        return Collections.frequency(this.quierenAdoptarIds, usuarioId) > 0;
    }

    public Boolean isPostuladoTransito(Integer usuarioId) {
        return Collections.frequency(this.ofrecenTransitoIds, usuarioId) > 0;
    }

    public Integer getPostulanteTransitoId() {
        return postulanteTransitoId;
    }

    public void setPostulanteTransitoId(Integer postulanteTransitoId) {
        this.postulanteTransitoId = postulanteTransitoId;
    }

    public String getDireccionTransito() {
        return direccionTransito;
    }

    public void setDireccionTransito(String direccionTransito) {
        this.direccionTransito = direccionTransito;
    }

    public String getConcretadoNombre() {
        return concretadoNombre;
    }

    public void setConcretadoNombre(String concretadoNombre) {
        this.concretadoNombre = concretadoNombre;
    }

    public String getTransitoNombre() {
        return transitoNombre;
    }

    public void setTransitoNombre(String transitoNombre) {
        this.transitoNombre = transitoNombre;
    }

    public Boolean getEnTransito() {
        return enTransito;
    }

    public void setEnTransito(Boolean enTransito) {
        this.enTransito = enTransito;
    }

    public Double getLatitudTransito() {
        return latitudTransito;
    }

    public void setLatitudTransito(Double latitudTransito) {
        this.latitudTransito = latitudTransito;
    }

    public Double getLongitudTransito() {
        return longitudTransito;
    }

    public void setLongitudTransito(Double longitudTransito) {
        this.longitudTransito = longitudTransito;
    }

    @Override
    public boolean equals(Object o) {
        return Objects.equals(this.id, ((Publicacion) o).id);
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
