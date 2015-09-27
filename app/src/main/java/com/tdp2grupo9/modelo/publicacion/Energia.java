package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Energia {
    private int id;
    private String tipo;

    public Energia() {
        this.id = 0;
        this.tipo = "DESCONOCIDO";
    }

     public Energia(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public static List<Energia> getEnergiasfromJson(JsonReader reader) throws JSONException, IOException {
        List<Energia> energias = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String params = reader.nextName();
            Energia energia = new Energia();
            switch (params) {
                case "id":
                    energia.setId(reader.nextInt());
                    break;
                case "tipo":
                    energia.setTipo(reader.nextString());
                    break;
            }
            energias.add(energia);
        }
        reader.endArray();
        return energias;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
