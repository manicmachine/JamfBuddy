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

import net.manicmachine.csather.dao.impl.UserDaoImpl;
import net.manicmachine.csather.model.User;
import net.manicmachine.csather.network.JssApi;
import net.manicmachine.csather.network.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class UserFragment extends Fragment {

    public final static String TAB_NAME = "users";
    public final static String TAG = "net.manicmachine.csather.UserFragment";

    private RecyclerView userRecyclerView;
    private UserFragment.UserAdapter userAdapter;
    private ArrayList<User> users;
    private UserDaoImpl userDao;
    private UserInfo userInfo;
    private JssApi jssApi;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfo = UserInfo.get(getActivity());
        jssApi = JssApi.get();
        userDao = new UserDaoImpl(App.getContext());

        if (jssApi.isConnected()) {
            jssApi.populate(TAB_NAME, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        users = new ArrayList<>();
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

                            User newUser = new User();
                            newUser.setId(Integer.parseInt(properties.get("id")));
                            newUser.setFullName(properties.get("name").toString());
                            users.add(newUser);

                            for (User user : users){
                                userDao.addUser(user);
                            }

                        }

                        userInfo.setUsers(users);

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
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        userRecyclerView = (RecyclerView) view.findViewById(R.id.userRecyclerView);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(!jssApi.isConnected()) {
            users = userInfo.getUsers();
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

        users = userInfo.getUsers();

        userAdapter = new UserFragment.UserAdapter(users);
        userRecyclerView.setAdapter(userAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class UserHolder extends RecyclerView.ViewHolder {

        private TextView userIdText;
        private TextView userNameText;
        private TextView recordNameLabel;
        private ImageView recordImage;
        private User user;

        public UserHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.fragment_record, parent, false));

            ConstraintLayout recordView = (ConstraintLayout) itemView.findViewById(R.id.recordWrapper);
            recordNameLabel = (TextView) itemView.findViewById(R.id.recordNameLabel);

            userIdText = (TextView) itemView.findViewById(R.id.recordId);
            userNameText = (TextView) itemView.findViewById(R.id.recordName);
            recordImage = (ImageView) itemView.findViewById(R.id.recordIcon);

            recordView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("OnClickListener", "User name: " + userNameText.getText().toString());
                }
            });


        }

        public void bind(User user) {

            this.user = user;

            recordNameLabel.setText("User Name");
            userIdText.setText(Integer.toString(user.getId()));
            userNameText.setText(user.getFullName());

            recordImage.setImageResource(R.mipmap.user);

        }

    }

    class UserAdapter extends RecyclerView.Adapter<UserFragment.UserHolder> {

        private ArrayList<User> users;

        public UserAdapter(ArrayList<User> users) {
            this.users = users;
        }

        @Override
        public UserFragment.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new UserFragment.UserHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(UserFragment.UserHolder holder, int position) {
            User user = users.get(position);
            holder.bind(user);

        }

        @Override
        public int getItemCount() {
            return users.size();
        }
    }
}
