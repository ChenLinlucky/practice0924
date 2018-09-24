package com.example.dell.practice0924.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class MyView extends ViewGroup{
    private int mleftMargin = 20;
    private int mtopMargin = 20;

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //绘制
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int leftMargin = mleftMargin;
        int topMargin = mtopMargin;
        //宽度
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        //高度
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //判断
        switch (modeHeight){
            case MeasureSpec.AT_MOST:
                int measuredHeight = 0;
                //循环遍历得到子的元素
                for (int j=0;j<getChildCount();j++){
                    int measuredWidth = getChildAt(j).getMeasuredWidth();
                    measuredHeight = getChildAt(j).getMeasuredHeight();
                    if (leftMargin+measuredWidth+mleftMargin>getWidth()){
                        leftMargin = mleftMargin;
                        topMargin+=measuredHeight+mtopMargin;
                    }
                    leftMargin+=measuredWidth+mleftMargin;
                }
                topMargin+=measuredHeight+mtopMargin;
                break;
        }
        setMeasuredDimension(sizeWidth,topMargin);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int leftMargin = mleftMargin;
        int topMargin = mtopMargin;

        for (int j=0;j<getChildCount();j++){
            int measuredWidth = getChildAt(j).getMeasuredWidth();
            int measuredHeight = getChildAt(j).getMeasuredHeight();

            if (leftMargin+measuredWidth+mleftMargin>getWidth()){
                leftMargin=mleftMargin;
                topMargin+=measuredHeight+mtopMargin;
                getChildAt(j).layout(leftMargin,topMargin,leftMargin+measuredWidth,topMargin+measuredHeight);
            }else {
                getChildAt(j).layout(leftMargin,topMargin,leftMargin+measuredWidth,topMargin+measuredHeight);
            }
            leftMargin+=measuredWidth+mleftMargin;
        }
    }
}

