package com.example.seunghyeonpark.dailycondition;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.seunghyeonpark.dailycondition.CONDITION.Condition;
import com.example.seunghyeonpark.dailycondition.Database.DatabaseHelper;
import com.example.seunghyeonpark.dailycondition.Fragment.CalendarFragment;
import com.example.seunghyeonpark.dailycondition.Fragment.HomeFragment;
import com.example.seunghyeonpark.dailycondition.Fragment.RecommendFragment;
import com.example.seunghyeonpark.dailycondition.Fragment.SettingFragment;
import com.example.seunghyeonpark.dailycondition.Fragment.StatisticsFragment;
import com.example.seunghyeonpark.dailycondition.User.User;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.server.converter.StringToIntConverter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BackPressCloseHandler backPressCloseHandler;
    private User USER;
    private DatabaseHelper Mydb;
    private Condition todayCondition;
    private boolean bChecked;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Mydb = new DatabaseHelper(this);
        //Cursor res = Mydb.getAllDataFromCondition();


        //Cursor res = Mydb.getAllDataFromCondition();
        //res.moveToPosition(0);

        //res.moveToFirst();
        Cursor mycursor = Mydb.getAllDataFromCondition();
/*
        StringBuffer sb = new StringBuffer();
        while(res.moveToNext()){
            sb.append("name : " + res.getString(0) + "\n");
            sb.append("age : " + res.getString(1)+ "\n");
            sb.append("weight : " + res.getString(2)+ "\n");
            sb.append("height : " + res.getString(3)+ "\n");
            sb.append("drink : " + res.getString(4)+ "\n");
            sb.append("smoke : " + res.getString(5)+ "\n");
        }
*/
/*
        StringBuffer sb = new StringBuffer();
        while(res.moveToNext()){
            sb.append("date : " + res.getString(0) + "\n");
        }

        */
        /*
        StringBuffer b = new StringBuffer();
        while(mycursor.moveToNext()){
            b.append(mycursor.getString(0) + "\n");
            b.append(String.valueOf(mycursor.getFloat(1)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(2)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(3)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(4)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(5)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(6)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(7)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(8)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(9)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(10)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(11)) + "\n");
            b.append(String.valueOf(mycursor.getFloat(12)) + "\n");
        }
*/


        //showMessage("Data",b.toString());

        mycursor.close();






        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        backPressCloseHandler = new BackPressCloseHandler(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }


    /*
    *
    *  If You want to kill this app, consequently tab twice
    *
    * */
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.ic_action_home2,
                R.drawable.ic_action_like2,
                R.drawable.ic_action_calendar2,
                R.drawable.ic_action_setting2
        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "ONE");
        adapter.addFrag(new RecommendFragment(), "TWO");
        adapter.addFrag(new CalendarFragment(), "THREE");
        adapter.addFrag(new SettingFragment(), "FOUR");
        viewPager.setAdapter(adapter);
    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            // return null to display only the icon
            return null;
        }
    }

}
