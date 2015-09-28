package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Edad extends AtributoPublicacion {

    public static final String CLAVE = "edad";

    public void jsonToEdad(JsonReader reader) throws JSONException, IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    this.setId(reader.nextInt());
                    break;
                case "nombre":
                    this.setValor(reader.nextString());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public static List<AtributoPublicacion> getEdadesfromJson(JsonReader reader) throws JSONException, IOException {
        List<AtributoPublicacion> edades = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Edad edad = new Edad();
            edad.jsonToEdad(reader);
            edades.add(edad);
        }
        reader.endArray();
        return edades;
    }

    public String getName() {
        return "Edad";
    }

}
