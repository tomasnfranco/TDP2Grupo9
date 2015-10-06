package com.tdp2grupo9.view.checkable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

/**
 * Created by Tomas on 03/10/2015.
 */
public class CuidadosEspecialesCheckable extends CustomCheckable {

    public CuidadosEspecialesCheckable(Context context) {
        super(context);
    }

    public CuidadosEspecialesCheckable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CuidadosEspecialesCheckable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected String getTextChecked() {
        return getContext().getString(R.string.requiere_cuidados_especiales);
    }

    @Override
    protected String getTextUnhecked() {
        return getResources().getString(R.string.no_requiere_cuidados_especiales);
    }

    @Override
    protected Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.cuidados_especiales);
    }

}
