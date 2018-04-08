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

public class Q8 extends Fragment implements RadioGroup.OnCheckedChangeListener{

    RadioGroup rg8;
    public static Q8 newInstance() {
        Q8 fragment = new Q8();
        return fragment;
    }

    public Q8() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q8, null);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        rg8 = (RadioGroup)root.findViewById(R.id.rg8);
        rg8.setOnCheckedChangeListener(this);
        return root;
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        switch(i)
        {
            case R.id.ans8_1:
                ((DailycheckActivity)getActivity()).nArr[7] = 0.0f;
                break;
            case R.id.ans8_2:
                ((DailycheckActivity)getActivity()).nArr[7] = 1.5f;
                break;
            case R.id.ans8_3:
                ((DailycheckActivity)getActivity()).nArr[7] = 2.25f;
                break;
            case R.id.ans8_4:
                ((DailycheckActivity)getActivity()).nArr[7] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(8);
    }
}
