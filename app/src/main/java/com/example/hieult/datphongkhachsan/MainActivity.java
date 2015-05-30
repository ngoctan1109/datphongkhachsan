package com.example.hieult.datphongkhachsan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
//import android.widget.ImageView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import com.example.hieult.datphongkhachsan.Room_helpers;
//public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
//    ListView lv;
//    Button btnxemthongtin;
//    String[] values = new String[]{"Phòng 001", "Phòng 002", "Phòng 003", "Phòng 004", "Phòng 005", "Phòng 006", "Phòng 007",
//            "Phòng 008", "Phòng 008", "Phòng 010"};

public class MainActivity extends Activity {

    class Room {
        public int r_id;
        public String r_name;
        public double r_price;
        public int r_type;
        public String r_img;
    }

    public JSONArray jArray;
    public String result = null;
    public InputStream is = null;
    public StringBuilder sb = null;
    public static JSONObject jObj = null;
    public JSONArray room = null;
//    public FancyAdapter fa = null;
    static ArrayList<String> rs;

    public static String base_url="http://192.168.31.1/hotel/"; //chổ này mấy bác gõ ipconfig rồi lấy port của máy mình bỏ vô nha

    public static String webservice_file;
    static String url;
    public String url1;
    EditText edit_timkiem ;
    ListView lvroom;

//    String[] values = new String[]{"Phòng 001", "Phòng 002", "Phòng 003", "Phòng 004", "Phòng 005", "Phòng 006", "Phòng 007","Phòng 008", "Phòng 008", "Phòng 010"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_timkiem = (EditText)findViewById(R.id.edtloaiphong);
        lvroom = (ListView )findViewById(R.id.lvroom);
        lvroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(MainActivity.this,datphong.class);
                Bundle bd =new Bundle();
                bd.putString("id",lvroom.getAdapter().getItem(position).toString().substring(0,1));
                bd.putString("name",lvroom.getAdapter().getItem(position).toString().substring(2,11));
                in.putExtras(bd);
                startActivity(in);
            }
        });
        webservice_file = "test.php";
        url = base_url+webservice_file;
        ListData j = new ListData();
        j.execute(new String[]{url});

        Button btn = (Button) findViewById(R.id.btntim);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(edit_timkiem.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"Dieu kien tim kiem khong duoc de trong !" , Toast.LENGTH_SHORT ).show();
                }else{
                    webservice_file = "timkiem.php?loaiphong=" + edit_timkiem.getText().toString() ;
                    url = base_url+webservice_file;
                    ListData j = new ListData();
                    j.execute(new String[]{url});
                }
            }
        });
//        Button btntest=(Button)findViewById(R.id.btnxemtt);
//        btntest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ihienthi=new Intent(MainActivity.this,datphong.class);
//                startActivity(ihienthi);
//            }
//        });
    }
    private class ListData extends AsyncTask<String, Void, Boolean>{
        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Loading ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
        protected void onPostExecute(Boolean result) {
            if (result == true){
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,list1);

                lvroom.setAdapter(adapter1);
            }
            else {
                Toast.makeText(MainActivity.this, " Error " , Toast.LENGTH_SHORT).show();
            }

//        @Override
//        protected void onPostExecute(String json) {
//            super.onPostExecute(json);
//            pDialog.dismiss();
//            mylist = new ArrayList<Room>();
//            try {
//                jArray = new JSONArray(result);
//                for (int i = 0; i < jArray.length(); i++) {
//                    JSONObject json_data = jArray.getJSONObject(i);
//                    Room rs = new Room();
//                    rs.r_id = json_data.getInt("id");
//                    rs.r_name = json_data.getString("name");
//                    rs.r_price = json_data.getDouble("price");
//                    rs.r_type = json_data.getInt("room_type");
//                    rs.r_img = json_data.getString("image");
//                    mylist.add(rs);
//                }////
//                ListView lv = (ListView) findViewById(R.id.lvroom);
//                if (fa == null) {
//                    fa = new FancyAdapter();
//                } else {
//                    fa.notifyDataSetChanged();
//                }
//                lv.setAdapter(fa);
//                Button btntest=(Button)findViewById(R.id.btnxemtt);
//        btntest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent ihienthi=new Intent(MainActivity.this,datphong.class);
//                startActivity(ihienthi);
//            }
//        });
//        }
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
//            } catch (JSONException e1) {
//                Toast.makeText(getBaseContext(), "No Room Found", Toast.LENGTH_LONG).show();
//            } catch (ParseException e1) {
//                e1.printStackTrace();
//            }
        }
        String text = "" ;
        ArrayList<String> list1;
        @Override
        protected Boolean doInBackground(String... urls) {
                InputStream is1 ;
                for(String url1 : urls){
                    try {
                        HttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(url1);
                        HttpResponse response = client.execute(post);
                        is1 = response.getEntity().getContent();

                    } catch (ClientProtocolException e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        return false;
                    } catch (IOException e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    BufferedReader reader;

                    try{
                        reader = new BufferedReader(new InputStreamReader(is1,"iso-8859-1"),8);
                        String line = null ;
                        while ((line = reader.readLine() ) != null ){
                            text += line + "\n" ;
                        }
                        is1.close();
                    }
                    catch (UnsupportedEncodingException e){
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    list1 = new ArrayList();
                    try {
                        JSONArray jArray = new JSONArray(text);

                        for(int i=0 ; i<jArray.length();i++){
                            JSONObject jsonData = jArray.getJSONObject(i);
                            list1.add(jsonData.getString("id") +" " + jsonData.getString("name") + "  "+jsonData.getString("price"));
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            return true;
        }

//        public void GetData(){
//            Intent in = new Intent(MainActivity.this,datphong.class);
//                Bundle bd = new Bundle();
//                bd.putString("id",list1.get(0).substring(0,1));
//                in.putExtras(bd);
//            startActivity(in);
//        }
    }
//    ArrayList<Room> mylist;
//    public class JSONParse extends AsyncTask<Void, String, String> {
//        public ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Getting Data ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(Void... args) {
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(url);
//                HttpResponse response = httpclient.execute(httppost);
//                HttpEntity entity = response.getEntity();
//                is = entity.getContent();
//            } catch (Exception e) {
//                Log.e("log_tag", "Error in http connection" + e.toString());
//            }
////Phần này đọc dữ liệu từ WS và lưu dưới dạn chuỗi
//            try {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//                sb = new StringBuilder();
//                sb.append(reader.readLine() + "\n");
//
//                String line = "0";
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line + "\n");
//                }
//                is.close();
//                result = sb.toString();
//            } catch (Exception e) {
//                Log.e("log_tag", "Error converting result " + e.toString());
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String json) {
//            super.onPostExecute(json);
//            pDialog.dismiss();
//            mylist = new ArrayList<Room>();
//            try {
//                jArray = new JSONArray(result);
//                for (int i = 0; i < jArray.length(); i++) {
//                    JSONObject json_data = jArray.getJSONObject(i);
//                    Room rs = new Room();
//                    rs.r_id = json_data.getInt("id");
//                    rs.r_name = json_data.getString("name");
//                    rs.r_price = json_data.getDouble("price");
//                    mylist.add(rs);
//                }////
//                ListView lv = (ListView) findViewById(R.id.lvroom);
//                if (fa == null) {
//                    fa = new FancyAdapter();
//                } else {
//                    fa.notifyDataSetChanged();
//                }
//                lv.setAdapter(fa);
////                Button btntest=(Button)findViewById(R.id.btnxemtt);
////        btntest.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent ihienthi=new Intent(MainActivity.this,datphong.class);
////                startActivity(ihienthi);
////            }
////        });
////        }
////        ArrayAdapter adapter=new ArrayAdapter<String>(this, R.layout.main,mylist);
////        lv.setAdapter(adapter);
////        lv.setOnItemClickListener(this);
////        Button btntest=(Button)findViewById(R.id.button);
////        btntest.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent ihienthi=new Intent(MainActivity.this,datphong.class);
////                startActivity(ihienthi);
////            }
////        });
//            } catch (JSONException e1) {
//                Toast.makeText(getBaseContext(), "No Room Found", Toast.LENGTH_LONG).show();
//            } catch (ParseException e1) {
//                e1.printStackTrace();
//            }
//        }
//
//    }

//    public class JSONParse2 extends AsyncTask<Void, String, String> {
//        public ProgressDialog pDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Getting Data ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(Void... args) {
//            try {
//                HttpClient httpclient = new DefaultHttpClient();
//                HttpPost httppost = new HttpPost(url1);
//                HttpResponse response = httpclient.execute(httppost);
//                HttpEntity entity = response.getEntity();
//                is = entity.getContent();
//            } catch (Exception e) {
//                Log.e("log_tag", "Error in http connection" + e.toString());
//            }
////Phần này đọc dữ liệu từ WS và lưu dưới dạn chuỗi
//            try {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//                sb = new StringBuilder();
//                sb.append(reader.readLine() + "\n");
//
//                String line = "0";
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line + "\n");
//                }
//                is.close();
//                result = sb.toString();
//            } catch (Exception e) {
//                Log.e("log_tag", "Error converting result " + e.toString());
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String json) {
//            super.onPostExecute(json);
//            pDialog.dismiss();
//            mylist = new ArrayList<Room>();
//            try {
//                jArray = new JSONArray(result);
//                for (int i = 0; i < jArray.length(); i++) {
//                    JSONObject json_data = jArray.getJSONObject(i);
//                    Room rs = new Room();
//                    rs.r_id = json_data.getInt("id");
//                    rs.r_name = json_data.getString("name");
//                    rs.r_price = json_data.getDouble("price");
//                    mylist.add(rs);
//                }////
//                ListView lv = (ListView) findViewById(R.id.lvroom);
//                if (fa == null) {
//                    fa = new FancyAdapter();
//                } else {
//                    fa.notifyDataSetChanged();
//                }
//                lv.setAdapter(fa);
//            } catch (JSONException e1) {
//                Toast.makeText(getBaseContext(), "No Room Found", Toast.LENGTH_LONG).show();
//            } catch (ParseException e1) {
//                e1.printStackTrace();
//            }
//        }
//
//    }
//    class ViewHolder {
//        public TextView id = null;
//        public TextView name = null;
//        public TextView price = null;
//        public TextView type = null;
//        public ImageView image = null;
//
//        //        ImageView icon;
////        TextView text;
//        ViewHolder(View row) {
//            id = (TextView) row.findViewById(R.id.item_id);
//            name = (TextView) row.findViewById(R.id.item_name);
//            price = (TextView) row.findViewById(R.id.item_price);
//            type = (TextView) row.findViewById(R.id.item_room_type);
//            image = (ImageView) row.findViewById(R.id.item_img);
//        }
//
//        void populateFrom(Room r) {
//            id.setText(Integer.toString(r.r_id));
//            name.setText(r.r_name);
//            price.setText("Giá: " + Double.toString(r.r_price)+" vnđ/đêm");
//            type.setText("Loại phòng: "+Room_helpers.getRoomType(r.r_type));
//            image.setImageResource(Room_helpers.getRoomImage(r.r_type));
//        }
//    }
//    class FancyAdapter extends ArrayAdapter<Room> {
//        FancyAdapter() {
//            super(MainActivity.this, android.R.layout.simple_list_item_1, mylist);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView == null) {
//                LayoutInflater inflater = getLayoutInflater();
//                convertView = inflater.inflate(R.layout.main, null);
//                holder = new ViewHolder(convertView);
////            holder.text = (TextView) convertView.findViewById(R.id.txtphong);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//            holder.populateFrom(mylist.get(position));
//            return convertView;
//        }
//    }




    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent idatphong = new Intent(MainActivity.this, datphong.class);
        startActivity(idatphong);
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
