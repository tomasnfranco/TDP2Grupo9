package com.tdp2grupo9.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.adapter.AlertaAdapter;


public class MisNotificacionesFragment extends AlertasFragment{
    private View mFragmentView;

    public static MisNotificacionesFragment newInstance() {
        return new MisNotificacionesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_mis_notificaciones, container, false);

        mListView = (ListView) mFragmentView.findViewById(R.id.listViewMisAlertas);

        buscarAlertasTask = new BuscarAlertasTask();
        buscarAlertasTask.execute((Void) null);

        if (getArguments() != null)
            focusOnItemId = getArguments().getInt("itemId");

        return mFragmentView;
    }

    @Override
    protected void cargarListView() {
        AlertaAdapter adapter = new AlertaAdapter(mFragmentView.getContext(), mAlertas, getActivity());
        mListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
