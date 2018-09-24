package com.example.dell.practice0924.widge;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.practice0924.R;

public class togglebutton extends LinearLayout implements View.OnClickListener {
    private Button jia;
    private Button jian;
    private TextView text_num;
    public togglebutton(Context context) {
        super(context);
    }

    public togglebutton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.include3, this);
        jia = findViewById(R.id.jia);
        jian = findViewById(R.id.jian);
        text_num = findViewById(R.id.text_num);
        jia.setOnClickListener(this);
        jian.setOnClickListener(this);

    }
        @Override
            public void onClick(View v) {
            String s = text_num.getText().toString();
            int i = Integer.parseInt(s);
            switch (v.getId()){
                case R.id.jia:
                    if(addAndMinusu!=null){
                        addAndMinusu.add();
                    }
                    break;
                case R.id.jian:
                    if(addAndMinusu!=null){
                        addAndMinusu.minus();
                    }
            }
        }

    public togglebutton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //定义的接口回调
    private   AddAndMinus addAndMinusu;
    public interface AddAndMinus{
        void add();
        void minus();
    }
    public void setAddAndMinusu(AddAndMinus addAndMinusu){
        this.addAndMinusu=addAndMinusu;
    }

}
