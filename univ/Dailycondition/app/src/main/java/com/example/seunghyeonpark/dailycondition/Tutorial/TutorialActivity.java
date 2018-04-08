package com.example.seunghyeonpark.dailycondition.Tutorial;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.seunghyeonpark.dailycondition.BackPressCloseHandler;
import com.example.seunghyeonpark.dailycondition.R;

import me.relex.circleindicator.CircleIndicator;



public class TutorialActivity extends AppCompatActivity {
    TutorialAdapter adapter;
    ViewPager pager;
    BackPressCloseHandler backPressCloseHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        adapter = new TutorialAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}