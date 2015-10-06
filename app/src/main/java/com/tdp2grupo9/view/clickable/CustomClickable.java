package com.tdp2grupo9.view.clickable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdp2grupo9.R;

/**
 * Created by tfranco on 10/5/2015.
 */
public abstract class CustomClickable extends LinearLayout {

    public CustomClickable(Context context) {
        super(context);
        initialize(context);
    }

    public CustomClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public CustomClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(getContext(), R.layout.custom_clickable, this);
        ((ImageView) findViewById(R.id.custom_clickable_image)).setImageDrawable(getImageDrawable());
        ((TextView) findViewById(R.id.custom_clickable_text)).setText(getText());
    }

    public abstract String getText();

    public abstract Drawable getImageDrawable();
}
