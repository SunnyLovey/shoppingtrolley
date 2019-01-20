package com.bawei.demo.shoppingtrolley.payment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.detailactivity.AddShoppingBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayingActivity extends AppCompatActivity implements IView{
    @BindView(R.id.money_pay)
    Button money_pay;
    @BindView(R.id.pay_success)
    RelativeLayout pay_success;
    @BindView(R.id.pay_fail)
    RelativeLayout pay_fail;
    private IPresenterImpl iPresenter;
    private String id;
    private String price;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paying);
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        id = intent.getStringExtra("id");
        price = intent.getStringExtra("price");
    }
    //去支付
    public void getPay(){
        Map<String,String> map=new HashMap<>();
        map.put("orderId",id);
        map.put("payType",type+"");
        iPresenter.startRequest(Apis.url_pay,map, AddShoppingBean.class);
    }

    @OnClick({R.id.radio_yue,R.id.radio_wei,R.id.radio_zhi,R.id.money_pay,R.id.success_back,R.id.sucess_sel,R.id.fail_pay})
    public void setOnClick(View v){
        switch (v.getId()){
            case R.id.radio_yue:
                type=1;
                money_pay.setText("余额支付"+price+"元");
                break;
            case R.id.radio_wei:
                type=2;
                money_pay.setText("微信支付"+price+"元");
                break;
            case R.id.radio_zhi:
                type=3;
                money_pay.setText("支付宝支付"+price+"元");
                break;
            case R.id.money_pay:
                getPay();
                break;
            case R.id.success_back:
                finish();
                break;
            case R.id.sucess_sel:
                finish();
                break;
            case R.id.fail_pay:
                pay_success.setVisibility(View.GONE);
                pay_fail.setVisibility(View.GONE);
                break;
            default:break;
        }
    }

    @Override
    public void requestSuccess(Object data) {
        AddShoppingBean bean= (AddShoppingBean) data;
        if(bean.getMessage().equals("支付成功")){
            pay_success.setVisibility(View.VISIBLE);
            pay_fail.setVisibility(View.GONE);
        }else{
            pay_success.setVisibility(View.GONE);
            pay_fail.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void requestFail(String error) {
          MeansUtils.toast(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detachView();
    }
}
