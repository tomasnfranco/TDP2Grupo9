package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Castrado extends AtributoPublicacion {

    public static final String CLAVE = "castrado";

    public void jsonToCastrado(JsonReader reader) throws JSONException, IOException {
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
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public static List<AtributoPublicacion> getCastradosfromJson(JsonReader reader) throws JSONException, IOException {
        List<AtributoPublicacion> castrados = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Castrado castrado = new Castrado();
            castrado.jsonToCastrado(reader);
            castrados.add(castrado);
        }
        reader.endArray();
        return castrados;
    }

    public String getName() {
        return "Castrado";
    }

}
