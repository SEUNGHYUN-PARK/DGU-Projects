package com.example.seunghyeonpark.dailycondition.CONDITION;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.seunghyeonpark.dailycondition.Database.DatabaseHelper;
import com.example.seunghyeonpark.dailycondition.MainActivity;
import com.example.seunghyeonpark.dailycondition.R;

import java.util.Calendar;

/**
 * Created by SeungHyeonPark on 2016. 11. 10..
 */

public class Q12 extends Fragment implements RadioGroup.OnCheckedChangeListener{

    RadioGroup rg12;
    DatabaseHelper myHelper;
    Calendar mCalendar;
    public static Q12 newInstance() {
        Q12 fragment = new Q12();
        return fragment;
    }

    public Q12() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q12, null);
        rg12 = (RadioGroup)root.findViewById(R.id.rg12);
        rg12.setOnCheckedChangeListener(this);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        switch (i) {
            case R.id.ans12_1:
                ((DailycheckActivity) getActivity()).nArr[11] = 0.0f;
                break;
            case R.id.ans12_2:
                ((DailycheckActivity) getActivity()).nArr[11] = 1.5f;
                break;
            case R.id.ans12_3:
                ((DailycheckActivity) getActivity()).nArr[11] = 2.25f;
                break;
            case R.id.ans12_4:
                ((DailycheckActivity) getActivity()).nArr[11] = 3.375f;
                break;
        }

        alertDialog();


    }
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // AlertDialog를 띄울 activity를 argument로 지정해야 한다.

        builder.setTitle("일일 설문을 마치시겠습니까?"); // AlertDialog.builder를 통해 Title text를 입력
        builder.setPositiveButton("네", new DialogInterface.OnClickListener() { // AlertDialog.Builder에 Positive Button을 생성
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myHelper = new DatabaseHelper(getContext());
                dialog.dismiss();
                mCalendar = Calendar.getInstance();
                //
                String year   = Integer.toString(mCalendar.get(Calendar.YEAR));
                String month  = Integer.toString(mCalendar.get(Calendar.MONTH) + 1);
                String day    = Integer.toString(mCalendar.get(Calendar.DATE));

                StringBuffer stringBuffer = new StringBuffer();
                String Date = year + "." + month +"." + day;
                String Result = new String();

                for(int i = 0 ; i < 12 ; i++){
                    stringBuffer.append("/" + String.valueOf(((DailycheckActivity)getActivity()).nArr[i]));
                }

                Result = Date + stringBuffer.toString();


                myHelper.insertDataToConditionTable(Result);
                //데이터베이스에 넣는과정 total-sum이 레알 트루 점수;
                Condition todayCondition= new Condition(getContext());
                String PerCentCondition = String.valueOf(todayCondition.getPercentCondition());


                String myFeed = new String();

                Cursor Namecursor = myHelper.getAllDataFromUser();


                myFeed = Date+"/" + PerCentCondition;

                //
                myHelper.insertDataToFeedTable(myFeed);
                Intent goHome = new Intent(getActivity(), MainActivity.class);
                startActivity(goHome);
                getActivity().finish();
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() { // AlertDialog.Builder에 Negative Button을 생성
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // "아니오" button이 받은 DialogInterface를 dismiss 하여 MainView로 돌아감
                return ;
            }
        });
        AlertDialog dialog = builder.create(); // 위의 builder를 생성할 AlertDialog 객체 생성
        dialog.show(); // dialog를 화면에 뿌려 줌


    }
}
