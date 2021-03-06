package com.tdp2grupo9.view.clickable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

/**
 * Created by Tomas on 05/10/2015.
 */
public class MapaClickable extends CustomClickable {

    public MapaClickable(Context context) {
        super(context);
    }

    public MapaClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapaClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getText() {
        return getContext().getString(R.string.guardar);
    }

    @Override
    public Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.ic_localizacion);
    }

}
