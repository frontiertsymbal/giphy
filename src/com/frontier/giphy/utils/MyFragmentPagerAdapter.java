package com.frontier.giphy.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.frontier.giphy.PageFragment;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> urls = new ArrayList<String>();

    public MyFragmentPagerAdapter(FragmentManager fm, List<String> urls) {
        super(fm);
        this.urls.clear();
        this.urls.addAll(urls);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putString("url", urls.get(position));
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

}
