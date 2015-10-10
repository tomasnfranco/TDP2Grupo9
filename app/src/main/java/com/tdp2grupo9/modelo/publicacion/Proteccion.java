package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Proteccion extends AtributoPublicacion {

    public static final String CLAVE = "proteccion";

    public Proteccion() {
        super();
    }

    public Proteccion(int id) {
        super(id);
    }

    public void jsonToProteccion(JsonReader reader) throws JSONException, IOException {
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

    public static List<AtributoPublicacion> getProteccionesfromJson(JsonReader reader) throws JSONException, IOException {
        List<AtributoPublicacion> protecciones = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Proteccion proteccion= new Proteccion();
            proteccion.jsonToProteccion(reader);
            protecciones.add(proteccion);
        }
        reader.endArray();
        return protecciones;
    }

    public String getName() {
        return "Proteccion";
    }

}
