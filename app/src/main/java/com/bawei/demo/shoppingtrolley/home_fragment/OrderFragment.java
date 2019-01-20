package com.bawei.demo.shoppingtrolley.home_fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.allordersfragment.AllOrdersFragment;
import com.bawei.demo.shoppingtrolley.complete.CompleteFragment;
import com.bawei.demo.shoppingtrolley.evaluate.EvaluateFragment;
import com.bawei.demo.shoppingtrolley.payment.PaymentFragment;
import com.bawei.demo.shoppingtrolley.receiving.ReceivingFragment;
import com.bawei.demo.shoppingtrolley.shoppingcar.ShoppingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderFragment extends BaseFragment {
    @BindView(R.id.order_viewpager)
    ViewPager viewPager_order;
   @BindView(R.id.bill_TabLayout)
    TabLayout tabLayout;
   ViewPagerAdapter viewPagerAdapter;
    @Override
    protected int findViewId() {
        return R.layout.layout_home_order;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
       viewPager_order.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager_order);


    }


}
