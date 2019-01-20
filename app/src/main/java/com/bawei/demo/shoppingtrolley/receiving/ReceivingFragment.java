package com.bawei.demo.shoppingtrolley.receiving;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
 * 待收货，确定收货
 * 20190111
 * */
public class ReceivingFragment extends BaseFragment implements IView{
    @BindView(R.id.receive_recycler)
    RecyclerView xRecyclerView;

    private IPresenterImpl iPresenter;
    private int mPage=1;
    private  final  int STATUS=2;
    private final int COUNT=20;
    private ReceiveAdapter adapter;
    @Override
    protected int findViewId() {
        return R.layout.layout_receiving;
    }

    @Override
    protected void initData(View view) {
        //实例化
        iPresenter=new IPresenterImpl(this);
        //绑定
        ButterKnife.bind(this,view);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(manager);

        adapter=new ReceiveAdapter(getActivity());
        xRecyclerView.setAdapter(adapter);

        adapter.setOnCallBack(new ReceiveAdapter.CallBack() {
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
