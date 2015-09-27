package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PapelesAlDia {
    private int id;
    private String tipo;

    public PapelesAlDia() {
        this.id = 0;
        this.tipo = "DESCONOCIDO";
    }

    public PapelesAlDia(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }


    public static List<PapelesAlDia> getPapelesAlDiafromJson(JsonReader reader) throws JSONException, IOException {
        List<PapelesAlDia> papelesAlDias = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String params = reader.nextName();
            PapelesAlDia papelesAlDia = new PapelesAlDia();
            switch (params) {
                case "id":
                    papelesAlDia.setId(reader.nextInt());
                    break;
                case "tipo":
                    papelesAlDia.setTipo(reader.nextString());
                    break;
            }
            papelesAlDias.add(papelesAlDia);
        }
        reader.endArray();
        return papelesAlDias;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
