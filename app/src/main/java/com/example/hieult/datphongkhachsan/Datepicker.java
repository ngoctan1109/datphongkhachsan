package com.example.hieult.datphongkhachsan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Tân on 5/20/2015.
 */
public class Datepicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText txtdate;
    public Datepicker(View view){
        txtdate=(EditText)view;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int date=calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,date);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String Date=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        txtdate.setText(Date);
    }
}
