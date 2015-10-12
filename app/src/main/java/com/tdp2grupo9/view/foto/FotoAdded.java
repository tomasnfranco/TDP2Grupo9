package com.tdp2grupo9.view.foto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tdp2grupo9.R;

/**
 * Created by Tomas on 10/10/2015.
 */
public class FotoAdded extends RelativeLayout {

    private FotoPicker mFotoPicker;
    private ImageView mFotoImageView;

    public FotoAdded(Context context) {
        super(context);
        initialize(context);
    }

    public FotoAdded(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public FotoAdded(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(getContext(), R.layout.foto_added, this);
        mFotoImageView = (ImageView) findViewById(R.id.foto_image_view);
        ImageView deleteFoto = (ImageView) findViewById(R.id.delete_foto_image_view);
        deleteFoto.bringToFront();
        final FotoAdded fotoAdded = this;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mFotoPicker.removeFotoAdded(fotoAdded);
            }
        });
    }

    public void setFotoPicker(FotoPicker fotoPicker) {
        mFotoPicker = fotoPicker;
    }

    public void setImageBitmap(Bitmap bitmap) {
        mFotoImageView.setImageBitmap(bitmap);
    }

    public Bitmap getImageBitmap() {
        return ((BitmapDrawable) mFotoImageView.getDrawable()).getBitmap();
    }
}
