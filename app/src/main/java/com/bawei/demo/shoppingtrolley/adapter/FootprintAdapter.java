package com.bawei.demo.shoppingtrolley.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.bean.FootprintBean;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootprintAdapter extends RecyclerView.Adapter<FootprintAdapter.ViewHolder> {
    private List<FootprintBean.ResultBean> list;
    private Context context;
    public static final String QuanZi_FaBu_Time = "yyyy-MM-dd HH:mm:ss";

    public FootprintAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<FootprintBean.ResultBean> list) {
        this.list .clear();
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addList(List<FootprintBean.ResultBean> list) {
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_footprint_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView_browseNum.setText("已浏览"+list.get(i).getBrowseNum()+"次");
        Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.imageView_masterPic);
        //解密   时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(QuanZi_FaBu_Time, Locale.getDefault());
        viewHolder.textView_browseTime.setText(dateFormat.format(list.get(i).getBrowseTime()));

        viewHolder.textView_commodityName.setText(list.get(i).getCommodityName());
        viewHolder.textView_price.setText("￥"+list.get(i).getPrice()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.footprint_browseNum)
        TextView textView_browseNum;
        @BindView(R.id.footprint_browseTime)
        TextView textView_browseTime;
        @BindView(R.id.footprint_commodityName)
        TextView textView_commodityName;
        @BindView(R.id.footprint_masterPic)
        ImageView imageView_masterPic;
        @BindView(R.id.footprint_price)
        TextView textView_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
