package com.example.seunghyeonpark.dailycondition.CONDITION;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.seunghyeonpark.dailycondition.R;

/**
 * Created by SeungHyeonPark on 2016. 11. 10..
 */

public class Q1 extends Fragment implements RadioGroup.OnCheckedChangeListener{
    RadioGroup rg1;

    FragmentPagerAdapter fragmentPagerAdapter;
    public static Q1 newInstance() {
        Q1 fragment = new Q1();
        return fragment;
    }

    public Q1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q1, null);
        rg1 = (RadioGroup)root.findViewById(R.id.rg1);
        rg1.setOnCheckedChangeListener(this);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        switch(i)
        {
            case R.id.ans1:
                ((DailycheckActivity)getActivity()).nArr[0] = 0.0f;
                break;
            case R.id.ans2:
                ((DailycheckActivity)getActivity()).nArr[0] = 1.5f;
                break;
            case R.id.ans3:
                ((DailycheckActivity)getActivity()).nArr[0] = 2.25f;
                break;
            case R.id.ans4:
                ((DailycheckActivity)getActivity()).nArr[0] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(1);

    }

}
