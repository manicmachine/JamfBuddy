package net.manicmachine.csather.jamfbuddy;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import net.manicmachine.csather.model.Computer;
import net.manicmachine.csather.model.MobileDevice;
import net.manicmachine.csather.model.User;
import net.manicmachine.csather.network.JssApi;
import net.manicmachine.csather.network.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;

public class DeviceListActivity extends AppCompatActivity {

    JssApi jssApi;
    Bundle userInfo;
    Computer[] computers;
    MobileDevice[] mobileDevices;
    User[] users;

    public final static String TAG = "net.manicmachine.csather.DeviceListActivity";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        userInfo = getIntent().getExtras().getBundle("userInfo");

        jssApi = new JssApi();

        jssApi.setHostname(userInfo.getString("hostname"));
        jssApi.setPort(userInfo.getString("port"));
        jssApi.setUsername(userInfo.getString(getString(R.string.username)));
        jssApi.setPassword(userInfo.getString(getString(R.string.password)));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //TODO: Determine what tab is currently selected and pass it to populate().
            jssApi.populate("computers", new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    switch (jssApi.getStatusCode()) {
                        case HttpURLConnection.HTTP_OK:
                            try {
                                System.out.println("Response: " + response.toString());
                                JSONArray computerArray = response.getJSONArray("computers");
                                System.out.println("Json Array: " + computerArray.toString());
                                computers = new Computer[computerArray.length()];
                                System.out.println("Computer array length:" + Integer.toString(computers.length));

                                for(int i = 0; i < computerArray.length(); i++) {

                                    JSONObject computerJson = computerArray.getJSONObject(i);
                                    System.out.println("Computer Json #" + Integer.toString(i) + ": " + computerJson.toString());
                                    Computer newComputer = new Computer();
                                    HashMap<String, String> properties = new HashMap<>();
                                    Iterator<?> values = computerJson.keys();

                                    while(values.hasNext()) {

                                        String value = (String) values.next();
                                        properties.put(value, computerJson.get(value).toString());

                                        System.out.println("Json key: " + value + ", Property: " + properties.get(value).toString());

                                    }

                                    newComputer.setGeneralInfo(properties);
                                    computers[i] = newComputer;

                                }
                            } catch (JSONException ex) {
                                Log.d(TAG, "Error: Failed to unpack JSON response. " + ex.getMessage());
                            }
                             break;
                        case HttpURLConnection.HTTP_FORBIDDEN:
                            //TODO: Setup toast to notify the user their server rejected the login: HTTP 403
                            break;
                        case HttpURLConnection.HTTP_UNAUTHORIZED:
                            //TODO: Setup toast to notify the user their credentials were incorrect: HTTP 401
                            break;
                        case HttpURLConnection.HTTP_INTERNAL_ERROR:
                            //TODO: Setup toast to notify the user their JSS encountered an error: HTTP 500
                            break;
                }
            }});

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_computer_list, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
