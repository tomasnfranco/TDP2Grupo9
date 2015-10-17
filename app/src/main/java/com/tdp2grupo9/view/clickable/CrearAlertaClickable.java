package com.tdp2grupo9.view.clickable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;


public class CrearAlertaClickable extends CustomClickable{
    public CrearAlertaClickable(Context context) {
        super(context);
    }

    public CrearAlertaClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CrearAlertaClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getText() {
        return getContext().getString(R.string.crear_alerta);
    }

    @Override
    public Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.ic_alerta);
    }
}
