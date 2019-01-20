package com.bawei.demo.shoppingtrolley.evaluate;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bawei.demo.shoppingtrolley.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleImageAdpter extends RecyclerView.Adapter<RecycleImageAdpter.ViewHolder> {
    private List<Object> mList;
    private Context mContext;

    public RecycleImageAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        mList.add(mList.size(), R.drawable.common_btn_camera_blue);
    }

    public void setmList(List<Object> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.grid_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i>0){
            viewHolder.imageView.setImageBitmap((Bitmap)mList.get(i));
        }
        if (i==0){
            viewHolder.imageView.setBackgroundResource((Integer)mList.get(i));
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mImageClick!=null){
                        mImageClick.getdata();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.grild_item_image);
        }
    }
    private ImageClick mImageClick;
    public void getImage(ImageClick mImageClick){
        this.mImageClick=mImageClick;
    }
    public interface ImageClick{
        void getdata();
    }
}
