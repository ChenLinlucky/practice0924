package com.example.dell.practice0924.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.dell.practice0924.R;
import com.example.dell.practice0924.bean.news;
import com.example.dell.practice0924.ui.MainActivity;

import java.util.List;

public class OutAdapter extends RecyclerView.Adapter<OutAdapter.oneHolder> {
    private Context context;
    private List<news.DataBean> list;
    private InnerAdapter innerAdapter;

    public OutAdapter(Context context, List<news.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public oneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.include1, null);
        oneHolder holder = new oneHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final oneHolder holder, final int position) {


        holder.cb_02.setChecked(list.get(position).isOutchecked());

        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.recy_view01.setLayoutManager(manager);
        innerAdapter = new InnerAdapter(context, list.get(position).getList());
        holder.recy_view01.setAdapter(innerAdapter);
        //商家选中控制里面的子条目
        holder.cb_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ischecked = holder.cb_02.isChecked();//定义商家的选中状态
                list.get(position).setOutchecked(ischecked);
                if (ischecked){
                    for (int i = 0; i < list.get(holder.getLayoutPosition()).getList().size(); i++) {
                        list.get(holder.getLayoutPosition()).getList().get(i).setInnerchecked(true);
                    }
                }else{
                    for (int i = 0; i < list.get(holder.getLayoutPosition()).getList().size(); i++) {
                        list.get(holder.getLayoutPosition()).getList().get(i).setInnerchecked(false);
                    }
                }
                onclickchangelisten.onitemchecked(holder.getLayoutPosition(),ischecked);
            }
        });

        //获取里层条目状态
        innerAdapter.setOnclickchangelisten(new InnerAdapter.onclickchangelisten() {
            @Override
            public void onchecked(int layoutPosition, boolean checked) {
                boolean b= true;
                for (int i = 0; i < list.get(holder.getLayoutPosition()).getList().size(); i++) {
                    boolean innerchecked = list.get(holder.getLayoutPosition()).getList().get(i).isInnerchecked();
                    b=(b&innerchecked);
                }
                holder.cb_02.setChecked(b);
                list.get(position).setOutchecked(b);
                onclickchangelisten.onchecked(holder.getLayoutPosition(),checked);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //创建视图
    class oneHolder extends RecyclerView.ViewHolder{

        private final RecyclerView recy_view01;
        private final CheckBox cb_02;

        public oneHolder(View itemView) {
            super(itemView);
            cb_02 = itemView.findViewById(R.id.cb_02);
            recy_view01 = itemView.findViewById(R.id.recy_view01);
        }
    }
    private onclickchangelisten onclickchangelisten;

    public void setOnclickchangelisten(onclickchangelisten onclickchangelisten) {
        this.onclickchangelisten = onclickchangelisten;
    }
    public interface onclickchangelisten{
        void onchecked(int layoutPosition, boolean checked);
        void onitemchecked(int layoutPosition, boolean ischecked);
    }
}
