package com.tdp2grupo9.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Imagen;

import java.util.List;

/**
 * Created by emmanuelfls371 on 17/10/2015.
 */
public class GalleryAdapter extends BaseAdapter {

    private Context context;
    List<Imagen> imagenes;

    public GalleryAdapter(Context context, List<Imagen> imagenes) {
        this.context = context;
        this.imagenes = imagenes;
    }

    @Override
    public int getCount() {
        return imagenes.size();
    }

    @Override
    public Object getItem(int i) {
        return imagenes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(imagenes.get(position).getBitmap());
        imageView.setLayoutParams(new Gallery.LayoutParams(300, 200));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        return imageView;
        /*
        View imagenesView;
        ImageView imagen = new ImageView(context);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            //imagenesView = new View(context);
            imagenesView = inflater.inflate(R.layout.cardview_perfil_mascota_fotos, null);
        } else {
            imagenesView = (View) convertView;
        }
        Bitmap bmp = Bitmap.createScaledBitmap(imagenes.get(position).getBitmap(), 150, 150, true);
        imagen.setImageBitmap(bmp);
        return imagenesView;
        */
    }


}
