package com.example.parkseunghyeon.playsmoothnew.Tutorial;

/**
 * Created by SeungHyeonPark on 2017. 06. 20..
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkseunghyeon.playsmoothnew.R;


/**
 *  튜토리얼을 이루고있는 프레그먼트는 전부 xml파일에서 실질적인 데이터를 가져옵니다.
 */

public class SelectionTutorial extends Fragment {

    public static SelectionTutorial newInstance() {
        SelectionTutorial fragment = new SelectionTutorial();
        return fragment;
    }

    public SelectionTutorial() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.selectiontutorial, null);
        return root;
    }
}