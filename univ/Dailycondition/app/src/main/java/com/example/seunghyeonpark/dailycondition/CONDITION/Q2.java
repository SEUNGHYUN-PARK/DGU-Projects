package com.example.seunghyeonpark.dailycondition.CONDITION;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.seunghyeonpark.dailycondition.R;

/**
 * Created by SeungHyeonPark on 2016. 11. 10..
 */

public class Q2 extends Fragment implements RadioGroup.OnCheckedChangeListener{

    RadioGroup rg2;

    int sum;
    public static Q2 newInstance() {
        Q2 fragment = new Q2();
        return fragment;
    }

    public Q2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.q2, null);
        ((DailycheckActivity)getActivity()).pager.getCurrentItem();
        rg2 = (RadioGroup)root.findViewById(R.id.rg2);
        rg2.setOnCheckedChangeListener(this);

        return root;
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i)
    {
        switch(i)
        {
            case R.id.ans2_1:
                ((DailycheckActivity)getActivity()).nArr[1] = 0.0f;
                break;
            case R.id.ans2_2:
                ((DailycheckActivity)getActivity()).nArr[1] = 1.5f;
                break;
            case R.id.ans2_3:
                ((DailycheckActivity)getActivity()).nArr[1] = 2.25f;
                break;
            case R.id.ans2_4:
                ((DailycheckActivity)getActivity()).nArr[1] = 3.375f;
                break;
        }

        ((DailycheckActivity)getActivity()).pager.setCurrentItem(2);


    }
}
