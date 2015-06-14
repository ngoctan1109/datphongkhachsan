package com.example.hieult.datphongkhachsan;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;

public class datphong extends ActionBarActivity {
    TextView txtphong ;
    EditText hoten , email,txtngaynhan,txtngaytra,cmnd,sdt;
    Button btndatphong ;
    String id , name ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datphong);
        btndatphong = (Button)findViewById(R.id.btndatphong);
        btndatphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData id = new InsertData();
                //chổ này mấy bác gõ ipconfig rồi lấy port của máy mình bỏ vô nha
                id.execute(new String[]{"http://192.168.56.1:8080/hotel/dangkyphong.php"});
                Intent in = new Intent(datphong.this,xemthongtin.class);
                startActivity(in);
            }
        });
        hoten = (EditText)findViewById(R.id.edthoten);
        email = (EditText)findViewById(R.id.edtemail);
        txtngaynhan=(EditText)findViewById(R.id.edtngaybd);
        txtngaytra=(EditText)findViewById(R.id.edtngaytr);
        cmnd=(EditText)findViewById(R.id.edtcmnd);
        sdt=(EditText)findViewById(R.id.edtsdt);
        txtphong = (TextView)findViewById(R.id.txtphong);
        Intent in = this.getIntent();
        Bundle bd = in.getExtras();
        id = bd.getString("id");
        txtphong.setText(bd.getString("name"));


        txtngaynhan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Datepicker datepicker=new Datepicker(v);
                    FragmentTransaction fr=getFragmentManager().beginTransaction();
                    datepicker.show(fr,"DatePicker");
                }
            }
        });
        txtngaytra.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Datepicker datepicker=new Datepicker(v);
                    FragmentTransaction fr=getFragmentManager().beginTransaction();
                    datepicker.show(fr,"DatePicker");
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_datphong, menu);
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

    private class InsertData extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPostExecute(Boolean result) {
            if (result == true) {
                Toast.makeText(datphong.this, " Đặt phòng  thành công ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(datphong.this, " Đã xãy ra lỗi ", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected Boolean doInBackground(String... urls) {

            for (String url1 : urls) {
                try {
                    ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("customer_name", hoten.getText().toString()));
                    pairs.add(new BasicNameValuePair("email", email.getText().toString()));
                    pairs.add(new BasicNameValuePair("arrive_date", txtngaynhan.getText().toString()));
                    pairs.add(new BasicNameValuePair("leave_date", txtngaytra.getText().toString()));
                    pairs.add(new BasicNameValuePair("mobile", sdt.getText().toString()));
                    pairs.add(new BasicNameValuePair("identity_number", cmnd.getText().toString()));

                    pairs.add(new BasicNameValuePair("id_phong", id));

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url1);
                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    HttpResponse response = client.execute(post);

                } catch (ClientProtocolException e) {
                    Toast.makeText(datphong.this, e.toString(), Toast.LENGTH_SHORT).show();
                    return false;
                } catch (IOException e) {
                    Toast.makeText(datphong.this, e.toString(), Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
            return true;
        }
    }
}
