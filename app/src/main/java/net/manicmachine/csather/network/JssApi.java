package net.manicmachine.csather.network;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JssApi {

    private static final String TAG = "JssApi";

    private String hostname;
    private String port;
    private String username;
    private String password;
    private int statusCode;

    public RequestQueue queue;

    //TODO: Add logic to resend the request if the JSS responds incorrectly (getting XML instead of JSON)

    public void authenticate(final VolleyCallback callback) {

        String fqdn = hostname + ":" + port;
        String apiEndpoint = "/JSSResource/accounts/username/";
        String fullUrl = fqdn + apiEndpoint + username;
        System.out.println("FQDN: " + fqdn + ", API:" + apiEndpoint + ", Full URL:" + fullUrl);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                fullUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        callback.onSuccess(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.toString());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<>();
                String credentials = username + ":" + password;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);

                headers.put("Authorization", auth);
                headers.put("User-Agent", "Pocket Admin");
                headers.put("Content-Type", "application/json; charset=utf-8");

                System.out.println(headers.toString());
                return headers;

            }

            // Override the default parseNetworkResponse so that I can extract the status code
            // of the returned response.
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);
    }


    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
