package com.tdp2grupo9.listview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.PublicacionAtributos;
import com.tdp2grupo9.modelo.publicacion.AtributoPublicacion;
import com.tdp2grupo9.modelo.publicacion.Raza;

public class Item {

    private Activity activity;
    private Publicacion publicacion;
    private PublicacionAtributos publicacionAtributos;

    public Item(Publicacion publicacion, Activity activity, PublicacionAtributos publicacionAtributos) {
        this.publicacion = publicacion;
        this.publicacionAtributos = publicacionAtributos;
        this.activity = activity;
    }

    public Bitmap getImage() {
        //if (publicacion.getImagenes().size() > 0) return publicacion.getImagenes().get(0);
        return BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.icon_default);
    }

    public String getTitle() {
        return publicacion.getNombreMascota();
    }

    public String getRaza() {
        for (AtributoPublicacion r : publicacionAtributos.getRazas()) {
            if (r.getId() == publicacion.getRaza().getId())
                return r.getValor();
        }
        return "";
    }

    public String getEdad() {
        for (AtributoPublicacion r : publicacionAtributos.getEdades()) {
            if (r.getId() == publicacion.getEdad().getId())
                return r.getValor();
        }
        return "";
    }

    public Boolean requiereCuidadosEspeciales() {
        return publicacion.getRequiereCuidadosEspeciales();
    }

    public Boolean tieneCondicionesDeAdopcion() {
        return publicacion.getCondiciones().isEmpty();
    }

    public Boolean requiereHogarDeTransito() {
        return publicacion.getNecesitaTransito();
    }


}