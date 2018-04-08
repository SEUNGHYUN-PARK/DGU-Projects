package com.example.seunghyeonpark.dailycondition.User;

/**
 * Created by kimdonghyun on 2016. 11. 27..
 */

public class User {

    private String  sName;
    private int     nAge;
    private int     nWeight;
    private int     nHeight;
    private int     bSmoke;
    private int     bDrink;


    public void SetName(String sParamName){
        this.sName = sParamName;
    }


    public void SetWeight(int  nParamWeight){
        this.nWeight = nParamWeight;
    }
    public void SetHeight(int nParamHeight){
        this.nHeight = nParamHeight;
    }
    public void SetSmoke(int nParamSmoke){
        this.bSmoke = nParamSmoke;
    }
    public void SetDrink(int nParamDrink){this.bDrink = nParamDrink;}
    public void SetAge(int nParamAge){this.nAge = nParamAge;}



    public String GetName(){
        return sName;
    }

    public int GetWeight(){
        return nWeight;
    }

    public int GetHeight(){
        return nHeight;
    }

    public int GetSmoke(){
        return bSmoke;
    }

    public int GetDrink(){
        return bDrink;
    }


}
