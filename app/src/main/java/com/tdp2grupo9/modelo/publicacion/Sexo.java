package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sexo extends AtributoPublicacion {

    public static final String CLAVE = "sexo";


    public void jsonToSexo(JsonReader reader) throws JSONException, IOException {
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

    public static List<AtributoPublicacion> getSexosfromJson(JsonReader reader) throws JSONException, IOException {
        List<AtributoPublicacion> sexos = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Sexo sexo = new Sexo();
            sexo.jsonToSexo(reader);
            sexos.add(sexo);
        }
        reader.endArray();
        return sexos;
    }

    public String getName() {
        return "Sexo";
    }

}
