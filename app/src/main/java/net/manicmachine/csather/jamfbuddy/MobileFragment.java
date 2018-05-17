package net.manicmachine.csather.jamfbuddy;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.manicmachine.csather.dao.impl.MobileDaoImpl;
import net.manicmachine.csather.model.MobileDevice;
import net.manicmachine.csather.network.JssApi;
import net.manicmachine.csather.network.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class MobileFragment extends Fragment {

    public final static String TAB_NAME = "mobiledevices";
    public final static String TAG = "net.manicmachine.csather.MobileFragment";

    private RecyclerView mobileRecyclerView;
    private MobileFragment.MobileAdapter mobileAdapter;
    private ArrayList<MobileDevice> mobileDevices;
    private MobileDaoImpl mobileDao;
    private UserInfo userInfo;
    private JssApi jssApi;

    public MobileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfo = UserInfo.get(getActivity());
        jssApi = JssApi.get();
        mobileDao = new MobileDaoImpl(App.getContext());

        if (jssApi.isConnected()) {
            jssApi.populate(TAB_NAME, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        mobileDevices = new ArrayList<>();
                        JSONArray recordArray;

                        System.out.println("Response: " + response.toString());

                        // The JSS doesn't label mobile devices consistently, so using a literal here
                        // instead of referencing TAB_NAME.
                        recordArray = response.getJSONArray("mobile_devices");

                        System.out.println("Json Array: " + recordArray.toString());

                        for (int i = 0; i < recordArray.length(); i++) {

                            JSONObject recordJson = recordArray.getJSONObject(i);
                            System.out.println(TAB_NAME + " Json #" + Integer.toString(i) + ": " + recordJson.toString());

                            HashMap<String, String> properties = new HashMap<>();

                            properties.put("id", recordJson.getString("id"));
                            properties.put("name", recordJson.getString("name"));

                            MobileDevice newMobileDevice = new MobileDevice();
                            newMobileDevice.setGeneralInfo(properties);
                            mobileDevices.add(newMobileDevice);

                            for (MobileDevice mobileDevice : mobileDevices) {
                                mobileDao.addMobileDevice(mobileDevice);
                            }

                        }

                        userInfo.setMobileDevices(mobileDevices);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUI();
                            }
                        });


                    } catch (JSONException ex) {
                        Log.d(TAG, "Error: Failed to unpack JSON response. " + ex.getMessage());
                    }
                }

                @Override
                public void onError(int errorCode) {
                    Log.d(TAG, "Network error encountered: " + Integer.toString(errorCode));
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobile, container, false);
        mobileRecyclerView = (RecyclerView) view.findViewById(R.id.mobileRecyclerView);
        mobileRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (!jssApi.isConnected()) {
            mobileDevices = userInfo.getMobileDevices();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateUI();
                }
            });
        }

        return view;
    }

    public void updateUI() {

        mobileDevices = userInfo.getMobileDevices();

        mobileAdapter = new MobileFragment.MobileAdapter(mobileDevices);
        mobileRecyclerView.setAdapter(mobileAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class MobileHolder extends RecyclerView.ViewHolder {

        private TextView mobileIdText;
        private TextView mobileNameText;
        private TextView recordNameLabel;
        private MobileDevice mobileDevice;
        private ImageView recordImage;

        public MobileHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.fragment_record, parent, false));

            ConstraintLayout recordView = (ConstraintLayout) itemView.findViewById(R.id.recordWrapper);
            recordImage = (ImageView) itemView.findViewById(R.id.recordIcon);
            recordNameLabel = (TextView) itemView.findViewById(R.id.recordNameLabel);

            mobileIdText = (TextView) itemView.findViewById(R.id.recordId);
            mobileNameText = (TextView) itemView.findViewById(R.id.recordName);

            recordView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OnClickListener", "Mobile name: " + mobileNameText.getText().toString());
                }
            });
        }

        public void bind(MobileDevice mobileDevice) {

            this.mobileDevice = mobileDevice;

            recordNameLabel.setText("Mobile Device Name");
            mobileIdText.setText(mobileDevice.getGeneralInfo().get("id"));
            mobileNameText.setText(mobileDevice.getGeneralInfo().get("name"));

            String formattedName = mobileNameText.getText().toString().toLowerCase().replace(" ", "");

            if (formattedName.contains("ipad")) {
                recordImage.setImageResource(R.mipmap.ipad);
            } else {
                recordImage.setImageResource(R.mipmap.iphone);
            }

        }

    }

    class MobileAdapter extends RecyclerView.Adapter<MobileFragment.MobileHolder> {

        private ArrayList<MobileDevice> mobileDevices;

        public MobileAdapter(ArrayList<MobileDevice> mobileDevices) {
            this.mobileDevices = mobileDevices;
        }

        @Override
        public MobileFragment.MobileHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new MobileFragment.MobileHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(MobileFragment.MobileHolder holder, int position) {
            MobileDevice mobileDevice = mobileDevices.get(position);
            holder.bind(mobileDevice);

        }

        @Override
        public int getItemCount() {
            return mobileDevices.size();
        }
    }
}
