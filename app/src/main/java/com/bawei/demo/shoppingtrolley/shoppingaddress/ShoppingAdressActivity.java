package com.bawei.demo.shoppingtrolley.shoppingaddress;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
import com.bawei.demo.shoppingtrolley.detailactivity.AddShoppingBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.view.IView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShoppingAdressActivity extends AppCompatActivity implements IView{
    @BindView(R.id.add_address)
    Button button_add_address;
    @BindView(R.id.recycler_address)
    RecyclerView recyclerView_address;
    private IPresenterImpl iPresenter;
    private AddressAdapter addressAdapter;
    private List<AddressBean.Result> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_adress);
        //实例化
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_address.setLayoutManager(manager);
        int spacingInPixels = 10;
        recyclerView_address.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        addressAdapter=new AddressAdapter(this);
        recyclerView_address.setAdapter(addressAdapter);

        addressAdapter.setOnCallBackListener(new AddressAdapter.CallBackListener() {
            @Override
            public void getRadioId(int id) {
                Map<String,String> map=new HashMap<>();
                map.put("id",id+"");
                iPresenter.startRequest(Apis.url_defsult_address,map, AddShoppingBean.class);

            }
        });
        iPresenter.startRequestGet(Apis.url_sel_address,AddressBean.class);

    }

    @OnClick({R.id.add_address})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.add_address:
                Intent intent=new Intent(ShoppingAdressActivity.this,AddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        iPresenter.startRequestGet(Apis.url_sel_address,AddressBean.class);
    }

    @Override
    public void requestSuccess(Object data) {
          if(data instanceof AddressBean){
              AddressBean bean= (AddressBean) data;
              if(bean.getMessage().equals("你还没有收货地址，快去添加吧")){
                  MeansUtils.toast("你还没有收货地址，快去添加吧");
              }else {
                  result = bean.getResult();
                  addressAdapter.setList(result);
              }

          }
          if(data instanceof AddShoppingBean){
              AddShoppingBean bean= (AddShoppingBean) data;
              if(bean.getStatus().equals("0000")){
                  MeansUtils.toast(bean.getMessage());
              }

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
