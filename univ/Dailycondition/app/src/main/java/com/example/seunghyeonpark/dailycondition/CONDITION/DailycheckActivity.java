package com.example.seunghyeonpark.dailycondition.CONDITION;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.seunghyeonpark.dailycondition.BackPressCloseHandler;
import com.example.seunghyeonpark.dailycondition.R;
import com.example.seunghyeonpark.dailycondition.Tutorial.TutorialAdapter;

import me.relex.circleindicator.CircleIndicator;


public class DailycheckActivity extends AppCompatActivity {
    DailycheckAdapter adapter;
    ViewPager pager;


    //dbHelper
    BackPressCloseHandler backPressCloseHandler;
    Float []nArr = new Float[13];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailycheck);
        adapter = new DailycheckAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.dailypager);
        pager.setAdapter(adapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.dailyindicator);
        indicator.setViewPager(pager);
        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}