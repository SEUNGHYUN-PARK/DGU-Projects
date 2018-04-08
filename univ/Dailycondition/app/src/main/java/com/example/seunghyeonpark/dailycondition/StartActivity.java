package com.example.seunghyeonpark.dailycondition;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.seunghyeonpark.dailycondition.Database.DatabaseHelper;
import com.example.seunghyeonpark.dailycondition.Tutorial.TutorialActivity;

import java.util.Calendar;

public class StartActivity extends AppCompatActivity {
    Boolean isFirstRun;
    Boolean isPushOn;
    @Override



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isfirstrun", true);
        isPushOn =getSharedPreferences("PREFERENCE",MODE_PRIVATE).getBoolean("ispushon",true);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

              if(isPushOn) // 푸쉬가 켜짐 설정해놨다면
              {
                  if (isFirstRun)
                  {
                      Calendar calendar = Calendar.getInstance();
                      calendar.set(Calendar.HOUR_OF_DAY,07);
                      calendar.set(Calendar.MINUTE,00);
                      calendar.set(Calendar.SECOND,00);
                      Intent intent = new Intent(getApplicationContext(), Notification_receiver.class);

                      PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                      AlarmManager alarmManger = (AlarmManager) getSystemService(ALARM_SERVICE);
                      alarmManger.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent);
                      Intent IntentToTutorial = new Intent(StartActivity.this,TutorialActivity.class);
                      startActivity(IntentToTutorial);
                      finish();
                  }
                  else
                  {
                      Intent IntentToMain = new Intent(StartActivity.this, MainActivity.class);
                      startActivity(IntentToMain);
                      finish();
                  }//여기에 딜레이 후 시작할 작업들을 입력
              }
              else // 아니라면
              {
                  if (isFirstRun)
                  {
                      Intent IntentToTutorial = new Intent(StartActivity.this,TutorialActivity.class);
                      startActivity(IntentToTutorial);
                      finish();
                  }
                  else
                  {
                      Intent IntentToMain = new Intent(StartActivity.this, MainActivity.class);
                      startActivity(IntentToMain);
                      finish();
                  }//여기에 딜레이 후 시작할 작업들을 입력
              }

            }
        }, 1000);// 1초 정도 딜레이를 준 후 시작


    }


}
