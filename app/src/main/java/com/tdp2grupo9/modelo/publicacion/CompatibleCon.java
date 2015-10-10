package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompatibleCon extends AtributoPublicacion {

    public static final String CLAVE = "compatibleCon";

    public CompatibleCon() {
        super();
    }

    public CompatibleCon(int id) {
        super(id);
    }

    public void jsonToCompatibleCon(JsonReader reader) throws JSONException, IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "id":
                    this.setId(reader.nextInt());
                    break;
                case "compatibleCon":
                    this.setValor(reader.nextString());
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
    }

    public static List<AtributoPublicacion> getCompatibilidadesfromJson(JsonReader reader) throws JSONException, IOException {
        List<AtributoPublicacion> compatibilidades = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            CompatibleCon compatibleCon = new CompatibleCon();
            compatibleCon.jsonToCompatibleCon(reader);
            compatibilidades.add(compatibleCon);
        }
        reader.endArray();
        return compatibilidades;
    }

    public String getName() {
        return "Compatible Con";
    }

}
