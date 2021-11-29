package com.example.airsick;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int[] tabIcons = {
            R.drawable.ic_baseline_home_24,
            R.drawable.ic_baseline_my_location_24,
            R.drawable.ic_baseline_list_24,
            R.drawable.ic_baseline_search_24
    };
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPageAdapter pagerAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        // Attach the ViewPager to the TabLayout
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    public class SectionsPageAdapter extends FragmentPagerAdapter{
        public SectionsPageAdapter(FragmentManager fm) { super(fm); }

        @Override
        public int getCount() { return 4; }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    return new HomeActivity();
                case 1:
                    return new MapActivity();
                case 2:
                    return new RankingsActivity();
                case 3:
                    return new WatchlistActivity();
            }
            return null;
        }
    }
}