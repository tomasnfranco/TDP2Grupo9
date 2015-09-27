package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Castrado {
    private int id;
    private String tipo;

    public Castrado() {
        this.id = 0;
        this.tipo = "DESCONOCIDO";
    }

    public Castrado(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public static List<Castrado> getCastradosfromJson(JsonReader reader) throws JSONException, IOException {
        List<Castrado> castrados = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String params = reader.nextName();
            Castrado castrado = new Castrado();
            switch (params) {
                case "id":
                    castrado.setId(reader.nextInt());
                    break;
                case "tipo":
                    castrado.setTipo(reader.nextString());
                    break;
            }
            castrados.add(castrado);
        }
        reader.endArray();
        return castrados;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
