package jp.ac.neec.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter_OtherPage extends FragmentStatePagerAdapter {
    int numberOfTabs;

    public PagerAdapter_OtherPage(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentOtherCode tab1 = new FragmentOtherCode();
                return tab1;
            case 1:
                FragmentOtherFav tab2 = new FragmentOtherFav();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}