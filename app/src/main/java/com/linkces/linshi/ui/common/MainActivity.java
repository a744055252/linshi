package com.linkces.linshi.ui.common;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.linkces.linshi.R;
import com.linkces.linshi.base.RxBaseActivity;
import com.linkces.linshi.ui.adapter.MainAdapter;

import butterknife.BindView;

/**
 * Created by guanhuan_li on 2018/1/22.
 */

public class MainActivity extends RxBaseActivity {

    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;

    @BindView(R.id.main_item)
    TabLayout mainItem;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initViewPage();
    }

    private void initViewPage() {
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        mainViewpager.setOffscreenPageLimit(4);
        mainViewpager.setAdapter(mainAdapter);
        mainItem.setupWithViewPager(mainViewpager);
        mainViewpager.setCurrentItem(0);
    }

    @Override
    public void initToolBar() {

    }

}
