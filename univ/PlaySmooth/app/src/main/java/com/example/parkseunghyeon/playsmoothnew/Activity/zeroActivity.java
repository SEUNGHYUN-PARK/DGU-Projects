package com.example.parkseunghyeon.playsmoothnew.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.parkseunghyeon.playsmoothnew.Metronome.MetronomeActivity;
import com.example.parkseunghyeon.playsmoothnew.R;

public class zeroActivity extends AppCompatActivity implements View.OnClickListener{
    Button[] btsz;
    int[] sarr = {R.id.numone, R.id.numtwo, R.id.numthree, R.id.numfour, R.id.numfive, R.id.numsix, R.id.numseven, R.id.numeight, R.id.numnine, R.id.numten};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zero);

        initview();

    }

    public void initview() {
        btsz = new Button[sarr.length];
        for (int i = 0; i < sarr.length; i++) {
            btsz[i] = (Button) findViewById(sarr[i]);
        }
        for (int i = 0; i < sarr.length; i++)
        {
            btsz[i].setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MetronomeActivity.class);
        if(view==btsz[0])
        {
            intent.putExtra("player",0);
        }
        else if(view==btsz[1])
        {
            intent.putExtra("player",1);
        }
        else if(view==btsz[2])
        {
            intent.putExtra("player",2);
        }
        else if(view==btsz[3])
        {
            intent.putExtra("player",3);
        }
        else if(view==btsz[4])
        {
            intent.putExtra("player",4);
        }
        else if(view==btsz[5])
        {
            intent.putExtra("player",5);
        }
        else if(view==btsz[6])
        {
            intent.putExtra("player",6);
        }
        else if(view==btsz[7])
        {
            intent.putExtra("player",7);
        }
        else if(view==btsz[8])
        {
            intent.putExtra("player",8);
        }
        else if(view==btsz[9])
        {
            intent.putExtra("player",9);
        }
        startActivity(intent);


    }
}

