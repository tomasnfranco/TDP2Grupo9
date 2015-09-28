package com.tdp2grupo9.utils;

import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;

public abstract class Connection {

    private static final String SERVERURL = "http://10.0.3.2:8080/api/"; //TODO: pasar a un .config o algo
    //10.0.3.2 genymotion
    public static HttpURLConnection getHttpUrlConnection(String controller_action) throws IOException {
        return (HttpURLConnection) new URL(SERVERURL + controller_action).openConnection();
    }
}
