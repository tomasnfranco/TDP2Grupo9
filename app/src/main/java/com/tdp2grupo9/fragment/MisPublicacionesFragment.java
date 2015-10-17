package com.tdp2grupo9.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.listview.PublicacionesAdapter;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.Usuario;


public class MisPublicacionesFragment  extends PublicacionesFragment {

    private View mFragmentView;

    public static MisPublicacionesFragment newInstance() {
        return new MisPublicacionesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_mis_publicaciones, container, false);

        Publicacion publicacion = null;
        mListView = (ExpandableListView) mFragmentView.findViewById(R.id.list_view_mis_publicaciones);

        mBuscarAdopcionesTask = new BuscarAdopcionesTask(publicacion, Tipos.MIS_PUBLICACIONES);
        mBuscarAdopcionesTask.execute((Void) null);
        return mFragmentView;
    }

    @Override
    protected void cargarListView() {
        mListView.setAdapter(new PublicacionesAdapter(mFragmentView.getContext(), mPublicaciones));
    }
}
