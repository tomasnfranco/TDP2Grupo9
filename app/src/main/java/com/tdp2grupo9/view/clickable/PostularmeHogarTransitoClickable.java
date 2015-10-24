package com.tdp2grupo9.view.clickable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;


public class PostularmeHogarTransitoClickable extends CustomClickable {
    public PostularmeHogarTransitoClickable(Context context) {
        super(context);
    }

    public PostularmeHogarTransitoClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PostularmeHogarTransitoClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getText() {
        return getContext().getString(R.string.postularme_hogar);
    }

    @Override
    public Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.button_transito);
    }
}