package com.bawei.demo.shoppingtrolley.shoppingaddress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;
/*
* author:jingjing
* 新增收货地址
* time:20190109
* */
public class AddressActivity extends AppCompatActivity implements CityPickerListener,IView {
    @BindView(R.id.edit_name)
    EditText editText_name;
    @BindView(R.id.edit_phone)
    EditText editText_phone;
    @BindView(R.id.edit_address)
    EditText editText_address;
    @BindView(R.id.edit_zipCode)
    EditText editText_zipCode;
    @BindView(R.id.detail_address)
    EditText editText_detail_address;
    @BindView(R.id.save_address)
    Button save_address;
    @BindView(R.id.image_next)
    ImageView imageView_next;
    private CityPicker cityPicker;
    private IPresenterImpl iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        //实例化
        iPresenter=new IPresenterImpl(this);
        cityPicker = new CityPicker(AddressActivity.this, this);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.save_address,R.id.image_next})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.save_address:
                saveAddress();
                break;
            case R.id.image_next:
                getAddress();
                break;
        }
    }
     //得到地址
    private void getAddress() {
        cityPicker.show();
    }

    //保存地址
    private void saveAddress() {
        String name = editText_name.getText().toString();
        String phone = editText_phone.getText().toString();
        String detail_address = editText_detail_address.getText().toString();
        String zipCode = editText_zipCode.getText().toString();
        String address = editText_address.getText().toString();
        Map<String,String> params=new HashMap<>();
        params.put("realName",name);
        params.put("phone",phone);
        params.put("address",address+" "+detail_address);
        params.put("zipCode",zipCode);
        iPresenter.startRequest(Apis.url_add_address,params, AddShoppingBean.class);
    }

    @Override
    public void getCity(String s) {
        editText_address.setText(s);
    }
    @Override
    public void onBackPressed() {
        if (cityPicker.isShow()){
            cityPicker.close();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void requestSuccess(Object data) {
           AddShoppingBean bean= (AddShoppingBean) data;
           if(bean.getStatus().equals("0000")){
               MeansUtils.toast(bean.getMessage());
               finish();
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
