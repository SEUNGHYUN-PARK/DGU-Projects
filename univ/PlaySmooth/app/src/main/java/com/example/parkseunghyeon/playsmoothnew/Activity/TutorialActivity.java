package com.example.parkseunghyeon.playsmoothnew.Activity;

import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.parkseunghyeon.playsmoothnew.Adapter.TutorialAdapter;
import com.example.parkseunghyeon.playsmoothnew.R;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {

    TutorialAdapter adapter;
    ViewPager pager;

    private SharedPreferences pref; // 튜토리얼을 반복적으로 보여주면 안되기때문에 이를 기록해주는 역할을 하는 객체
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        adapter = new TutorialAdapter(getSupportFragmentManager());
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);
    }
}
