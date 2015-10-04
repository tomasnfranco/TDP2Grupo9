package com.tdp2grupo9.view.checkers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

/**
 * Created by Tomás on 03/10/2015.
 */
public class CuidadosEspecialesCheck extends CustomCheck {

    public CuidadosEspecialesCheck(Context context) {
        super(context);
    }

    public CuidadosEspecialesCheck(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CuidadosEspecialesCheck(Context context, AttributeSet attrs, int defStyleAttr) {
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
