package com.tdp2grupo9.view.clickable;

/**
 * Created by emmanuelfls371 on 25/10/2015.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

public class EliminarPostulacionTransitoClickable extends CustomClickable{
    public EliminarPostulacionTransitoClickable(Context context) {
        super(context);
    }

    public EliminarPostulacionTransitoClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EliminarPostulacionTransitoClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getText() {
        return getContext().getString(R.string.cancelar_postulacion);
    }

    @Override
    public Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.button_transito);
    }
}