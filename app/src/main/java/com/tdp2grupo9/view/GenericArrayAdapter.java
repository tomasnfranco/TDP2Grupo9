package com.tdp2grupo9.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.tdp2grupo9.modelo.publicacion.AtributoPublicacion;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericArrayAdapter<T> extends ArrayAdapter<T> {

    // Vars
    protected LayoutInflater mInflater;
    protected Context context;

    public GenericArrayAdapter(Context context, int simple_spinner_item, List<T> objects) {
        super(context, simple_spinner_item, objects);
        init(context);
        this.context = context;
    }

    // Headers
    public abstract void drawText(TextView textView, T object);

    private void init(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;

        if (convertView == null) {
            convertView = mInflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        drawText(vh.textView, getItem(position));

        vh.textView.setTextColor(Color.BLACK);
        if (position == 0)
            vh.textView.setTextColor(Color.GRAY);

        return convertView;
    }

    static class ViewHolder {

        TextView textView;

        protected ViewHolder(View rootView) {
            textView = (TextView) rootView.findViewById(android.R.id.text1);
        }
    }

}
