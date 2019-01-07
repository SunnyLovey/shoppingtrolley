package com.bawei.demo.shoppingtrolley.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
import com.bawei.demo.shoppingtrolley.adapter.FootprintAdapter;
import com.bawei.demo.shoppingtrolley.bean.FootprintBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FootprintActivity extends AppCompatActivity implements IView{
    @BindView(R.id.footprint_xrecyclerview)
    XRecyclerView xRecyclerView_footprint;
    private IPresenterImpl iPresenter;
    private final int lineCounts=2;
    private FootprintAdapter adapter;
    private int mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint);
        ButterKnife.bind(this);
        //实例化
        iPresenter=new IPresenterImpl(this);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(lineCounts,StaggeredGridLayoutManager.VERTICAL);
        xRecyclerView_footprint.setLayoutManager(manager);
        int spacingInPixels = 20;
        xRecyclerView_footprint.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter=new FootprintAdapter(this);
        xRecyclerView_footprint.setAdapter(adapter);

        mPage=1;
        xRecyclerView_footprint.setPullRefreshEnabled(true);
        xRecyclerView_footprint.setLoadingMoreEnabled(true);
        xRecyclerView_footprint.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        iPresenter.startRequestGet(String.format(Apis.url_footprint,mPage,8), FootprintBean.class);
    }

    @Override
    public void requestSuccess(Object data) {
           FootprintBean bean= (FootprintBean) data;
        List<FootprintBean.ResultBean> result = bean.getResult();
        if(mPage==1){
            adapter.setList(result);
        }else {
            adapter.addList(result);
        }
        mPage++;
        xRecyclerView_footprint.refreshComplete();
        xRecyclerView_footprint.loadMoreComplete();
    }

    @Override
    public void requestFail(String error) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detachView();
    }
}
