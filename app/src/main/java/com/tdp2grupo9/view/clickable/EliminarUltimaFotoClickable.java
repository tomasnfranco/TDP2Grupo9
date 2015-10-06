package com.tdp2grupo9.view.clickable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

/**
 * Created by Tomás on 05/10/2015.
 */
public class EliminarUltimaFotoClickable extends CustomClickable {

    public EliminarUltimaFotoClickable(Context context) {
        super(context);
    }

    public EliminarUltimaFotoClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EliminarUltimaFotoClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getText() {
        return getContext().getString(R.string.eliminar_ultima_foto);
    }

    @Override
    public Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.ic_delete);
    }

}
