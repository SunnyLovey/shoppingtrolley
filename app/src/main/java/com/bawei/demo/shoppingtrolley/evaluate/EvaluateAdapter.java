package com.bawei.demo.shoppingtrolley.evaluate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderAdapter;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderBean;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderGoodsAdapter;
import com.bawei.demo.shoppingtrolley.payment.PayingAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder> {
    private List<OrderBean.OrderListBean> list;
    private Context context;
    private EvaluateGoodsAdapter adapter;

    public EvaluateAdapter(Context context) {
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
        View view= LayoutInflater.from(context).inflate(R.layout.evaluate_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        List<OrderBean.OrderListBean.DetailListBean> detailList = list.get(i).getDetailList();
        viewHolder.textView_code.setText(list.get(i).getOrderId());
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView_evalute_detail.setLayoutManager(manager);
        adapter=new EvaluateGoodsAdapter(detailList,context);
        viewHolder.recyclerView_evalute_detail.setAdapter(adapter);
        adapter.setOnCallBack(new EvaluateGoodsAdapter.CallBack() {
            @Override
            public void getPosition(int position) {

                OrderBean.OrderListBean.DetailListBean bean = list.get(i).getDetailList().get(position);
                Intent intent=new Intent(context,EvaluateActivity.class);
                intent.putExtra("aList", (Serializable) bean);
                intent.putExtra("orderId",list.get(position).getOrderId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_evalute_code)
        TextView textView_code;
        @BindView(R.id.recycler_evalute_detail)
        RecyclerView recyclerView_evalute_detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
