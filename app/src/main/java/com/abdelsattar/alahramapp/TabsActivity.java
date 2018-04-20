package com.abdelsattar.alahramapp;



import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.abdelsattar.alahramapp.MyNotificationHelper.Config;
import com.abdelsattar.alahramapp.MyNotificationHelper.NotificationUtils;
import com.abdelsattar.alahramapp.adpaters.TabsPagerAdapter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class TabsActivity extends AppCompatActivity {


    private static final String TAG = SearchForOrderActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.rectangle_804_copy_2));
//        tabLayout.addTab(tabLayout.newTab().setText("ج"));
        tabLayout.addTab(tabLayout.newTab().setText("معاينة و تواصل"));
        tabLayout.addTab(tabLayout.newTab().setText("متابعة الطلب"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
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
        viewPager.setCurrentItem(viewPager.getAdapter().getCount()-1);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        if(regId == null) {
            String token = FirebaseInstanceId.getInstance().getToken();
            storeRegIdInPref(token);
        }





    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();

        //Path Request Id with token to API
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


}
