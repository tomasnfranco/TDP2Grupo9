package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompatibleCon {
    private int id;
    private String compatibleCon;

    public CompatibleCon() {
        this.id = 0;
        this.compatibleCon = "DESCONOCIDO";
    }

    public CompatibleCon(int id, String compatibleCon) {
        this.id = id;
        this.compatibleCon = compatibleCon;
    }

    public static List<CompatibleCon> getCompatibilidadesfromJson(JsonReader reader) throws JSONException, IOException {
        List<CompatibleCon> compatibilidades = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String params = reader.nextName();
            CompatibleCon compatibleCon = new CompatibleCon();
            switch (params) {
                case "id":
                    compatibleCon.setId(reader.nextInt());
                    break;
                case "compatibleCon":
                    compatibleCon.setCompatibleCon(reader.nextString());
                    break;
            }
            compatibilidades.add(compatibleCon);
        }
        reader.endArray();
        return compatibilidades;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompatibleCon(String compatibleCon) {
        this.compatibleCon = compatibleCon;
    }
}
