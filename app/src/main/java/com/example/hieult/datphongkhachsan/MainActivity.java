package com.example.hieult.datphongkhachsan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lvroom);
        String[] values = new String[]{"Phòng 001", "Phòng 002", "Phòng 003", "Phòng 004", "Phòng 005", "Phòng 006", "Phòng 007",
                "Phòng 008", "Phòng 008", "Phòng 010"};
        ArrayList<String> srcList = new ArrayList<String>(Arrays.asList(values));
        lv.setAdapter(new CustomListAdapter(this, srcList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iHienthi = new Intent(MainActivity.this,datphong.class);
                startActivity(iHienthi);
            }
        });
    }

    public class CustomListAdapter extends BaseAdapter {
        private ArrayList<String> listData;
        private LayoutInflater layoutInflater;

        public CustomListAdapter(Context context, ArrayList<String> listData) {
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
                holder.text = (TextView) convertView.findViewById(R.id.txtroom);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(String.valueOf(position));
            return convertView;
        }

        class ViewHolder {
            ImageView icon;
            TextView text;
        }
    }
}
