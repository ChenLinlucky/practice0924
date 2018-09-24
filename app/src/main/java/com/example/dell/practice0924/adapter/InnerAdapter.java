package com.example.dell.practice0924.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.practice0924.R;
import com.example.dell.practice0924.bean.bean1;
import com.example.dell.practice0924.bean.news;
import com.example.dell.practice0924.widge.togglebutton;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.twoHolder>{
    private Context context;
    private List<news.DataBean.ListBean> list;
    private TextView text_num;

    public InnerAdapter(Context context, List<news.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public twoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.include2, null);
        twoHolder holder = new twoHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final twoHolder holder, final int position) {
        holder.cb_03.setChecked(list.get(position).isInnerchecked());


        text_num = holder.togglebutton.findViewById(R.id.text_num);
        text_num.setText(list.get(position).getNum()+"");

        holder.price.setText("单价为"+list.get(position).getPrice());
        holder.shop_name.setText(list.get(position).getTitle());
        String[] split = list.get(position).getImages().split("\\|");
        Glide.with(context).load(split[0]).into(holder.img);


        //加减
        holder.togglebutton.setAddAndMinusu(new togglebutton.AddAndMinus() {
            @Override
            public void add() {
                list.get(position).setNum(list.get(position).getNum()+1);
                bean1 bean1 = new bean1();
                EventBus.getDefault().post(bean1);
            }

            @Override
            public void minus() {
                list.get(position).setNum(list.get(position).getNum()-1);
                bean1 bean1 = new bean1();
                EventBus.getDefault().post(bean1);
            }
        });
      holder.cb_03.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              list.get(position).setInnerchecked(holder.cb_03.isChecked());
              onclickchangelisten.onchecked(holder.getLayoutPosition(),holder.cb_03.isChecked());
          }
      });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class twoHolder extends RecyclerView.ViewHolder{
        private final CheckBox cb_03;
        private final TextView shop_name;
        private final TextView price;
        private final ImageView img;
        private final togglebutton togglebutton;

        public twoHolder(View itemView) {
            super(itemView);
            cb_03 = itemView.findViewById(R.id.cb_03);
            shop_name = itemView.findViewById(R.id.shop_name);
            price = itemView.findViewById(R.id.price);
            img = itemView.findViewById(R.id.img);
            togglebutton = itemView.findViewById(R.id.togglebutton);
        }
    }


    private onclickchangelisten onclickchangelisten;
    public void setOnclickchangelisten(InnerAdapter.onclickchangelisten onclickchangelisten){
        this.onclickchangelisten= onclickchangelisten;
    }
    public interface onclickchangelisten{
        void onchecked(int layoutPosition, boolean checked);
    }
}
