package com.example.parkseunghyeon.playsmoothnew.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.parkseunghyeon.playsmoothnew.Metronome.MetronomeActivity;
import com.example.parkseunghyeon.playsmoothnew.R;

public class oneActivity extends AppCompatActivity implements View.OnClickListener{

    Button[] btso;
    int[] sarro = {R.id.numeleven, R.id.numtwelve, R.id.numthirteen, R.id.numfourteen, R.id.numfifteen, R.id.numsixteen, R.id.numseventeen, R.id.numeightteen, R.id.numnineteen, R.id.numtwenty};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        initview();
    }

    public void initview() {
        btso = new Button[sarro.length];
        for (int i = 0; i < sarro.length; i++) {
            btso[i] = (Button) findViewById(sarro[i]);
        }
        for (int i = 0; i < sarro.length; i++)
        {
            btso[i].setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MetronomeActivity.class);
        if(view==btso[0])
        {
            intent.putExtra("player",10);
        }
        else if(view==btso[1])
        {
            intent.putExtra("player",11);
        }
        else if(view==btso[2])
        {
            intent.putExtra("player",12);
        }
        else if(view==btso[3])
        {
            intent.putExtra("player",13);
        }
        else if(view==btso[4])
        {
            intent.putExtra("player",14);
        }
        else if(view==btso[5])
        {
            intent.putExtra("player",15);
        }
        else if(view==btso[6])
        {
            intent.putExtra("player",16);
        }
        else if(view==btso[7])
        {
            intent.putExtra("player",17);
        }
        else if(view==btso[8])
        {
            intent.putExtra("player",18);
        }
        else if(view==btso[9])
        {
            intent.putExtra("player",19);
        }
        startActivity(intent);


    }
}
