package com.tdp2grupo9.modelo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Fecha {

    public static Date parseStringToDateTime(String datetime) {
        //"2015-10-05T21:25:31Z",
        final DateFormat fmt;
        if (datetime.endsWith("Z")) {
            fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        } else {
            fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        }
        try {
            return fmt.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseDate(Date date) {
        int dia = date.getDate();
        int mes = date.getMonth() + 1;
        int anio = date.getYear() + 1900;

        return dia + "/" + mes + "/" + anio;
    }

}
