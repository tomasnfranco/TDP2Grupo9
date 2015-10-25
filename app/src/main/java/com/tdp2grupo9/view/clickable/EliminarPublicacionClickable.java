package com.tdp2grupo9.view.clickable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

/**
 * Created by emmanuelfls371 on 25/10/2015.
 */
public class EliminarPublicacionClickable extends CustomClickable{
    public EliminarPublicacionClickable(Context context) {
        super(context);
    }

    public EliminarPublicacionClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EliminarPublicacionClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getText() {
        return getContext().getString(R.string.eliminar_publicacion);
    }

    @Override
    public Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.button_eliminar);
    }
}