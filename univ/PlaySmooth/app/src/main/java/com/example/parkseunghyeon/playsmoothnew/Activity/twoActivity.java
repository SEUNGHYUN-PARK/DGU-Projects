package com.example.parkseunghyeon.playsmoothnew.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.parkseunghyeon.playsmoothnew.Metronome.MetronomeActivity;
import com.example.parkseunghyeon.playsmoothnew.R;

public class twoActivity extends AppCompatActivity implements View.OnClickListener{

    Button[] btst;
    int[] sarrt = {R.id.numtwentyone, R.id.numtwentytwo, R.id.numtwentythree, R.id.numtwentyfour, R.id.numtwentyfive, R.id.numtwentysix, R.id.numtwentyseven, R.id.numtwentyeight, R.id.numtwentynine, R.id.numthirty};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        initview();
    }

    public void initview() {
        btst = new Button[sarrt.length];
        for (int i = 0; i < sarrt.length; i++) {
            btst[i] = (Button) findViewById(sarrt[i]);
        }
        for (int i = 0; i < sarrt.length; i++)
        {
            btst[i].setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MetronomeActivity.class);
        if(view==btst[0])
        {
            intent.putExtra("player",20);
        }
        else if(view==btst[1])
        {
            intent.putExtra("player",21);
        }
        else if(view==btst[2])
        {
            intent.putExtra("player",22);
        }
        else if(view==btst[3])
        {
            intent.putExtra("player",23);
        }
        else if(view==btst[4])
        {
            intent.putExtra("player",24);
        }
        else if(view==btst[5])
        {
            intent.putExtra("player",25);
        }
        else if(view==btst[6])
        {
            intent.putExtra("player",26);
        }
        else if(view==btst[7])
        {
            intent.putExtra("player",27);
        }
        else if(view==btst[8])
        {
            intent.putExtra("player",28);
        }
        else if(view==btst[9])
        {
            intent.putExtra("player",29);
        }
        startActivity(intent);


    }
}
