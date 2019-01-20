package com.bawei.demo.shoppingtrolley.payment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderAdapter;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderBean;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderGoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayingAdapter extends RecyclerView.Adapter<PayingAdapter.ViewHolder> {
    private List<OrderBean.OrderListBean> list;
    private Context context;
    private OrderGoodsAdapter adapter;
    private double totalPrice;

    public PayingAdapter(Context context) {
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
        View view= LayoutInflater.from(context).inflate(R.layout.layout_paying,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        List<OrderBean.OrderListBean.DetailListBean> detailList = list.get(i).getDetailList();
        viewHolder.textView_code.setText(list.get(i).getOrderId());
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView_order_detail.setLayoutManager(manager);
        adapter=new OrderGoodsAdapter(detailList,context);
        viewHolder.recyclerView_order_detail.setAdapter(adapter);

        //计算价格
        int size = list.get(i).getDetailList().size();
        int num=0;
        totalPrice = 0;
        for(int j=0;j<size;j++){
            num+=list.get(i).getDetailList().get(j).getCommodityCount();
            totalPrice +=list.get(i).getDetailList().get(j).getCommodityPrice()*list.get(i).getDetailList().get(j).getCommodityCount();
        }

        SpannableString spannableString = new SpannableString("共"+num+"件商品");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.textView_order_total_count.setText(spannableString);

        SpannableString spannableString1 = new SpannableString("需付款"+ totalPrice +"元");
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 3, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.textView_order_total_price.setText(spannableString1);

        //支付传订单ID
        final double finalTotalPrice = totalPrice;
        viewHolder.textView_go_to_pay_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PayingActivity.class);
                intent.putExtra("id",list.get(i).getOrderId());
                intent.putExtra("price", totalPrice +"");
                context.startActivity(intent);
            }
        });
        viewHolder.textView_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(longCallBack!=null){
                    longCallBack.getOrderId(list.get(i).getOrderId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_order_pay)
        TextView textView_code;
        @BindView(R.id.recycler_order_pay)
        RecyclerView recyclerView_order_detail;
        @BindView(R.id.order_total_count)
        TextView textView_order_total_count;
        @BindView(R.id.order_total_price)
        TextView textView_order_total_price;
        @BindView(R.id.go_to_pay_order)
        TextView textView_go_to_pay_order;
        @BindView(R.id.cancel_order)
        TextView textView_cancel_order;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public LongCallBack longCallBack;
    public void setOnLongCallBack(LongCallBack callBack){
        this.longCallBack=callBack;
    }
    public interface LongCallBack{
        void getOrderId(String orderId);
    }
}
