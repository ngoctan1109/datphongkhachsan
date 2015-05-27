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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.HttpResponse;
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

//public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
//    ListView lv;
//    Button btnxemthongtin;
//    String[] values = new String[]{"Phòng 001", "Phòng 002", "Phòng 003", "Phòng 004", "Phòng 005", "Phòng 006", "Phòng 007",
//            "Phòng 008", "Phòng 008", "Phòng 010"};

public class MainActivity extends Activity {
       ArrayList<Room> mylist = new ArrayList<Room>();

    class Room {
        public int r_id;
        public String r_name;
        public double r_price;
    }

    public JSONArray jArray;
    public String result = null;
    public InputStream is = null;
    public StringBuilder sb = null;
    public static JSONObject jObj = null;
    public JSONArray room = null;
    public FancyAdapter fa = null;
    static ArrayList<String> rs;
    public static String base_url="http://192.168.56.1:8080/hotel/"; //chổ này mấy bác gõ ipconfig rồi lấy port của máy mình bỏ vô nha
    public static String webservice_file;
    static String url;
//    String[] values = new String[]{"Phòng 001", "Phòng 002", "Phòng 003", "Phòng 004", "Phòng 005", "Phòng 006", "Phòng 007","Phòng 008", "Phòng 008", "Phòng 010"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webservice_file = "hotel/test.php";
        url = base_url+webservice_file;
        JSONParse j = new JSONParse();
        j.execute();

        Button btn = (Button) findViewById(R.id.btntim);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                url = base_url+webservice_file;
                JSONParse j = new JSONParse();
                j.execute();
            }

        });
    }

    public class JSONParse extends AsyncTask<Void, String, String> {
        public ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
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
            try {
                jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Room rs = new Room();
                    rs.r_id = json_data.getInt("id");
                    rs.r_name = json_data.getString("name");
                    rs.r_price = json_data.getDouble("price");
                    mylist.add(rs);
                }////
                ListView lv = (ListView) findViewById(R.id.lvroom);
                if (fa == null) {
                    fa = new FancyAdapter();
                } else {
                    fa.notifyDataSetChanged();
                }
                lv.setAdapter(fa);
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
            } catch (JSONException e1) {
                Toast.makeText(getBaseContext(), "No Room Found", Toast.LENGTH_LONG).show();
            } catch (ParseException e1) {
                e1.printStackTrace();
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
    }


    class ViewHolder {
        public TextView id = null;
        public TextView name = null;
        public TextView price = null;

        //        ImageView icon;
//        TextView text;
        ViewHolder(View row) {
            id = (TextView) row.findViewById(R.id.item_id);
            name = (TextView) row.findViewById(R.id.item_name);
            price = (TextView) row.findViewById(R.id.item_price);
        }

        void populateFrom(Room r) {
            id.setText(Integer.toString(r.r_id));
            name.setText(r.r_name);
            price.setText(Double.toString(r.r_price));
        }
    }


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