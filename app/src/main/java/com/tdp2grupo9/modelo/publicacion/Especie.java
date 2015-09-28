package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Especie extends AtributoPublicacion {

    public static final String CLAVE = "especie";

    public void jsonToEspecie(JsonReader reader) throws JSONException, IOException {
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

    public static List<AtributoPublicacion> getEspeciesfromJson(JsonReader reader) throws JSONException, IOException {
        List<AtributoPublicacion> especies = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Especie especie = new Especie();
            especie.jsonToEspecie(reader);
            especies.add(especie);
        }
        reader.endArray();
        return especies;
    }

    public String getName() {
        return "Especie";
    }

}
