package com.example.mamanguo.chooseServices.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mamanguo.R;
import com.example.mamanguo.chooseServices.serviceCategories.AccessoriesFragment;
import com.example.mamanguo.chooseServices.serviceCategories.BottomsFragment;
import com.example.mamanguo.chooseServices.serviceCategories.FullBodyFragment;
import com.example.mamanguo.chooseServices.serviceCategories.ShoesFragment;
import com.example.mamanguo.chooseServices.serviceCategories.TopsFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2,
            R.string.tab_text_3, R.string.tab_text_4, R.string.tab_text_5};
    private final Context mContext;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new TopsFragment();
                break;
            case 1:
                fragment = new BottomsFragment();
                break;
            case 2:
                fragment = new FullBodyFragment();
                break;
            case 3:
                fragment = new ShoesFragment();
                break;
            case 4:
                fragment = new AccessoriesFragment();
                break;
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}