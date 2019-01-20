package com.bawei.demo.shoppingtrolley.shoppingcar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.shoppingaddress.AddressAdapter;
import com.bawei.demo.shoppingtrolley.shoppingaddress.AddressBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopAdapter extends RecyclerView.Adapter<PopAdapter.ViewHolder> {
    private List<AddressBean.Result> list;
    private Context context;

    public PopAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<AddressBean.Result> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_pop_address,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView_name.setText(list.get(i).getRealName());
        viewHolder.textView_phone.setText(list.get(i).getPhone());
        viewHolder.textView_address.setText(list.get(i).getAddress());
        viewHolder.textView_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBackListener!=null){
                    callBackListener.getId(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pop_name)
        TextView textView_name;
        @BindView(R.id.pop_phone)
        TextView textView_phone;
        @BindView(R.id.pop_address_show)
        TextView textView_address;
        @BindView(R.id.text_choose)
        TextView textView_choose;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public CallBackListener callBackListener;
    public void setOnCallBackListener(CallBackListener backListener){
        this.callBackListener=backListener;
    }

    public interface CallBackListener{
        void getId(int id);
    }
}
