package com.abdelsattar.alahramapp.adpaters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.abdelsattar.alahramapp.fragments.Fragment1;
import com.abdelsattar.alahramapp.fragments.Fragment2;
import com.abdelsattar.alahramapp.fragments.Fragment3;
import com.abdelsattar.alahramapp.fragments.Fragment4;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    int numberOfTabs;

    public TabsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numberOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            case 3:
                return new Fragment4();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}