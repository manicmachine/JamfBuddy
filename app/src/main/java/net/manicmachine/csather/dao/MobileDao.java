package net.manicmachine.csather.dao;

import net.manicmachine.csather.model.MobileDevice;
import java.util.ArrayList;

public interface MobileDao {

    MobileDevice getMobileDevice(int mobileDeviceId);

    ArrayList<MobileDevice> getAllMobileDevices();

    void deleteMobileDevice(int mobileDeviceId);
    void addMobileDevice(MobileDevice mobileDevice);
    int mobileCount();
}
