package com.bawei.demo.shoppingtrolley.shoppingcar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.customview.CustomCounter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.ViewHolder> {
    private List<GoodsBean.Result> list;
    private Context context;

    public PayAdapter(List<GoodsBean.Result> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.shopcar_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView_name.setText(list.get(i).getCommodityName());
        viewHolder.textView_price.setText("￥"+list.get(i).getPrice()+"");
        Glide.with(context).load(list.get(i).getPic()).into(viewHolder.imageView);
        viewHolder.editText.setText(list.get(i).getCount()+"");

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
