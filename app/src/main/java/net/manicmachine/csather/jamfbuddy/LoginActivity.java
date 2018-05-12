package net.manicmachine.csather.jamfbuddy;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;

import net.manicmachine.csather.network.JssApi;
import net.manicmachine.csather.network.VolleyCallback;

import org.json.JSONObject;

import java.io.Serializable;
import java.net.HttpURLConnection;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "net.manicmachine.csather.LoginActivity";

    EditText hostnameText;
    EditText portText;
    EditText usernameText;
    EditText passwordText;
    Button login;
    JssApi jssApi;
    Bundle userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        jssApi = new JssApi();

        App.setContext(this);

        userInfo = new Bundle();

        hostnameText = (EditText)this.findViewById(R.id.jssHostname);
        portText = (EditText)this.findViewById(R.id.jssPort);
        usernameText = (EditText) this.findViewById(R.id.username);
        passwordText = (EditText) this.findViewById(R.id.password);
        login = (Button) this.findViewById(R.id.loginButton);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO: Add validation to the entry fields

                // Set userInfo, populate the jssApi object and attempt to authenticate with the JSS.
                // Once authenticated, switch to the DeviceListActivity. Otherwise display an error
                // to the user.
                userInfo.putString(getString(R.string.username), !Util.isEmpty(usernameText) ? usernameText.getText().toString() : null);
                userInfo.putString(getString(R.string.password), !Util.isEmpty(passwordText) ? passwordText.getText().toString() : null);
                userInfo.putString("hostname", !Util.isEmpty(hostnameText) ? hostnameText.getText().toString() : null);
                userInfo.putString("port", !Util.isEmpty(portText) ? portText.getText().toString() : null);

                jssApi.setHostname(userInfo.getString("hostname"));
                jssApi.setPort(userInfo.getString("port"));
                jssApi.setUsername(userInfo.getString(getString(R.string.username)));
                jssApi.setPassword(userInfo.getString(getString(R.string.password)));

                Log.d(TAG, "onClick:" + jssApi.getHostname() + ":" + jssApi.getPort());

                jssApi.authenticate(new VolleyCallback() {

                    // Create a callback for Volley so that we can take an action based upon the
                    // response provided.

                    @Override
                    public void onSuccess(JSONObject response) {
                        switch (jssApi.getStatusCode()) {
                            case HttpURLConnection.HTTP_OK:
                                Intent intent = new Intent(getApplicationContext(), DeviceListActivity.class);
                                intent.putExtra("userInfo", userInfo);
                                startActivity(intent);
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
                    }
                });
            }
        });
    }
}
