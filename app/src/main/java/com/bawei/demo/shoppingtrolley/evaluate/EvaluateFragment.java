package com.bawei.demo.shoppingtrolley.evaluate;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderAdapter;
import com.bawei.demo.shoppingtrolley.allordersfragment.OrderBean;
import com.bawei.demo.shoppingtrolley.home_fragment.BaseFragment;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluateFragment extends BaseFragment implements IView{
    @BindView(R.id.evaluate_recycler)
    RecyclerView xRecyclerView;
    private IPresenterImpl iPresenter;
    private int mPage=1;
    private  final  int STATUS=3;
    private final int COUNT=20;
    private EvaluateAdapter adapter;
    @Override
    protected int findViewId() {
        return R.layout.layout_evaluate;
    }

    @Override
    protected void initData(View view) {
       iPresenter=new IPresenterImpl(this);
        ButterKnife.bind(this,view);

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(manager);

        adapter=new EvaluateAdapter(getActivity());
        xRecyclerView.setAdapter(adapter);


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
