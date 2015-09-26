package com.tdp2grupo9.modelo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class Usuario {

    private static final Usuario INSTANCIA = new Usuario();
    private static final String SERVERURL = "http://10.0.3.2:8080/api/"; //TODO: pasar a un .config o algo
    //10.0.2.2
    //10.0.3.2 genymotion
    private Long facebookId ;
    private String facebookToken;

    private Integer latitud;
    private Integer longitud;

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

    private Usuario() {
        this.resetearAtributos();
    }

    public static Usuario getInstancia() {
        return INSTANCIA;
    }

    private HttpURLConnection getHttpUrlConnection(String controller_action) throws IOException {
        return (HttpURLConnection) new URL(SERVERURL + controller_action).openConnection();
    }

    private void resetearAtributos() {
        this.facebookId = Long.valueOf(0);
        this.facebookToken = "";
        this.latitud = 0;
        this.longitud = 0;
        this.logueado = false;
        this.activo = false;
        this.autopublicar = false;
        this.ofreceTransito = false;
        this.email = "";
        this.password = "";
        this.token = "";
        this.username = "";
        this.telefono = "";
        this.direccion = "";
    }

    private void jsonToUsuario(JSONObject json) throws JSONException {
        this.facebookId = json.getLong("facebookId");
        this.token = json.getString("token");
        Log.i("BuscaSuSHuellas","Token recibido del server:"+token);
        this.latitud = json.getInt("latitud");
        this.longitud = json.getInt("longitud");

        //this.logueado = json.getBoolean("logueado");
        this.activo = json.getBoolean("activo");
        this.autopublicar = json.getBoolean("autoPublicar");
        //this.ofreceTransito = json.getBoolean("ofreceTransito");

        this.email = json.getString("email");
        this.password = json.getString("password");
        this.token = json.getString("token");
        this.username = json.getString("username");
        this.telefono = json.getString("telefono");
        this.direccion = json.getString("direccion");
    }

    public void registrarConFacebook(){
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = this.getHttpUrlConnection("usuario");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("facebookid="+this.facebookId+"&direccion="+this.direccion+"&telefono="+this.telefono);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_CREATED) {
                Log.i("BuscaSusHuellas", "Usuario registrado " + urlConnection.getResponseMessage());
                InputStream streamAParsear;
                streamAParsear = urlConnection.getInputStream();
                this.jsonToUsuario(new JSONObject(streamAParsear.toString()));
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

    public void loginConFacebook(){
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = this.getHttpUrlConnection("usuario/login");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("facebookId="+this.facebookId);
            out.write("");
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                String response = urlConnection.getResponseMessage();
                Log.i("BuscaSusHuellas", "Login realizado correctamente " + response);
                InputStream streamAParsear;
                streamAParsear = urlConnection.getInputStream();
                this.jsonToUsuario(new JSONObject(streamAParsear.toString()));

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

    public void logout(){
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = this.getHttpUrlConnection("usuario/logout");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("token="+this.token);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                this.resetearAtributos();
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
    }

    public void registrar(){
        //TODO: registrar con email y password
        Log.w("BuscaSusHuellas", "Registrar con email y password no implementado");
    }

    public void login(){
        //TODO: login con email y password
        Log.w("BuscaSusHuellas", "Login con email y password no implementado");
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

    public Boolean isLogueado() {
        return logueado;
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

    public Integer getLatitud() {
        return latitud;
    }

    public Integer getLongitud() {
        return longitud;
    }


}