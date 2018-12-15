package jp.ac.neec.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter_MyPage extends FragmentStatePagerAdapter {
    int numberOfTabs;

    public PagerAdapter_MyPage(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentMypageCode tab1 = new FragmentMypageCode();
                return tab1;
            case 1:
                FragmentMypageFav tab2 = new FragmentMypageFav();
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