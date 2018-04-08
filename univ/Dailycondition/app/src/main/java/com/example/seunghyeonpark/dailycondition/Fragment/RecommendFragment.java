package com.example.seunghyeonpark.dailycondition.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seunghyeonpark.dailycondition.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment {

    int[] images = new int[] {R.drawable.ban, R.drawable.diet, R.drawable.jogging, R.drawable.sleep, R.drawable.walking, R.drawable.streching};
    int[] imagesa = new int[] {R.drawable.bana, R.drawable.dieta, R.drawable.jogginga, R.drawable.sleepa, R.drawable.walkinga, R.drawable.strechinga};
    TextView recommend;
    ImageView motion;
    ImageView manswer;
    public RecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        motion = (ImageView) view.findViewById(R.id.motion);
        manswer = (ImageView)view.findViewById(R.id.manswer);
        int imageId = (int)(Math.random() * images.length);
        int imageIda = (int)(Math.random() * images.length);
        motion.setBackgroundResource(images[imageId]);
        if(imageId==0)
        {
            manswer.setBackgroundResource(imagesa[0]);
        }
        if(imageId==1)
        {
            manswer.setBackgroundResource(imagesa[1]);
        }
        if(imageId==2)
        {
            manswer.setBackgroundResource(imagesa[2]);
        }
        if(imageId==3)
        {
            manswer.setBackgroundResource(imagesa[3]);
        }
        if(imageId==4)
        {
            manswer.setBackgroundResource(imagesa[4]);
        }
        if(imageId==5)
        {
            manswer.setBackgroundResource(imagesa[5]);
        }

        return view;
    }

}