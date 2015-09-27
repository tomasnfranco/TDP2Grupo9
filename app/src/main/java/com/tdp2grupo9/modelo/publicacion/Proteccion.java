package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Proteccion {
    private int id;
    private String tipo;

    public Proteccion() {
        this.id = 0;
        this.tipo = "DESCONOCIDO";
    }

    public Proteccion(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public static List<Proteccion> getProteccionesfromJson(JsonReader reader) throws JSONException, IOException {
        List<Proteccion> protecciones = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String params = reader.nextName();
            Proteccion proteccion= new Proteccion();
            switch (params) {
                case "id":
                    proteccion.setId(reader.nextInt());
                    break;
                case "tipo":
                    proteccion.setTipo(reader.nextString());
                    break;
            }
            protecciones.add(proteccion);
        }
        reader.endArray();
        return protecciones;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
