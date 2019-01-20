package com.bawei.demo.shoppingtrolley.shoppingcar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
import com.bawei.demo.shoppingtrolley.detailactivity.AddShoppingBean;
import com.bawei.demo.shoppingtrolley.detailactivity.ShopBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.shoppingaddress.AddressActivity;
import com.bawei.demo.shoppingtrolley.shoppingaddress.AddressAdapter;
import com.bawei.demo.shoppingtrolley.shoppingaddress.AddressBean;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.view.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
* author:zhangjing
* 创建订单
*
* */
public class PayActivity extends AppCompatActivity implements IView{
    @BindView(R.id.text_addr)
    TextView textView_addr;
    @BindView(R.id.name)
    TextView textView_name;
    @BindView(R.id.phone)
    TextView textView_phone;
    @BindView(R.id.pop_address)
    TextView textView_address;
    @BindView(R.id.text_commit_info)
    TextView textView_commit_info;
    @BindView(R.id.text_commit_money)
    TextView textView_commit_money;
    @BindView(R.id.text_commit_pay)
    TextView textView_commit_pay;
    @BindView(R.id.recycler_addr)
    RecyclerView recyclerView_addr;
    @BindView(R.id.image_addr)
    ImageView image_address;
    @BindView(R.id.relative_no_address)
    RelativeLayout relative_no_address;
    @BindView(R.id.relative_addr)
    RelativeLayout relative_addr;

    private int num;
    private double priceTotal;
    private PopupWindow popupWindow;
    private RecyclerView addr_recycler;
    private IPresenterImpl iPresenter;
    private PopAdapter popAdapter;
    private List<AddressBean.Result> result;
    private List<GoodsBean.Result> aList;
    private int id;
   private PayAdapter payAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this);
        //得到商品数量和价钱
        result=new ArrayList<>();
        Intent intent = getIntent();
        num = intent.getIntExtra("num", 1);
        priceTotal = intent.getDoubleExtra("priceTotal", 1);
        aList = (List<GoodsBean.Result>) intent.getSerializableExtra("aList");
         //先查一下地址
        iPresenter.startRequestGet(Apis.url_sel_address,AddressBean.class);
        popAdapter=new PopAdapter(this);

        //设置数量和总价
        setTextNum();

        //展示商品
        showGoods();


    }
    //展示商品
    private void showGoods() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_addr.setLayoutManager(manager);
        int spacingInPixels = 10;
        recyclerView_addr.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        payAdapter=new PayAdapter(aList,this);
        recyclerView_addr.setAdapter(payAdapter);
    }

    //设置数量和总价
    private void setTextNum() {
        SpannableString spannableString = new SpannableString("共"+num+"件商品，");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_commit_info.setText(spannableString);

        SpannableString spannableString1 = new SpannableString("需付款"+priceTotal+"元");
        spannableString1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), 3, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_commit_money.setText(spannableString1);
    }

    //创建订单
    private void createPay(int gid) {
       // MeansUtils.toast(gid+"");
        String str="[";
        for (GoodsBean.Result result:aList){
            str+="{\"commodityId\":"+result.getCommodityId()+",\"amount\":"+result.getCount()+"},";
        }
        String substring = str.substring(0, str.length() - 1);
        substring+="]";
        Map<String,String> map=new HashMap<>();
        map.put("orderInfo",substring);
        map.put("totalPrice",priceTotal+"");
        map.put("addressId",gid+"");
       iPresenter.startRequest(Apis.url_order,map,AddShoppingBean.class);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.text_addr,R.id.text_commit_pay,R.id.image_addr})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.image_addr:
               popAddress();
                break;
            case R.id.text_commit_pay:
                createPay(id);
                break;
            case R.id.text_addr:
               Intent intent=new Intent(PayActivity.this, AddressActivity.class);
               startActivity(intent);
                break;
        }
    }

       //弹出popupWindow

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void popAddress() {
        View v = View.inflate(this, R.layout.layout_pop, null);
        addr_recycler = v.findViewById(R.id.addr_recycler);
             popRecycler();
        popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, -120);
        popupWindow.showAsDropDown(image_address,-300,0,Gravity.CENTER_VERTICAL);


    }

    private void popRecycler() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        addr_recycler.setLayoutManager(manager);
        int spacingInPixels = 10;
        addr_recycler.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        addr_recycler.setAdapter(popAdapter);
        popAdapter.setOnCallBackListener(new PopAdapter.CallBackListener() {
            @Override
            public void getId(int i) {
                textView_name.setText(result.get(i).getRealName());
                textView_phone.setText(result.get(i).getPhone());
                textView_address.setText(result.get(i).getAddress());
                id = result.get(i).getId();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        iPresenter.startRequestGet(Apis.url_sel_address,AddressBean.class);
    }

    @Override
    public void requestSuccess(Object data) {
            if(data instanceof AddressBean){
                AddressBean bean= (AddressBean) data;
               if(bean.getMessage().equals("你还没有收货地址，快去添加吧")){
                   if(relative_no_address.getVisibility()==View.INVISIBLE){
                       relative_no_address.setVisibility(View.VISIBLE);
                       relative_addr.setVisibility(View.INVISIBLE);
                   }
               }else {
                   relative_no_address.setVisibility(View.INVISIBLE);
                   relative_addr.setVisibility(View.VISIBLE);
                   result = bean.getResult();
                   popAdapter.setList(result);
                   for (int i=0;i<result.size();i++){
                       int whetherDefault = result.get(i).getWhetherDefault();
                       if(whetherDefault==1){
                           id = result.get(i).getId();
                           textView_name.setText(result.get(i).getRealName());
                           textView_phone.setText(result.get(i).getPhone());
                           textView_address.setText(result.get(i).getAddress());
                       }
                   }
               }

            }
            if(data instanceof AddShoppingBean){
                AddShoppingBean bean= (AddShoppingBean) data;
                MeansUtils.toast(bean.getMessage());
                finish();
            }
    }

    @Override
    public void requestFail(String error) {
        MeansUtils.toast(error);
    }
}
