package com.bawei.demo.shoppingtrolley.circle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
import com.bawei.demo.shoppingtrolley.adapter.CircleAdapter;
import com.bawei.demo.shoppingtrolley.bean.CircleBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
* author:zhangjing
* 我的圈子
* time:20190111
* */
public class CircleActivity extends AppCompatActivity implements IView{
    @BindView(R.id.my_circle_recycler)
    XRecyclerView xRecyclerView;
    private IPresenterImpl iPresenter;
    private int mPage;
    private MyCircleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        //绑定
        ButterKnife.bind(this);
        //实例化
        iPresenter=new IPresenterImpl(this);

        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        int spacingInPixels = 20;
        xRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        mPage=1;
        adapter=new MyCircleAdapter(this);
        xRecyclerView.setAdapter(adapter);
        //允许刷新加载
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setPullRefreshEnabled(true);

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
        iPresenter.startRequestGet(String.format(Apis.url_my_circle,mPage,6), MyCircleBean.class);
    }

    @Override
    public void requestSuccess(Object data) {
      MyCircleBean bean= (MyCircleBean) data;
        List<MyCircleBean.ResultBean> result = bean.getResult();
        if(mPage==1){
            adapter.setList(result);
        }else {
            adapter.addList(result);
        }
        mPage++;
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();

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
