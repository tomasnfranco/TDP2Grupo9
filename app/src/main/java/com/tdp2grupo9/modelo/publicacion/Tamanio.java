package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tamanio extends AtributoPublicacion {

    public static final String CLAVE = "tamanio";

    public void jsonToTamanio(JsonReader reader) throws JSONException, IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    this.setId(reader.nextInt());
                    break;
                case "tipo":
                    this.setValor(reader.nextString());
                    break;
            }
        }
        reader.endObject();
    }

    public static List<Tamanio> getTamaniosfromJson(JsonReader reader) throws JSONException, IOException {
        List<Tamanio> tamanios = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Tamanio tamanio = new Tamanio();
            tamanio.jsonToTamanio(reader);
            tamanios.add(tamanio);
        }
        reader.endArray();
        return tamanios;
    }

}
