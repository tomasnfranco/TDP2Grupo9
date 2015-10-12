package com.tdp2grupo9.view.foto;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.adopcion.PublicarAdopcionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas on 10/10/2015.
 */
public class FotoPicker extends LinearLayout {

    private static final int MAXIMO_FOTOS_PERMITIDAS = 6;
    private Context mContext;
    private PublicarAdopcionFragment mFragment;
    private int mCantidadFotosCargadas = 0;
    private LinearLayout mFotosLayout;
    private ImageView mAddFotoView;
    private List<FotoAdded> mFotosAdded;

    public FotoPicker(Context context) {
        super(context);
        initialize(context);
    }

    public FotoPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public FotoPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(getContext(), R.layout.foto_picker, this);
        mFotosAdded = new ArrayList<FotoAdded>();
        mContext = context;
        mFotosLayout = (LinearLayout) findViewById(R.id.fotos_horizontal_clickable);
        mAddFotoView = (ImageView) findViewById(R.id.add_foto_image_view);
        AddFotoOnClickListener onClickListener = new AddFotoOnClickListener();
        mAddFotoView.setOnClickListener(onClickListener);
    }

    public void setFragment(PublicarAdopcionFragment fragment) {
        mFragment = fragment;
    }

    public List<Bitmap> getImagesBitmaps() {
        List<Bitmap> bitmaps = new ArrayList<Bitmap>();
        for (FotoAdded fotoAdded : mFotosAdded) {
            bitmaps.add(fotoAdded.getImageBitmap());
        }
        return bitmaps;
    }

    private class AddFotoOnClickListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (mCantidadFotosCargadas < MAXIMO_FOTOS_PERMITIDAS) {
                mFragment.loadImageRequest();
            }
            else {
                Toast.makeText(mContext, mContext.getString(R.string.maximo_fotos_alcanzado), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void addFoto(Bitmap bitmap) {
        FotoAdded fotoAdded = new FotoAdded(getContext());
        fotoAdded.setFotoPicker(this);
        fotoAdded.setImageBitmap(bitmap);
        mFotosLayout.addView(fotoAdded, mCantidadFotosCargadas);
        mFotosAdded.add(fotoAdded);
        ++mCantidadFotosCargadas;
        if (mCantidadFotosCargadas == MAXIMO_FOTOS_PERMITIDAS) {
            mAddFotoView.setVisibility(GONE);
        }
    }

    public void removeFotoAdded(FotoAdded fotoAdded) {
        if (mCantidadFotosCargadas > 0) {
            mFotosLayout.removeView(fotoAdded);
            mFotosAdded.remove(fotoAdded);
            --mCantidadFotosCargadas;
            if (mCantidadFotosCargadas == (MAXIMO_FOTOS_PERMITIDAS-1)) {
                mAddFotoView.setVisibility(VISIBLE);
            }
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.no_hay_fotos), Toast.LENGTH_LONG).show();
        }
    }
}
