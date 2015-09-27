package com.tdp2grupo9.modelo;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Usuario {

    private static final Usuario INSTANCIA = new Usuario();
    private static final String SERVERURL = "http://192.168.1.3:8080/api/"; //TODO: pasar a un .config o algo
    //10.0.2.2
    //10.0.3.2 genymotion
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
        this.latitud = Double.valueOf(0);
        this.longitud = Double.valueOf(0);
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

    private void jsonToUsuario(JsonReader reader) throws JSONException, IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
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
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public void registrarConFacebook(){
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = this.getHttpUrlConnection("usuario");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            Log.i("BuscaSusHuellas", "FACEBOOK ID: " + this.facebookId);

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write("facebookId="+this.facebookId+"&direccion="+this.direccion+"&telefono="+this.telefono+"&latitud="+
                    this.latitud+"&longitud="+this.longitud+"&autoPublicar="+this.autopublicar+"&ofreceTransito="+this.ofreceTransito);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_CREATED) {
                Log.i("BuscaSusHuellas", "Usuario registrado " + urlConnection.getResponseMessage());
                this.jsonToUsuario(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                this.logueado = true;
            } else {
                this.logueado = false;
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

            Log.i("BuscaSusHuellas", "FACEBOOK ID " + this.facebookId );
            out.write("facebookId="+this.facebookId);
            out.close();

            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                String response = urlConnection.getResponseMessage();
                Log.i("BuscaSusHuellas", "Login realizado correctamente " + response);
                this.jsonToUsuario(new JsonReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8")));
                this.logueado = true;
            } else {
                this.logueado = false;
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


}