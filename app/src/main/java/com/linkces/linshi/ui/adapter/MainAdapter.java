package com.linkces.linshi.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.linkces.linshi.App;
import com.linkces.linshi.R;
import com.linkces.linshi.ui.fragment.HotFragment;
import com.linkces.linshi.ui.fragment.MainFragment;
import com.linkces.linshi.ui.fragment.MyFragment;
import com.linkces.linshi.ui.fragment.WorkFragment;

/**
 * Created by guanhuan_li on 2018/1/22.
 */

public class MainAdapter extends FragmentPagerAdapter {

    private final String[] TITLES;
    private Fragment[] fragments;

    public MainAdapter(FragmentManager fm) {
        super(fm);
        TITLES = App.app.getResources().getStringArray(R.array.sections);
        fragments = new Fragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments[position] == null){
            switch (position){
                case 0 :
                    fragments[position] = MainFragment.newInstance();
                    break;
                case 1 :
                    fragments[position] = WorkFragment.newInstance();
                    break;
                case 2 :
                    fragments[position] = HotFragment.newInstance();
                    break;
                case 3 :
                    fragments[position] = MyFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
