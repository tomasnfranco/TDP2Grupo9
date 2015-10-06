package com.tdp2grupo9.view.checkers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

/**
 * Created by Tomas on 03/10/2015.
 */
public class HogarTransitoCheck extends CustomCheck {

    public HogarTransitoCheck(Context context) {
        super(context);
    }

    public HogarTransitoCheck(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HogarTransitoCheck(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected String getTextChecked() {
        return getContext().getString(R.string.requiere_hogar_transito);
    }

    @Override
    protected String getTextUnhecked() {
        return getResources().getString(R.string.no_requiere_hogar_transito);
    }

    @Override
    protected Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.home_transit);
    }

}
