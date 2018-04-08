package com.example.seunghyeonpark.dailycondition;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.seunghyeonpark.dailycondition.Database.DatabaseHelper;
import com.example.seunghyeonpark.dailycondition.Tutorial.TutorialActivity;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{


    public int bSmoke;
    public int bDrink;


    private Button btnFinishResister;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegistrationActivity.this, TutorialActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        btnFinishResister = (Button)findViewById(R.id.btnFinishResister);
        btnFinishResister.setOnClickListener(this);

    }



    public void transform()
    {
        DatabaseHelper dbHelper= new DatabaseHelper(this);
        dbHelper.DeleteTable("USER");
        dbHelper.DeleteTable("FEED");
        dbHelper.DeleteTable("FOODLIST");
        dbHelper.DeleteTable("CONDITION");
        dbHelper.insertDataToConditionTable("2016.12.1/0/0/0/0/0/0/0/0/0/0/0/0");
        dbHelper.insertDataToConditionTable("2016.12.2/1/3/2/0/0/0/0/0/0/0/0/0");
        dbHelper.insertDataToConditionTable("2016.12.3/0/0/0/4/0/0/0/0/0/0/0/0");
        dbHelper.insertDataToConditionTable("2016.12.4/0/0/0/0/5/6/0/0/0/0/0/0");
        dbHelper.insertDataToConditionTable("2016.12.5/0/3/0/2/0/7/0/0/3/0/0/0");
        dbHelper.insertDataToConditionTable("2016.12.6/0/6/3/0/6/0/2/4/0/6/0/0");
        dbHelper.insertDataToConditionTable("2016.12.7/3/0/2/0/1/0/2/0/3/0/7/0");
        dbHelper.insertDataToFeedTable("2016.12.1/15.5");
        dbHelper.insertDataToFeedTable("2016.12.2/40");
        dbHelper.insertDataToFeedTable("2016.12.3/55");
        dbHelper.insertDataToFeedTable("2016.12.4/90");
        dbHelper.insertDataToFeedTable("2016.12.5/95");
        dbHelper.insertDataToFeedTable("2016.12.6/15");
        dbHelper.insertDataToFeedTable("2016.12.7/30");

        dbHelper.insertDataToFoodListTable("감자 0.23 0.39 0.58 0.45 0.26 0.0014 0.22 0.46 0.28 0.64 0.25 0.32");
        dbHelper.insertDataToFoodListTable("계란 0.23 0.39 0.3 0.38 0.26 0.25 0.22 0.49 0.27 0.48 0.25 0.35");
        dbHelper.insertDataToFoodListTable("계피차 0.28 0.072 0.12 0.1 0.032 0.028 0.26 0.18 0.056 0.18 0.043 0.087");
        dbHelper.insertDataToFoodListTable("국화차 0.045 0.023 0.068 0.032 0.009 0.016 0.023 0.12 0.017 0.056 0.022 0.026");
        dbHelper.insertDataToFoodListTable("굴 0.22 0.29 0.51 0.3 0.11 0.1 0.19 0.34 0.24 0.34 0.12 0.27");
        dbHelper.insertDataToFoodListTable("낙지 0.09 0.24 0.42 0.16 0.045 0.025 0.3 0.24 0.057 0.15 0.035 0.15");
        dbHelper.insertDataToFoodListTable("매실 0.14 0.24 0.44 0.096 0.081 0.064 0.44 0.28 0.27 0.093 0.09 0.22");
        dbHelper.insertDataToFoodListTable("무 0.29 0.23 0.32 1 0.32 0.3 0.26 0.6 0.3 0.25 0.097 0.42");
        dbHelper.insertDataToFoodListTable("미역 0.45 0.29 0.29 0.27 0.12 0.086 0.21 0.35 0.13 0.33 0.11 0.27");
        dbHelper.insertDataToFoodListTable("박하차 0.049 0.077 0.1 0.057 0.022 0.015 0.19 0.19 0.031 0.016 0.032 0.053");
        dbHelper.insertDataToFoodListTable("시금치 0.19 0.27 0.46 0.26 0.098 0.11 0.48 0.32 0.13 0.37 0.097 0.24");
        dbHelper.insertDataToFoodListTable("파 0.32 0.52 0.81 0.68 0.07 0.27 0.26 0.24 0.12 0.29 0.094 0.23");
        dbHelper.insertDataToFoodListTable("호두 0.21 0.27 0.25 0.28 0.12 0.13 0.2 0.35 0.2 0.11 0.14 0.27");
        dbHelper.insertDataToFoodListTable("마늘 0.24 0.38 0.34 0.42 0.055 0.23 0.23 0.17 0.24 0.65 0.041 0.29");
        dbHelper.insertDataToFoodListTable("검은콩 0.3 0.084 0.17 0.024 0.04 0.025 0.055 0.18 0.03 0.19 0.034 0.076");
        dbHelper.insertDataToFoodListTable("배 0.37 0.26 0.64 0.32 0.15 0.11 0.34 0.26 0.2 0.31 0.17 0.6");
        dbHelper.insertDataToFoodListTable("피망 0.31 0.13 0.13 0.082 0.033 0.028 0.31 0.25 0.048 0.23 0.034 0.13");
        dbHelper.insertDataToFoodListTable("아스파라거스 0.46 0.069 0.18 0.051 0.028 0.013 0.42 0.12 0.024 0.077 0.023 0.044");
        dbHelper.insertDataToFoodListTable("우유 0.26 0.19 0.72 0.22 0.078 0.28 0.28 0.22 0.3 0.25 0.29 0.38");
        dbHelper.insertDataToFoodListTable("콩나물 0.37 0.094 0.37 0.17 0.07 0.055 0.33 0.28 0.1 0.27 0.073 0.21");
        dbHelper.insertDataToFoodListTable("피클 0.29 0.11 0.17 0.08 0.03 0.027 0.27 0.23 0.042 0.14 0.03 0.085");
        dbHelper.insertDataToFoodListTable("고구마 0.22 0.34 0.29 0.35 0.045 0.23 0.22 0.47 0.26 0.21 0.25 0.34");
        dbHelper.insertDataToFoodListTable("구기자차 0.071 0.018 0.056 0.029 0.0052 0.0053 0.052 0.014 0.013 0.031 0.006 0.013");
        dbHelper.insertDataToFoodListTable("두부 0.23 0.36 0.53 0.37 0.23 0.22 0.21 0.19 0.27 0.17 0.24 0.32");
        dbHelper.insertDataToFoodListTable("모시조개 0.21 0.033 0.05 0.023 0.0086 0.017 0.1 0.068 0.014 0.13 0.014 0.031");
        dbHelper.insertDataToFoodListTable("참깨 0.28 0.096 0.24 0.088 0.025 0.02 0.27 0.11 0.037 0.13 0.021 0.058");
        dbHelper.insertDataToFoodListTable("쇠간 0.087 0.018 0.066 0.027 0.006 0.021 0.083 0.053 0.012 0.035 0.019 0.044");
        dbHelper.insertDataToFoodListTable("인삼 0.22 0.26 0.34 0.3 0.15 0.19 0.21 0.32 0.18 0.49 0.13 0.26");
        dbHelper.insertDataToFoodListTable("전복 0.16 0.26 0.46 0.24 0.087 0.047 0.35 0.3 0.12 0.28 0.069 0.23");
        dbHelper.insertDataToFoodListTable("홍합 0.26 0.11 0.32 0.072 0.036 0.026 0.25 0.18 0.037 0.11 0.024 0.068");
        dbHelper.insertDataToFoodListTable("해삼 0.14 0.079 0.14 0.046 0.016 0.0087 0.13 0.065 0.018 0.055 0.011 0.03");
        dbHelper.insertDataToFoodListTable("블루베리 0.21 0.31 0.27 0.27 0.21 0.14 0.21 0.18 0.22 0.36 0.19 0.3");
        dbHelper.insertDataToFoodListTable("바나나 0.23 0.35 0.58 0.4 0.046 0.26 0.22 0.21 0.27 0.2 0.26 0.35");
        dbHelper.insertDataToFoodListTable("사과 0.26 0.18 0.42 0.19 0.059 0.26 0.25 0.52 0.3 0.23 0.29 0.36");
        dbHelper.insertDataToFoodListTable("머루 0.05 0.012 0.039 0.025 0.0043 0.0034 0.041 0.023 0.0063 0.038 0.007 0.0087");
        dbHelper.insertDataToFoodListTable("양배추 0.21 0.29 0.49 0.074 0.047 0.13 0.49 0.36 0.2 0.12 0.13 0.27");
        dbHelper.insertDataToFoodListTable("크렌베리 0.54 0.085 0.52 0.075 0.048 0.061 0.51 0.28 0.043 0.17 0.041 0.17");
        dbHelper.insertDataToFoodListTable("허브차 0.088 0.047 0.18 0.055 0.024 0.024 0.056 0.15 0.006 0.19 0.024 0.072");
        dbHelper.insertDataToFoodListTable("꿀 0.26 0.51 0.39 0.64 0.078 0.28 0.25 0.22 0.32 0.24 0.31 0.42");
        dbHelper.insertDataToFoodListTable("다시마 0.42 0.2 0.44 0.19 0.1 0.11 0.37 0.26 0.087 0.29 0.09 0.21");
        dbHelper.insertDataToFoodListTable("무화과 0.25 0.074 0.22 0.056 0.018 0.014 0.23 0.12 0.023 0.12 0.019 0.054");
        dbHelper.insertDataToFoodListTable("민들레차 0.28 0.069 0.14 0.14 0.028 0.029 0.056 0.22 0.052 0.049 0.045 0.079");
        dbHelper.insertDataToFoodListTable("알로에 0.21 0.26 0.24 0.26 0.19 0.16 0.49 0.39 0.18 0.32 0.022 0.29");
        dbHelper.insertDataToFoodListTable("연근 0.28 0.092 0.21 0.08 0.024 0.023 0.25 0.09 0.029 0.21 0.03 0.053");
        dbHelper.insertDataToFoodListTable("파인애플 0.2 0.26 0.59 0.23 0.095 0.099 0.4 0.36 0.13 0.088 0.096 0.25");
        dbHelper.insertDataToFoodListTable("토마토 0.23 0.38 0.3 0.12 0.047 0.24 0.22 0.21 0.26 0.21 0.25 0.33");
        dbHelper.insertDataToFoodListTable("생강 0.23 0.28 0.27 0.1 0.13 0.16 0.21 0.13 0.052 0.21 0.037 0.27");
        dbHelper.insertDataToFoodListTable("장어 0.34 0.26 0.49 0.19 0.057 0.035 0.33 0.26 0.067 0.24 0.053 0.14");
        dbHelper.insertDataToFoodListTable("참치 0.17 0.3 0.49 0.23 0.085 0.061 0.38 0.31 0.11 0.29 0.068 0.24");
        dbHelper.insertDataToFoodListTable("파파야 0.4 0.047 0.22 0.039 0.014 0.017 0.36 0.15 0.022 0.044 0.013 0.05");
        dbHelper.insertDataToFoodListTable("헛개차 0.091 0.022 0.056 0.03 0.0096 0.0052 0.069 0.032 0.011 0.048 0.014 0.014");
        dbHelper.insertDataToFoodListTable("홍삼 0.21 0.22 0.31 0.043 0.11 0.17 0.16 0.088 0.14 0.14 0.13 0.24");
        dbHelper.insertDataToFoodListTable("고등어 0.36 0.26 0.48 0.22 0.052 0.057 0.34 0.28 0.11 0.28 0.065 0.22");
        dbHelper.insertDataToFoodListTable("꽁치 0.16 0.13 0.15 0.058 0.013 0.0074 0.12 0.059 0.018 0.06 0.012 0.028");
        dbHelper.insertDataToFoodListTable("닭가슴살 0.21 0.3 0.25 0.29 0.22 0.2 0.21 0.21 0.23 0.36 0.22 0.33");
        dbHelper.insertDataToFoodListTable("대추차 0.088 0.032 0.063 0.034 0.017 0.0046 0.03 0.021 0.014 0.029 0.026 0.013");
        dbHelper.insertDataToFoodListTable("체리 0.18 0.29 0.47 0.22 0.076 0.06 0.17 0.092 0.13 0.33 0.082 0.26");
        dbHelper.insertDataToFoodListTable("호박 0.63 0.28 0.48 0.28 0.13 0.09 0.19 0.33 0.2 0.14 0.11 0.26");
        dbHelper.insertDataToFoodListTable("고추 0.22 0.36 0.52 0.093 0.034 0.12 0.62 0.15 0.27 0.45 0.17 0.3");
        dbHelper.insertDataToFoodListTable("새우 0.52 0.36 0.5 0.29 0.15 0.14 0.47 0.4 0.25 0.36 0.18 0.29");
        dbHelper.insertDataToFoodListTable("카레 0.18 0.27 0.46 0.24 0.018 0.067 0.14 0.31 0.12 0.3 0.077 0.24");
        dbHelper.insertDataToFoodListTable("커피 0.33 0.23 0.46 0.24 0.14 0.31 0.29 0.24 0.31 0.71 0.31 0.49");
        dbHelper.insertDataToFoodListTable("키위 0.18 0.26 0.46 0.26 0.12 0.1 0.17 0.37 0.17 0.33 0.12 0.28");
        dbHelper.insertDataToFoodListTable("감잎차 0.087 0.027 0.11 0.033 0.031 0.029 0.074 0.036 0.037 0.1 0.037 0.12");
        dbHelper.insertDataToFoodListTable("귤 0.21 0.27 0.47 0.28 0.11 0.12 0.49 0.34 0.2 0.16 0.024 0.28");
        dbHelper.insertDataToFoodListTable("유자차 0.21 0.084 0.25 0.053 0.022 0.013 0.18 0.085 0.035 0.11 0.024 0.044");
        dbHelper.insertDataToFoodListTable("자몽 0.21 0.33 0.48 0.28 0.24 0.17 0.21 0.44 0.24 0.14 0.19 0.32");
        dbHelper.insertDataToFoodListTable("도라지 0.42 0.12 0.43 0.047 0.056 0.034 0.35 0.25 0.089 0.16 0.094 0.11");
        dbHelper.insertDataToFoodListTable("모과 0.31 0.067 0.3 0.12 0.043 0.026 0.046 0.034 0.056 0.087 0.065 0.088");
        dbHelper.insertDataToFoodListTable("녹차 0.23 0.096 0.52 0.31 0.24 0.23 0.22 0.46 0.24 0.17 0.24 0.31");
        dbHelper.insertDataToFoodListTable("미나리 0.33 0.15 0.38 0.15 0.044 0.034 0.3 0.26 0.059 0.28 0.05 0.11");
        dbHelper.insertDataToFoodListTable("양파 0.23 0.36 0.31 0.1 0.21 0.16 0.22 0.16 0.24 0.16 0.039 0.29");
        dbHelper.insertDataToFoodListTable("당근 0.71 0.32 0.27 0.32 0.22 0.2 0.21 0.41 0.24 0.17 0.21 0.3");
        dbHelper.insertDataToFoodListTable("강황 0.28 0.063 0.29 0.078 0.031 0.064 0.26 0.23 0.049 0.28 0.047 0.11");
        dbHelper.insertDataToFoodListTable("브로콜리 0.43 0.26 0.47 0.26 0.14 0.11 0.4 0.34 0.12 0.09 0.11 0.25");
        dbHelper.insertDataToFoodListTable("상추 0.33 0.21 0.23 0.15 0.054 0.047 0.11 0.25 0.084 0.8 0.057 0.15");
        dbHelper.insertDataToFoodListTable("율무차 0.067 0.017 0.14 0.024 0.0061 0.0059 0.056 0.019 0.013 0.031 0.0072 0.018");
        dbHelper.insertDataToFoodListTable("포도 0.22 0.3 0.5 0.32 0.13 0.16 0.59 0.61 0.25 0.16 0.19 0.28");
        dbHelper.insertDataToFoodListTable("오렌지 0.25 0.34 0.54 0.33 0.045 0.25 0.56 0.45 0.27 0.48 0.11 0.36");




        EditText edittextName = (EditText) findViewById(R.id.editName);
        EditText edittextAge = (EditText) findViewById(R.id.editAge);
        EditText edittextWeight = (EditText) findViewById(R.id.editWeight);
        EditText edittextHeight = (EditText) findViewById(R.id.editHeight);

        RadioGroup smokeGroup = (RadioGroup) findViewById(R.id.SmokeGroup);
        smokeGroup.setOnCheckedChangeListener(mSmokeSelection);

        RadioGroup DrinkGroup = (RadioGroup) findViewById(R.id.DrinkGroup);
        smokeGroup.setOnCheckedChangeListener(mDrinkSelection);


        String m_sName =  (String) edittextName.getText().toString();
        String m_sAge =  (String) edittextAge.getText().toString();
        String m_sWeight =  (String) edittextWeight.getText().toString();
        String m_sHeight =  (String) edittextHeight.getText().toString();
        String m_sSmoke =  (String) Integer.toString(bSmoke);
        String m_sDrink =  (String) Integer.toString(bDrink);

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(m_sName+"/" );
        stringBuffer.append(m_sAge+"/" );
        stringBuffer.append(m_sWeight+"/" );
        stringBuffer.append(m_sHeight+"/" );
        stringBuffer.append(m_sDrink+"/" );
        stringBuffer.append(m_sSmoke);


        dbHelper.insertDataToUserTable(stringBuffer.toString());

    }

    RadioGroup.OnCheckedChangeListener mSmokeSelection =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (radioGroup.getId() == R.id.SmokeGroup) {
                        switch (i) {
                            case R.id.rbNonSmoke:
                                bSmoke = 0;
                                break;
                            case R.id.rbSmoke:
                                bSmoke = 1;
                                break;
                        }

                    }

                }
            };

    RadioGroup.OnCheckedChangeListener mDrinkSelection =
            new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (radioGroup.getId() == R.id.DrinkGroup) {
                        switch (i) {
                            case R.id.rbNonDrink:
                                bDrink = 0;
                                break;
                            case R.id.rbDrink:
                                bDrink = 1;
                                break;
                        }

                    }

                }
            };


    @Override
    public void onClick(View view) {
        transform();
        Intent intent = new Intent(RegistrationActivity.this,ExplainActivity.class);
        startActivity(intent);
        finish();
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isfirstrun", false).commit();
    }
}
