package com.bawei.demo.shoppingtrolley.home_fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.allordersfragment.AllOrdersFragment;
import com.bawei.demo.shoppingtrolley.complete.CompleteFragment;
import com.bawei.demo.shoppingtrolley.evaluate.EvaluateFragment;
import com.bawei.demo.shoppingtrolley.payment.PaymentFragment;
import com.bawei.demo.shoppingtrolley.receiving.ReceivingFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    String[] strings = new String[]{
            "全部订单","待付款","待收货","待评价","已完成"
    };

  public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings[position];
    }

    @Override
    public Fragment getItem(int i) {

        switch (i)
        {
            case 0:
                return new AllOrdersFragment();
            case 1:
                return new PaymentFragment();
            case 2:
                return new ReceivingFragment();
            case 3:
               return new EvaluateFragment();
            case 4:
                return new CompleteFragment();
                default:
                    return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return strings.length;
    }



}
