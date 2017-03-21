package com.engineer.reader.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.engineer.reader.R;
import com.engineer.reader.base.BaseActivity;
import com.engineer.reader.fragments.DoubanFragment;
import com.engineer.reader.fragments.GankFragment;

public class IndexActivity extends BaseActivity {


    private FragmentManager mFragmentManager;
    private String currentFragmentTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragmentManager = getSupportFragmentManager();
        switchFragment("index");
    }

    public void switchFragment(String name) {
        if (currentFragmentTag != null && currentFragmentTag.equals(name))
            return;

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        Fragment currentFragment = mFragmentManager.findFragmentByTag(currentFragmentTag);
        if (currentFragment != null) {
            ft.hide(currentFragment);
        }

        Fragment foundFragment = mFragmentManager.findFragmentByTag(name);

        if (foundFragment == null) {
            switch (name) {
                case "index":
                    foundFragment = new GankFragment();
                    break;
                case "douban":
                    foundFragment = DoubanFragment.newInstance(name, name);
                    break;
                case "center":

                    break;
            }


        }

        if (foundFragment == null) {

        } else if (foundFragment.isAdded()) {
            ft.show(foundFragment);
        } else {
            ft.add(R.id.content, foundFragment, name);
        }
        ft.commit();
        currentFragmentTag = name;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment("index");
                    return true;
                case R.id.navigation_wixin:
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment("douban");
                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }

    };


}
