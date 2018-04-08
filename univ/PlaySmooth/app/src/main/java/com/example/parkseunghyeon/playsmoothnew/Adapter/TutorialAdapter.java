package com.example.parkseunghyeon.playsmoothnew.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.parkseunghyeon.playsmoothnew.Tutorial.DrumGuideTutorial;
import com.example.parkseunghyeon.playsmoothnew.Tutorial.EndTutorial;
import com.example.parkseunghyeon.playsmoothnew.Tutorial.SelectionTutorial;

/**
 * Created by parkseunghyeon on 2017. 7. 2..
 */

public class TutorialAdapter extends FragmentPagerAdapter {

    private final int NUM_ITEMS = 3;

    public TutorialAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0)
            return DrumGuideTutorial.newInstance();
        else if(position ==1)
            return SelectionTutorial.newInstance();
        else if(position ==2)
            return EndTutorial.newInstance();

        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
