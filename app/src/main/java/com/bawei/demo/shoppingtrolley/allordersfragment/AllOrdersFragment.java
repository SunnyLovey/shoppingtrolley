package com.bawei.demo.shoppingtrolley.allordersfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
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
* 全部订单，支付
* 20190111
* */
public class AllOrdersFragment extends BaseFragment implements IView{
    @BindView(R.id.order_recycler)
    RecyclerView xRecyclerView;
    private IPresenterImpl iPresenter;
    private OrderAdapter adapter;
    private  final  int STATUS=0;
    private final int COUNT=20;
    private int mPage=1;
    @Override
    protected int findViewId() {
        return R.layout.all_order;
    }

    @Override
    protected void initData(View view) {
        //实例化
        iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this,view);

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(manager);

        adapter=new OrderAdapter(getActivity());
        xRecyclerView.setAdapter(adapter);

       adapter.setOnLongCallBack(new OrderAdapter.LongCallBack() {
           @Override
           public void getOrderId(String orderId) {
               iPresenter.startRequestDelete(String.format(Apis.url_delete_order,orderId), AddShoppingBean.class);
           }
       });
       adapter.setOnCallBack(new OrderAdapter.CallBack() {
           @Override
           public void getOrderId(String orderId) {
               Map<String,String> map=new HashMap<>();
               map.put("orderId",orderId);
               iPresenter.startRequestPut(Apis.url_confirm_goods,map, AddShoppingBean.class);
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
           if(bean.getMessage().equals("删除成功")){
             getInfo();
           }

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
