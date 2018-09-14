package net.manicmachine.csather.jamfbuddy;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.manicmachine.csather.dao.impl.*;
import net.manicmachine.csather.model.Computer;
import net.manicmachine.csather.model.MobileDevice;
import net.manicmachine.csather.model.User;
import net.manicmachine.csather.network.JssApi;

import java.util.ArrayList;

public class RecordListActivity extends AppCompatActivity {

    public final static String TAG = "net.manicmachine.csather.jamfbuddy.RecordListActivity";

    private JssFragmentStatePagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private RecyclerView.Adapter deviceAdapter;

    JssApi jssApi;
    UserInfo userInfo;
    ArrayList<Computer> computers;
    ArrayList<MobileDevice> mobileDevices;
    ArrayList<User> users;
    ComputerDaoImpl computerDao;
    MobileDaoImpl mobileDao;
    UserDaoImpl userDao;

    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        computerDao = new ComputerDaoImpl(App.getContext());
        mobileDao = new MobileDaoImpl(App.getContext());
        userDao = new UserDaoImpl(App.getContext());

        userInfo = UserInfo.get(App.getContext());

        jssApi = JssApi.get();

        if (jssApi.isConnected()) {
            computers = new ArrayList<>();
            mobileDevices = new ArrayList<>();
            users = new ArrayList<>();
        }

        // Configure adapters, pagers and fragment managers.
        mPagerAdapter = new JssFragmentStatePagerAdapter(getSupportFragmentManager());
        fragmentManager = getSupportFragmentManager();
        mViewPager = (ViewPager)findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);

        // Create and populateRecords the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Listeners for when the user swipes between tabs, or taps a tab.
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    //TODO: PA-11, Add refresh button to actionbar for on demand syncing.
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_device_list, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {}
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
