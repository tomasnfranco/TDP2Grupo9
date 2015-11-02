package com.tdp2grupo9.modelo;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

public abstract class Connection {

    public static final String LOCALHOST = "localhost";
    public static final String ANDROIDEMUL = "10.0.2.2";
    public static final String GENYHOST = "10.0.3.2";
    public static final String ROMIAPK = "192.168.1.3";

    private static final String SERVERURL = "http://"+GENYHOST+":8080/api/"; //TODO: pasar a un .config o algo

    public static HttpURLConnection getHttpUrlConnection(String controller_action) throws IOException {
        return (HttpURLConnection) new URL(SERVERURL + controller_action).openConnection();
    }

    public static String getHttpStringConnection(String controller_action) throws IOException {
        return SERVERURL + controller_action;
    }
}
