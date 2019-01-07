package com.bawei.demo.shoppingtrolley.home_fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.activity.FootprintActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyFragment extends BaseFragment {
    @BindView(R.id.text_my_footprint)
    TextView textView_my_footprint;
    @Override
    protected int findViewId() {
        return R.layout.layout_home_my;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);

    }
    @OnClick({R.id.text_my_footprint})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.text_my_footprint:
                Intent intent=new Intent(getActivity(), FootprintActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
