package com.example.seunghyeonpark.dailycondition.CONDITION;

import android.content.Context;
import android.database.Cursor;

import com.example.seunghyeonpark.dailycondition.Database.DatabaseHelper;
import com.example.seunghyeonpark.dailycondition.Recommend;

/**
 * Created by kimdonghyun on 2016. 12. 8..
 */

public class Condition {

    private String date;
    private Float headache;
    private Float hangover;
    private Float fatigue;
    private Float stomache;
    private Float heartburn;
    private Float heartthump;
    private Float insomnia;
    private Float stiff;
    private Float muscleache;
    private Float chill;
    private Float cough;
    private Float runny_nose;
    private String RecommendData;


    public Condition(Context context){
        DatabaseHelper myHelper = new DatabaseHelper(context);
        Cursor ConditionCursor = myHelper.getAllDataFromCondition();
        ConditionCursor.moveToLast();


        date = ConditionCursor.getString(0);
        headache = ConditionCursor.getFloat(1);
        hangover = ConditionCursor.getFloat(2);
        fatigue = ConditionCursor.getFloat(3);
        stomache = ConditionCursor.getFloat(4);
        heartburn = ConditionCursor.getFloat(5);
        heartthump = ConditionCursor.getFloat(6);
        insomnia = ConditionCursor.getFloat(7);
        stiff = ConditionCursor.getFloat(8);
        muscleache = ConditionCursor.getFloat(9);
        chill = ConditionCursor.getFloat(10);
        cough = ConditionCursor.getFloat(11);
        runny_nose = ConditionCursor.getFloat(12);

        Recommend myRecommend = new Recommend(context,this);

        RecommendData = myRecommend.GetResult();

        ConditionCursor.close();
    }

    public Condition(Context context, Cursor ConditionCursor){
        date = ConditionCursor.getString(0);
        headache = ConditionCursor.getFloat(1);
        hangover = ConditionCursor.getFloat(2);
        fatigue = ConditionCursor.getFloat(3);
        stomache = ConditionCursor.getFloat(4);
        heartburn = ConditionCursor.getFloat(5);
        heartthump = ConditionCursor.getFloat(6);
        insomnia = ConditionCursor.getFloat(7);
        stiff = ConditionCursor.getFloat(8);
        muscleache = ConditionCursor.getFloat(9);
        chill = ConditionCursor.getFloat(10);
        cough = ConditionCursor.getFloat(11);
        runny_nose = ConditionCursor.getFloat(12);

        Recommend myRecommend = new Recommend(context,this);

        RecommendData = myRecommend.GetResult();

    }

    public Float getHeadache(){
        return this.headache;
    }
    public Float getHangover(){return hangover;}
    public Float getFatigue(){return fatigue;}
    public Float getStomache(){return stomache;}
    public Float getHeartburn(){return heartburn;}
    public Float getHeartthump(){return heartthump;}
    public Float getInsomnia(){return insomnia;}
    public Float getStiff(){return stiff;}
    public Float getMuscleache(){return muscleache;}
    public Float getChill(){return chill;}
    public Float getCough(){return cough;}
    public Float getRunny_nose(){return runny_nose;}

    public String getDate(){
        return this.date;
    }


    public int getPercentCondition(){
        float SumOfConditionPercent;
        int Result;

        SumOfConditionPercent = headache + hangover + fatigue + stomache +
                heartburn + heartthump + insomnia + stiff + muscleache + chill + cough + runny_nose;

        Result = (int)(((float)(40.5) - SumOfConditionPercent)  * (float)(100/40.5));


        return Result;
    }

    public String getRecommendData(){
        return this.RecommendData;

    }





}
