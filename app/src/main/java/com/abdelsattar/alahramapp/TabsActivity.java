package com.abdelsattar.alahramapp;



import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.abdelsattar.alahramapp.adpaters.TabsPagerAdapter;

public class TabsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("A"));
        tabLayout.addTab(tabLayout.newTab().setText("B"));
        tabLayout.addTab(tabLayout.newTab().setText("C"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_action_add));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager =  (ViewPager)findViewById(R.id.container);

        final TabsPagerAdapter adapter = new TabsPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });



    }


}
