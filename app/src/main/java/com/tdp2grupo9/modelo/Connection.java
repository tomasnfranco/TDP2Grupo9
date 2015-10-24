package com.tdp2grupo9.modelo;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

public abstract class Connection {

    private static final String LOCALHOST = "localhost";
    private static final String ANDROIDEMUL = "10.0.2.2";
    private static final String GENYHOST = "10.0.3.2";
    private static final String ROMIAPK = "192.168.0.14";

    private static final String SERVERURL = "http://"+GENYHOST+":8080/api/"; //TODO: pasar a un .config o algo

    public static HttpURLConnection getHttpUrlConnection(String controller_action) throws IOException {
        return (HttpURLConnection) new URL(SERVERURL + controller_action).openConnection();
    }

    public static String getHttpStringConnection(String controller_action) throws IOException {
        return SERVERURL + controller_action;
    }
}
