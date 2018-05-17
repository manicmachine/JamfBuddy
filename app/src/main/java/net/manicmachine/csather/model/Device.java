package net.manicmachine.csather.model;

import java.util.HashMap;

/**
 * Created by csather on 4/24/18.
 */

public class Device {

    private HashMap<String, String> generalInfo;
    private HashMap<String, String> hardwareInfo;
    private HashMap<String, String> osInfo;
    private HashMap<String, String> purchasingInfo;
    private HashMap<String, String> locInfo;

    private User user;

    public Device(){
        this.generalInfo = new HashMap<>();
        this.hardwareInfo = new HashMap<>();
        this.osInfo = new HashMap<>();
        this.purchasingInfo = new HashMap<>();
        this.locInfo = new HashMap<>();
    }

    public HashMap<String, String> getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(HashMap<String, String> generalInfo) {
        this.generalInfo = generalInfo;
    }

    public HashMap<String, String> getHardwareInfo() {
        return hardwareInfo;
    }

    public void setHardwareInfo(HashMap<String, String> hardwareInfo) {
        this.hardwareInfo = hardwareInfo;
    }

    public HashMap<String, String> getPurchasingInfo() {
        return purchasingInfo;
    }

    public void setPurchasingInfo(HashMap<String, String> purchasingInfo) {
        this.purchasingInfo = purchasingInfo;
    }

    public HashMap<String, String> getOsInfo() {
        return osInfo;
    }

    public void setOsInfo(HashMap<String, String> osInfo) {
        this.osInfo = osInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<String, String> getLocInfo() {
        return locInfo;
    }

    public void setLocInfo(HashMap<String, String> locInfo) {
        this.locInfo = locInfo;
    }
}
