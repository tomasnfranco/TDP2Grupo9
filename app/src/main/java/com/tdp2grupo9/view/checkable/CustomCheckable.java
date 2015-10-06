package com.tdp2grupo9.view.checkable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdp2grupo9.R;

public abstract class CustomCheckable extends LinearLayout implements Checkable {

    private String textChecked;
    private String textUnchecked;
    private boolean checked;
    private ImageView crossImage;

    public CustomCheckable(Context context) {
        super(context);
        initialize(context);
    }

    public CustomCheckable(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public CustomCheckable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(getContext(), R.layout.custom_check, this);
        textChecked = getTextChecked();
        textUnchecked = getTextUnhecked();
        ((ImageView) findViewById(R.id.custom_check_image)).setImageDrawable(getImageDrawable());
        ((TextView) findViewById(R.id.custom_check_text)).setText(textUnchecked);
        crossImage = (ImageView) findViewById(R.id.custom_check_cross);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(!checked);
            }
        });
    }

    protected abstract String getTextChecked();

    protected abstract String getTextUnhecked();

    protected abstract Drawable getImageDrawable();

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            ((TextView) findViewById(R.id.custom_check_text)).setText(textChecked);
            crossImage.setVisibility(INVISIBLE);
            crossImage.bringToFront();
        } else {
            ((TextView) findViewById(R.id.custom_check_text)).setText(textUnchecked);
            crossImage.setVisibility(VISIBLE);
        }
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        this.setChecked(!checked);
    }
}
