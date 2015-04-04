package com.frontier.giphy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.frontier.giphy.utils.CircularViewPagerHandler;
import com.frontier.giphy.utils.MyFragmentPagerAdapter;

import java.util.List;

public class PagerActivity extends FragmentActivity {

    private MyFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private CircularViewPagerHandler listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager);

        List<String> stringList = getIntent().getStringArrayListExtra("urls");
        int position = getIntent().getIntExtra("position", 0);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), stringList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position);
        listener = new CircularViewPagerHandler(viewPager);
        viewPager.setOnPageChangeListener(listener);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PagerActivity.this, MainActivity.class);
        int tmp = listener.getCurrentPosition();
        intent.putExtra("position", tmp);
        setResult(RESULT_OK, intent);
        finish();

    }
}
