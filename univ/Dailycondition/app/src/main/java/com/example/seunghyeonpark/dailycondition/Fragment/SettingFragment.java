package com.example.seunghyeonpark.dailycondition.Fragment;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.seunghyeonpark.dailycondition.Notification_receiver;
import com.example.seunghyeonpark.dailycondition.R;
import com.example.seunghyeonpark.dailycondition.RegistrationActivity;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{


    Toolbar toolbar;
    Button myInfo,alarm,version,mail;
    AlarmManager alarmManger;
    Calendar calendar;
    PendingIntent pendingIntent;
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_setting,null);
        calendar = Calendar.getInstance();
        alarmManger = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        calendar.set(Calendar.HOUR_OF_DAY,07);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.SECOND,00);
        Intent intent = new Intent(getActivity().getApplicationContext(), Notification_receiver.class);

        pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        myInfo = (Button)v.findViewById(R.id.myInfo);
        myInfo.setOnClickListener(this);
        alarm = (Button)v.findViewById(R.id.alarm);
        alarm.setOnClickListener(this);
        version = (Button)v.findViewById(R.id.version);
        version.setOnClickListener(this);
        mail = (Button)v.findViewById(R.id.mail);
        mail.setOnClickListener(this);


        /// / Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onClick(View view) {



        if(view==myInfo)
        {

            Intent myInfointent = new Intent(getActivity(),RegistrationActivity.class);
            startActivity(myInfointent);
        }
        if(view==alarm)
        {
            final CharSequence[] items = new CharSequence[]{"켜기", "끄기"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("알람 설정");
            dialog.setItems(items, new DialogInterface.OnClickListener() {
                // 리스트 선택 시 이벤트
                public void onClick(DialogInterface dialog, int which) {
                    if(items[which]=="켜기")
                    {
                        alarmManger.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent);
                        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                                .putBoolean("ispushon", true).commit();
                    }
                    else if(items[which]=="끄기")
                    {
                        alarmManger.cancel(pendingIntent);
                        getActivity().getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE).edit()
                                .putBoolean("ispushon", false).commit();
                    }
                }
            });
            dialog.show();
        }
        if(view==version)
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("만든이 / 버전정보");
            dialog.setMessage("만든이 : M4W1\n(박승현,김동현,김하연,박성한,정수양)\n\n버전정보:1.0.2");
            dialog.show();
        }
        if(view==mail)
        {
            Uri uri= Uri.parse("mailto:dgupsh@gmail.com");
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(intent);
        }
    }
}
