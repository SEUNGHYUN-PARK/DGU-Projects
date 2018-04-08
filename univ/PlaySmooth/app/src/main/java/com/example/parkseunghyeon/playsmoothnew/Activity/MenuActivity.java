package com.example.parkseunghyeon.playsmoothnew.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;

import com.example.parkseunghyeon.playsmoothnew.BackPressCloseHandler;
import com.example.parkseunghyeon.playsmoothnew.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button guide;
    private Button select;
    private BackPressCloseHandler backPressCloseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        backPressCloseHandler = new BackPressCloseHandler(this);

        initview();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    public void initview() {

        guide = (Button)findViewById(R.id.guide);
        select = (Button)findViewById(R.id.select);

        guide.setOnClickListener(this);
        select.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == guide)
        {
            Intent intent = new Intent(getApplicationContext(),guideActivity.class);
            startActivity(intent);
        }
        else if(view == select)
        {
            Intent intent = new Intent(getApplicationContext(),selectActivity.class);
            startActivity(intent);
        }

    }
}
