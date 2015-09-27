package com.tdp2grupo9.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.inicio.HomeActivity;

/**
 * Boton para navegar al Home desde la Toolbar.
 */
public class HomeButton extends ImageView {

    public HomeButton(Context context) {
        super(context);
        initialize(context);
    }

    public HomeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public HomeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        this.setBackgroundResource(R.drawable.ic_home);
        this.setAdjustViewBounds(true);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), HomeActivity.class));
            }
        });
    }

}
