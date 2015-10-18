package com.tdp2grupo9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Mensaje;

import java.util.List;


public class MensajeAdapter extends BaseAdapter {

    private List<Mensaje> mensajes;
    private Context context;

    public MensajeAdapter(Context context, List<Mensaje> mensajes) {
        this.mensajes = mensajes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mensajes.size();
    }

    @Override
    public Object getItem(int i) {
        return mensajes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View consultasView = getInflatedViewIfNecessary(view, parent);
        ((TextView) consultasView.findViewById(R.id.infConsulta)).setText(mensajes.get(i).getPregunta());
        ((TextView) consultasView.findViewById(R.id.infRespuesta)).setText(mensajes.get(i).getRespuesta());
        return consultasView;
    }

    private View getInflatedViewIfNecessary(View view, ViewGroup viewGroup) {
        View consultasView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            consultasView = inflater.inflate(R.layout.layout_consulta, viewGroup, false);
        } else {
            consultasView = view;
            limpiarConsultasView(consultasView);
        }
        return consultasView;
    }

    private void limpiarConsultasView(View consultasView) {
        ((TextView) consultasView.findViewById(R.id.infConsulta)).setText("");
        ((TextView) consultasView.findViewById(R.id.infRespuesta)).setText("");
    }
}
