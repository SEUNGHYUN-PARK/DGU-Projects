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

public class Q7 extends Fragment implements RadioGroup.OnCheckedChangeListener {

    RadioGroup rg7;
    public static Q7 newInstance() {
        Q7 fragment = new Q7();
        return fragment;
    }

    public Q7() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q7, null);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        rg7 = (RadioGroup)root.findViewById(R.id.rg7);
        rg7.setOnCheckedChangeListener(this);
        return root;
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        switch(i)
        {
            case R.id.ans7_1:
                ((DailycheckActivity)getActivity()).nArr[6] = 0.0f;
                break;
            case R.id.ans7_2:
                ((DailycheckActivity)getActivity()).nArr[6] = 1.5f;
                break;
            case R.id.ans7_3:
                ((DailycheckActivity)getActivity()).nArr[6] = 2.25f;
                break;
            case R.id.ans7_4:
                ((DailycheckActivity)getActivity()).nArr[6] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(7);
    }
}
