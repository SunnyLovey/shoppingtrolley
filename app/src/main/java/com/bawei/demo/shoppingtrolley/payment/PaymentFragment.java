package com.bawei.demo.shoppingtrolley.payment;


import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderAdapter;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderBean;
import com.bawei.demo.shoppingtrolley.detailactivity.AddShoppingBean;
import com.bawei.demo.shoppingtrolley.home_fragment.BaseFragment;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
* author:zhangjing
* 待付款，支付
* 20190111
*
* */

public class PaymentFragment extends BaseFragment implements IView{
    @BindView(R.id.pay_recycler)
    RecyclerView xRecyclerView;
    private IPresenterImpl iPresenter;
    private int mPage=1;
    private  final  int STATUS=1;
    private final int COUNT=20;
    private PayingAdapter adapter;
    @Override
    protected int findViewId() {
        return R.layout.layout_payment;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        iPresenter=new IPresenterImpl(this);

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        adapter=new PayingAdapter(getActivity());
        xRecyclerView.setAdapter(adapter);

    adapter.setOnLongCallBack(new PayingAdapter.LongCallBack() {
        @Override
        public void getOrderId(String orderId) {
            iPresenter.startRequestDelete(String.format(Apis.url_delete_order,orderId), AddShoppingBean.class);
        }
    });

        getInfo();
    }

    private void getInfo() {
        iPresenter.startRequestGet(String.format(Apis.url_find_order,STATUS,mPage,COUNT),OrderBean.class);
    }

   @Override
    public void onResume() {
        super.onResume();
       getInfo();
    }

    @Override
    public void requestSuccess(Object data) {
       if(data instanceof OrderBean){
           OrderBean bean= (OrderBean) data;
           List<OrderBean.OrderListBean> orderList = bean.getOrderList();
           adapter.setList(orderList);

       }
        if(data instanceof AddShoppingBean){
            AddShoppingBean bean= (AddShoppingBean) data;
            MeansUtils.toast(bean.getMessage());


        }

    }

    @Override
    public void requestFail(String error) {
        MeansUtils.toast(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        iPresenter.detachView();
    }
}
