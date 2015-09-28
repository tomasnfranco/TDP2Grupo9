package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Raza extends AtributoPublicacion {

    public static final String CLAVE = "raza";

    public void jsonToRaza(JsonReader reader) throws JSONException, IOException {
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

    public static List<AtributoPublicacion> getRazasfromJson(JsonReader reader) throws JSONException, IOException {
        List<AtributoPublicacion> razas = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Raza raza = new Raza();
            raza.jsonToRaza(reader);
            razas.add(raza);
        }
        reader.endArray();
        return razas;
    }

    public String getName() {
        return "Raza";
    }

}
