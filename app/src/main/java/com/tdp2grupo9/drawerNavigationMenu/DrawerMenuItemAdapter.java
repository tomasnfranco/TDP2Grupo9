package com.tdp2grupo9.drawerNavigationMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2grupo9.R;

import java.util.List;

public class DrawerMenuItemAdapter extends BaseAdapter{

    List<DrawerMenuItem> mItems;
    Context mContext;
    String email;
    String username;

    public DrawerMenuItemAdapter(Context context,List<DrawerMenuItem> mItems, String email, String username) {
        this.mItems = mItems;
        this.mContext = context;
        this.email = email;
        this.username = username;
    }

    @Override
    public int getCount() {
        return mItems.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position+1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (position == 0){
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            }

            TextView email_user = (TextView) convertView.findViewById(R.id.email);
            TextView username_user = (TextView) convertView.findViewById(R.id.username);
            email_user.setText(this.email);
            username_user.setText(this.username);
            return convertView;
        }else {

            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(DrawerMenuActivity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_drawer_menu_item, null);
            ImageView imgIcon = (ImageView) convertView.findViewById(R.id.img_drawer_menu_item_icon);
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_drawer_menu_item_text);

            DrawerMenuItem item = mItems.get(position-1);

            imgIcon.setImageResource(item.getIcon());
            tvTitle.setText(item.getText());
            return convertView;
        }
    }
}
