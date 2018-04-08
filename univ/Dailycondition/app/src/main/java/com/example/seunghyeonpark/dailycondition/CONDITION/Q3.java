package com.example.seunghyeonpark.dailycondition.CONDITION;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.seunghyeonpark.dailycondition.R;

/**
 * Created by SeungHyeonPark on 2016. 11. 10..
 */

public class Q3 extends Fragment implements RadioGroup.OnCheckedChangeListener{


    RadioGroup rg3;
    public static Q3 newInstance() {
        Q3 fragment = new Q3();
        return fragment;
    }

    public Q3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q3, null);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        rg3 = (RadioGroup)root.findViewById(R.id.rg3);
        rg3.setOnCheckedChangeListener(this);
        return root;
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        switch(i)
        {
            case R.id.ans3_1:
                ((DailycheckActivity)getActivity()).nArr[2] = 0.0f;
                break;
            case R.id.ans3_2:
                ((DailycheckActivity)getActivity()).nArr[2] = 1.5f;
                break;
            case R.id.ans3_3:
                ((DailycheckActivity)getActivity()).nArr[2] = 2.25f;
                break;
            case R.id.ans3_4:
                ((DailycheckActivity)getActivity()).nArr[2] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(3);
    }
}
