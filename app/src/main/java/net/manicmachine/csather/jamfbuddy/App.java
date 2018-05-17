package net.manicmachine.csather.jamfbuddy;

import android.app.Application;
import android.content.Context;

import net.manicmachine.csather.dao.impl.ComputerDaoImpl;
import net.manicmachine.csather.dao.impl.MobileDaoImpl;
import net.manicmachine.csather.dao.impl.UserDaoImpl;

public class App extends Application {
    private static Context context;


    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        App.context = context;
    }
}