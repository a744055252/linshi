package com.linkces.linshi.ui.fragment;

import android.os.Bundle;

import com.linkces.linshi.R;
import com.linkces.linshi.base.RxLazyFragment;

public class MyFragment extends RxLazyFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my;
    }

    public static MyFragment newInstance(){
        return new MyFragment();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
