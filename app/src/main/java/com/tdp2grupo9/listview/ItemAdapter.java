package com.tdp2grupo9.listview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2grupo9.R;
import com.tdp2grupo9.listview.Item;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<Item> items;

    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.activity_list_item, parent, false);
        }

        ImageView ivItem = (ImageView) rowView.findViewById(R.id.ivItem);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        ImageView ivCuidadosEspeciales = (ImageView) rowView.findViewById(R.id.iv_cuidados_esp);
        ImageView ivHogarEnTransito = (ImageView) rowView.findViewById(R.id.iv_hogar_transito);

        Item item = this.items.get(position);
        tvTitle.setText(item.getTitle());
        ivItem.setImageResource(item.getImage());
        if(item.requiereCuidadosEspeciales()) ivCuidadosEspeciales.setImageResource(R.drawable.cuidados_especiales);
        if(item.requiereHogarDeTransito()) ivHogarEnTransito.setImageResource(R.drawable.home_transit);

        return rowView;
    }

}