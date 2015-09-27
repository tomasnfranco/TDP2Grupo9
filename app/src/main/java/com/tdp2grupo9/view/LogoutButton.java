package com.tdp2grupo9.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.login.LogoutActivity;

/**
 * Boton para navegar al Logout desde la Toolbar.
 */
public class LogoutButton extends ImageView {

    public LogoutButton(Context context) {
        super(context);
        initialize(context);
    }

    public LogoutButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public LogoutButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        this.setBackgroundResource(R.drawable.ic_user);
        this.setAdjustViewBounds(true);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), LogoutActivity.class));
            }
        });
    }
}