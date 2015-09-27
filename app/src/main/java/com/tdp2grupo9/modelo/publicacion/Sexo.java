package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sexo {
    private int id;
    private String tipo;

    public Sexo() {
        this.id = 0;
        this.tipo = "DESCONOCIDO";
    }

    public Sexo(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public static List<Sexo> getSexosfromJson(JsonReader reader) throws JSONException, IOException {
        List<Sexo> sexos = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String params = reader.nextName();
            Sexo sexo = new Sexo();
            switch (params) {
                case "id":
                    sexo.setId(reader.nextInt());
                    break;
                case "tipo":
                    sexo.setTipo(reader.nextString());
                    break;
            }
            sexos.add(sexo);
        }
        reader.endArray();
        return sexos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
