package com.tdp2grupo9.interpeter;

import android.content.Context;

import com.tdp2grupo9.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tomás on 26/09/2015.
 */
public class AtributosJSONInterpeter {

    public static final String COLOR_KEY = "color";
    public static final String TAMANIO_KEY= "tamanio";
    public static final String EDAD_KEY= "edad";
    public static final String SEXO_KEY= "sexo";
    public static final String ENERGIA_KEY= "energia";
    public static final String COMPATIBLECON_KEY= "compatibleCon";
    public static final String VACUNASALDIA_KEY= "vacunasAlDia";
    public static final String CASTRADO_KEY= "castrado";
    public static final String PAPELESALDIA_KEY= "papelesAlDia";
    public static final String PROTECCION_KEY= "proteccion";
    public static final String ESPECIE_KEY= "especie";

    public static final String TIPO_KEY = "tipo";
    public static final String NOMBRE_KEY = "nombre";
    public static final String COMPATIBLE_CON_KEY = "compatibleCon";

    private JSONObject jsonAtributos;
    Map<String, String> mapFriendlyNames;
    Context context;

    public AtributosJSONInterpeter(Context context) {
        this.context = context;
        createAtributosJson();
        createMapFriendlyNames();
    }

    private void createAtributosJson() {
        try {
            jsonAtributos = new JSONObject(getJsonString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createMapFriendlyNames() {
        mapFriendlyNames = new HashMap<String, String>();
        mapFriendlyNames.put(COLOR_KEY, context.getResources().getString(R.string.color_hint));
        mapFriendlyNames.put(TAMANIO_KEY, context.getResources().getString(R.string.tamanio_hint));
        mapFriendlyNames.put(EDAD_KEY, context.getResources().getString(R.string.edad_hint));
        mapFriendlyNames.put(SEXO_KEY, context.getResources().getString(R.string.sexo_hint));
        mapFriendlyNames.put(ENERGIA_KEY, context.getResources().getString(R.string.energia_hint));
        mapFriendlyNames.put(COMPATIBLECON_KEY, context.getResources().getString(R.string.compatible_ninios_hint));
        mapFriendlyNames.put(VACUNASALDIA_KEY, context.getResources().getString(R.string.vacunas_hint));
        mapFriendlyNames.put(CASTRADO_KEY, context.getResources().getString(R.string.castrado_hint));
        mapFriendlyNames.put(PAPELESALDIA_KEY, context.getResources().getString(R.string.papeles_hint));
        mapFriendlyNames.put(PROTECCION_KEY, context.getResources().getString(R.string.proteccion_hint));
        mapFriendlyNames.put(ESPECIE_KEY, context.getResources().getString(R.string.especie_hint));
    }

    public String[] getAtributos(String key, String atributoKey) {
        try {
            JSONArray jsonArray = jsonAtributos.getJSONArray(key);
            int length = jsonArray.length();
            String atributos[] = new String[length+1];
            atributos[0] = mapFriendlyNames.get(key);
            for (int i = 1; i <= length; i++) {
                atributos[i] = (String)((JSONObject) jsonArray.get(i-1)).get(atributoKey);
            }
            return atributos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new String[0];
    }


    private String getJsonString() {
        //TODO Conectarse con el servidor y obtener el JSON
        return "{\n" +
                "  \"castrado\": [\n" +
                "    {\n" +
                "      \"class\": \"server.Castrado\",\n" +
                "      \"id\": 1,\n" +
                "      \"tipo\": \"Si\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Castrado\",\n" +
                "      \"id\": 2,\n" +
                "      \"tipo\": \"No\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Castrado\",\n" +
                "      \"id\": 3,\n" +
                "      \"tipo\": \"Desconocido\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"color\": [\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 1,\n" +
                "      \"nombre\": \"Apricot\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 2,\n" +
                "      \"nombre\": \"Atigrado\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 3,\n" +
                "      \"nombre\": \"Beige\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 4,\n" +
                "      \"nombre\": \"Blanco\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 5,\n" +
                "      \"nombre\": \"Canela\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 6,\n" +
                "      \"nombre\": \"Fuego\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 7,\n" +
                "      \"nombre\": \"Gris\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 8,\n" +
                "      \"nombre\": \"Marrón\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 9,\n" +
                "      \"nombre\": \"Naranja\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 10,\n" +
                "      \"nombre\": \"Negro\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 11,\n" +
                "      \"nombre\": \"Sal y pimienta\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 12,\n" +
                "      \"nombre\": \"Te\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 13,\n" +
                "      \"nombre\": \"Tricolor\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Color\",\n" +
                "      \"id\": 14,\n" +
                "      \"nombre\": \"Otro\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"compatibleCon\": [\n" +
                "    {\n" +
                "      \"class\": \"server.CompatibleCon\",\n" +
                "      \"id\": 1,\n" +
                "      \"compatibleCon\": \"Niños\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.CompatibleCon\",\n" +
                "      \"id\": 2,\n" +
                "      \"compatibleCon\": \"Otras Mascotas\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"edad\": [\n" +
                "    {\n" +
                "      \"class\": \"server.Edad\",\n" +
                "      \"id\": 1,\n" +
                "      \"nombre\": \"Cachorro (hasta 1 año)\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Edad\",\n" +
                "      \"id\": 2,\n" +
                "      \"nombre\": \"Adulto joven (de 2 a 4 años)\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Edad\",\n" +
                "      \"id\": 3,\n" +
                "      \"nombre\": \"Adulto (de 5 a 9 años)\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Edad\",\n" +
                "      \"id\": 4,\n" +
                "      \"nombre\": \"Viejitos (+ de 10 años)\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Edad\",\n" +
                "      \"id\": 5,\n" +
                "      \"nombre\": \"Desconocido\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"energia\": [\n" +
                "    {\n" +
                "      \"class\": \"server.Energia\",\n" +
                "      \"id\": 1,\n" +
                "      \"tipo\": \"Muy enérgico\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Energia\",\n" +
                "      \"id\": 2,\n" +
                "      \"tipo\": \"Enérgico\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Energia\",\n" +
                "      \"id\": 3,\n" +
                "      \"tipo\": \"Adicto a la televisión\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Energia\",\n" +
                "      \"id\": 4,\n" +
                "      \"tipo\": \"No aplica\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"especie\": [\n" +
                "    {\n" +
                "      \"class\": \"server.Especie\",\n" +
                "      \"id\": 1,\n" +
                "      \"tipo\": \"Perro\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Especie\",\n" +
                "      \"id\": 2,\n" +
                "      \"tipo\": \"Gato\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"papelesAlDia\": [\n" +
                "    {\n" +
                "      \"class\": \"server.PapelesAlDia\",\n" +
                "      \"id\": 1,\n" +
                "      \"tipo\": \"Si\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.PapelesAlDia\",\n" +
                "      \"id\": 2,\n" +
                "      \"tipo\": \"No\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.PapelesAlDia\",\n" +
                "      \"id\": 3,\n" +
                "      \"tipo\": \"No Aplica\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"proteccion\": [\n" +
                "    {\n" +
                "      \"class\": \"server.Proteccion\",\n" +
                "      \"id\": 1,\n" +
                "      \"tipo\": \"Guardián\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Proteccion\",\n" +
                "      \"id\": 2,\n" +
                "      \"tipo\": \"Algo protector\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Proteccion\",\n" +
                "      \"id\": 3,\n" +
                "      \"tipo\": \"Poco o nada protector\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Proteccion\",\n" +
                "      \"id\": 4,\n" +
                "      \"tipo\": \"No aplica\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"sexo\": [\n" +
                "    {\n" +
                "      \"class\": \"server.Sexo\",\n" +
                "      \"id\": 1,\n" +
                "      \"tipo\": \"Macho\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Sexo\",\n" +
                "      \"id\": 2,\n" +
                "      \"tipo\": \"Hembra\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Sexo\",\n" +
                "      \"id\": 3,\n" +
                "      \"tipo\": \"Desconocido\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"tamanio\": [\n" +
                "    {\n" +
                "      \"class\": \"server.Tamanio\",\n" +
                "      \"id\": 1,\n" +
                "      \"tipo\": \"Chico\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Tamanio\",\n" +
                "      \"id\": 2,\n" +
                "      \"tipo\": \"Mediano\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.Tamanio\",\n" +
                "      \"id\": 3,\n" +
                "      \"tipo\": \"Grande\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"vacunasAlDia\": [\n" +
                "    {\n" +
                "      \"class\": \"server.VacunasAlDia\",\n" +
                "      \"id\": 1,\n" +
                "      \"tipo\": \"Si\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.VacunasAlDia\",\n" +
                "      \"id\": 2,\n" +
                "      \"tipo\": \"No\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"class\": \"server.VacunasAlDia\",\n" +
                "      \"id\": 3,\n" +
                "      \"tipo\": \"Desconocido\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

}
