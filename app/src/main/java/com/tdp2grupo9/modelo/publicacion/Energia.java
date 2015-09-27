package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Energia extends AtributoPublicacion {

    public static final String CLAVE = "energia";

    public void jsonToEnergia(JsonReader reader) throws JSONException, IOException {
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

    public static List<Energia> getEnergiasfromJson(JsonReader reader) throws JSONException, IOException {
        List<Energia> energias = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Energia energia = new Energia();
            energia.jsonToEnergia(reader);
            energias.add(energia);
        }
        reader.endArray();
        return energias;
    }

}
