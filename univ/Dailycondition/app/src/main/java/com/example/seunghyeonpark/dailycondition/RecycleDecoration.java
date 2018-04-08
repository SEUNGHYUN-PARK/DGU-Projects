package com.example.seunghyeonpark.dailycondition;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by kimdonghyun on 2016. 12. 5..
 */

public class RecycleDecoration extends RecyclerView.ItemDecoration{
    Drawable mDevider;

    int[] ATTR = {android.R.attr.listDivider};


    public RecycleDecoration(Context context){
        mDevider = context.obtainStyledAttributes(ATTR).getDrawable(0);
        context.obtainStyledAttributes(ATTR).recycle();
    }


    @Override
    public void getItemOffsets(Rect OutRect, View view, RecyclerView parent, RecyclerView.State state){
        OutRect.set(0,4,0,mDevider.getIntrinsicHeight()+8);
    }

}
