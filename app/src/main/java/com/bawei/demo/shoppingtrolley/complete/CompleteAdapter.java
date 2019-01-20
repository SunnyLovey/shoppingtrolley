package com.bawei.demo.shoppingtrolley.complete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderAdapter;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderBean;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderGoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompleteAdapter extends RecyclerView.Adapter<CompleteAdapter.ViewHolder> {
    private List<OrderBean.OrderListBean> list;
    private Context context;
    private CompleteGoodsAdapter adapter;

    public CompleteAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<OrderBean.OrderListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.complete_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        List<OrderBean.OrderListBean.DetailListBean> detailList = list.get(i).getDetailList();
        viewHolder.textView_code.setText(list.get(i).getOrderId());
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView_complete_detail.setLayoutManager(manager);
        adapter=new CompleteGoodsAdapter(detailList,context);
        viewHolder.recyclerView_complete_detail.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_complete_code)
        TextView textView_code;
        @BindView(R.id.recycler_complete_detail)
        RecyclerView recyclerView_complete_detail;
        @BindView(R.id.image_complete_del)
        ImageView imageView_del;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
