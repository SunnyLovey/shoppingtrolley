package com.bawei.demo.shoppingtrolley.shoppingaddress;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.customview.CustomCounter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private List<AddressBean.Result> list;
    private Context context;

    public AddressAdapter(Context context) {
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
        View view= LayoutInflater.from(context).inflate(R.layout.layout_address,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
         viewHolder.textView_name.setText(list.get(i).getRealName());
         viewHolder.textView_phone.setText(list.get(i).getPhone());
         viewHolder.textView_address.setText(list.get(i).getAddress());
        if(list.get(i).getWhetherDefault()==1){
            viewHolder.radioButton_address.setChecked(true);
        }else {
            viewHolder.radioButton_address.setChecked(false);
        }

        viewHolder.radioButton_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if(isChecked){
                     for (int a=0;a<list.size();a++){
                         list.get(a).setWhetherDefault(2);
                         viewHolder.radioButton_address.setChecked(false);
                     }
                     list.get(i).setWhetherDefault(1);
                     viewHolder.radioButton_address.setChecked(isChecked);
                     if(callBackListener!=null){
                         callBackListener.getRadioId(list.get(i).getId());
                     }
                    notifyDataSetChanged();

                 }
             }
         });

        viewHolder.textView_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressBean.Result result=new AddressBean.Result(list.get(i).getAddress(),
                        list.get(i).getCreateTime(),
                        list.get(i).getId(),
                        list.get(i).getPhone(),
                        list.get(i).getRealName(),
                        list.get(i).getUserId(),
                        list.get(i).getWhetherDefault(),
                        list.get(i).getZipCode()
                        );
                Intent intent=new Intent(context,UpdateAdressActivity.class);
                intent.putExtra("address",(Serializable) result);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_name)
        TextView textView_name;
        @BindView(R.id.text_phone)
        TextView textView_phone;
        @BindView(R.id.text_address_show)
        TextView textView_address;
        @BindView(R.id.radio_address)
        RadioButton radioButton_address;
        @BindView(R.id.text_update)
        TextView textView_update;

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
        void getRadioId(int id);
    }
}
