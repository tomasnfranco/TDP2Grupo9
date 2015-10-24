package com.tdp2grupo9.modelo.publicacion;

import android.graphics.Bitmap;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.tdp2grupo9.modelo.Connection;
import com.tdp2grupo9.modelo.Imagen;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

public class Postulante {

    private static final String LOG_TAG = "BSH.Postulante";

    private Integer id;
    private String username;
    private String email;
    private Imagen foto;

    public Postulante() {
        this.id = 0;
        this.username = "";
        this.email = "";
        this.foto = null;
    }

    private void jsonToPostulante(JsonReader reader) throws JSONException, IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    this.id = reader.nextInt();
                    break;
                case "email":
                    this.email = reader.nextString();
                    break;
                case "username":
                    this.username = reader.nextString();
                    break;
                case "foto":
                    if(reader.peek()== JsonToken.NULL)
                        reader.nextNull();
                    else {
                        this.foto = new Imagen();
                        this.foto.setImg(Imagen.bytesFromBase64DEFAULT(reader.nextString()));
                    }
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public static Postulante obtenerPostulante (String token, Integer postulanteId) {

        String METHOD = "obtenerPostulante";

        Postulante postulante = new Postulante();

        HttpURLConnection urlConnection = null;
        try {
            String atributos = +postulanteId+ "?token=" + token;

            Log.e(LOG_TAG, METHOD + " enviado al servidor " + atributos);

            urlConnection = Connection.getHttpUrlConnection("usuario/" + atributos);

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                postulante.jsonToPostulante(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                Log.d(LOG_TAG, METHOD + " postulante obtenido id: " + postulante.getId());

            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return postulante;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Imagen getFoto() {
        return foto;
    }

    public void setFoto(Bitmap imagen) {
        this.foto = new Imagen();
        foto.setBitmap(imagen);
    }

}
