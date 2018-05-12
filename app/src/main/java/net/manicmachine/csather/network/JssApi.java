package net.manicmachine.csather.network;

import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JssApi {

    private static final String TAG = "net.manicmachine.csather.JssApi";

    private String hostname;
    private String port;
    private String username;
    private String password;
    private int statusCode;

    public RequestQueue queue;

    //TODO: Add logic to resend the request if the JSS responds incorrectly (getting XML instead of JSON)

    public void authenticate(final VolleyCallback callback) {

        String url = buildApiUrl("accounts/username/" + username);
        System.out.println("Full URL:" + url);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        callback.onSuccess(response);

                    }
                },
                new Response.ErrorListener() {
                    // There's a product issue with the JSS where sometimes it'll reply with XML
                    // when asked for JSON. The onErrorResponse will take this into account.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorString = error.toString().contains("<?xml") ?
                                "Received XML response instead of JSON. Trying again..." :
                                error.toString();

                        Log.d(TAG, "Volley Error: " + errorString);

                        authenticate(callback);
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

        // Increase timeout to compensate for slow JSS'
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                3,
                2
        ));

        RequestQueueSingleton.add(request);
    }

    public HashMap<String, String> populate(final String target, final VolleyCallback callback) {
        HashMap<String, String> jssResponse = new HashMap<>();

        String url = buildApiUrl("computers");
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorString = error.toString().contains("<?xml") ?
                                "Received XML response instead of JSON. Trying again..." :
                                error.toString();

                        Log.d(TAG, "Volley Error: " + errorString);

                        populate(target, callback);
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

                return headers;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                3,
                2
        ));

        RequestQueueSingleton.add(request);

        return jssResponse;
    }

    public String buildApiUrl(String endPoint) {

        StringBuilder fullUrl = new StringBuilder();

        fullUrl.append(hostname + ":" + port);
        fullUrl.append("/JSSResource/" + endPoint);

        return fullUrl.toString();

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
