package com.bawei.demo.shoppingtrolley.wallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.detailactivity.AddShoppingBean;
import com.bawei.demo.shoppingtrolley.payment.PayingAdapter;
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
* 我的钱包
* time:20190111
* */

public class WalletActivity extends AppCompatActivity implements IView{
    @BindView(R.id.pay_xrecycle)
    XRecyclerView xRecyclerView;
    @BindView(R.id.text_money)
    TextView textView_money;
    private IPresenterImpl iPresenter;
    private int mPage;
    private final int COUNT=8;
    private WalletAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        iPresenter=new IPresenterImpl(this);

        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        mPage=1;
        //允许刷新加载
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setPullRefreshEnabled(true);
        adapter=new WalletAdapter(this);
        xRecyclerView.setAdapter(adapter);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                getInfo();
            }



            @Override
            public void onLoadMore() {
                getInfo();
            }
        });
        getInfo();

    }
    private void getInfo() {
        iPresenter.startRequestGet(String.format(Apis.url_wallet,mPage,COUNT),WalletBean.class);
    }
    @Override
    public void requestSuccess(Object data) {
        WalletBean bean= (WalletBean) data;
        int balance = bean.getResult().getBalance();
        textView_money.setText(balance+"");
        List<WalletBean.ResultBean.DetailListBean> detailList = bean.getResult().getDetailList();
        if(mPage==1){
            adapter.setList(detailList);
        }else {
            adapter.addList(detailList);
        }
        mPage++;
        xRecyclerView.loadMoreComplete();
        xRecyclerView.refreshComplete();
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
