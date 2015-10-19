package com.tdp2grupo9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.modelo.Mensaje;
import com.tdp2grupo9.utils.TiposEnum;

import java.util.List;


public class MensajeAdapter extends BaseAdapter {

    private List<Mensaje> mensajes;
    private Context context;
    private TiposEnum tipo;
    private String pregunta = "";
    private String respuesta = "";
    private TextView infConsulta;
    private TextView infRespuesta;
    private View viewContainer;

    public MensajeAdapter(Context context, List<Mensaje> mensajes, TiposEnum type) {
        this.mensajes = mensajes;
        this.context = context;
        this.tipo = type;
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
        infConsulta = (TextView) consultasView.findViewById(R.id.infConsulta);
        infRespuesta = (TextView) consultasView.findViewById(R.id.infRespuesta);
        viewContainer = consultasView.findViewById(R.id.viewsContainer);

        pregunta = mensajes.get(i).getPregunta();
        respuesta = mensajes.get(i).getRespuesta();

        if (tipo == TiposEnum.MIS_PUBLICACIONES){
            if (respuesta.isEmpty()){
                infConsulta.setText(pregunta);
                infRespuesta.setVisibility(View.GONE);
            }else {
                infConsulta.setText(pregunta);
                viewContainer.setVisibility(View.GONE);
                infRespuesta.setText(respuesta);
                respuesta = "";
            }
        }else {
            infConsulta.setText(pregunta);
            viewContainer.setVisibility(View.GONE);
            infRespuesta.setText(respuesta);
            respuesta = "";
        }

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
