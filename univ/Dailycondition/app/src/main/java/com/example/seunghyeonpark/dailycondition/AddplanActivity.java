package com.example.seunghyeonpark.dailycondition;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IntegerRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.seunghyeonpark.dailycondition.Database.DatabaseHelper;

import java.util.Calendar;

public class AddplanActivity extends AppCompatActivity implements
        View.OnClickListener {

    // Widget GUI
    Button enroll;
    TextView txtDate, txtTime;
    EditText editText;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplan);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {

        if (v == txtDate) {


            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(year+ "." +(monthOfYear + 1) + "." + dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    mYear = i;
                    mMonth = i1;
                    mDay = i2;
                }
            });


            dpd.show();
        }

        if (v == txtTime) {

            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }
        if(v==enroll)
        {
            Intent enrollintent = new Intent(AddplanActivity.this,MainActivity.class);
            DatabaseHelper myHelper = new DatabaseHelper(getBaseContext());
            //데이터변환 삽입하는과정

            String date = Integer.toString(mYear) +"." + Integer.toString(mMonth) + "." + Integer.toString(mDay);
            String time = Integer.toString(mHour) + ":" + Integer.toString(mMinute);
            String plan = editText.getText().toString();

            String input = date + "/" + time + "/" + plan;


            myHelper.insertDataToSchedule(input);


            Toast.makeText(getApplicationContext(),"일정 추가가 완료되었습니다.",Toast.LENGTH_SHORT).show();
            startActivity(enrollintent);
            finish();
        }
    }

    public void init()
    {
        toolbar= (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("일정 추가");
        setSupportActionBar(toolbar);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTime = (TextView) findViewById(R.id.txtTime);
        enroll = (Button)findViewById(R.id.enroll);
        editText = (EditText)findViewById(R.id.editText);

        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        enroll.setOnClickListener(this);
    }
}
