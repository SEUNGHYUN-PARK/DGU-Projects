package com.example.seunghyeonpark.dailycondition.Fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.seunghyeonpark.dailycondition.AddplanActivity;
import com.example.seunghyeonpark.dailycondition.Database.DatabaseHelper;
import com.example.seunghyeonpark.dailycondition.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 *  달력은 직접 처음부터 열까지 다 이미지 한 것 보다는 오픈소스가 디자인상으로 외관상으로 더 보기 좋기에 오픈소스 사용
 *  하지만 일정추가기능은 직접 구현해야하는 기능, 일정추가기능에 한해서는 LOC에 포함
 *  Created by SeungHyeon Park on 2016. 11. 24..
 */

public class CalendarFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private java.util.Calendar currentCalender = java.util.Calendar.getInstance(Locale.KOREA);
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.KOREA);
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy - MMM", Locale.KOREA);
    private boolean shouldShow = false;
    private CompactCalendarView compactCalendarView;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    //DatabaseHelper dbHelper;

    TextView Date;
    TextView Time;
    EditText Plan;
    private int mYear, mMonth, mDay, mHour, mMinute;





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        final List<String> mutableBookings = new ArrayList<>();
        final ListView bookingsListView = (ListView) v.findViewById(R.id.bookings_listview);
        final ArrayAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mutableBookings);
        bookingsListView.setAdapter(adapter);
        compactCalendarView = (CompactCalendarView) v.findViewById(R.id.compactcalendar_view);
        floatingActionButton = (FloatingActionButton)v.findViewById(R.id.addplan);
        floatingActionButton.setOnClickListener(this);
        Date = new TextView(getActivity());
        Time = new TextView(getActivity());
        Plan = new EditText(getActivity());

        DatabaseHelper dbHelper= new DatabaseHelper(this.getContext());

        dbHelper.insertDataToSchedule("2016.12.1/14:00/gggg");




        loadEvents();
        compactCalendarView.invalidate();

        logEventsByMonth(compactCalendarView);

        toolbar = (Toolbar)v.findViewById(R.id.tool_bar);
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        setHasOptionsMenu(true);

        //set title on calendar scroll
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                toolbar.setTitle(dateFormatForMonth.format(dateClicked));
                List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);

                Log.d(TAG, "inside onclick " + dateFormatForDisplaying.format(dateClicked));
                if (bookingsFromMap != null) {
                    Log.d(TAG, bookingsFromMap.toString());
                    mutableBookings.clear();
                    for (Event booking : bookingsFromMap) {
                        mutableBookings.add((String) booking.getData());
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });


        return v;
    }
    private void openCalendarOnCreate(View v) {
        final RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.main_content);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                compactCalendarView.showCalendarWithAnimation();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        getActivity().invalidateOptionsMenu();
        // Set to current day on resume to set calendar to latest day
        // toolbar.setTitle(dateFormatForMonth.format(new Date()));
    }

    private void loadEvents() {

        //Cursor myCursor = dbHelper.getAllDataFromSchedule();
        //while
        addEvents(0, 0);
    }

    private void loadEventsForYear(int year) {
        addEvents(java.util.Calendar.DECEMBER, year);
        addEvents(java.util.Calendar.AUGUST, year);
    }

    private void logEventsByMonth(CompactCalendarView compactCalendarView) {
        currentCalender.setTime(new Date());
        currentCalender.set(java.util.Calendar.DAY_OF_MONTH, 1);
        currentCalender.set(java.util.Calendar.MONTH, java.util.Calendar.AUGUST);
        List<String> dates = new ArrayList<>();
        for (Event e : compactCalendarView.getEventsForMonth(new Date())) {
            dates.add(dateFormatForDisplaying.format(e.getTimeInMillis()));
        }
        Log.d(TAG, "Events for Aug with simple date formatter: " + dates);
        Log.d(TAG, "Events for Aug month using default local and timezone: " + compactCalendarView.getEventsForMonth(currentCalender.getTime()));
    }

    private void addEvents(int month, int year) {
        currentCalender.setTime(new Date());

        Date firstDayOfMonth = currentCalender.getTime();


            long timeInMillis = currentCalender.getTimeInMillis();
            DatabaseHelper dbHelper = new DatabaseHelper(this.getContext());
            Cursor dbCursor = dbHelper.getAllDataFromSchedule();
            List<Event> events = new ArrayList<>();

        while(dbCursor.moveToNext()) {
                String Date = dbCursor.getString(0);
                String Time = dbCursor.getString(1);
                String Contents = dbCursor.getString(2);
                currentCalender.setTime(firstDayOfMonth);

                StringTokenizer stringTokenizer = new StringTokenizer(Date, ".");
                int tempyear = Integer.parseInt(stringTokenizer.nextToken());
                int tempmonth = Integer.parseInt(stringTokenizer.nextToken());
                int tempday = Integer.parseInt(stringTokenizer.nextToken());



                    currentCalender.add(java.util.Calendar.DAY_OF_MONTH, tempday - (int)(java.util.Calendar.DAY_OF_MONTH) - 3);
                    timeInMillis = currentCalender.getTimeInMillis();



                events = getEvents(timeInMillis, tempday, Contents, Time);
                compactCalendarView.addEvents(events);
            }

    }

    private List<Event> getEvents(long timeInMillis, int day, String Event, String Time) {


            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "약속 :" + Event + "\n시간 : " + Time));



    }

    private void setToMidnight(java.util.Calendar calendar) {
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calendar.set(java.util.Calendar.MINUTE, 0);
        calendar.set(java.util.Calendar.SECOND, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.calendarfragment, menu);
        menu.getItem(R.id.action_plus);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_plus:
                Intent intent = new Intent(getActivity(),AddplanActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View view) {
        if(view == floatingActionButton)
        {
            Intent Addplan = new Intent(getActivity(),AddplanActivity.class);
            startActivity(Addplan);




                /*
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.VERTICAL);


                Date.setHint("탭해서 날짜선택");
                Time.setHint("탭해서 시간선택");
                Date.setOnClickListener(this);
                Time.setOnClickListener(this);
                linearLayout.addView(Date);
                linearLayout.addView(Time);
                linearLayout.addView(Plan);



                AlertDialog.Builder dialog =  new AlertDialog.Builder(getActivity());
                dialog.setTitle("일정 입력")
                        .setView(linearLayout)
                        .setPositiveButton("등록", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();

            }
            if(view == Date)
            {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                mYear = c.get(java.util.Calendar.YEAR);
                mMonth = c.get(java.util.Calendar.MONTH);
                mDay = c.get(java.util.Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                Date.setText(year+ "." +(monthOfYear + 1) + "." + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
            if(view==Time)
            {
                final java.util.Calendar c = java.util.Calendar.getInstance();
                mHour = c.get(java.util.Calendar.HOUR_OF_DAY);
                mMinute = c.get(java.util.Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // Display Selected time in textbox
                                Time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                tpd.show();
            }
            */
        }
    }
}