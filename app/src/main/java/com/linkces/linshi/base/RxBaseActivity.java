package com.linkces.linshi.base;

import android.os.Bundle;

import com.linkces.linshi.App;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity基类
 */
public abstract class RxBaseActivity extends RxAppCompatActivity {
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        //统一管理activity
        App.app.addActivity(this);
    }


    /**
     * 设置布局layout
     *
     * @return int layout的id
     */
    public abstract int getLayoutId();

    /**
     * 初始化views
     *
     * @param savedInstanceState savedInstanceState
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 初始化toolbar
     */
    public abstract void initToolBar();

    /**
     * 加载数据
     */
    public void loadData() {
    }

    /**
     * 显示进度条
     */
    public void showProgressBar() {
    }

    /**
     * 隐藏进度条
     */
    public void hideProgressBar() {
    }

    /**
     * 初始化recyclerView
     */
    public void initRecyclerView() {
    }

    /**
     * 初始化refreshLayout
     */
    public void initRefreshLayout() {
    }

    /**
     * 设置数据显示
     */
    public void finishTask() {
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        App.app.deleteActivity(this);
        super.onDestroy();
    }
}
