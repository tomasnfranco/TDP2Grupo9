package com.tdp2grupo9.modelo.publicacion;

import android.support.v4.view.NestedScrollingChild;
import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NecesitaTransito extends AtributoPublicacion {

    public static final String CLAVE = "necesitaTransito";

    public void jsonToNecesitaTransito(JsonReader reader) throws JSONException, IOException {
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

    public static List<NecesitaTransito> getNecesitaTransitofromJson(JsonReader reader) throws JSONException, IOException {
        List<NecesitaTransito> necesitaTransitos = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            NecesitaTransito necesitaTransito = new NecesitaTransito();
            necesitaTransito.jsonToNecesitaTransito(reader);
            necesitaTransitos.add(necesitaTransito);
        }
        reader.endArray();
        return necesitaTransitos;
    }

}
