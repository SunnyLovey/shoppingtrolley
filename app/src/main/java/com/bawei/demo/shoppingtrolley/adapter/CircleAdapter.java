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
import com.bawei.demo.shoppingtrolley.bean.CircleBean;
import com.bawei.demo.shoppingtrolley.customview.MyLayout;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder> {
    private List<CircleBean.ResultBean> list;
    private Context context;
    public static final String QuanZi_FaBu_Time = "yyyy-MM-dd HH:mm:ss";


    public CircleAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<CircleBean.ResultBean> list) {
        this.list.clear();
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addList(List<CircleBean.ResultBean> list) {
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_circle_home_page,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.textView_nickName.setText(list.get(i).getNickName());
        //解密   时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(QuanZi_FaBu_Time, Locale.getDefault());
        viewHolder.textView_createTime.setText(dateFormat.format(list.get(i).getCreateTime()));
        viewHolder.simpleDraweeView_headPic.setImageURI(list.get(i).getHeadPic());
        viewHolder.textView_content.setText(list.get(i).getContent());
        Glide.with(context).load(list.get(i).getImage()).into(viewHolder.simpleDraweeView_image);
        viewHolder.textView_hand_count.setText(list.get(i).getGreatNum()+"");
        if(list.get(i).getWhetherGreat()==1){
            viewHolder.imageView_hand.setImageResource(R.drawable.common_btn_prise_s);

        }else{
            viewHolder.imageView_hand.setImageResource(R.drawable.common_btn_prise_n);
        }
        viewHolder.imageView_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(circleCallBack!=null){
                    if(list.get(i).getWhetherGreat()==1){
                        circleCallBack.getInformation(list.get(i).getId(),list.get(i).getWhetherGreat(),i);

                    }else {
                        viewHolder.myLayout.addLayout();
                        circleCallBack.getInformation(list.get(i).getId(),list.get(i).getWhetherGreat(),i);

                    }
                }
            }
        });



    }
    public void addHand(int position){
        list.get(position).setWhetherGreat(1);
        list.get(position).setGreatNum(list.get(position).getGreatNum()+1);
        notifyDataSetChanged();
    }
    public void cancelHand(int position){
        list.get(position).setWhetherGreat(2);
        list.get(position).setGreatNum(list.get(position).getGreatNum()-1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simple_headPic)
        SimpleDraweeView simpleDraweeView_headPic;
        @BindView(R.id.text_nickName)
        TextView textView_nickName;
        @BindView(R.id.text_createTime)
        TextView textView_createTime;
        @BindView(R.id.text_content)
        TextView textView_content;
        @BindView(R.id.image_simple)
        ImageView simpleDraweeView_image;
        @BindView(R.id.image_hand)
        ImageView imageView_hand;
        @BindView(R.id.hand_count)
        TextView textView_hand_count;
        @BindView(R.id.myLayout)
        MyLayout myLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public CircleCallBack circleCallBack;
    public void setOnCircleCallBack(CircleCallBack callBack){
        this.circleCallBack=callBack;
    }
    public interface CircleCallBack{
        void getInformation(int id,int whetherGreat,int position);
    }


}
