package com.bawei.demo.shoppingtrolley.allordersfragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.shoppingcar.PayAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderGoodsAdapter extends RecyclerView.Adapter<OrderGoodsAdapter.ViewHolder> {
    private List<OrderBean.OrderListBean.DetailListBean> list;
    private Context context;

    public OrderGoodsAdapter(List<OrderBean.OrderListBean.DetailListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.order_shopcar_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView_name.setText(list.get(i).getCommodityName());
        viewHolder.textView_price.setText("￥"+list.get(i).getCommodityPrice()+".0");
        String commodityPic = list.get(i).getCommodityPic();
        String[] split = commodityPic.split("\\,");
        Glide.with(context).load(split[0]).into(viewHolder.imageView);
        viewHolder.editText.setText(list.get(i).getCommodityCount()+"");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_pic)
        ImageView imageView;
        @BindView(R.id.goods_name)
        TextView textView_name;
        @BindView(R.id.goods_price)
        TextView textView_price;
        @BindView(R.id.edit_num)
        EditText editText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
