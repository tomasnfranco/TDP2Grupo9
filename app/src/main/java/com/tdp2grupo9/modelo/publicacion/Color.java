package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Color extends AtributoPublicacion {

    public static final String CLAVE = "color";

    public Color() {
        super();
    }

    public Color(int id) {
        super(id);
    }

    public void jsonToColor(JsonReader reader) throws JSONException, IOException {
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

    public static List<AtributoPublicacion> getColoresfromJson(JsonReader reader) throws JSONException, IOException {
        List<AtributoPublicacion> colores = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            Color color = new Color();
            color.jsonToColor(reader);
            colores.add(color);
        }
        reader.endArray();
        return colores;
    }

    public String getName() {
        return "Color";
    }
}
