package com.example.seunghyeonpark.dailycondition;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ExplainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent IntentToMain = new Intent(ExplainActivity.this,MainActivity.class);
                startActivity(IntentToMain);
                finish();
            }
        }, 1500);// 1.5초의 딜레이타임
    }
}
