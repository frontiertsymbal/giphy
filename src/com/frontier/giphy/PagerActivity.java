package com.frontier.giphy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.frontier.giphy.utils.MyFragmentPagerAdapter;

import java.util.List;

public class PagerActivity extends FragmentActivity {

    private MyFragmentPagerAdapter pagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager);

        List<String> stringList = getIntent().getStringArrayListExtra("urls");
        int position = getIntent().getIntExtra("position", 0);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), stringList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position);

    }
}
