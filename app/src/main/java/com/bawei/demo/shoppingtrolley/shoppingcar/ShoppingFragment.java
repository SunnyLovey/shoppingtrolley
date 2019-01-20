package com.bawei.demo.shoppingtrolley.shoppingcar;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
import com.bawei.demo.shoppingtrolley.home_fragment.BaseFragment;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.view.IView;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bawei.demo.shoppingtrolley.utils.MeansUtils.toast;

/*
author:zhangjing
购物车功能
跳转到结算页面

*/
public class ShoppingFragment extends BaseFragment implements IView{
    @BindView(R.id.exListView)
    RecyclerView recyclerView_goods;
    private GoodsAdapter adapter;
    private IPresenterImpl iPresenter;
    @BindView(R.id.tv_total_price)
    TextView textView_total_price;
    @BindView(R.id.tv_go_to_pay)
    TextView textView_go_to_pay;
    private List<GoodsBean.Result> result;
    private CheckBox checkBox_all_check;
    int num;
    double priceTotal;
    @Override
    protected int findViewId() {
        return R.layout.layout_home_shopping;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        checkBox_all_check = view.findViewById(R.id.all_chekbox);
        iPresenter=new IPresenterImpl(this);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_goods.setLayoutManager(manager);
        int spacingInPixels = 10;
        recyclerView_goods.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
       adapter=new GoodsAdapter(getActivity());
        recyclerView_goods.setAdapter(adapter);
        adapter.setOnCallBackListener(new GoodsAdapter.onCallBackListener() {
            @Override
            public void getCheckState() {
               priceTotal=0;
               num=0;
                int totalNum=0;
                for(int i=0;i<result.size();i++){
                    totalNum = result.get(i).getCount()+totalNum;
                    if(result.get(i).isCheck){
                        priceTotal=priceTotal+(result.get(i).getCount()*result.get(i).getPrice());
                        num=num+result.get(i).getCount();
                    }

                }
                if(num<totalNum){
                    checkBox_all_check.setChecked(false);
                }else {
                    checkBox_all_check.setChecked(true);
                }
                textView_total_price.setText("￥"+priceTotal);
                textView_go_to_pay.setText("去结算（"+num+")");
            }
        });
        checkBox_all_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAll(checkBox_all_check.isChecked());
              adapter.notifyDataSetChanged();
            }
        });
       iPresenter.startRequestGet(Apis.url_select_shoppingCar,GoodsBean.class);
    }
    //去结算
    @OnClick({R.id.tv_go_to_pay})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.tv_go_to_pay:
                if(num<=0){
                    MeansUtils.toast("请选择要结算的商品");
                }else {
                    List<GoodsBean.Result> aList=new ArrayList<>();
                    for(int i=0;i<result.size();i++){
                        if(result.get(i).isCheck){
                         aList.add(new GoodsBean.Result(result.get(i).getCommodityId(),
                                 result.get(i).getCommodityName(),
                                 result.get(i).getPic(),
                                 result.get(i).getPrice(),
                                 result.get(i).getCount(),
                                 result.get(i).isCheck()
                         ));
                        }
                    }
                    Intent intent=new Intent(getActivity(),PayActivity.class);
                    intent.putExtra("num",num);
                    intent.putExtra("priceTotal",priceTotal);
                    intent.putExtra("aList", (Serializable) aList);
                    getActivity().startActivity(intent);
                     for (int i=0;i<result.size();i++){
                         result.get(i).setCheck(false);
                         adapter.notifyDataSetChanged();
                     }
                }

                break;
        }
    }
    //全选功能
    private void checkAll(boolean checked) {
        priceTotal=0;
        num=0;
        for(int i=0;i<result.size();i++){
            result.get(i).setCheck(checked);
            priceTotal=priceTotal+(result.get(i).getCount()*result.get(i).getPrice());
            num=num+result.get(i).getCount();
        }
        if(checked){
            textView_total_price.setText("￥"+priceTotal);
            textView_go_to_pay.setText("去结算（"+num+")");
        }else {
            textView_total_price.setText("￥0.0");
            textView_go_to_pay.setText("去结算（"+0+")");
        }
    }

    @Override
    public void requestSuccess(Object data) {
         GoodsBean bean= (GoodsBean) data;
        result = bean.getResult();
        adapter.setList(result);

    }

    @Override
    public void requestFail(String error) {
     toast(error);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.detachView();
    }
}
