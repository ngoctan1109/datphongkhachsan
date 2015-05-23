package com.example.hieult.datphongkhachsan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.*;

import junit.framework.Test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
//    ListView lv;
//    Button btnxemthongtin;
//    String[] values = new String[]{"Phòng 001", "Phòng 002", "Phòng 003", "Phòng 004", "Phòng 005", "Phòng 006", "Phòng 007",
//            "Phòng 008", "Phòng 008", "Phòng 010"};
import java.util.HashMap;
import java.util.*;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
            ArrayList<Room> mylist = new ArrayList<Room>();

    class Room{
        public int r_id;
        public String r_name;
        public double r_price;
    }
    JSONArray jArray;
    String result = null;
    InputStream is = null;
    StringBuilder sb=null;

    FancyAdapter fa=null;
    static ArrayList<String> rsRow;
//    String[] values = new String[]{"Phòng 001", "Phòng 002", "Phòng 003", "Phòng 004", "Phòng 005", "Phòng 006", "Phòng 007","Phòng 008", "Phòng 008", "Phòng 010"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        lv = (ListView)findViewById(R.id.lvroom);
//        ArrayList<room> srcList = new ArrayList<room>();
//        for (int i = 0; i < values.length; i++) {
//            room item = new room(values[i]);
//            srcList.add(item);
//        }
//        lv.setAdapter(new MyArrayAdapter(this, srcList));
//        lv.setOnItemClickListener(this);
//        btnxemthongtin=(Button)findViewById(R.id.btnxemtt);
//        btnxemthongtin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this,xemthongtin.class);
//                startActivity(intent);
//Phần này tạo kết nối
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.0.104:80/hotel/test.php");
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }catch(Exception e){
            Log.e("log_tag", "Error in http connection"+e.toString());
        }
//Phần này đọc dữ liệu từ WS và lưu dưới dạn chuỗi
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");

            String line="0";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
        }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }

//paring data
//        int ct_id;
//        double ct_price;
//        String ct_name;
//        ArrayList<String> mylist = new ArrayList<String>();
        try{
            jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
                Room rs=new Room();
                rs.r_id=json_data.getInt("id");
                rs.r_name=json_data.getString("name");
                rs.r_price =json_data.getDouble("price");
                mylist.add(rs);
            }
//
            ListView lv = (ListView)findViewById(R.id.lvroom);
    fa = new FancyAdapter();
            lv.setAdapter(fa);


//        ArrayAdapter adapter=new ArrayAdapter<String>(this, R.layout.main,mylist);
//        lv.setAdapter(adapter);
//        lv.setOnItemClickListener(this);
//        Button btntest=(Button)findViewById(R.id.button);
//        btntest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ihienthi=new Intent(MainActivity.this,datphong.class);
//                startActivity(ihienthi);
//            }
//        });
        }
        catch(JSONException e1){
            Toast.makeText(getBaseContext(), "No Room Found" ,Toast.LENGTH_LONG).show();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    class FancyAdapter extends ArrayAdapter<Room>{
        FancyAdapter(){
            super(MainActivity.this,R.layout.main,mylist);
        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent idatphong=new Intent(MainActivity.this,datphong.class);
        startActivity(idatphong);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater= getLayoutInflater();
            convertView = inflater.inflate(R.layout.row, null);
            holder = new ViewHolder(convertView);
//            holder.text = (TextView) convertView.findViewById(R.id.txtphong);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.populateFrom(mylist.get(position));
        return convertView;
    }

    class ViewHolder {
        public TextView id=null;
        public TextView name=null;
        public TextView price=null;
        ImageView icon;
        TextView text;
        ViewHolder(View row){
            id=(TextView)row.findViewById(R.id.item_id);
            name=(TextView)row.findViewById(R.id.item_name);
            price=(TextView)row.findViewById(R.id.item_price);
        }
        void populateFrom(Room r){
            id.setText(r.r_id);
            name.setText(r.r_name);
            price.setText((int) r.r_price);
        }
    }
//    public class CustomListAdapter extends BaseAdapter {
//        private ArrayList<String> listData;
//        private LayoutInflater layoutInflater;
//
//        public CustomListAdapter(Context context, ArrayList<String> listData) {
//            this.listData = listData;
//            layoutInflater = LayoutInflater.from(context);
//        }
//
//        @Override
//        public int getCount() {
//            return listData.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return listData.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView == null) {
//                convertView = layoutInflater.inflate(R.layout.row, null);
//                holder = new ViewHolder();
//                holder.text = (TextView) convertView.findViewById(R.id.txtphong);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            holder.text.setText(String.valueOf(position));
//            return convertView;
//        }
//
//        class ViewHolder {
//            ImageView icon;
//            TextView text;
//        }
//    }
}
