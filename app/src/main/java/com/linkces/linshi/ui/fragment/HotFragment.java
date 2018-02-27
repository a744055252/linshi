package com.linkces.linshi.ui.fragment;

import android.os.Bundle;

import com.linkces.linshi.R;
import com.linkces.linshi.base.RxLazyFragment;

public class HotFragment extends RxLazyFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_hot;
    }

    public static HotFragment newInstance(){
        return new HotFragment();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
