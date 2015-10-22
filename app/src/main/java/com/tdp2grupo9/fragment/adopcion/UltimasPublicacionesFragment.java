package com.tdp2grupo9.fragment.adopcion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.fragment.PublicacionesFragment;
import com.tdp2grupo9.listview.PublicacionesAdapter;
import com.tdp2grupo9.modelo.Publicacion;
import com.tdp2grupo9.modelo.Usuario;
import com.tdp2grupo9.utils.TiposEnum;

/**
 * Created by emmanuelfls371 on 06/10/2015.
 */
public class UltimasPublicacionesFragment extends PublicacionesFragment {

    private View mFragmentView;

    public static UltimasPublicacionesFragment newInstance() {
        return new UltimasPublicacionesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_ultimas_publicaciones, container, false);

        Publicacion publicacion = new Publicacion();
        publicacion.setLongitud(Usuario.getInstancia().getLongitud());
        publicacion.setLatitud(Usuario.getInstancia().getLatitud());
        publicacion.setDistancia(200);

        mListView = (ExpandableListView) mFragmentView.findViewById(R.id.list_view_ultimas_publicaciones);

        mBuscarAdopcionesTask = new BuscarAdopcionesTask(publicacion, TiposEnum.RECIENTES);
        mBuscarAdopcionesTask.execute((Void) null);
        return mFragmentView;
    }

    protected void cargarListView(){
        mListView.setAdapter(new PublicacionesAdapter(mFragmentView.getContext(), mPublicaciones, TiposEnum.RECIENTES));
    }

}
