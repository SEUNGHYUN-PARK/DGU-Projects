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

public class Q10 extends Fragment implements RadioGroup.OnCheckedChangeListener{

    RadioGroup rg10;
    public static Q10 newInstance() {
        Q10 fragment = new Q10();
        return fragment;
    }

    public Q10() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q10, null);
        rg10 = (RadioGroup)root.findViewById(R.id.rg10);
        rg10.setOnCheckedChangeListener(this);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        switch(i)
        {
            case R.id.ans10_1:
                ((DailycheckActivity)getActivity()).nArr[9] = 0.0f;
                break;
            case R.id.ans10_2:
                ((DailycheckActivity)getActivity()).nArr[9] = 1.5f;
                break;
            case R.id.ans10_3:
                ((DailycheckActivity)getActivity()).nArr[9] = 2.25f;
                break;
            case R.id.ans10_4:
                ((DailycheckActivity)getActivity()).nArr[9] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(10);
    }
}
