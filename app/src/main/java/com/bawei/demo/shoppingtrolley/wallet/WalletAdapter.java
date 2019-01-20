package com.bawei.demo.shoppingtrolley.wallet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private List<WalletBean.ResultBean.DetailListBean> list;
    private Context context;
    public static final String QuanZi_FaBu_Time = "yyyy-MM-dd HH:mm:ss";

    public WalletAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<WalletBean.ResultBean.DetailListBean> list) {
        this.list .clear();
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addList(List<WalletBean.ResultBean.DetailListBean> list) {
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.wallet_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.price.setText(list.get(i).getAmount()+"");
      //  Log.i("TAG",list.get(i).getCreateTime()+"");
        //解密   时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(QuanZi_FaBu_Time, Locale.getDefault());
        viewHolder.date.setText(dateFormat.format(list.get(i).getCreateTime()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pay_price)
        TextView price;
        @BindView(R.id.pay_date)
        TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
