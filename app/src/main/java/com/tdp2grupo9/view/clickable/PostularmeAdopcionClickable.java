package com.tdp2grupo9.view.clickable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tdp2grupo9.R;

/**
 * Created by emmanuelfls371 on 24/10/2015.
 */
public class PostularmeAdopcionClickable extends CustomClickable {
    public PostularmeAdopcionClickable(Context context) {
        super(context);
    }

    public PostularmeAdopcionClickable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PostularmeAdopcionClickable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String getText() {
        return getContext().getString(R.string.postularme_adopcion);
    }

    @Override
    public Drawable getImageDrawable() {
        return getResources().getDrawable(R.drawable.button_adoptar);
    }
}
