package net.manicmachine.csather.model;

import java.util.HashMap;

/**
 * Created by csather on 4/24/18.
 */

public class MobileDevice extends Device {

    private HashMap<String, String> securityInfo;
    private HashMap<String, String> appInfo;
    private HashMap<String, String> confProfileInfo;

    public MobileDevice(){}

    public HashMap<String, String> getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(HashMap<String, String> securityInfo) {
        this.securityInfo = securityInfo;
    }

    public HashMap<String, String> getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(HashMap<String, String> appInfo) {
        this.appInfo = appInfo;
    }

    public HashMap<String, String> getConfProfileInfo() {
        return confProfileInfo;
    }

    public void setConfProfileInfo(HashMap<String, String> confProfileInfo) {
        this.confProfileInfo = confProfileInfo;
    }
}
