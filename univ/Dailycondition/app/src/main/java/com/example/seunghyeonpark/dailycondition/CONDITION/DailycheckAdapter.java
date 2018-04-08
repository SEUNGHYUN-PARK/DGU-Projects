package com.example.seunghyeonpark.dailycondition.CONDITION;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.seunghyeonpark.dailycondition.Tutorial.CalendarTutorial;
import com.example.seunghyeonpark.dailycondition.Tutorial.EndTutorial;
import com.example.seunghyeonpark.dailycondition.Tutorial.HomeTutorial;
import com.example.seunghyeonpark.dailycondition.Tutorial.RecommendTutorial;

public class DailycheckAdapter extends FragmentPagerAdapter {
    private final int NUM_ITEMS = 12;

    public DailycheckAdapter(FragmentManager fm) {
        super(fm);
    }

    public int getCount() {
        return NUM_ITEMS;
    }

    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return Q1.newInstance();
            case 1:
                return Q2.newInstance();
            case 2:
                return Q3.newInstance();
            case 3:
                return Q4.newInstance();
            case 4:
                return Q5.newInstance();
            case 5:
                return Q6.newInstance();
            case 6:
                return Q7.newInstance();
            case 7:
                return Q8.newInstance();
            case 8:
                return Q9.newInstance();
            case 9:
                return Q10.newInstance();
            case 10:
                return Q11.newInstance();
            case 11:
                return Q12.newInstance();
            default:
                return null;
        }
    }
}