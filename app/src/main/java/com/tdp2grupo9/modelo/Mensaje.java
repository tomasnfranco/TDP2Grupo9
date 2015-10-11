package com.tdp2grupo9.modelo;

import android.util.JsonReader;
import android.util.Log;

import com.tdp2grupo9.modelo.publicacion.Imagen;
import com.tdp2grupo9.utils.Connection;
import com.tdp2grupo9.utils.Fecha;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mensaje {

    private static final String LOG_TAG = "BSH.Mensaje";

    private int id;
    private int publicacionId;
    private int usuarioPreguntaId;
    private String usuarioPreguntaNombre;
    private String pregunta;
    private String respuesta;
    private Date fechaPregunta;
    private Date fechaRespuesta;

    public Mensaje() {
        this.id = 0;
        this.publicacionId = 0;
        this.usuarioPreguntaId = 0;
        this.usuarioPreguntaNombre = "";
        this.pregunta = "";
        this.respuesta = "";
        this.fechaPregunta = null;
        this.fechaRespuesta = null;
    }

    private static List<Mensaje> jsonToMensajes(JsonReader reader) throws IOException, JSONException {
        List<Mensaje> mensajes = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Mensaje mensaje = new Mensaje();
            mensaje.jsonToMensaje(reader);
            mensajes.add(mensaje);
        }
        reader.endArray();
        return mensajes;
    }

    private void jsonToMensaje(JsonReader reader) throws JSONException, IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    this.id = reader.nextInt();
                    break;
                case "fechaPregunta":
                    this.fechaPregunta = Fecha.parseStringToDateTime(reader.nextString());
                    break;
                case "fechaRespuesta":
                    this.fechaRespuesta = Fecha.parseStringToDateTime(reader.nextString());
                    break;
                case "publicacion":
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String namepublicacion = reader.nextName();
                        switch (namepublicacion) {
                            case "id":
                                this.publicacionId = reader.nextInt();
                                break;
                            default:
                                reader.skipValue();
                                break;
                        }
                    }
                    reader.endObject();
                    break;
                case "respuesta":
                    this.respuesta = reader.nextString();
                    break;
                case "texto":
                    this.pregunta = reader.nextString();
                    break;
                case "pregunta":
                    this.pregunta = reader.nextString();
                    break;
                case "usuarioPregunta":
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String nameusuario = reader.nextName();
                        switch (nameusuario) {
                            case "id":
                                this.usuarioPreguntaId = reader.nextInt();
                                break;
                            default:
                                reader.skipValue();
                                break;
                        }
                    }
                    reader.endObject();
                    break;
                case "usuarioPreguntaNombre":
                    this.usuarioPreguntaNombre = reader.nextString();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public void guardarPregunta(String token) {
        String METHOD = "guardarPregunta";

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("mensaje");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String parametros = "token="+token+"&texto="+this.pregunta+ "&publicacion="+this.publicacionId;

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();

            Log.d(LOG_TAG, METHOD + " url= " + parametros);

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_CREATED) {

                this.jsonToMensaje(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));

                Log.d(LOG_TAG, METHOD + " pregunta guardada id " + this.id);
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

    public void responderPregunta(String token) {
        String METHOD = "responderPregunta";

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("mensaje/responder");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String parametros = "token="+token+"&texto="+this.respuesta+
                                "&mensaje="+this.id;

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();

            Log.d(LOG_TAG, METHOD + " url= " + parametros);

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {

                Log.d(LOG_TAG, METHOD + " respuesta a pregunta enviada ");
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

    public static Mensaje obtenerMensaje(String token, Integer id) {

        String METHOD = "obtenerMensaje";

        Mensaje mensaje = new Mensaje();

        HttpURLConnection urlConnection = null;
        try {
            String atributos = +id+ "?token=" + token;

            Log.e(LOG_TAG, METHOD + " enviado al servidor " + atributos);

            urlConnection = Connection.getHttpUrlConnection("mensaje/"+atributos);

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                mensaje.jsonToMensaje(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                Log.d(LOG_TAG, METHOD + " mensaje obtenido id: " + mensaje.getId());

            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return mensaje;
    }

    public static List<Mensaje> buscarMensajes(String token, Publicacion publicacion) {

        String METHOD = "buscarMensajes";

        List<Mensaje> mensajes = new ArrayList<>();

        HttpURLConnection urlConnection = null;
        try {
            String parametros = "?token="+token+"&publicacion="+publicacion.getId();

            Log.e(LOG_TAG, METHOD + " enviado al servidor " + parametros);

            urlConnection = Connection.getHttpUrlConnection("publicacion/mensajes"+parametros);

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                mensajes = Mensaje.jsonToMensajes(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                Log.d(LOG_TAG, METHOD + " mensjes obtenidos " + mensajes.size());
            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return mensajes;
    }

    public int getId() {
        return id;
    }

    public int getPublicacionId() {
        return publicacionId;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public Date getFechaPregunta() {
        return fechaPregunta;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublicacionId(int publicacionId) {
        this.publicacionId = publicacionId;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public void setFechaPregunta(Date fechaPregunta) {
        this.fechaPregunta = fechaPregunta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }
}
