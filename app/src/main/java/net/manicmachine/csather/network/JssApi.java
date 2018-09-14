package net.manicmachine.csather.network;

import android.arch.lifecycle.LiveData;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import net.manicmachine.csather.model.Computer;
import net.manicmachine.csather.model.Record;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JssApi {

    public static JssApi jssApi;

    private static final String TAG = "net.manicmachine.csather.JssApi";

    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_TIMEOUT = 408;
    public static final int HTTP_INTERNAL_ERROR = 500;
    public static final int HTTP_SERVICE_UNAVAIL = 503;

    private String hostname;
    private String port;
    private String username;
    private String password;
    private int xmlCounter = 0;
    private boolean connected = true;

    public RequestQueue queue;

    /*
        Authenticate to the JSS by querying the Users endpoint. This way we can confirm that
        not only do we have the credentials necessary to authenticate to the JSS, but we can
        also pull what privileges that user may have.

        Currently we don't do anything with the privileges but this'll be used to provide more
        granular functionality for the end-user.

     */

    // TODO: PA-7, capture privileges from response and use that to create an object to represent the current user.

    public void authenticate(final VolleyCallback callback) {

        String url = buildApiUrl("accounts/username/" + username);
        JsonObjectRequest request = JssRequest(url, callback);

        RequestQueueSingleton.add(request);
    }

    public void populateRecords(final String endPoint, final VolleyCallback callback) {

        String url = buildApiUrl(endPoint);
        JsonObjectRequest request = JssRequest(url, callback);

        RequestQueueSingleton.add(request);
    }

    //TODO: Implement a means to retrieve record details from the JSS.
//    public LiveData<Computer> getComputer(int recordId) {
//        String url = buildApiUrl("computers", recordId);
//
//
//    }

    public String buildApiUrl(String endPoint) {

        StringBuilder fullUrl = new StringBuilder();

        fullUrl.append(hostname + ":" + port);
        fullUrl.append("/JSSResource/" + endPoint);

        return fullUrl.toString();

    }

    public String buildApiUrl(String endPoint, int recordId) {
        StringBuilder fullUrl = new StringBuilder();

        fullUrl.append(hostname + ":" + port);
        fullUrl.append("/JSSResource/" + endPoint + "/id/" + recordId);

        return fullUrl.toString();
    }

    public JsonObjectRequest JssRequest(String url, VolleyCallback callback) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        xmlCounter = 0; // reset counter
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    // There's a product issue with the JSS where sometimes it'll reply with XML
                    // when asked for JSON. The onErrorResponse will take this into account.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorString = error.toString();
                        Log.d(TAG, error.getClass().toString());
                        if (error.toString().contains("<?xml") && xmlCounter < 10) {

                            // JSS sent XML instead of JSON... Try again.
                            xmlCounter += 1;
                            errorString = "Received XML response instead of JSON (#" + Integer.toString(xmlCounter) + ") Trying again...";
                            authenticate(callback);

                        } else if (error.toString().contains("<?xml") && xmlCounter >= 10) {

                            xmlCounter = 0;
                            errorString = "Too many XML responses encountered. Quitting...";
                            callback.onError(HTTP_INTERNAL_ERROR);

                        } else if (error.getClass().equals(TimeoutError.class)) {

                            // The request timed out while trying to contact the JSS.
                            callback.onError(HTTP_TIMEOUT);

                        } else if (error.getClass().equals(NoConnectionError.class)) {

                            // No connection can be found/unknown host errors.
                            callback.onError(HTTP_SERVICE_UNAVAIL);

                        } else {

                            if (error.networkResponse != null) {
                                // Handle various HTTP errors.
                                switch (error.networkResponse.statusCode) {
                                    case HTTP_UNAUTHORIZED:
                                        callback.onError(HTTP_UNAUTHORIZED);
                                        break;
                                    case HTTP_FORBIDDEN:
                                        callback.onError(HTTP_FORBIDDEN);
                                        break;
                                    case HTTP_INTERNAL_ERROR:
                                        callback.onError(HTTP_INTERNAL_ERROR);
                                        break;
                                    default:
                                        callback.onError(HTTP_INTERNAL_ERROR);
                                        break;
                                }
                            }
                        }

                        Log.d(TAG, "Volley Error: " + errorString);
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
                headers.put("Accept", "application/json; charset=utf-8");

                return headers;

            }
        };

        // Increase timeout to compensate for slow JSS'
        request.setRetryPolicy(new DefaultRetryPolicy(
                500,
                2,
                2
        ));

        return request;
    }

    public static JssApi get() {
        if (jssApi == null) {
            jssApi = new JssApi();
        }

        return jssApi;
    }

    private JssApi() {}

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

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

}
