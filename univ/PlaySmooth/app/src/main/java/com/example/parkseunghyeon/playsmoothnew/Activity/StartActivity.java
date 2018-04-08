package com.example.parkseunghyeon.playsmoothnew.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.parkseunghyeon.playsmoothnew.R;

public class StartActivity extends AppCompatActivity {



    Boolean isFirstRun;

    private SharedPreferences pref; // 튜토리얼을 반복적으로 보여주면 안되기때문에 이를 기록해주는 역할을 하는 객체
    private SharedPreferences.Editor prefEditor;
    private Handler h; // 강제 딜레이 주기위한 객체
    private int delaytime = 3000; // 일반 기본적으로 3초안에 제대로 연결상태를 확인할수 있다는 전제하에 3초지정

    /*
        이 시간동안 제대로 연결이 되었는지 안되었는지 체크
    */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isfirstrun", true);
        h = new Handler();
        h.postDelayed(checker,delaytime); // 3초동안 튜토리얼로 넘어갈지 아님 메뉴로 갈지 판단

    }

    private Runnable checker = new Runnable() {
        @Override
        public void run() {
            if(isFirstRun)
            {
                Intent i = new Intent(StartActivity.this, TutorialActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }else
            {
                Intent i = new Intent(StartActivity.this, MenuActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }

        }
    };
}
