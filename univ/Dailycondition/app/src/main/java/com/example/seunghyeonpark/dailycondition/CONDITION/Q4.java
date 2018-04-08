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

public class Q4 extends Fragment implements RadioGroup.OnCheckedChangeListener {

    RadioGroup rg4;
    public static Q4 newInstance() {
        Q4 fragment = new Q4();
        return fragment;
    }

    public Q4() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q4, null);
        rg4 = (RadioGroup)root.findViewById(R.id.rg4);
        rg4.setOnCheckedChangeListener(this);

        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        switch(i)
        {
            case R.id.ans4_1:
                ((DailycheckActivity)getActivity()).nArr[3] = 0.0f;
                break;
            case R.id.ans4_2:
                ((DailycheckActivity)getActivity()).nArr[3] = 1.5f;
                break;
            case R.id.ans4_3:
                ((DailycheckActivity)getActivity()).nArr[3] = 2.25f;
                break;
            case R.id.ans4_4:
                ((DailycheckActivity)getActivity()).nArr[3] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(4);
    }
}
