package com.bawei.demo.shoppingtrolley.receiving;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReceiveAdapter extends RecyclerView.Adapter<ReceiveAdapter.ViewHolder> {
    private List<OrderBean.OrderListBean> list;
    private Context context;
    private ReceiveGoodsAdapter adapter;

    public ReceiveAdapter(Context context) {
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
        View view= LayoutInflater.from(context).inflate(R.layout.receive_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        List<OrderBean.OrderListBean.DetailListBean> detailList = list.get(i).getDetailList();
     viewHolder.textView_receive_code.setText(list.get(i).getOrderId());
     viewHolder.textView_receive_expressSn.setText(list.get(i).getExpressSn());
     viewHolder.textView_receive_comoney.setText(list.get(i).getExpressCompName());
        final LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(OrientationHelper.VERTICAL);
        viewHolder.recyclerView_receive_detail.setLayoutManager(manager);
        adapter=new ReceiveGoodsAdapter(detailList,context);
        viewHolder.recyclerView_receive_detail.setAdapter(adapter);

        //订单ID
        viewHolder.textView_confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myCallBack!=null){
                    myCallBack.getOrderId(list.get(i).getOrderId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_receive_code)
        TextView textView_receive_code;
        @BindView(R.id.recycler_receive_detail)
        RecyclerView recyclerView_receive_detail;
        @BindView(R.id.receive_comoney)
        TextView textView_receive_comoney;
        @BindView(R.id.receive_expressSn)
        TextView textView_receive_expressSn;
        @BindView(R.id.confirm_order)
        TextView textView_confirm_order;
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
        void getOrderId(String orderId);
    }
}
