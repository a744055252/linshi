package com.linkces.linshi.ui.fragment;

import android.os.Bundle;

import com.linkces.linshi.R;
import com.linkces.linshi.base.RxLazyFragment;

public class WorkFragment extends RxLazyFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_work;
    }

    public static WorkFragment newInstance(){
        return new WorkFragment();
    }

    @Override
    public void finishCreateView(Bundle state) {

    }
}
