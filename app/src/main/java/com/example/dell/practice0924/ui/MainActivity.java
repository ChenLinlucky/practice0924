package com.example.dell.practice0924.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.practice0924.R;
import com.example.dell.practice0924.adapter.OutAdapter;
import com.example.dell.practice0924.bean.bean1;
import com.example.dell.practice0924.bean.news;
import com.example.dell.practice0924.di.Icontract;
import com.example.dell.practice0924.di.Presenterimp;
import com.example.dell.practice0924.view.MyView;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Icontract.iview {

    private Presenterimp presenterimp;
    private RecyclerView recy_view;
    private OutAdapter adapter;
    private com.example.dell.practice0924.bean.news news;
    private CheckBox cb_01;
    private TextView zj;
    private EditText edit_sou;
    private Button btn_sou;
    private TextView text_clear;
    private List<String> list = new ArrayList<>();//流式
    private MyView myview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recy_view = findViewById(R.id.recy_view);
        cb_01 = findViewById(R.id.cb_01);
        zj = findViewById(R.id.zj);
        edit_sou = findViewById(R.id.edit_sou);
        btn_sou = findViewById(R.id.btn_sou);
        text_clear = findViewById(R.id.text_clear);
        myview = findViewById(R.id.myview);
        text_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myview.removeAllViews();
            }
        });
        btn_sou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = edit_sou.getText().toString();
                list.add(s);
                TextView view = new TextView(MainActivity.this);
                view.setText(s);
                myview.addView(view);
                myview.setPadding(5,5,5,5);
            }
        });

        presenterimp = new Presenterimp();
        EventBus.getDefault().register(this);//!!!!!!!!注册
        presenterimp.attchview(this);
        presenterimp.requestinfo();
        cb_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_01.isChecked()){
                    for (int i = 0; i <news.getData().size() ; i++) {
                        news.getData().get(i).setOutchecked(true);
                        for (int j = 0; j <news.getData().get(i).getList().size() ; j++) {
                            news.getData().get(i).getList().get(j).setInnerchecked(true);
                        }
                    }
                }else {
                    for (int i = 0; i <news.getData().size() ; i++) {
                        news.getData().get(i).setOutchecked(false);
                        for (int j = 0; j <news.getData().get(i).getList().size() ; j++) {
                            news.getData().get(i).getList().get(j).setInnerchecked(false);
                        }
                    }

                }
                //总价的方法
                  initzong();
                adapter.notifyDataSetChanged();
            }


        });

    }



    private void initzong() {
        int zong=0;
        for (int i = 0; i <news.getData().size(); i++) {
            for (int j = 0; j < news.getData().get(i).getList().size(); j++) {
             if(news.getData().get(i).getList().get(j).isInnerchecked()){
                 zong+=news.getData().get(i).getList().get(j).getNum()*news.getData().get(i).getList().get(j).getPrice();
             }
            }
        }
        zj.setText(zong+"元");
        adapter.notifyDataSetChanged();
    }

    //接收加减的方法，，，，，主线程
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  msg(bean1 ha){
        initzong();
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterimp.datachview(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void data(final String s) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
               // Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                news = gson.fromJson(s, news.class);
                List<com.example.dell.practice0924.bean.news.DataBean> data = news.getData();
                LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                recy_view.setLayoutManager(manager);
                adapter = new OutAdapter(MainActivity.this, data);
                recy_view.setAdapter(adapter);
                //全选  来控制商家跟条目
                adapter.setOnclickchangelisten(new OutAdapter.onclickchangelisten() {
                    @Override
                    public void onchecked(int layoutPosition, boolean checked) {
                        boolean b=true;
                        for (int i = 0; i < news.getData().size(); i++) {
                            boolean outchecked = news.getData().get(i).isOutchecked();
                            for (int j = 0; j < news.getData().get(i).getList().size(); j++) {
                                boolean innerchecked = news.getData().get(i).getList().get(j).isInnerchecked();
                                b=(b&outchecked&innerchecked);
                            }

                        }
                        cb_01.setChecked(b);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onitemchecked(int layoutPosition, boolean ischecked) {
                        //设置外层的选中状态
                        news.getData().get(layoutPosition).setOutchecked(ischecked);
                        boolean b=true;
                        for (int i = 0; i < news.getData().size(); i++) {
                            boolean outchecked = news.getData().get(i).isOutchecked();
                            for (int j = 0; j < news.getData().get(i).getList().size(); j++) {
                                boolean innerchecked = news.getData().get(i).getList().get(j).isInnerchecked();
                                b=(b&outchecked&innerchecked);
                            }
                        }
                        cb_01.setChecked(b);
                        adapter.notifyDataSetChanged();

                    }
                });
            }
        });
    }

}
