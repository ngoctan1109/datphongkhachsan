package com.example.hieult.datphongkhachsan;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class datphong extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datphong);
        EditText txtngaynhan=(EditText)findViewById(R.id.edtngaybd);
        EditText txtngaytra=(EditText)findViewById(R.id.edtngaytr);
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
}