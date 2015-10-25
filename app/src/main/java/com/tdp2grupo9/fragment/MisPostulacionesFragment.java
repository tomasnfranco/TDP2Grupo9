package com.tdp2grupo9.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.listview.PublicacionesAdapter;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.TiposEnum;


public class MisPostulacionesFragment extends PublicacionesFragment {
    private View mFragmentView;

    public static MisPostulacionesFragment newInstance() {
        return new MisPostulacionesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_mis_postulaciones, container, false);

        Publicacion publicacion = null;
        mListView = (ExpandableListView) mFragmentView.findViewById(R.id.list_view_mis_postulaciones);

        mBuscarAdopcionesTask = new PublicacionesFragment.BuscarAdopcionesTask(publicacion, TiposEnum.MIS_POSTULACIONES);
        mBuscarAdopcionesTask.execute((Void) null);
        return mFragmentView;
    }

    @Override
    protected void cargarListView() {
        mListView.setAdapter(new PublicacionesAdapter(mFragmentView.getContext(), mPublicaciones, TiposEnum.MIS_POSTULACIONES));
    }
}
