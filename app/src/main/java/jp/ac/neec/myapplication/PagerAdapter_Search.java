package jp.ac.neec.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter_Search extends FragmentStatePagerAdapter {
    int numberOfTabs;

    public PagerAdapter_Search(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentSearchMen tab1 = new FragmentSearchMen();
                return tab1;
            case 1:
                FragmentSearchWomen tab2 = new FragmentSearchWomen();
                return tab2;
            case 2:
                FragmentSearchKids tab3 = new FragmentSearchKids();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}