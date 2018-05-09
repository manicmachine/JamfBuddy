package net.manicmachine.csather.db;

import android.provider.BaseColumns;

public class UserDevicesContractEntry extends DBContract implements BaseColumns {

    public static final String TABLE = "users_devices";
    public static final String COL_USER_DEVICES_USER_ID = "user_id";
    public static final String COL_USER_DEVICES_COMPUTER_ID = "computer_id";
    public static final String COL_USER_DEVICES_MOBILE_ID = "device_id";

}
