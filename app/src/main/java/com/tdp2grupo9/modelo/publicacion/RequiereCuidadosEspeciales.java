package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequiereCuidadosEspeciales extends AtributoPublicacion {

    public static final String CLAVE = "requiereCuidadosEspeciales";

    public void jsonToRequiereCuidadosEspeciales(JsonReader reader) throws JSONException, IOException {
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

    public static List<AtributoPublicacion> getRequiereCuidadosEspecialesfromJson(JsonReader reader) throws JSONException, IOException {
        List<AtributoPublicacion> requiereCuidadosEspecialess = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            RequiereCuidadosEspeciales requiereCuidadosEspeciales = new RequiereCuidadosEspeciales();
            requiereCuidadosEspeciales.jsonToRequiereCuidadosEspeciales(reader);
            requiereCuidadosEspecialess.add(requiereCuidadosEspeciales);
        }
        reader.endArray();
        return requiereCuidadosEspecialess;
    }

    public String getName() {
        return "Requiere cuidados especiales";
    }
}
