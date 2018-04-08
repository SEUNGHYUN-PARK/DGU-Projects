package com.example.parkseunghyeon.playsmoothnew.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.parkseunghyeon.playsmoothnew.Metronome.MetronomeActivity;
import com.example.parkseunghyeon.playsmoothnew.R;

public class threeActivity extends AppCompatActivity implements View.OnClickListener{

    Button[] btsth;
    int[] sarrth = {R.id.numthirtyone, R.id.numthirtytwo, R.id.numthirtythree, R.id.numthirtyfour, R.id.numthirtyfive, R.id.numthirtysix, R.id.numthirtyseven, R.id.numthirtyeight, R.id.numthirtynine, R.id.numfourty};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        initview();
    }
    public void initview() {
        btsth = new Button[sarrth.length];
        for (int i = 0; i < sarrth.length; i++) {
            btsth[i] = (Button) findViewById(sarrth[i]);
        }
        for (int i = 0; i < sarrth.length; i++)
        {
            btsth[i].setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MetronomeActivity.class);
        if(view==btsth[0])
        {
            intent.putExtra("player",30);
        }
        else if(view==btsth[1])
        {
            intent.putExtra("player",31);
        }
        else if(view==btsth[2])
        {
            intent.putExtra("player",32);
        }
        else if(view==btsth[3])
        {
            intent.putExtra("player",33);
        }
        else if(view==btsth[4])
        {
            intent.putExtra("player",34);
        }
        else if(view==btsth[5])
        {
            intent.putExtra("player",35);
        }
        else if(view==btsth[6])
        {
            intent.putExtra("player",36);
        }
        else if(view==btsth[7])
        {
            intent.putExtra("player",37);
        }
        else if(view==btsth[8])
        {
            intent.putExtra("player",38);
        }
        else if(view==btsth[9])
        {
            intent.putExtra("player",39);
        }
        startActivity(intent);
    }
}
