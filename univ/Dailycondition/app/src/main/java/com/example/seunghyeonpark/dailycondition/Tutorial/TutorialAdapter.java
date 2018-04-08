package com.example.seunghyeonpark.dailycondition.Tutorial;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TutorialAdapter extends FragmentPagerAdapter {
    private final int NUM_ITEMS = 4;

    public TutorialAdapter(FragmentManager fm) {
        super(fm);
    }

    public int getCount() {
        return NUM_ITEMS;
    }

    public Fragment getItem(int position) {
        if (position == 0)
            return HomeTutorial.newInstance();
        else if (position == 1)
            return RecommendTutorial.newInstance();
        else if (position == 2)
            return CalendarTutorial.newInstance();
        else if(position==3)
            return EndTutorial.newInstance();
        return null;
    }
}