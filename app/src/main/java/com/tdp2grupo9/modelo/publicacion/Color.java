package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Color {
    private int id;
    private String nombre;

    public Color(){
        this.id = 0;
        this.nombre = "DESCONOCIDO";
    }

    public Color(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static List<Color> getColoresfromJson(JsonReader reader) throws JSONException, IOException {
        List<Color> colores = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String params = reader.nextName();
            Color color = new Color();
            switch (params) {
                case "id":
                    color.setId(reader.nextInt());
                    break;
                case "nombre":
                    color.setTipo(reader.nextString());
                    break;
            }
            colores.add(color);
        }
        reader.endArray();
        return colores;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String nombre) {
        this.nombre = nombre;
    }
}
