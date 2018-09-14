package net.manicmachine.csather.jamfbuddy;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.manicmachine.csather.dao.impl.ComputerDaoImpl;
import net.manicmachine.csather.dao.impl.MobileDaoImpl;
import net.manicmachine.csather.dao.impl.UserDaoImpl;
import net.manicmachine.csather.network.JssApi;
import net.manicmachine.csather.network.VolleyCallback;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "net.manicmachine.csather.jamfbuddy.LoginActivity";

    EditText hostnameText;
    EditText portText;
    EditText usernameText;
    EditText passwordText;
    Button login;
    JssApi jssApi;
    UserInfo userInfo;
    Boolean isValid;
    ComputerDaoImpl computerDao;
    MobileDaoImpl mobileDao;
    UserDaoImpl userDao;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isValid = true;
        jssApi = JssApi.get();
        userInfo = UserInfo.get(this);
        App.setContext(this);

        computerDao = new ComputerDaoImpl(App.getContext());
        mobileDao = new MobileDaoImpl(App.getContext());
        userDao = new UserDaoImpl(App.getContext());

        hostnameText = (EditText)this.findViewById(R.id.jssHostname);
        portText = (EditText)this.findViewById(R.id.jssPort);
        usernameText = (EditText) this.findViewById(R.id.username);
        passwordText = (EditText) this.findViewById(R.id.password);
        login = (Button) this.findViewById(R.id.loginButton);



        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Set userInfo, populateRecords the jssApi object and attempt to authenticate with the JSS.
                // Once authenticated, switch to the RecordListActivity. Otherwise display an error
                // to the user.
                if (Util.isEmpty(usernameText)) {
                    isValid = false;
                    Util.displayError(App.getContext(), "Please provide a valid username.");
                }

                if (Util.isEmpty(passwordText)) {
                    isValid = false;
                    Util.displayError(App.getContext(),"Please provide a valid password.");
                }

                if (Util.isEmpty(hostnameText)) {
                    isValid = false;
                    Util.displayError(App.getContext(), "Please provide a valid hostname/IP address.");
                } else {
                    if(!hostnameText.getText().toString().toLowerCase().contains("https://")){
                        if(!hostnameText.getText().toString().toLowerCase().contains("http://")) {
                            String address = "https://" + hostnameText.getText().toString().toLowerCase();
                            hostnameText.setText(address);
                        } else {
                            hostnameText.setText(hostnameText.getText().toString().replace("http://", "https://"));
                        }

                    }
                }

                if (Util.isEmpty(portText)) {
                    isValid = false;
                    Util.displayError(App.getContext(),"Please provide a valid port.");
                }

                if (isValid) {
                    userInfo.setUsername(usernameText.getText().toString());
                    userInfo.setPassword(passwordText.getText().toString());
                    userInfo.setHostname(hostnameText.getText().toString());
                    userInfo.setPort(portText.getText().toString());

                    jssApi.setHostname(userInfo.getHostname());
                    jssApi.setPort(userInfo.getPort());
                    jssApi.setUsername(userInfo.getUsername());
                    jssApi.setPassword(userInfo.getPassword());

                    // TODO: PA-9, ProgressDialog is deprecated. Will need to update to a ProgressBar workflow.
                    progressDialog = ProgressDialog.show(App.getContext(), "", "Contacting Jamf Pro Server...", false);
                    jssApi.authenticate(new VolleyCallback() {

                        // Create a callback for Volley so that we can take an action based upon the
                        // response provided.

                        @Override
                        public void onSuccess(JSONObject response) {
                            progressDialog.dismiss();
                            jssApi.setConnected(true);
                            Intent intent = new Intent(getApplicationContext(), RecordListActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onError(int errorCode) {

                            progressDialog.dismiss();
                            jssApi.setConnected(false);

                            switch(errorCode) {
                                case JssApi.HTTP_UNAUTHORIZED:
                                    Util.displayError(App.getContext(),
                                            "Unauthorized error received from server. Verify your credentials.");
                                    break;
                                case JssApi.HTTP_FORBIDDEN:
                                    Util.displayError(App.getContext(),
                                            "Forbidden error received from server. Verify your credentials and privileges.");
                                    break;
                                case JssApi.HTTP_TIMEOUT:
                                    Util.displayError(App.getContext(),
                                            "The server request timed out. Verify your hostname/port and try again.");
                                    if(hasData()) {
                                        offlinePrompt();
                                    }
                                    break;
                                case JssApi.HTTP_INTERNAL_ERROR:
                                    Util.displayError(App.getContext(),
                                            "The server encountered an unknown internal error.");
                                    break;
                                case JssApi.HTTP_SERVICE_UNAVAIL:
                                    Util.displayError(App.getContext(),
                                            "Unknown host error encountered. Verify your internet connection.");
                                    if(hasData()) {
                                        offlinePrompt();
                                    }
                                    break;
                                default:
                                    break;
                            }

                        }
                    });
                }
            }
        });
    }

    public boolean hasData() {
        int computerRecords = computerDao.computerCount();
        int mobileRecords = mobileDao.mobileCount();
        int userRecords = userDao.userCount();

        if (computerRecords + mobileRecords + userRecords > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void offlinePrompt() {
        FragmentManager manager = getFragmentManager();
        OfflineModeFragment offlineModeFragment = new OfflineModeFragment();

        offlineModeFragment.show(manager, OfflineModeFragment.TAG);
    }
}
