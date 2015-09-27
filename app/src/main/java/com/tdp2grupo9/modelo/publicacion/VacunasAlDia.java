package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VacunasAlDia {
    private int id;
    private String tipo;

    public VacunasAlDia() {
        this.id = 0;
        this.tipo = "DESCONOCIDO";
    }

    public VacunasAlDia(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public static List<VacunasAlDia> getVacunasAlDiafromJson(JsonReader reader) throws JSONException, IOException {
        List<VacunasAlDia> vacunasAlDias = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String params = reader.nextName();
            VacunasAlDia vacunasAlDia = new VacunasAlDia();
            switch (params) {
                case "id":
                    vacunasAlDia.setId(reader.nextInt());
                    break;
                case "tipo":
                    vacunasAlDia.setTipo(reader.nextString());
                    break;
            }
            vacunasAlDias.add(vacunasAlDia);
        }
        reader.endArray();
        return vacunasAlDias;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
