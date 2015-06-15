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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends Activity {

    class Room {
        public int r_id;
        public String r_name;
        public double r_price;
        public int r_type;
        public String r_img;
        public String r_info;
    }

    public JSONArray jArray;
    public String result = null;
    public InputStream is = null;
    public StringBuilder sb = null;
    public static JSONObject jObj = null;
    public JSONArray room = null;
    //    public FancyAdapter fa = null;
    static ArrayList<String> rs;

    //public static String base_url = "http://192.168.0.103:80/hotel/"; //chổ này mấy bác gõ ipconfig rồi lấy port của máy mình bỏ vô nha
    public static String base_url = "http://192.168.56.1:8080/hotel/"; //chổ này mấy bác gõ ipconfig rồi lấy port của máy mình bỏ vô nha

    public static String webservice_file,timkiem;
    static String url;
    public String url1;
    EditText edit_timkiem;
    ListView lvroom;
    TextView txtname,txtid;
    public static ListData j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_timkiem = (EditText) findViewById(R.id.edtloaiphong);
        lvroom = (ListView) findViewById(R.id.lvroom);

        lvroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(MainActivity.this, datphong.class);
                Bundle bd = new Bundle();
                txtname=(TextView) view.findViewById(R.id.item_name);
                txtid=(TextView) view.findViewById(R.id.item_id);
                bd.putString("id", txtid.getText().toString());
                bd.putString("name", txtname.getText().toString());
                in.putExtras(bd);
                startActivity(in);
            }
        });
        webservice_file = "test.php";
        url = base_url + webservice_file;
        j = new ListData();
        j.execute();

        Button btn = (Button) findViewById(R.id.btntim);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (edit_timkiem.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Nhập loại phòng cần tìm!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String str = edit_timkiem.getText().toString().toLowerCase();
                        timkiem = "timkiem.php?loaiphong=" + Room_helpers.getRoomTypeNumber(str);
                        //webservice_file = "test.php";
                        url = base_url + timkiem;
                        j = new ListData();
                        j.execute();
                        //lvroom.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                        //lvroom.smoothScrollToPosition(lvroom.getAdapter().getCount());
                        //lvroom.setStackFromBottom(true);
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Không thể load dữ liệu", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        Button btnxem = (Button) findViewById(R.id.btnxem);
        btnxem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent ihienthi = new Intent(MainActivity.this,xemthongtin.class);
                startActivity(ihienthi);
            }
        });
    }

    public ArrayList<Room> mylist;
    public FancyAdapter fa;

    public class ListData extends AsyncTask<Void, String, String> {
        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... args) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection" + e.toString());
            }
//Phần này đọc dữ liệu từ WS và lưu dưới dạn chuỗi
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                sb = new StringBuilder();
                sb.append(reader.readLine() + "\n");

                String line = "0";
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            pDialog.dismiss();
            mylist = new ArrayList<Room>();
            try {
                jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Room rs = new Room();
                    rs.r_id = json_data.getInt("id");
                    rs.r_name = json_data.getString("name");
                    rs.r_price = json_data.getDouble("price");
                    rs.r_type = json_data.getInt("room_type");
                   // rs.r_img = json_data.getString("image");
                    //rs.r_info = json_data.getString("description");
                    mylist.add(rs);
                }////
                ListView lv = (ListView) findViewById(R.id.lvroom);
                if (fa == null) {
                    fa = new FancyAdapter();
                } else {
                    fa.notifyDataSetChanged();
                }
                lv.setAdapter(fa);
            } catch (JSONException e1) {
                Toast.makeText(getBaseContext(), "Không tìm thấy phòng nào", Toast.LENGTH_LONG).show();
            } catch (ParseException e1) {
                Toast.makeText(getBaseContext(), "Không thể load dữ liệu", Toast.LENGTH_LONG).show();
            }
        }

    }

    class FancyAdapter extends ArrayAdapter<Room> {
        FancyAdapter() {
            super(MainActivity.this, android.R.layout.simple_list_item_1, mylist);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.main, null);
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
            public TextView idroom=null;
            //public TextView info = null;
            public TextView name = null;
            public TextView price = null;
            public TextView type = null;
            public ImageView image = null;

            //        ImageView icon;
//        TextView text;
            ViewHolder(View row) {
                idroom = (TextView) row.findViewById(R.id.item_id);
                //info = (TextView) row.findViewById(R.id.item_info);
                name = (TextView) row.findViewById(R.id.item_name);
                price = (TextView) row.findViewById(R.id.item_price);
                type = (TextView) row.findViewById(R.id.item_room_type);
                image = (ImageView) row.findViewById(R.id.item_img);
            }

            void populateFrom(Room r) {
                idroom.setText(""+r.r_id);
               //info.setText(r.r_info);
                name.setText(r.r_name);
                price.setText("Giá: " + Double.toString(r.r_price) + " vnđ/đêm");

                type.setText("Loại phòng: " + Room_helpers.getRoomType(r.r_type));
                image.setImageResource(Room_helpers.getRoomImage(r.r_type));
            }
        }
    }
}