package com.example.seunghyeonpark.dailycondition.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seunghyeonpark.dailycondition.CONDITION.Condition;
import com.example.seunghyeonpark.dailycondition.CONDITION.DailycheckActivity;
import com.example.seunghyeonpark.dailycondition.Database.DatabaseHelper;
import com.example.seunghyeonpark.dailycondition.ListItems;
import com.example.seunghyeonpark.dailycondition.MainActivity;
import com.example.seunghyeonpark.dailycondition.R;
import com.example.seunghyeonpark.dailycondition.Recommend;
import com.example.seunghyeonpark.dailycondition.RecycleDecoration;
import com.example.seunghyeonpark.dailycondition.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;


/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ListItems> arrList = new ArrayList<>();
    String  m_sUserName;
    LinearLayout upper_Layout;
    Button btnUpperButton;
    boolean isChecked = false;


    public HomeFragment() {

        // mRecyclerView
        //updateList();
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_home,null);


        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_home,null);
        Calendar todayTime = Calendar.getInstance();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        upper_Layout = (LinearLayout) v.findViewById(R.id.top_layout_home);
        btnUpperButton = (Button)v.findViewById(R.id.btnFragHome);


        final Condition today_condition = new Condition(this.getContext());

        Recommend recommend = new Recommend(getContext(),today_condition);
        btnUpperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isChecked) {
                    Intent intentToCheck = new Intent(getActivity(), DailycheckActivity.class);
                    startActivity(intentToCheck);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); // AlertDialog를 띄울 activity를 argument로 지정해야 한다.

                    builder.setTitle("오늘의 컨디션 확인!");
                    builder.setMessage("오늘 당신의 컨디션은 약 " + today_condition.getPercentCondition() + "퍼센트 정도입니다!");
                    AlertDialog dialog = builder.create(); // 위의 builder를 생성할 AlertDialog 객체 생성
                    dialog.show(); // dialog를 화면에 뿌려 줌
                }
            }
        });

        String year   = Integer.toString(todayTime.get(Calendar.YEAR));
        String month  = Integer.toString(todayTime.get(Calendar.MONTH) + 1);
        String day    = Integer.toString(todayTime.get(Calendar.DATE));

        String Date = year + "." + month +"." + day;


        if (Date.equals(today_condition.getDate())) {
            upper_Layout.setBackgroundResource(R.drawable.top2);
            isChecked = true;
        }
        else {
            upper_Layout.setBackgroundResource(R.drawable.top1);
            isChecked = false;
        }


        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DatabaseHelper dbHelper = new DatabaseHelper(this.getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor NameCursor = dbHelper.getAllDataFromUser();
        NameCursor.moveToFirst();
        m_sUserName = NameCursor.getString(0);
        Cursor cursor = dbHelper.getAllDataFromFeed();
        Cursor ConditionCursor = dbHelper.getAllDataFromCondition();
        Cursor DateCursor = dbHelper.getAllDataFromSchedule();

        String Schedule;

        cursor.moveToFirst();
        ConditionCursor.moveToFirst();
        do{
            Schedule = "";
            Condition myCondition = new Condition(this.getContext(),ConditionCursor);
            DateCursor.moveToFirst();
            StringTokenizer stringTokenizer = new StringTokenizer(ConditionCursor.getString(0), ".");
            int condyear = Integer.parseInt(stringTokenizer.nextToken());
            int condmonth = Integer.parseInt(stringTokenizer.nextToken());
            int condday = Integer.parseInt(stringTokenizer.nextToken());
            while(DateCursor.moveToNext()) {
                StringTokenizer stringTok = new StringTokenizer(DateCursor.getString(0), ".");
                int schyear = Integer.parseInt(stringTok.nextToken());
                int schmonth = Integer.parseInt(stringTok.nextToken());
                int schday = Integer.parseInt(stringTok.nextToken());
                if ((schmonth + 1 == condmonth) && (schday == condday) && (schyear == condyear)) {
                    Schedule = DateCursor.getString(2);
                }
            }

            if(Schedule == "") Schedule = "일정이 없는 날이에요!";
            ListItems listItems = new ListItems(m_sUserName, cursor.getString(0), cursor.getString(1), myCondition.getRecommendData(),Schedule);
            arrList.add(listItems);
        }while(cursor.moveToNext() && ConditionCursor.moveToNext());

        recyclerView.addItemDecoration(new RecycleDecoration(getContext()));





        dbHelper.close();

        adapter = new RecyclerAdapter(this.getContext(),arrList);
        recyclerView.setAdapter(adapter);

        return v;

    }


}













