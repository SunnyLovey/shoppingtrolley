package com.bawei.demo.shoppingtrolley.home_fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.Toast;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
import com.bawei.demo.shoppingtrolley.adapter.CircleAdapter;
import com.bawei.demo.shoppingtrolley.bean.CircleBean;
import com.bawei.demo.shoppingtrolley.bean.GreatBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
* 圈子和点赞
*
* */

public class CircleFragment extends BaseFragment implements IView{
    @BindView(R.id.circle_xrecyclerview)
    XRecyclerView xRecyclerView_circle;
    IPresenterImpl iPresenter;
    private int mPage;
    private CircleAdapter circleAdapter;
    @Override
    protected int findViewId() {
        return R.layout.layout_home_circle;
    }

    @Override
    protected void initData(View view) {
        //绑定
       ButterKnife.bind(this,view);
       //实例化IPresenterImpl
        iPresenter=new IPresenterImpl(this);
        mPage=1;
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView_circle.setLayoutManager(manager);
        int spacingInPixels = 20;
        xRecyclerView_circle.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        circleAdapter=new CircleAdapter(getActivity());
        xRecyclerView_circle.setAdapter(circleAdapter);
        circleAdapter.setOnCircleCallBack(new CircleAdapter.CircleCallBack() {

            @Override
            public void getInformation(int id, int whetherGreat,int position) {
               //当前用户点赞
                if(whetherGreat==1){
                    cancelGreat(id);//取消点赞
                    circleAdapter.cancelHand(position);
                }else {
                    addGreat(id);//点赞
                    circleAdapter.addHand(position);
                }
            }


        });
        //允许刷新加载
        xRecyclerView_circle.setLoadingMoreEnabled(true);
        xRecyclerView_circle.setPullRefreshEnabled(true);

        xRecyclerView_circle.setLoadingListener(new XRecyclerView.LoadingListener() {
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
        iPresenter.startRequestGet(String.format(Apis.url_circle,mPage,6), CircleBean.class);
    }
    //点赞
    private void addGreat(int id) {
        Map<String,String> params=new HashMap<>();
        params.put("circleId",id+"");
       iPresenter.startRequest(Apis.url_addCircle,params,GreatBean.class);
    }
   //取消点赞
    private void cancelGreat(int id) {
    iPresenter.startRequestDelete(String.format(Apis.url_cancelCircle,id),GreatBean.class);
    }

    @Override
    public void requestSuccess(Object data) {
        if(data instanceof CircleBean){
            CircleBean bean= (CircleBean) data;
            List<CircleBean.ResultBean> result = bean.getResult();
            if(mPage==1){
                circleAdapter.setList(result);
            }else {
                circleAdapter.addList(result);
            }
            mPage++;
            xRecyclerView_circle.refreshComplete();
            xRecyclerView_circle.loadMoreComplete();
        }else if(data instanceof GreatBean){
            GreatBean bean= (GreatBean) data;
            String message = bean.getMessage();
            toast(message);
        }

    }

    @Override
    public void requestFail(String error) {
          toast(error);
    }
    //吐司
    public void toast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消绑定
        iPresenter.detachView();
    }
}
