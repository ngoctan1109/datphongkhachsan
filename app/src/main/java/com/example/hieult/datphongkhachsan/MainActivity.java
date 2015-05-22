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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    ListView lv;

    String[] values = new String[]{"Phòng 001", "Phòng 002", "Phòng 003", "Phòng 004", "Phòng 005", "Phòng 006", "Phòng 007",
            "Phòng 008", "Phòng 008", "Phòng 010"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.lvroom);
        ArrayList<String> srcList = new ArrayList<String>(Arrays.asList(values));
        lv.setAdapter(new MyArrayAdapter(this, srcList));
        lv.setOnItemClickListener(this);
        Button btntest=(Button)findViewById(R.id.button);
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ihienthi=new Intent(MainActivity.this,datphong.class);
                startActivity(ihienthi);
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent idatphong=new Intent(MainActivity.this,datphong.class);
        startActivity(idatphong);
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
