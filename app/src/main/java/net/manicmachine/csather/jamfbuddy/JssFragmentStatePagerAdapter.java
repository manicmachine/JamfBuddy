package net.manicmachine.csather.jamfbuddy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class JssFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    static final int NUM_ITEMS = 3;
    static final int COMPUTER_TAB = 0;
    static final int MOBILE_TAB = 1;
    static final int USER_TAB = 2;

    public JssFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case COMPUTER_TAB:
                fragment = new ComputerFragment();
                break;
            case MOBILE_TAB:
                fragment = new MobileFragment();
                break;
            case USER_TAB:
                fragment = new UserFragment();
                break;
            default:
                return null;
        }

        return fragment;
    }


    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
