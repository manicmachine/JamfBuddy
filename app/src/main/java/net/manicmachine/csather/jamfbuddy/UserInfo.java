package net.manicmachine.csather.jamfbuddy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import net.manicmachine.csather.model.Computer;
import net.manicmachine.csather.model.MobileDevice;
import net.manicmachine.csather.model.User;

import java.util.ArrayList;

public class UserInfo {
    private static UserInfo userInfo;

    private String username;
    private String password;

    private String hostname;
    private String port;

    private ArrayList<Computer> computers;
    private ArrayList<MobileDevice> mobileDevices;
    private ArrayList<User> users;

    public static UserInfo get(Context context) {
        if (userInfo == null) {
            userInfo = new UserInfo(context);
        }

        return userInfo;
    }

    private UserInfo(Context context) {
        computers = new ArrayList<>();
        mobileDevices = new ArrayList<>();
        users = new ArrayList<>();
    }

    public ArrayList<Computer> getComputers() {
        return computers;
    }

    public ArrayList<MobileDevice> getMobileDevices() {
        return mobileDevices;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setComputers(ArrayList<Computer> computers) {
        this.computers = computers;
    }

    public void setMobileDevices(ArrayList<MobileDevice> mobileDevices) {
        this.mobileDevices = mobileDevices;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
