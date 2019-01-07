package com.bawei.demo.shoppingtrolley.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.bean.GoodsInfoBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerLifeAdapter extends RecyclerView.Adapter<RecyclerLifeAdapter.ViewHolder> {
    private List<GoodsInfoBean.ResultBean.PzshBean.CommodityListBeanX> list;
    private Context context;

    public RecyclerLifeAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<GoodsInfoBean.ResultBean.PzshBean.CommodityListBeanX> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_life_goods,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView_name.setText(list.get(i).getCommodityName());
        viewHolder.textView_price.setText("￥"+list.get(i).getPrice()+".00");
        viewHolder.simpleDraweeView.setImageURI(list.get(i).getMasterPic());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.life_masterPic)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.life_commodityName)
        TextView textView_name;
        @BindView(R.id.life_price)
        TextView textView_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
