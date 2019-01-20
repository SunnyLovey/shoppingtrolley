package com.bawei.demo.shoppingtrolley.detailactivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.adapter.CircleAdapter;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<GoodsCommentBean.ResultBean> list;
    private Context context;
    public static final String QuanZi_FaBu_Time = "yyyy-MM-dd HH:mm:ss";

    public CommentAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<GoodsCommentBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_comment,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textView_nickName.setText(list.get(i).getNickName());
        //解密   时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(QuanZi_FaBu_Time, Locale.getDefault());
        viewHolder.textView_createTime.setText(dateFormat.format(list.get(i).getCreateTime()));
        viewHolder.comment_headPic.setImageURI(list.get(i).getHeadPic());
        viewHolder.textView_content.setText(list.get(i).getContent());
        String image = list.get(i).getImage();
        String[] split = image.split("//,");
        Glide.with(context).load(split[0]).into(viewHolder.simpleDraweeView_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.comment_headPic)
        SimpleDraweeView comment_headPic;
        @BindView(R.id.comment_text_nickName)
        TextView textView_nickName;
        @BindView(R.id.comment_text_createTime)
        TextView textView_createTime;
        @BindView(R.id.comment_text_content)
        TextView textView_content;
        @BindView(R.id.comment_image)
        ImageView simpleDraweeView_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
