package com.bawei.demo.shoppingtrolley.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.eventbusbean.MessageBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.FlagNum;
import com.bawei.demo.shoppingtrolley.view.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class DetailActivity extends AppCompatActivity implements IView{
    private IPresenterImpl iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //实例化IPresenterImpl
        iPresenter=new IPresenterImpl(this);
        //注册
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(MessageBean messageBean){
        if(messageBean.getFlag()== FlagNum.HOT_NUM){
            int hot_id= (int) messageBean.getObject();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void requestSuccess(Object data) {

    }

    @Override
    public void requestFail(String error) {

    }
}
