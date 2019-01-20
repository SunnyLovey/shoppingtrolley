package com.bawei.demo.shoppingtrolley.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.shoppingcar.GoodsAdapter;
import com.bawei.demo.shoppingtrolley.shoppingcar.GoodsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.PUT;

public class CustomCounter extends LinearLayout {
    private Context mContext;
   @BindView(R.id.text_jia)
    TextView textView_jia;
    @BindView(R.id.text_jian)
    TextView textView_jian;
    @BindView(R.id.edit_num)
    EditText editText_num;
    private GoodsAdapter adapter;
    private int num;
    private int position;
    private List<GoodsBean.Result> list;


    public CustomCounter(Context context) {
        super(context);
        mContext=context;
        list=new ArrayList<>();
        initView();
    }

    public CustomCounter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        list=new ArrayList<>();
        initView();
    }
    public void setData(GoodsAdapter adapter, final List<GoodsBean.Result> list, int i){
        this.adapter=adapter;
        this.list=list;
        position=i;
        num = list.get(i).getCount();
        editText_num.setText(num+"");
        editText_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String s1 = String.valueOf(s);
                    num = Integer.parseInt(s1);
                    list.get(position).setCount(num);

                }catch (Exception e){
                    list.get(position).setCount(1);
                }
                if(callBackListener!=null){
                    callBackListener.getCheckState();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void initView(){
        View view= View.inflate(mContext, R.layout.layout_count,null);
        ButterKnife.bind(this,view);
        addView(view);
    }
    @OnClick({R.id.text_jia,R.id.text_jian})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.text_jia:
                jia();
                break;
            case R.id.text_jian:
                jian();
                break;
        }
    }

    private void jian() {
        if(num>1){
            num--;
            editText_num.setText(num+"");
            list.get(position).setCount(num);
            callBackListener.getCheckState();
            adapter.notifyItemChanged(position);
        }else {
            Toast.makeText(mContext,"数量不能小于1",Toast.LENGTH_LONG).show();
        }
    }

    private void jia() {
        num++;
        editText_num.setText(num+"");
        list.get(position).setCount(num);
        callBackListener.getCheckState();
        adapter.notifyItemChanged(position);
    }

    public onCallBackListener callBackListener;
    public void setOnCallBackListener(onCallBackListener backListener){
        this.callBackListener=backListener;
    }

    public interface onCallBackListener{
        void getCheckState();
    }
}
