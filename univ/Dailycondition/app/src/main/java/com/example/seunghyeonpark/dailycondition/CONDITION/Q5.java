package com.example.seunghyeonpark.dailycondition.CONDITION;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.seunghyeonpark.dailycondition.R;

/**
 * Created by SeungHyeonPark on 2016. 11. 10..
 */

public class Q5 extends Fragment implements RadioGroup.OnCheckedChangeListener{

    RadioGroup rg5;
    public static Q5 newInstance() {
        Q5 fragment = new Q5();
        return fragment;
    }

    public Q5() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q5, null);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        rg5 = (RadioGroup)root.findViewById(R.id.rg5);
        rg5.setOnCheckedChangeListener(this);
        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        switch(i)
        {
            case R.id.ans5_1:
                ((DailycheckActivity)getActivity()).nArr[4] = 0.0f;
                break;
            case R.id.ans5_2:
                ((DailycheckActivity)getActivity()).nArr[4] = 1.5f;
                break;
            case R.id.ans5_3:
                ((DailycheckActivity)getActivity()).nArr[4] = 2.25f;
                break;
            case R.id.ans5_4:
                ((DailycheckActivity)getActivity()).nArr[4] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(5);
    }
}
