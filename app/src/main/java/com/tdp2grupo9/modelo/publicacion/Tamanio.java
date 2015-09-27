package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tamanio {
    int id;
    String tipo;

    public Tamanio() {
        this.id = 0;
        this.tipo = "DESCONOCIDO";
    }

    public Tamanio(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public static List<Tamanio> getTamaniosfromJson(JsonReader reader) throws JSONException, IOException {
        List<Tamanio> tamanios = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String params = reader.nextName();
            Tamanio tamanio = new Tamanio();
            switch (params) {
                case "id":
                    tamanio.setId(reader.nextInt());
                    break;
                case "tipo":
                    tamanio.setTipo(reader.nextString());
                    break;
            }
            tamanios.add(tamanio);
        }
        reader.endArray();
        return tamanios;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
