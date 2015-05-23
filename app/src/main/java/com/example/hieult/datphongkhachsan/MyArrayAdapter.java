package com.example.hieult.datphongkhachsan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tân on 5/22/2015.
 */
public class MyArrayAdapter extends BaseAdapter {
    private ArrayList<room> listData;
    private LayoutInflater layoutInflater;

    public MyArrayAdapter(Context context, ArrayList<room> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.txtnameroom = (TextView) convertView.findViewById(R.id.txtroom);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        room room=(room)getItem(position);
        holder.txtnameroom.setText(room.getNameroom());
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView txtnameroom;
    }
}