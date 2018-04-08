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

public class Q9 extends Fragment implements RadioGroup.OnCheckedChangeListener{

    RadioGroup rg9;
    public static Q9 newInstance() {
        Q9 fragment = new Q9();
        return fragment;
    }

    public Q9() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q9, null);
        rg9 = (RadioGroup)root.findViewById(R.id.rg9);
        rg9.setOnCheckedChangeListener(this);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        switch(i)
        {
            case R.id.ans9_1:
                ((DailycheckActivity)getActivity()).nArr[8] = 0.0f;
                break;
            case R.id.ans9_2:
                ((DailycheckActivity)getActivity()).nArr[8] = 1.5f;
                break;
            case R.id.ans9_3:
                ((DailycheckActivity)getActivity()).nArr[8] = 2.25f;
                break;
            case R.id.ans9_4:
                ((DailycheckActivity)getActivity()).nArr[8] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(9);
    }
}
