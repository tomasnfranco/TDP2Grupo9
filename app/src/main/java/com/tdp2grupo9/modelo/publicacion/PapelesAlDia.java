package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PapelesAlDia extends AtributoPublicacion {

    public static final String CLAVE = "papelesAlDia";

    public void jsonToPapelesAlDia(JsonReader reader) throws JSONException, IOException {
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

    public static List<PapelesAlDia> getPapelesAlDiafromJson(JsonReader reader) throws JSONException, IOException {
        List<PapelesAlDia> papelesAlDias = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            PapelesAlDia papelesAlDia = new PapelesAlDia();
            papelesAlDia.jsonToPapelesAlDia(reader);
            papelesAlDias.add(papelesAlDia);
        }
        reader.endArray();
        return papelesAlDias;
    }
}
