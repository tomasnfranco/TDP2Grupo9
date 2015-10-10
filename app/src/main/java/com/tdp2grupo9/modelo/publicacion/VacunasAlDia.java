package com.tdp2grupo9.modelo.publicacion;

import android.util.JsonReader;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VacunasAlDia extends AtributoPublicacion {

    public static final String CLAVE = "vacunasAlDia";

    public VacunasAlDia() {
        super();
    }

    public VacunasAlDia(int id) {
        super(id);
    }

    public void jsonToVacuna(JsonReader reader) throws JSONException, IOException {
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

    public static List<AtributoPublicacion> getVacunasAlDiafromJson(JsonReader reader) throws JSONException, IOException {
        List<AtributoPublicacion> vacunasAlDias = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            VacunasAlDia vacunasAlDia = new VacunasAlDia();
            vacunasAlDia.jsonToVacuna(reader);
            vacunasAlDias.add(vacunasAlDia);
        }
        reader.endArray();
        return vacunasAlDias;
    }

    public String getName() {
        return "Vacunas al dia";
    }

}
