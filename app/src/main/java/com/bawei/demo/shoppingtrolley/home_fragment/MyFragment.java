package com.bawei.demo.shoppingtrolley.home_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.activity.FootprintActivity;
import com.bawei.demo.shoppingtrolley.app.MyApplication;
import com.bawei.demo.shoppingtrolley.circle.CircleActivity;
import com.bawei.demo.shoppingtrolley.personal.PersonalActivity;
import com.bawei.demo.shoppingtrolley.personal.UserBean;
import com.bawei.demo.shoppingtrolley.presenter.IPresenterImpl;
import com.bawei.demo.shoppingtrolley.shoppingaddress.ShoppingAdressActivity;
import com.bawei.demo.shoppingtrolley.utils.Apis;
import com.bawei.demo.shoppingtrolley.utils.MeansUtils;
import com.bawei.demo.shoppingtrolley.utils.RetrofitUtils;
import com.bawei.demo.shoppingtrolley.view.IView;
import com.bawei.demo.shoppingtrolley.wallet.WalletActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class MyFragment extends BaseFragment implements IView{
    @BindView(R.id.text_my_footprint)
    TextView textView_my_footprint;
    @BindView(R.id.text_my_shipping_address)
    TextView textView_my_shipping_address;
    @BindView(R.id.text_my_wallet)
    TextView textView_my_wallet;
    @BindView(R.id.text_my_circle)
    TextView textView_my_circle;
     @BindView(R.id.text_personal_information)
    TextView textView_personal_information;
    @BindView(R.id.text_username)
    TextView textView_username;
    @BindView(R.id.image_icon)
    SimpleDraweeView simpleDraweeView;
    private IPresenterImpl iPresenter;


    @Override
    protected int findViewId() {
        return R.layout.layout_home_my;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        iPresenter=new IPresenterImpl(this);
        iPresenter.startRequestGet(Apis.url_user_info,UserBean.class);


    }
    @OnClick({R.id.text_my_footprint,R.id.text_my_shipping_address,R.id.text_my_wallet,R.id.text_my_circle,R.id.text_personal_information,R.id.image_icon})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.text_my_footprint:
                Intent intent=new Intent(getActivity(), FootprintActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.text_my_shipping_address:
                Intent intent_address=new Intent(getActivity(), ShoppingAdressActivity.class);
                getActivity().startActivity(intent_address);
                break;

                case R.id.text_my_wallet:
                Intent intent_wallet=new Intent(getActivity(), WalletActivity.class);
                getActivity().startActivity(intent_wallet);
                break;

            case R.id.text_my_circle:
                Intent intent_circle=new Intent(getActivity(), CircleActivity.class);
                getActivity().startActivity(intent_circle);
                break;
            case R.id.text_personal_information:
                Intent intent_information=new Intent(getActivity(), PersonalActivity.class);
                getActivity().startActivity(intent_information);
                break;
            case R.id.image_icon:
              //  painter();//换头像
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        iPresenter.startRequestGet(Apis.url_user_info,UserBean.class);
    }

    @Override
    public void requestSuccess(Object data) {
        UserBean bean= (UserBean) data;
        String headPic = bean.getResult().getHeadPic();
        String nickName = bean.getResult().getNickName();
        simpleDraweeView.setImageURI(headPic);
        textView_username.setText(nickName);
    }

    @Override
    public void requestFail(String error) {
        MeansUtils.toast(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.detachView();

    }
}
