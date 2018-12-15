package jp.ac.neec.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter_Timeline extends FragmentStatePagerAdapter {
    int numberOfTabs;

    public PagerAdapter_Timeline(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentTimelineAll tab1 = new FragmentTimelineAll();
                return tab1;
            case 1:
                FragmentTimelineMen tab2 = new FragmentTimelineMen();
                return tab2;
            case 2:
                FragmentTimelineWomen tab3 = new FragmentTimelineWomen();
                return tab3;
            case 3:
                FragmentTimelineKids tab4 = new FragmentTimelineKids();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}