package net.manicmachine.csather.jamfbuddy;


import android.content.Intent;
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

import net.manicmachine.csather.dao.impl.ComputerDaoImpl;
import net.manicmachine.csather.model.Computer;
import net.manicmachine.csather.network.JssApi;
import net.manicmachine.csather.network.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ComputerFragment extends Fragment {

    public final static String TAB_NAME = "computers";
    public final static String TAG = "net.manicmachine.csather.jamfbuddy.ComputerFragment";

    private RecyclerView computerRecyclerView;
    private ComputerAdapter computerAdapter;
    private ArrayList<Computer> computers;
    private ComputerDaoImpl computerDao;
    private UserInfo userInfo;
    private JssApi jssApi;

    public ComputerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfo = UserInfo.get(App.getContext());
        jssApi = JssApi.get();
        computerDao = new ComputerDaoImpl(App.getContext());

        if (jssApi.isConnected()) {
            jssApi.populateRecords(TAB_NAME, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        computers = new ArrayList<>();
                        JSONArray recordArray;

                        System.out.println("Response: " + response.toString());

                        recordArray = response.getJSONArray(TAB_NAME);

                        System.out.println("Json Array: " + recordArray.toString());

                        for (int i = 0; i < recordArray.length(); i++) {

                            JSONObject recordJson = recordArray.getJSONObject(i);
                            System.out.println(TAB_NAME + " Json #" + Integer.toString(i) + ": " + recordJson.toString());

                            HashMap<String, String> properties = new HashMap<>();

                            properties.put("id", recordJson.getString("id"));
                            properties.put("name", recordJson.getString("name"));

                            Computer newComputer = new Computer();
                            newComputer.setGeneralInfo(properties);
                            computers.add(newComputer);

                        }

                        for (Computer computer : computers) {
                            computerDao.addComputer(computer);
                        }

                        // Update UI
                        userInfo.setComputers(computers);
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
        View view = inflater.inflate(R.layout.fragment_computer, container, false);
        computerRecyclerView = (RecyclerView) view.findViewById(R.id.computerRecyclerView);
        computerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (!jssApi.isConnected()) {
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

        computers = userInfo.getComputers();

        computerAdapter = new ComputerAdapter(computers);
        computerRecyclerView.setAdapter(computerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class ComputerHolder extends RecyclerView.ViewHolder {

        private TextView computerIdText;
        private TextView computerNameText;
        private TextView recordNameLabel;
        private ImageView recordImage;
        private Computer computer;

        public ComputerHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.fragment_record, parent, false));

            ConstraintLayout recordView = (ConstraintLayout) itemView.findViewById(R.id.recordWrapper);
            recordImage = (ImageView) itemView.findViewById(R.id.recordIcon);
            recordNameLabel = (TextView) itemView.findViewById(R.id.recordNameLabel);

            computerIdText = (TextView) itemView.findViewById(R.id.recordId);
            computerNameText = (TextView) itemView.findViewById(R.id.recordName);

            //TODO: PA-6, Switch to a more detailed view when pressed.
            recordView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OnClickListener", "Computer name: " + computerNameText.getText().toString());

                    Intent intent = new Intent(getContext(), DetailsActivity.class);
                    intent.putExtra("type", "computer");
                    intent.putExtra("recordId", Integer.parseInt(computerIdText.getText().toString()));

                    startActivity(intent);
                }
            });
        }

        public void bind(Computer computer) {

            this.computer = computer;

            recordNameLabel.setText("Computer Name");
            computerIdText.setText(computer.getGeneralInfo().get("id"));
            computerNameText.setText(computer.getGeneralInfo().get("name"));

            if (computerNameText.getText().toString().toLowerCase().replace(" ", "").contains("macmini")) {
                recordImage.setImageResource(R.mipmap.macmini);
            }

        }

    }

    class ComputerAdapter extends RecyclerView.Adapter<ComputerHolder> {

        private ArrayList<Computer> computers;

        public ComputerAdapter(ArrayList<Computer> computers) {
            this.computers = computers;
        }

        @Override
        public ComputerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ComputerHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ComputerHolder holder, int position) {
            Computer computer = computers.get(position);

            holder.bind(computer);
        }

        @Override
        public int getItemCount() {
            return computers.size();
        }
    }

}
