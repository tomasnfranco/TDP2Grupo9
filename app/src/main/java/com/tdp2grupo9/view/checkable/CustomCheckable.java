package com.tdp2grupo9.view.checkable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tdp2grupo9.R;

public abstract class CustomCheckable extends RelativeLayout implements Checkable {

    private CheckBox mCheckBox;
    private ImageView mCheckView;

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
        initializeCheckView();
        initializeCheckBox();
    }

    private void initializeCheckView() {
        mCheckView = (ImageView) findViewById(R.id.custom_check_image);
        mCheckView.setImageDrawable(getImageDrawable());
    }

    private void initializeCheckBox() {
        mCheckBox = (CheckBox) findViewById(R.id.custom_check);
        mCheckBox.setText(getTextChecked());
        mCheckBox.setTextColor(Color.GRAY);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    mCheckView.setVisibility(VISIBLE);
                } else {
                    mCheckView.setVisibility(INVISIBLE);
                }
            }
        });
    }

    protected abstract String getTextChecked();

    protected abstract Drawable getImageDrawable();

    @Override
    public boolean isChecked() {
        return mCheckBox.isChecked();
    }

    @Override
    public void toggle() {
        this.setChecked(!mCheckBox.isChecked());
    }

    @Override
    public void setChecked(boolean cheched) {
        mCheckBox.setChecked(cheched);
    }

}
