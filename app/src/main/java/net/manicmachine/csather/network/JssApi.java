package net.manicmachine.csather.network;

import android.content.Context;
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

import net.manicmachine.csather.jamfbuddy.Util;

import org.json.JSONObject;

import java.net.UnknownHostException;
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
    private int statusCode;
    private int xmlCounter = 0;
    private boolean connected = true;

    public RequestQueue queue;

    public void authenticate(final VolleyCallback callback) {

        String url = buildApiUrl("accounts/username/" + username);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                                        break;
                                }
                            }
                        }

                        Log.d(TAG, "Volley Error: " + errorString);
                        callback.onError(HTTP_INTERNAL_ERROR);
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
                500,
                2,
                2
        ));

        RequestQueueSingleton.add(request);
    }

    public HashMap<String, String> populate(final String target, final VolleyCallback callback) {
        HashMap<String, String> jssResponse = new HashMap<>();

        String url = buildApiUrl(target);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        xmlCounter = 0;
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorString;

                        if (error.toString().contains("<?xml") && xmlCounter < 10) {

                            // JSS sent XML instead of JSON... Try again.
                            xmlCounter += 1;
                            errorString = "Received XML response instead of JSON. Trying again...";
                            populate(target, callback);

                        } else if (error.toString().contains("<?xml") && xmlCounter >= 10){

                            xmlCounter = 0;
                            errorString = "Too many XML responses encountered. Quitting...";
                            callback.onError(HTTP_INTERNAL_ERROR);

                        } else if (error.getClass().equals(TimeoutError.class)) {

                            // The request timed out while trying to contact the JSS.
                            callback.onError(HTTP_TIMEOUT);

                        } else {

                            // Handle various HTTP errors.
                            if (error.networkResponse != null) {
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
                                        break;
                                }
                            }
                        }

                        Log.d(TAG, "Volley Error: " + error.toString());
                        callback.onError(HTTP_INTERNAL_ERROR);
                    }
                }) {

            // Override getHeaders so that we can add basic authentication to the request.
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

            // Override parseNetworkResponse so that we can reference the status code of the response.
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                statusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }

        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                2,
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

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
