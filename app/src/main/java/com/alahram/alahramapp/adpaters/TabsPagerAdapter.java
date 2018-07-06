package com.alahram.alahramapp.adpaters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alahram.alahramapp.fragments.Fragment1;
import com.alahram.alahramapp.fragments.Fragment2;
import com.alahram.alahramapp.fragments.Fragment3;
import com.alahram.alahramapp.fragments.Fragment4;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    int numberOfTabs;

    public TabsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
//            case 0:
//                return new Fragment4();
//            case 1:
//                return new Fragment3();
//            case 2:
//                return new Fragment2();
//            case 3:
//                return new Fragment1();

            case 0:
                return new Fragment4();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment1();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}