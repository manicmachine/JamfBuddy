package net.manicmachine.csather.network;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import net.manicmachine.csather.jamfbuddy.App;

public class RequestQueueSingleton {

    private static RequestQueue queue;

    private RequestQueueSingleton() {}

    public static synchronized RequestQueue getInstance() {
        if (null == queue) {
            queue = Volley.newRequestQueue(App.getContext().getApplicationContext());
        }

        return queue;
    }

    public static synchronized <T> void add(Request<T> req) {
        getInstance().add(req);
    }
}
