package com.tdp2grupo9.view.clickable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

/**
 * Created by tfranco on 10/5/2015.
 */
public class ClearFiltersClickable extends CustomClickable {

    public ClearFiltersClickable(Context context) {
        super(context);
    }

    public ClearFiltersClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearFiltersClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getText() {
        return getContext().getString(R.string.limpiar_filtros);
    }

    @Override
    public Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.ic_clear_filters);
    }
}
