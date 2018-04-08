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

public class Q11 extends Fragment implements RadioGroup.OnCheckedChangeListener{

    RadioGroup rg11;
    public static Q11 newInstance() {
        Q11 fragment = new Q11();
        return fragment;
    }

    public Q11() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q11, null);
        rg11 = (RadioGroup)root.findViewById(R.id.rg11);
        rg11.setOnCheckedChangeListener(this);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        switch(i)
        {
            case R.id.ans11_1:
                ((DailycheckActivity)getActivity()).nArr[10] = 0.0f;
                break;
            case R.id.ans11_2:
                ((DailycheckActivity)getActivity()).nArr[10] = 1.5f;
                break;
            case R.id.ans11_3:
                ((DailycheckActivity)getActivity()).nArr[10] = 2.25f;
                break;
            case R.id.ans11_4:
                ((DailycheckActivity)getActivity()).nArr[10] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(11);
    }
}
