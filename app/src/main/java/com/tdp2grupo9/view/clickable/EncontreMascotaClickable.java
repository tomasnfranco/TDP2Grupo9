package com.tdp2grupo9.view.clickable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

/**
 * Created by emmanuelfls371 on 30/10/2015.
 */
public class EncontreMascotaClickable  extends CustomClickable {
    public EncontreMascotaClickable(Context context) {
        super(context);
    }

    public EncontreMascotaClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EncontreMascotaClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getText() {
        return getContext().getString(R.string.encontre_mascota);
    }

    @Override
    public Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.button_reclamo);
    }
}
