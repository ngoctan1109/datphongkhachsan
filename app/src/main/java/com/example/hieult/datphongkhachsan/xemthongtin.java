package com.example.hieult.datphongkhachsan;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class xemthongtin extends ActionBarActivity {
    EditText edthoten,edtcmnd,edtsdt,edtemail;
    ListView lvthongtin;
    Button btnxem;
    public static String base_url="http://192.168.56.1:8080/hotel/"; //chổ này mấy bác gõ ipconfig rồi lấy port của máy mình bỏ vô nha
    public InputStream is = null;
    public static String webservice_file;
    static String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemthongtin);
        edthoten=(EditText)findViewById(R.id.edthoten);
        edtcmnd=(EditText)findViewById(R.id.edtcmnd);
        edtsdt=(EditText)findViewById(R.id.edtsdt);
        edtemail=(EditText)findViewById(R.id.edtemail);
        lvthongtin=(ListView)findViewById(R.id.lvthongtin);
        btnxem=(Button)findViewById(R.id.btnxem);
        btnxem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten="hoten"+edthoten.getText().toString();
                String email="&email="+edtemail.getText().toString();
                    webservice_file = email;
                    Toast.makeText(xemthongtin.this,webservice_file, Toast.LENGTH_SHORT).show();
                   // url = base_url + webservice_file;
                    //ListData j = new ListData();
                   // j.execute(new String[]{url});
            }
        });
    }
    private class ListData extends AsyncTask<String, Void, Boolean> {
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
            if (result == true) {
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(xemthongtin.this, android.R.layout.simple_list_item_1, list1);

                lvthongtin.setAdapter(adapter1);
            } else {
                Toast.makeText(xemthongtin.this, " Error ", Toast.LENGTH_SHORT).show();
            }
        }

        String text = "";
        ArrayList<String> list1;

        @Override
        protected Boolean doInBackground(String... urls) {
            InputStream is1;
            for (String url1 : urls) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url1);
                    HttpResponse response = client.execute(post);
                    is1 = response.getEntity().getContent();

                } catch (ClientProtocolException e) {
                    Toast.makeText(xemthongtin.this, e.toString(), Toast.LENGTH_SHORT).show();
                    return false;
                } catch (IOException e) {
                    Toast.makeText(xemthongtin.this, e.toString(), Toast.LENGTH_SHORT).show();
                    return false;
                }

                BufferedReader reader;

                try {
                    reader = new BufferedReader(new InputStreamReader(is1, "iso-8859-1"), 8);
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        text += line + "\n";
                    }
                    is1.close();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                list1 = new ArrayList();
                try {
                    JSONArray jArray = new JSONArray(text);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsonData = jArray.getJSONObject(i);
                        list1.add(jsonData.getString("id") + " " + jsonData.getString("name") + "  " + jsonData.getString("price")+ " " + jsonData.getString("arrive_date")+ " " + jsonData.getString("leave_date"));
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return true;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_xemthongtin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
