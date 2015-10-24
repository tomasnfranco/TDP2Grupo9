package com.tdp2grupo9.modelo;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
/*
* #100.16 Test registración y login con email
#102.26 y #108.20 Tipo de publicacion en publicacion y en la alerta
#104.9 La distancia la vuelvo a pasar a double*/
public abstract class Connection {

    private static final String LOCALHOST = "localhost";
    private static final String ANDROIDEMUL = "10.0.2.2";
    private static final String GENYHOST = "10.0.3.2";
    private static final String ROMI = "192.168.0.14";

    private static final String SERVERURL = "http://"+GENYHOST+":8080/api/"; //TODO: pasar a un .config o algo

    public static HttpURLConnection getHttpUrlConnection(String controller_action) throws IOException {
        return (HttpURLConnection) new URL(SERVERURL + controller_action).openConnection();
    }

    public static String getHttpStringConnection(String controller_action) throws IOException {
        return SERVERURL + controller_action;
    }
}
