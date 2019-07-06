package com.example.androidauthpostgresqlnodejs.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.androidauthpostgresqlnodejs.R;
import com.example.androidauthpostgresqlnodejs.Tab1Fragment;
import com.example.androidauthpostgresqlnodejs.Tab2Fragment;
import com.example.androidauthpostgresqlnodejs.Tab3Fragment;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position) {
            case 0:
//                fragment = Fragment.instantiate(mContext,"Tab 1");
//                break;
                return new Tab1Fragment();
            case 1:
//                fragment = Fragment.instantiate(mContext, "Tab 2");
//                break;
                return new Tab2Fragment();
            case 2:
//                fragment = Fragment.instantiate(mContext, "Tab 3");
//                break;
                return new Tab3Fragment();
            default:
//                fragment = Fragment.instantiate(mContext, "None");
//                break;
                return null;
        }
//        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}