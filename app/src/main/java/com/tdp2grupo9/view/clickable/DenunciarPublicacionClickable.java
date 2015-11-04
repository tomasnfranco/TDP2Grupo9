package com.tdp2grupo9.view.clickable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

/**
 * Created by emmanuelfls371 on 25/10/2015.
 */
public class DenunciarPublicacionClickable extends CustomClickable{
    public DenunciarPublicacionClickable(Context context) {
        super(context);
    }

    public DenunciarPublicacionClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DenunciarPublicacionClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getText() {
        return getContext().getString(R.string.denunciar_publicacion);
    }

    @Override
    public Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.button_denunciar);
    }
}