package com.example.seunghyeonpark.dailycondition.Tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.seunghyeonpark.dailycondition.R;

/**
 * Created by SeungHyeonPark on 2016. 11. 10..
 */

public class HomeTutorial extends Fragment {

    public static HomeTutorial newInstance() {
        HomeTutorial fragment = new HomeTutorial();
        return fragment;
    }

    public HomeTutorial() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_tutorial, null);
        return root;
    }
}
