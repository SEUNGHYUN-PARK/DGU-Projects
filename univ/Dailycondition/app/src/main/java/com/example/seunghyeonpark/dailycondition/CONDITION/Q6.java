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

public class Q6 extends Fragment implements RadioGroup.OnCheckedChangeListener{
    RadioGroup rg6;
    public static Q6 newInstance() {
        Q6 fragment = new Q6();
        return fragment;
    }

    public Q6() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q6, null);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        rg6 = (RadioGroup)root.findViewById(R.id.rg6);
        rg6.setOnCheckedChangeListener(this);
        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        switch(i)
        {
            case R.id.ans6_1:
                ((DailycheckActivity)getActivity()).nArr[5] = 0.0f;
                break;
            case R.id.ans6_2:
                ((DailycheckActivity)getActivity()).nArr[5] = 1.5f;
                break;
            case R.id.ans6_3:
                ((DailycheckActivity)getActivity()).nArr[5] = 2.25f;
                break;
            case R.id.ans6_4:
                ((DailycheckActivity)getActivity()).nArr[5] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(6);
    }
}
