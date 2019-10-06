package com.example.tablayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.tablayout.Adapter.ViewPagerAdapter;
import com.example.tablayout.Fragment.BetawiFragment;
import com.example.tablayout.Fragment.IndoFragment;
import com.google.android.material.tabs.TabLayout;

public class KamusActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);

        toolbar = (Toolbar)findViewById(R.id.myToolbar);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.myViewPager);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new IndoFragment(), "INDO-BETAWI");
        viewPagerAdapter.addFragment(new BetawiFragment(), "BETAWI-INDO");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
