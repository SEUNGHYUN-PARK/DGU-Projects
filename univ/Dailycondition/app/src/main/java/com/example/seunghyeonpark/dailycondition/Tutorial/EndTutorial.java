package com.example.seunghyeonpark.dailycondition.Tutorial;

/**
 * Created by SeungHyeonPark on 2016. 11. 10..
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

//끝나는페이지로, 자기정보 등록하기버튼 누름으로서, 다음 개인정보 입력하는액티비티로 넘어가기

import com.example.seunghyeonpark.dailycondition.R;
import com.example.seunghyeonpark.dailycondition.RegistrationActivity;

public class EndTutorial extends Fragment {
    //Sqlite sqlite;
    private Button btnStart;

    public static EndTutorial newInstance() {
        EndTutorial fragment = new EndTutorial();
        return fragment;
    }

    public EndTutorial() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.end_tutorial, null);

        Button btnStart = (Button)root.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getActivity(), "개인정보를 등록합니다.", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }
}