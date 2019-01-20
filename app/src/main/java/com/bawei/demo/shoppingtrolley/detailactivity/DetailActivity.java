package com.bawei.demo.shoppingtrolley.detailactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.SpacesItemDecoration;
import com.bawei.demo.shoppingtrolley.bean.DetailBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.shoppingcar.GoodsBean;
import com.bawei.demo.shoppingtrolley.shoppingcar.PayActivity;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.view.IView;
import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
* 商品详情页
* 加入购物车
* */
public class DetailActivity extends AppCompatActivity implements IView{
    @BindView(R.id.image_back)
    ImageView image_back;
    @BindView(R.id.xbanner_goods)
    XBanner xBanner;
    @BindView(R.id.detail_price)
    TextView textView_price;
    @BindView(R.id.detail_title)
    TextView textView_title;
    @BindView(R.id.detail_weight)
    TextView textView_weight;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.comment_recycler)
    RecyclerView comment_recycler;
   private IPresenterImpl iPresenter;
   private CommentAdapter adapter;
   @BindView(R.id.button_add_shoppingcar)
    ImageView button_add_shoppingcar;
    @BindView(R.id.shopBuy)
    ImageView imageView_shopBuy;
    private int commodId;
    private DetailBean bean;
    private String[] split;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        iPresenter=new IPresenterImpl(this);
        //实例化
        ButterKnife.bind(this);
        Intent intent = getIntent();
        commodId = intent.getIntExtra("commodityId", 2);
        //商品的详情
        iPresenter.startRequestGet(String.format(Apis.url_detail_goods, commodId), DetailBean.class);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        comment_recycler.setLayoutManager(manager);
        int spacingInPixels = 10;
        comment_recycler.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        adapter=new CommentAdapter(this);
        comment_recycler.setAdapter(adapter);
         //商品评论
        iPresenter.startRequestGet(String.format(Apis.url_comment, commodId,1,5),GoodsCommentBean.class);

    }
    @OnClick({R.id.button_add_shoppingcar,R.id.image_back,R.id.shopBuy})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.button_add_shoppingcar:
                selShoppingCar();
                break;
            case R.id.image_back:
                finish();
                break;
            case R.id.shopBuy:
                payGoods();
                break;
        }
    }

    private void payGoods() {
        List<GoodsBean.Result> aList = new ArrayList<>();
        bean.getResult().setCheck(true);
        aList.add(new GoodsBean.Result(bean.getResult().getCommodityId(),
                bean.getResult().getCommodityName(),
               split[0],
                Double.valueOf(bean.getResult().getPrice()),
                1,
                bean.getResult().isCheck()));
        Intent intent = new Intent(DetailActivity.this, PayActivity.class);
        intent.putExtra("priceTotal",Double.valueOf(bean.getResult().getPrice()));
        intent.putExtra("num",1);
        intent.putExtra("aList", (Serializable) aList);
        startActivity(intent);

    }

    //查询购物车
    private void selShoppingCar(){
        iPresenter.startRequestGet(Apis.url_select_shoppingCar,GoodsBean.class);
    }
   //加入购物车
    private void addShoppingCar(List<ShopBean> list) {
      String str="[";
       if(list.size()==0){
           list.add(new ShopBean(commodId,1));
       }else {
           for (int i=0;i<list.size();i++){
               if(commodId==list.get(i).getCommodityId()){
                   int count = list.get(i).getCount();
                   count++;
                   list.get(i).setCount(count);
                   break;
               }else if (i==list.size()-1){
                   list.add(new ShopBean(commodId,1));
                   break;
               }
           }
       }
        for (ShopBean shopBean:list){
            str+="{\"commodityId\":"+shopBean.getCommodityId()+",\"count\":"+shopBean.getCount()+"},";
        }
        String substring = str.substring(0, str.length() - 1);
        substring+="]";
        Map<String,String> map=new HashMap<>();
        map.put("data",substring);

        iPresenter.startRequestPut(Apis.url_add_shoppingCar,map,AddShoppingBean.class);

    }

    @Override
    public void requestSuccess(Object data) {
        if(data instanceof DetailBean){
            bean = (DetailBean) data;
            String picture = bean.getResult().getPicture();
            split = picture.split("\\,");

            final List<String> list=new ArrayList<>();
            for(int i = 0; i< split.length; i++){
                list.add(split[i]);
            }
            xBanner.setData(list,null);
            xBanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(DetailActivity.this).load(list.get(position)).into((ImageView) view);
                    xBanner.setPageChangeDuration(1000);
                }
            });
            textView_price.setText("￥"+ bean.getResult().getPrice());
            textView_title.setText(bean.getResult().getCommodityName());
            textView_weight.setText(bean.getResult().getWeight()+"kg");
            webView.loadDataWithBaseURL(null, bean.getResult().getDetails(), "text/html", "utf-8", null);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);// 设置WebView可触摸放大缩小
            webView.getSettings().setUseWideViewPort(true);

        } if(data instanceof GoodsCommentBean){
            GoodsCommentBean bean= (GoodsCommentBean) data;
            List<GoodsCommentBean.ResultBean> result = bean.getResult();
            adapter.setList(result);
        } if(data instanceof GoodsBean){
            GoodsBean carBean= (GoodsBean) data;
            if(carBean.getMessage().equals("查询成功")){
                List<ShopBean> list=new ArrayList<>();
                List<GoodsBean.Result> result = carBean.getResult();
                for(GoodsBean.Result results:result){
                    list.add(new ShopBean(results.getCommodityId(),results.getCount()));
                }
                //把查到的数据 传给添加购物车方法 并判断
                addShoppingCar(list);
            }
          } if(data instanceof AddShoppingBean){
            AddShoppingBean bean= (AddShoppingBean) data;
            toast(bean.getMessage());
        }
        }




    @Override
    public void requestFail(String error) {
          toast(error);
    }
    //吐司
    public void toast(String message){
        Toast.makeText(DetailActivity.this,message,Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setVisibility(View.GONE);
            webView.removeAllViews();
            webView.destroy();
        }

    }
}
