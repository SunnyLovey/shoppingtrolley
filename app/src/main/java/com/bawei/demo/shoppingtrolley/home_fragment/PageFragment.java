package com.bawei.demo.shoppingtrolley.home_fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.adapter.LabelAdapter;
import com.bawei.demo.shoppingtrolley.bean.BannerBean;
import com.bawei.demo.shoppingtrolley.bean.GoodsInfoBean;
import com.bawei.demo.shoppingtrolley.bean.LabelBean;
import com.bawei.demo.shoppingtrolley.bean.OneCategoryBean;
import com.bawei.demo.shoppingtrolley.bean.SearchBean;
import com.bawei.demo.shoppingtrolley.bean.TwoCategoryBean;
import com.bawei.demo.shoppingtrolley.adapter.RecyclerHotAdapter;
import com.bawei.demo.shoppingtrolley.adapter.RecyclerLifeAdapter;
import com.bawei.demo.shoppingtrolley.adapter.RecyclerMarginAdapter;
import com.bawei.demo.shoppingtrolley.adapter.RecyclerSearchAdapter;
import com.bawei.demo.shoppingtrolley.adapter.RecyclerViewOneAdapter;
import com.bawei.demo.shoppingtrolley.adapter.RecyclerViewTwoAdapter;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.view.IView;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PageFragment extends BaseFragment implements IView{
    @BindView(R.id.viewpager_draw)
    XBanner xBanner;
    @BindView(R.id.text_search)
    TextView textView_search;
    @BindView(R.id.edit_goods)
    EditText editText_goods;
    @BindView(R.id.image_search)
    ImageView imageView_search;
    @BindView(R.id.brand_more)
    ImageView imageView_brand_more;
    @BindView(R.id.recycler_hot_goods)
    RecyclerView recyclerView_hot_goods;
    @BindView(R.id.recycler_magic_goods)
    RecyclerView recyclerView_margic_goods;
    @BindView(R.id.recycler_life_goods)
    RecyclerView recyclerView_life_goods;
    @BindView(R.id.imageview_pink_change)
    ImageView imageView_pick_change;
    @BindView(R.id.imageview_purple)
    ImageView imageView_purple_change;
    @BindView(R.id.imageview_yellow)
    ImageView imageView_yellow_change;
    @BindView(R.id.scrollview)
    ScrollView scrollView;
    @BindView(R.id.relative_hot)
    RelativeLayout relative_hot;
    @BindView(R.id.relative_margic)
    RelativeLayout relative_margic;
    @BindView(R.id.relative_life)
    RelativeLayout relative_life;
    @BindView(R.id.recycler_label_life)
    XRecyclerView recycler_label_life;
    @BindView(R.id.recycler_label)
    XRecyclerView recycler_label;
    @BindView(R.id.recycler_label_margic)
    XRecyclerView recycler_label_margic;
    @BindView(R.id.relative_search)
    RelativeLayout relativeLayout_search;
    @BindView(R.id.recycler_search_goods)
    XRecyclerView recyclerView_search_goods;
    @BindView(R.id.relative_no_goods)
    RelativeLayout relative_no_goods;
    private IPresenterImpl iPresenter;
    private RecyclerHotAdapter recyclerHotAdapter;
    private RecyclerMarginAdapter recyclerMarginAdapter;
    private RecyclerLifeAdapter recyclerLifeAdapter;
    private final int lineCounts=2;
    private RecyclerViewOneAdapter recyclerViewOneAdapter;
    private RecyclerViewTwoAdapter recyclerViewTwoAdapter;
    private LabelAdapter labelAdapter;
    private RecyclerSearchAdapter recyclerSearchAdapter;
    private int mPage,hot_mPage;
    private int hot_id,margic_id,life_id;
    private String goods_name;
    private RecyclerView recyclerView_one_grade;
    private RecyclerView recyclerView_two_grade;
    private PopupWindow popupWindow;

    @Override
    protected int findViewId() {
        return R.layout.layout_home_page;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        //实例化IPresenterImpl
        iPresenter=new IPresenterImpl(this);
         bannerView();//轮播图
         goodsHot();//商品热销
         goodsMagic();//魔力时尚
         goodsLife();//品质生活
          recyclerManger();//布局管理器

    }
    //设置PopupWindow
    private void gradeLine() {
        View v = View.inflate(getActivity(), R.layout.layout_popup, null);
        recyclerView_one_grade = v.findViewById(R.id.recycler_one_grade);
        //一级列表
        oneGrade();
        recyclerView_two_grade = v.findViewById(R.id.recycler_two_grade);
        //二级列表
        twoGrade();
        popupWindow = new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(v, Gravity.CENTER_VERTICAL, 0, -350);

    }

    private void twoGrade() {
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView_two_grade.setLayoutManager(manager);
        recyclerViewTwoAdapter=new RecyclerViewTwoAdapter(getActivity());
        recyclerView_two_grade.setAdapter(recyclerViewTwoAdapter);
        recyclerViewTwoAdapter.setOnCallBack(new RecyclerViewTwoAdapter.CallBack() {
            @Override
            public void getPositionId(String id) {
                searchGoods(Apis.url_two_goodsMore,id);
            }
        });
    }

    private void recyclerManger() {
        int spacingInPixels = 10;
        //搜索的管理器
        GridLayoutManager manager=new GridLayoutManager(getActivity(),lineCounts);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_search_goods.setLayoutManager(manager);
        recyclerView_search_goods.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerSearchAdapter=new RecyclerSearchAdapter(getActivity());
        //设置适配器
        recyclerView_search_goods.setAdapter(recyclerSearchAdapter);
        //商品热销的管理器
        GridLayoutManager manager1=new GridLayoutManager(getActivity(),lineCounts);
        manager1.setOrientation(OrientationHelper.VERTICAL);
        recycler_label.setLayoutManager(manager1);
        //允许刷新，加载
        recycler_label.setPullRefreshEnabled(true);
        recycler_label.setLoadingMoreEnabled(true);
        recycler_label.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        labelAdapter=new LabelAdapter(getActivity());
        recycler_label.setAdapter(labelAdapter);
        //魔力时尚的管理器
        GridLayoutManager manager2=new GridLayoutManager(getActivity(),lineCounts);
        manager2.setOrientation(OrientationHelper.VERTICAL);
        recycler_label_margic.setLayoutManager(manager2);
        recycler_label_margic.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recycler_label_margic.setAdapter(labelAdapter);
        //品质生活的管理器
        GridLayoutManager manager3=new GridLayoutManager(getActivity(),lineCounts);
        manager3.setOrientation(OrientationHelper.VERTICAL);
        recycler_label_life.setLayoutManager(manager3);
        recycler_label_life.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recycler_label_life.setAdapter(labelAdapter);
    }

    //商品热销更多
    private void goodsLabel() {
        hot_mPage=1;
        recycler_label.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                hot_mPage=1;
                getGoodsHot();
            }

            @Override
            public void onLoadMore() {
                getGoodsHot();
            }
        });
        getGoodsHot();

    }
    //请求商品热销更多的数据
    private void getGoodsHot() {
        iPresenter.startRequestGet(String.format(Apis.url_hot_Label,hot_id+"",hot_mPage,8), LabelBean.class);
    }
    //魔力时尚更多
    private void goodsLabelMargic() {
      hot_mPage=1;
      recycler_label_margic.setLoadingListener(new XRecyclerView.LoadingListener() {
          @Override
          public void onRefresh() {
              hot_mPage=1;
              getGoodsMargic();
          }

          @Override
          public void onLoadMore() {
              getGoodsMargic();
          }
      });
        getGoodsMargic();

    }
    //请求魔力时尚更多的数据
    private void getGoodsMargic() {
        iPresenter.startRequestGet(String.format(Apis.url_hot_Label,margic_id+"",hot_mPage,10), LabelBean.class);
    }

    //品质生活更多
    private void goodsLabelLife() {
        hot_mPage=1;
      recycler_label_life.setLoadingListener(new XRecyclerView.LoadingListener() {
          @Override
          public void onRefresh() {
              hot_mPage=1;
              getGoodsLife();
          }

          @Override
          public void onLoadMore() {
              getGoodsLife();
          }
      });
        getGoodsLife();

    }
    //请求品质生活更多的数据
    private void getGoodsLife() {
        iPresenter.startRequestGet(String.format(Apis.url_hot_Label,life_id+"",hot_mPage,8), LabelBean.class);
    }

    //一级列表
    private void oneGrade() {
          LinearLayoutManager manager=new LinearLayoutManager(getActivity());
          manager.setOrientation(OrientationHelper.HORIZONTAL);
          recyclerView_one_grade.setLayoutManager(manager);
          recyclerViewOneAdapter=new RecyclerViewOneAdapter(getActivity());
          recyclerView_one_grade.setAdapter(recyclerViewOneAdapter);
        iPresenter.startRequestGet(String.format(Apis.url_two_Category,"1001002"),TwoCategoryBean.class);
          recyclerViewOneAdapter.setOnCallBack(new RecyclerViewOneAdapter.CallBack() {
              @Override
              public void getPosition(String id, int position) {
                  iPresenter.startRequestGet(String.format(Apis.url_two_Category,id),TwoCategoryBean.class);
              }


          });
          iPresenter.startRequestGet(Apis.url_one_Category, OneCategoryBean.class);
    }

    //品质生活
    private void goodsLife() {
        GridLayoutManager manager=new GridLayoutManager(getActivity(),lineCounts);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_life_goods.setLayoutManager(manager);
        int spacingInPixels = 10;
        recyclerView_life_goods.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerLifeAdapter=new RecyclerLifeAdapter(getActivity());
        recyclerView_life_goods.setAdapter(recyclerLifeAdapter);
        iPresenter.startRequestGet(Apis.url_hot_goods, GoodsInfoBean.class);
    }
    //魔力时尚
    private void goodsMagic() {
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_margic_goods.setLayoutManager(manager);
        int spacingInPixels = 10;
        recyclerView_margic_goods.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        recyclerMarginAdapter=new RecyclerMarginAdapter(getActivity());
        recyclerView_margic_goods.setAdapter(recyclerMarginAdapter);
        iPresenter.startRequestGet(Apis.url_hot_goods, GoodsInfoBean.class);

    }
    //商品热销
    private void goodsHot() {
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerView_hot_goods.setLayoutManager(manager);
        int spacingInPixels = 10;
        recyclerView_hot_goods.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        recyclerHotAdapter=new RecyclerHotAdapter(getActivity());
        recyclerView_hot_goods.setAdapter(recyclerHotAdapter);


        iPresenter.startRequestGet(Apis.url_hot_goods, GoodsInfoBean.class);
    }

    @OnClick({R.id.image_search,R.id.text_search,R.id.brand_more,R.id.imageview_pink_change,R.id.imageview_purple,R.id.imageview_yellow})
    public void viewClick(View view){
        switch (view.getId()){
            case R.id.image_search:
                //判断是否显示隐藏
                hasVisibleEdit();
                break;
            case R.id.text_search:
                //判断是否显示隐藏
                hasVisibleImage();
                break;
            case R.id.brand_more:
                //弹出PopupWindow
                gradeLine();
                break;
            case R.id.imageview_pink_change:
                hasVisibleScroll();
                break;
            case R.id.imageview_purple:
                hasVisibleScrollMargic();
                break;
            case R.id.imageview_yellow:
                hasVisibleScrollLife();
                break;
        }
    }

    private void hasVisibleScrollLife() {
        if(scrollView.getVisibility()==View.VISIBLE){
            scrollView.setVisibility(View.INVISIBLE);
            relative_hot.setVisibility(View.INVISIBLE);
            relative_margic.setVisibility(View.INVISIBLE);
            relative_life.setVisibility(View.VISIBLE);
            //商品详情
            goodsLabelLife();
        }
    }

    private void hasVisibleScrollMargic() {
        if(scrollView.getVisibility()==View.VISIBLE){
            scrollView.setVisibility(View.INVISIBLE);
            relative_hot.setVisibility(View.INVISIBLE);
            relative_margic.setVisibility(View.VISIBLE);
            relative_life.setVisibility(View.INVISIBLE);
            //商品详情
            goodsLabelMargic();
        }
    }

    private void hasVisibleScroll() {
        if(scrollView.getVisibility()==View.VISIBLE){
            scrollView.setVisibility(View.INVISIBLE);
            relative_hot.setVisibility(View.VISIBLE);
            relative_margic.setVisibility(View.INVISIBLE);
            relative_life.setVisibility(View.INVISIBLE);
            //商品详情
            goodsLabel();
        }
    }

    private void hasVisibleImage() {
        if(textView_search.getVisibility()==View.VISIBLE){
            if(editText_goods.getText().toString().equals("")){
                imageView_search.setVisibility(View.VISIBLE);
                editText_goods.setVisibility(View.INVISIBLE);
                textView_search.setVisibility(View.INVISIBLE);
            }else {
                goods_name = editText_goods.getText().toString();
                searchGoods(Apis.url_search_goods,goods_name);

            }
        }
    }

    private void searchGoods(final String url, final String s) {
        mPage=1;
        scrollView.setVisibility(View.INVISIBLE);
        relativeLayout_search.setVisibility(View.VISIBLE);

        //允许刷新，加载
        recyclerView_search_goods.setPullRefreshEnabled(true);
        recyclerView_search_goods.setLoadingMoreEnabled(true);
        //监听事件
        recyclerView_search_goods.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
               mPage=1;
               iPresenter.startRequestGet(String.format(url, s,mPage,6), SearchBean.class);
            }

            @Override
            public void onLoadMore() {
                iPresenter.startRequestGet(String.format(url, s,mPage,6), SearchBean.class);
            }
        });
        iPresenter.startRequestGet(String.format(url, s,mPage,6), SearchBean.class);


    }

    private void hasVisibleEdit() {
            if(imageView_search.getVisibility()==View.VISIBLE){
                editText_goods.setVisibility(View.VISIBLE);
                textView_search.setVisibility(View.VISIBLE);
                imageView_search.setVisibility(View.INVISIBLE);
            }
    }

    private void bannerView() {
        iPresenter.startRequestGet(Apis.url_banner,BannerBean.class);
    }

    @Override
    public void requestSuccess(Object data) {
          backPage();
        if(data instanceof BannerBean){
            //banner图
            BannerBean bannerBean= (BannerBean) data;
            xBanner.setData(bannerBean.getResult(),null);
            xBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    BannerBean.ResultBean bean= (BannerBean.ResultBean) model;
                    Glide.with(getActivity()).load(bean.getImageUrl()).into((ImageView) view);
                    banner.setPageChangeDuration(1000);
                }
            });

        }else if(data instanceof GoodsInfoBean){
            GoodsInfoBean bean= (GoodsInfoBean) data;
            hot_id = bean.getResult().getRxxp().get(0).getId();
            //商品热销
            List<GoodsInfoBean.ResultBean.RxxpBean.CommodityListBean> commodityList = bean.getResult().getRxxp().get(0).getCommodityList();
            recyclerHotAdapter.setList(commodityList);

           //魔力时尚
            margic_id = bean.getResult().getMlss().get(0).getId();
            List<GoodsInfoBean.ResultBean.MlssBean.CommodityListBeanXX> commodityList1 = bean.getResult().getMlss().get(0).getCommodityList();
            recyclerMarginAdapter.setList(commodityList1);

            //品质生活
            life_id = bean.getResult().getPzsh().get(0).getId();
            List<GoodsInfoBean.ResultBean.PzshBean.CommodityListBeanX> commodityList2 = bean.getResult().getPzsh().get(0).getCommodityList();
            recyclerLifeAdapter.setList(commodityList2);

        }else if(data instanceof OneCategoryBean){
            OneCategoryBean bean= (OneCategoryBean) data;
            List<OneCategoryBean.ResultBean> result = bean.getResult();
            recyclerViewOneAdapter.setList(result);
        }
        else if(data instanceof LabelBean){
            //商品更多
            LabelBean bean= (LabelBean) data;
            List<LabelBean.ResultBean> result = bean.getResult();
            if(hot_mPage==1){
                labelAdapter.setList(result);
            }else {
                labelAdapter.addList(result);
            }
            hot_mPage++;
            recycler_label.loadMoreComplete();
            recycler_label.refreshComplete();
            recycler_label_life.loadMoreComplete();
            recycler_label_life.refreshComplete();
            recycler_label_margic.loadMoreComplete();
            recycler_label_margic.refreshComplete();

        }
        else if(data instanceof SearchBean){
            //搜索框搜索
            SearchBean bean= (SearchBean) data;
            List<SearchBean.ResultBean> result = bean.getResult();
            if(result.size()==0&&mPage==1){
              relative_no_goods.setVisibility(View.VISIBLE);
              relativeLayout_search.setVisibility(View.INVISIBLE);
            }else {
                if(mPage==1){
                    recyclerSearchAdapter.setList(result);
                }else {
                    recyclerSearchAdapter.addList(result);
                }
                mPage++;
                recyclerView_search_goods.loadMoreComplete();
                recyclerView_search_goods.refreshComplete();
            }

        }else if(data instanceof TwoCategoryBean){
            //二级列表
            TwoCategoryBean bean= (TwoCategoryBean) data;
            List<TwoCategoryBean.ResultBean> result = bean.getResult();
            recyclerViewTwoAdapter.setList(result);
        }

    }
    //监听返回键，返回上一面
    private long exitTime = 0;
    private void backPage() {
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                  relative_hot.setVisibility(View.INVISIBLE);
                    relative_life.setVisibility(View.INVISIBLE);
                    relative_margic.setVisibility(View.INVISIBLE);
                    relativeLayout_search.setVisibility(View.INVISIBLE);
                    relative_no_goods.setVisibility(View.INVISIBLE);
                  //  imageView_grade.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                 return true;//当fragment消费了点击事件后，返回true，activity中的点击事件就不会执行了

                }
                if(scrollView.getVisibility()==View.VISIBLE){
                    if(System.currentTimeMillis()-exitTime>2000){
                        exitTime=System.currentTimeMillis();
                        toast("在按一下退出shoppingtrolley");
                    }else {
                        //启动一个意图,回到桌面
                        Intent backHome = new Intent(Intent.ACTION_MAIN);
                        backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        backHome.addCategory(Intent.CATEGORY_HOME);
                        startActivity(backHome);
                    }
                }
                return false;//当fragmenet没有消费点击事件，返回false，activity中继续执行对应的逻辑
            }
        });
    }
    //失败的方法
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
        //解除绑定
        iPresenter.detachView();
    }
}
