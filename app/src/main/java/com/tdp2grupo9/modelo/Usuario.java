package com.tdp2grupo9.modelo;

import android.graphics.Bitmap;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.List;

public class Usuario {


    private static final String LOG_TAG = "BSH.Publicacion";

    private static final Usuario INSTANCIA = new Usuario();

    private Integer id;
    private Long facebookId ;
    private String facebookToken;

    private Double latitud;
    private Double longitud;

    private Boolean logueado;
    private Boolean activo;
    private Boolean autopublicar;
    private Boolean ofreceTransito;

    private String email;
    private String password;
    private String token;
    private String username;
    private String telefono;
    private String direccion;
    private String nombre;
    private String apellido;
    
    private Imagen foto;

    private Usuario() {
        this.resetearAtributos();
    }

    public static Usuario getInstancia() {
        return INSTANCIA;
    }

    public void resetearAtributos() {
        this.id = 0;
        this.facebookId = 0L;
        this.facebookToken = "";
        this.latitud = 0.0;
        this.longitud = 0.0;
        this.logueado = false;
        this.activo = false;
        this.autopublicar = null;
        this.ofreceTransito = null;
        this.email = "";
        this.password = "";
        this.token = "";
        this.username = "";
        this.telefono = "";
        this.direccion = "";
        this.foto = null;
    }

    private void jsonToUsuario(JsonReader reader) throws JSONException, IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    this.id = reader.nextInt();
                    break;
                case "token":
                    this.token = reader.nextString();
                    break;
                case "latitud":
                    this.latitud = reader.nextDouble();
                    break;
                case "longitud":
                    this.longitud = reader.nextDouble();
                    break;
                case "activo":
                    this.activo = reader.nextBoolean();
                    break;
                case "ofreceTransito":
                    this.ofreceTransito = reader.nextBoolean();
                    break;
                case "autopublicar":
                    this.autopublicar = reader.nextBoolean();
                    break;
                case "email":
                    this.email = reader.nextString();
                    break;
                case "username":
                    this.username = reader.nextString();
                    break;
                case "password":
                    this.password = reader.nextString();
                    break;
                case "telefono":
                    this.telefono = reader.nextString();
                    break;
                case "direccion":
                    this.direccion = reader.nextString();
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

    public void registrarConFacebook(){
        String METHOD = "registrarConFacebook";

        Log.d(LOG_TAG, METHOD + " facebookId " + this.facebookId);

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("usuario");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String parametros = "";
            parametros += "facebookId="+this.facebookId;
            parametros += "&direccion="+this.direccion;
            parametros += "&telefono="+this.telefono;
            parametros += "&latitud="+this.latitud.toString().replace('.', ',');
            parametros += "&longitud="+this.longitud.toString().replace('.', ',');
            if (this.getAutoPublicar() != null)
                parametros += "&autoPublicar="+this.autopublicar;
            if (this.getOfreceTransito() != null)
                parametros += "&ofreceTransito="+this.ofreceTransito;
            if (!this.getEmail().isEmpty())
                parametros += "&email="+this.getEmail();
            if (!this.getUsername().isEmpty())
                parametros += "&username="+this.getUsername();

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();

            Log.d(LOG_TAG, METHOD + " url= " + parametros);

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_CREATED) {
                this.jsonToUsuario(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                this.logueado = true;
                Log.d(LOG_TAG, METHOD + " usuario registrado y logueado correctamente.");
            } else {
                this.logueado = false;
                Log.w(LOG_TAG, METHOD + " respuesta no esperada. Usuario no registrado. " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public void loginConFacebook(){
        String METHOD = "loginConFacebook";

        Log.d(LOG_TAG, METHOD + " facebookId " + this.facebookId);

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("usuario/login");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());

            Log.i(LOG_TAG, METHOD + "FACEBOOK ID " + this.facebookId );
            out.write("facebookId="+this.facebookId);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                this.jsonToUsuario(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                this.logueado = true;
                Log.d(LOG_TAG, METHOD + " usuario logueado correctamente.");
            } else {
                this.logueado = false;
                Log.w(LOG_TAG, METHOD + " respuesta no esperada. Usuario no logueado. " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public void logout(){
        String METHOD = "logout";

        Log.d(LOG_TAG, METHOD + " token " + this.token);

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("usuario/logout");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("token=" + this.token);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                this.resetearAtributos();
                Log.d(LOG_TAG, METHOD + " usuario deslogueado correctamente.");
            } else {
                Log.w(LOG_TAG, METHOD + " respuesta no esperada. Usuario no deslogueado. " + urlConnection.getResponseMessage());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public void registrarConEmail(){
        String METHOD = "registrarConEmail";

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("usuario");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String parametros = "";
            parametros += "&email="+this.email;
            parametros += "&password="+this.password;
            parametros += "&direccion="+this.direccion;
            parametros += "&telefono="+this.telefono;
            parametros += "&latitud="+this.latitud.toString().replace('.', ',');
            parametros += "&longitud="+this.longitud.toString().replace('.', ',');
            if (this.getAutoPublicar() != null)
                parametros += "&autoPublicar="+this.autopublicar;
            if (this.getOfreceTransito() != null)
                parametros += "&ofreceTransito="+this.ofreceTransito;

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(parametros);
            out.close();

            Log.d(LOG_TAG, METHOD + " url= " + parametros);

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_CREATED) {
                this.jsonToUsuario(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                this.logueado = true;
                Log.d(LOG_TAG, METHOD + " usuario registrado y logueado correctamente.");
            } else {
                this.logueado = false;
                Log.w(LOG_TAG, METHOD + " respuesta no esperada. Usuario no registrado. " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public void login(){
        String METHOD = "loginConEmail";

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = Connection.getHttpUrlConnection("usuario/login");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("username="+this.username+"&password="+this.password);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                this.jsonToUsuario(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                this.logueado = true;
                Log.d(LOG_TAG, METHOD + " usuario logueado correctamente.");
            } else {
                this.logueado = false;
                Log.w(LOG_TAG, METHOD + " respuesta no esperada. Usuario no logueado. " + urlConnection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            Log.e(LOG_TAG, METHOD + " ERROR ", e);
        }  finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    public void quieroAdoptar(int publicacionId) {
        Publicacion.quieroAdoptar(this.token, publicacionId);
    }

    public void concretarAdopcion(Integer publicacionId, Integer postulanteId) {
        Publicacion.concretarAdopcion(this.token, publicacionId, postulanteId);
    }

    public List<Publicacion> obtenerMisPublicaciones(Integer offset, Integer max) {
        return Publicacion.obtenerPublicacionesDeUsuario(this.token, offset, max);
    }

    public List<Publicacion> obtenerMisPostulaciones(Integer offset, Integer max) {
        return Publicacion.obtenerPostulacionesDeUsuario(this.token, offset, max);
    }

    public List<Alerta> obtenerMisAlertas(Integer offset, Integer max) {
        return Alerta.obtenerAlertasDeUsuario(this.token, offset, max);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public void setFacebookId(Long facebookId) {
        this.facebookId = facebookId;
    }

    public void setFacebookToken (String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setAutopublicar(Boolean autopublicar) {
        this.autopublicar = autopublicar;
    }

    public void setOfreceTransito(Boolean ofreceTransito) {
        this.ofreceTransito = ofreceTransito;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Boolean isLogueado() {
        return logueado;
    }

    public Integer getId() {
        return id;
    }

    public Long getFacebookId() {
        return this.facebookId;
    }

    public String getToken() {
        return token;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Boolean isAutopublicar() {
        return autopublicar;
    }

    public Boolean isOfreceTransito() {
        return ofreceTransito;
    }

    public String getDireccion() {
        return direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        if (this.username.isEmpty())
            return this.nombre.concat(".").concat(this.apellido).toLowerCase();
        return this.username;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setFoto(Bitmap imagen) {
        this.foto = new Imagen();
        foto.setBitmap(imagen);
    }

    public String getFacebookToken() {
        return this.facebookToken;
    }

    public Boolean getOfreceTransito() {
        return ofreceTransito;
    }

    public Object getAutoPublicar() {
        return this.autopublicar;
    }
}