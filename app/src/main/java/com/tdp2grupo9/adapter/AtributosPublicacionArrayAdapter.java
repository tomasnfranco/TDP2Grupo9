package com.tdp2grupo9.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdp2grupo9.modelo.publicacion.AtributoPublicacion;

import java.util.List;

public class AtributosPublicacionArrayAdapter extends GenericArrayAdapter<AtributoPublicacion> {

    private List<AtributoPublicacion> atributos;

    public AtributosPublicacionArrayAdapter(Context context, int simple_spinner_item, List<AtributoPublicacion> objects) {
        super(context, simple_spinner_item, objects);
        atributos = objects;
    }

    @Override public void drawText(TextView textView, AtributoPublicacion object) {
        textView.setText(object.getValor());
    }

    public AtributoPublicacion getItem(int position){
        return atributos.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        final ViewHolder vh;

        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        drawText(vh.textView, getItem(position));

        if (position == 0)
            vh.textView.setText("");

        return convertView;
    }

}
