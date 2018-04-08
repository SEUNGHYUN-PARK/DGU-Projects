package com.example.parkseunghyeon.playsmoothnew.Tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parkseunghyeon.playsmoothnew.Activity.MenuActivity;
import com.example.parkseunghyeon.playsmoothnew.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SeungHyeonPark on 2017. 06. 20..
 */

/**
 *  튜토리얼을 이루고있는 프레그먼트는 전부 xml파일에서 실질적인 데이터를 가져옵니다.
 *  하지만 버튼을 눌러 액티비티를 빠져나와야하므로 getActivity()를 통해 본래의 액티비티를 얻고 다음 액티비티로 전환
 */

public class EndTutorial extends Fragment {

    public static EndTutorial newInstance() {
        EndTutorial fragment = new EndTutorial();
        return fragment;
    }
    public EndTutorial() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.endtutorial, null);
        Button btnStart = (Button)root.findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isfirstrun", false).commit();
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });
        return root;
    }
}
