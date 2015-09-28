package com.tdp2grupo9.listview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Publicacion;

public class Item {

    private Activity activity;
    private Publicacion publicacion;

    public Item() {
        super();
    }

    public Item(Publicacion publicacion, Activity activity) {
         this.publicacion = publicacion;
         this.activity = activity;
    }

    public Bitmap getImage() {
        if (publicacion.getImagenes().size() > 0) return publicacion.getImagenes().get(0);
        return BitmapFactory.decodeResource(this.activity.getResources(), R.drawable.icon_default);
    }

    public String getTitle() {
        return publicacion.getNombreMascota();
    }

    public Boolean requiereCuidadosEspeciales() {
        return publicacion.getRequiereCuidadosEspeciales();
    }

    public Boolean requiereHogarDeTransito() {
        return publicacion.getNecesitaTransito();
    }


}