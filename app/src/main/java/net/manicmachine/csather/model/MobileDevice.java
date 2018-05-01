package net.manicmachine.csather.model;

import java.util.HashMap;

/**
 * Created by csather on 4/24/18.
 */

public class MobileDevice extends Device {

    private HashMap<String, String> securityInfo;

    public MobileDevice(){}

    public HashMap<String, String> getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(HashMap<String, String> securityInfo) {
        this.securityInfo = securityInfo;
    }
}
