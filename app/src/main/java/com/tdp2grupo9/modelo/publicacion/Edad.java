package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Edad {
    private int id;
    private String nombre;

    public Edad(){
        this.id = 0;
        this.nombre = "DESCONOCIDO";
    }

    public Edad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static List<Edad> getEdadesfromJson(JsonReader reader) throws JSONException, IOException {
        List<Edad> edades = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String params = reader.nextName();
            Edad edad = new Edad();
            switch (params) {
                case "id":
                    edad.setId(reader.nextInt());
                    break;
                case "nombre":
                    edad.setNombre(reader.nextString());
                    break;
            }
            edades.add(edad);
        }
        reader.endArray();
        return edades;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
