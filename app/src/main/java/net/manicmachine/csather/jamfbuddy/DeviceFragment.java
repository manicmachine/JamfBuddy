package net.manicmachine.csather.jamfbuddy;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DeviceFragment extends ListFragment {

    TextView deviceNameText;
    TextView deviceIdText;

    public DeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device, container, false);
        TextView deviceNameText = (TextView) view.findViewById(R.id.deviceName);
        TextView deviceIdText = (TextView) view.findViewById(R.id.deviceId);

//        deviceNameText.setText(//something);
        return view;
    }

}
