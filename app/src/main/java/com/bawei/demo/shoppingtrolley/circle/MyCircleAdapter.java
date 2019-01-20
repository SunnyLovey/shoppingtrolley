package com.bawei.demo.shoppingtrolley.circle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.adapter.CircleAdapter;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCircleAdapter extends RecyclerView.Adapter<MyCircleAdapter.ViewHolder> {
    private List<MyCircleBean.ResultBean> list;
    private Context context;
    public static final String QuanZi_FaBu_Time = "yyyy-MM-dd HH:mm:ss";

    public MyCircleAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<MyCircleBean.ResultBean> list) {
        this.list.clear();
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();

    }
    public void addList(List<MyCircleBean.ResultBean> list) {
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.my_circle_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //解密   时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(QuanZi_FaBu_Time, Locale.getDefault());
        viewHolder.textView_createTime.setText(dateFormat.format(list.get(i).getCreateTime()));
        viewHolder.textView_content.setText(list.get(i).getContent());
        Glide.with(context).load(list.get(i).getImage()).into(viewHolder.imageView);
        viewHolder.textView_hand_count.setText(list.get(i).getGreatNum()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.circle_content)
        TextView textView_content;
        @BindView(R.id.circle_pic)
        ImageView imageView;
        @BindView(R.id.circle_hand)
        ImageView imageView_hand;
        @BindView(R.id.circle_hand_count)
        TextView textView_hand_count;
        @BindView(R.id.circle_time)
        TextView textView_createTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
