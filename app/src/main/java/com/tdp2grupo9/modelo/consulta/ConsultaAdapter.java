package com.tdp2grupo9.modelo.consulta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tdp2grupo9.R;

import java.util.List;


public class ConsultaAdapter extends BaseAdapter {

    private List<Consulta> consultas;
    private Context context;

    public ConsultaAdapter(Context context, List<Consulta> consultas) {
        this.consultas = consultas;
        this.context = context;
    }


    @Override
    public int getCount() {
        return consultas.size();
    }

    @Override
    public Object getItem(int i) {
        return consultas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View consultasView = getInflatedViewIfNecessary(view, parent);
        ((TextView) consultasView.findViewById(R.id.infConsulta)).setText(consultas.get(i).getPregunta());
        ((TextView) consultasView.findViewById(R.id.infRespuesta)).setText(consultas.get(i).getRespuesta());
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
