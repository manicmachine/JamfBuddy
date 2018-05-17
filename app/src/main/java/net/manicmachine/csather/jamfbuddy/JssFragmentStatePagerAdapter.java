package net.manicmachine.csather.jamfbuddy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

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

        System.out.println("Tab position:" + Integer.toString(position));
        Fragment fragment = null;
        switch (position) {
            case COMPUTER_TAB:
                fragment = new ComputerFragment();
                System.out.println("Computer_Tab selected; creating computer fragment: " + fragment.toString());
                break;
            case MOBILE_TAB:
                fragment = new MobileFragment();
                System.out.println("Mobile_Tab selected; creating mobile fragment: " + fragment.toString());
                break;
            case USER_TAB:
                fragment = new UserFragment();
                System.out.println("User_Tab selected; creating user fragment: " + fragment.toString());
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
