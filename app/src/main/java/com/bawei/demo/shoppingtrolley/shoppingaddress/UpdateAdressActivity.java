package com.bawei.demo.shoppingtrolley.shoppingaddress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.detailactivity.AddShoppingBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.view.IView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.leefeng.citypicker.CityPicker;
import me.leefeng.citypicker.CityPickerListener;

public class UpdateAdressActivity extends AppCompatActivity implements IView,CityPickerListener {
    @BindView(R.id.popo_edit_name)
    EditText editText_name;
    @BindView(R.id.popop_edit_phone)
    EditText editText_phone;
    @BindView(R.id.popop_edit_address)
    EditText editText_address;
    @BindView(R.id.popup_detail_address)
    EditText editText_detail_address;
    @BindView(R.id.popup_edit_zipCode)
    EditText editText_zipCode;
    @BindView(R.id.true_update)
    Button button_update;
    @BindView(R.id.image_next)
    ImageView imageView_next;
    private IPresenterImpl iPresenter;
    private int id;
    private CityPicker cityPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_adress);
        iPresenter=new IPresenterImpl(this);
        cityPicker = new CityPicker(UpdateAdressActivity.this, this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        AddressBean.Result result = (AddressBean.Result) intent.getSerializableExtra("address");
      //  Log.i("TAG",result.getRealName()+result.getPhone()+result.getAddress()+result.getZipCode());
        String[] split = result.getAddress().split("\\ ");
        editText_address.setText(split[0]+" "+split[1]+" "+split[2]);
        editText_detail_address.setText(split[3]);
        id = result.getId();
        editText_name.setText(result.getRealName());
        editText_phone.setText(result.getPhone());
        editText_zipCode.setText(result.getZipCode());



    }
    @OnClick({R.id.true_update,R.id.image_next})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.true_update:
                updateAddress();
                break;
            case R.id.image_next:
                getAddress();
                break;
        }
    }



    private void getAddress() {
        cityPicker.show();
    }

    private void updateAddress() {
        String name = editText_name.getText().toString();
        String phone = editText_phone.getText().toString();
        String zipCode = editText_zipCode.getText().toString();
        String detail_address = editText_detail_address.getText().toString();
        String address= editText_address.getText().toString();
        Map<String,String> params=new HashMap<>();
        params.put("id",id+"");
        params.put("realName",name);
        params.put("phone",phone);
        params.put("address",address+" "+detail_address);
        params.put("zipCode",zipCode);
        iPresenter.startRequestPut(Apis.url_update_address,params, AddShoppingBean.class);


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



}
