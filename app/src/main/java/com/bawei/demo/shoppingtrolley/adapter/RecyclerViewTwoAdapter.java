package com.bawei.demo.shoppingtrolley.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.bean.TwoCategoryBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewTwoAdapter extends RecyclerView.Adapter<RecyclerViewTwoAdapter.ViewHolder> {
    private List<TwoCategoryBean.ResultBean> list;
    private Context context;
    private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色

    public RecyclerViewTwoAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
        isClicks=new ArrayList<>();
    }

    public void setList(List<TwoCategoryBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
        for(int i=0;i<list.size();i++){
            isClicks.add(false);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_two_category,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
           viewHolder.textView.setText(list.get(i).getName());
        if(isClicks.get(i)){
            viewHolder.textView.setTextColor(Color.RED);
        }else{
            viewHolder.textView.setTextColor(Color.WHITE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   for(int i = 0; i <isClicks.size();i++){
                       isClicks.set(i,false);
                   }
                   isClicks.set(i,true);
                   notifyDataSetChanged();
                   if(myCallBack!=null){
                       myCallBack.getPositionId(list.get(i).getId());
                   }
               }
           });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_two_category)
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public CallBack myCallBack;
    public void setOnCallBack(CallBack callBack){
        this.myCallBack=callBack;
    }
    public interface CallBack{
        void getPositionId(String id);
    }
}
