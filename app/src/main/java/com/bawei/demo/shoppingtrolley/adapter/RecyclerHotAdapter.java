package com.bawei.demo.shoppingtrolley.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.bean.GoodsInfoBean;
import com.bawei.demo.shoppingtrolley.detailactivity.DetailActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerHotAdapter extends RecyclerView.Adapter<RecyclerHotAdapter.ViewHolder> {
    private List<GoodsInfoBean.ResultBean.RxxpBean.CommodityListBean> list;
    private Context context;

    public RecyclerHotAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }


    public void setList(List<GoodsInfoBean.ResultBean.RxxpBean.CommodityListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= View.inflate(context,R.layout.layout_hot_goods,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //设值
                viewHolder.textView_name.setText(list.get(i).getCommodityName());
               viewHolder.textView_price.setText("￥"+list.get(i).getPrice()+".00");
             viewHolder.simpleDraweeView.setImageURI(list.get(i).getMasterPic());
             viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent=new Intent(context, DetailActivity.class);
                     intent.putExtra("commodityId",list.get(i).getCommodityId());
                     context.startActivity(intent);
                 }
             });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.masterPic)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.commodityName)
        TextView textView_name;
        @BindView(R.id.hot_price)
        TextView textView_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
