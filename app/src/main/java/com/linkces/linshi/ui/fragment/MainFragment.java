package com.linkces.linshi.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linkces.linshi.R;
import com.linkces.linshi.base.RxLazyFragment;
import com.linkces.linshi.ui.adapter.section.HomeRecommendBannerSection;
import com.linkces.linshi.ui.widget.sectioned.SectionedRecyclerViewAdapter;
import com.linkces.linshi.ui.widget.sectioned.banner.BannerEntity;
import com.linkces.linshi.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends RxLazyFragment {

//    @BindView(R.id.recycle)
    RecyclerView recycle;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private SectionedRecyclerViewAdapter mSectionedAdapter;
    private List<BannerEntity> banners = new ArrayList<>();

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
//        if (!isPrepared || !isVisible) {
//            return;
//        }
        banners.add(new BannerEntity("www.baidu.com","http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/121233171gz9.jpg","1"));
        banners.add(new BannerEntity("www.baidu.com","http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/121233171gz9.jpg","1"));
        banners.add(new BannerEntity("www.baidu.com","http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/121233171gz9.jpg","1"));
        banners.add(new BannerEntity("www.baidu.com","http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201506/121233171gz9.jpg","1"));
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initRecyclerView() {
        mSectionedAdapter = new SectionedRecyclerViewAdapter();
        mSectionedAdapter.addSection(new HomeRecommendBannerSection(banners));
        recycle = $(R.id.recycle);
        recycle.setAdapter(mSectionedAdapter);
//        recycle.setVisibility(View.VISIBLE);
//        mSectionedAdapter.notifyDataSetChanged();
    }
}
