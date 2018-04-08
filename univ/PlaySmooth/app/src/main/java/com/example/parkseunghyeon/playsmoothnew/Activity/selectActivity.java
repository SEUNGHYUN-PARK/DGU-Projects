package com.example.parkseunghyeon.playsmoothnew.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.parkseunghyeon.playsmoothnew.R;

public class selectActivity extends AppCompatActivity implements View.OnClickListener{

    private Button zerotoone;
    private Button onetotwo;
    private Button twotothree;
    private Button threetofour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        initview();
    }

    public void initview() {
        zerotoone = (Button)findViewById(R.id.zerotoone);
        onetotwo = (Button)findViewById(R.id.onetotwo);
        twotothree = (Button)findViewById(R.id.twotothree);
        threetofour = (Button)findViewById(R.id.threetofour);

        zerotoone.setOnClickListener(this);
        onetotwo.setOnClickListener(this);
        twotothree.setOnClickListener(this);
        threetofour.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==zerotoone)
        {
            Intent intent = new Intent(getApplicationContext(),zeroActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else if(view==onetotwo)
        {
            Intent intent = new Intent(getApplicationContext(),oneActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        else if(view==twotothree)
        {
            Intent intent = new Intent(getApplicationContext(),twoActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        }
        else if(view==threetofour)
        {
            Intent intent = new Intent(getApplicationContext(),threeActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    }
}
