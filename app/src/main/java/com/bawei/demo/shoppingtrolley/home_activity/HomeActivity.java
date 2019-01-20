package com.bawei.demo.shoppingtrolley.home_activity;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.bawei.demo.shoppingtrolley.R;
import com.bawei.demo.shoppingtrolley.customview.CustomViewPager;
import com.bawei.demo.shoppingtrolley.home_fragment.BaseFragment;
import com.bawei.demo.shoppingtrolley.home_fragment.CircleFragment;
import com.bawei.demo.shoppingtrolley.home_fragment.MyFragment;
import com.bawei.demo.shoppingtrolley.home_fragment.OrderFragment;
import com.bawei.demo.shoppingtrolley.home_fragment.PageFragment;
import com.bawei.demo.shoppingtrolley.shoppingcar.ShoppingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    //获取资源ID
    @BindView(R.id.home_viewpager)
    CustomViewPager viewPager;
    @BindView(R.id.home_group)
    RadioGroup radioGroup;
    private List<BaseFragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        //实例化List
        list=new ArrayList<>();
        //添加
        list.add(new PageFragment());
        list.add(new CircleFragment());
        list.add(new ShoppingFragment());
        list.add(new OrderFragment());
        list.add(new MyFragment());

         //创建适配器
         createAdapter();

        //滑动可以选中对应的按钮
          checkButton();


         //点击切换 Fragment
         changeFragment();

    }

    private void changeFragment() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.home_page:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.home_circle:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.home_shopping_cart:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.home_order:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.home_my:
                        viewPager.setCurrentItem(4);
                        break;
                }
            }
        });
    }

    private void checkButton() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        radioGroup.check(R.id.home_page);
                        break;
                    case 1:
                        radioGroup.check(R.id.home_circle);
                        break;
                    case 2:
                        radioGroup.check(R.id.home_shopping_cart);
                        break;
                    case 3:
                        radioGroup.check(R.id.home_order);
                        break;
                    case 4:
                        radioGroup.check(R.id.home_my);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void createAdapter() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }

}
