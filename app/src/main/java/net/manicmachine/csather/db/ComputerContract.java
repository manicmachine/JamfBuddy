package net.manicmachine.csather.db;

import android.provider.BaseColumns;

/**
 * Created by csather on 4/26/18.
 */

public class ComputerContract {
    public static final String DB_NAME = "net.manicmachine.csather.jamfbuddy";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        // General
        public static final String TABLE = "computers";
        public static final String COL_COMPUTER_ID = "id";
        public static final String COL_COMPUTER_NAME = "name";
        public static final String COL_COMPUTER_MAC = "mac_address";
        public static final String COL_COMPUTER_ALT_MAC = "alt_mac_address";
        public static final String COL_COMPUTER_IP = "ip_address";
        public static final String COL_COMPUTER_REPORTED_IP = "last_reported_ip";
        public static final String COL_COMPUTER_SERIAL = "serial_number";
        public static final String COL_COMPUTER_UDID = "udid";
        public static final String COL_COMPUTER_JAMF_VERSION = "jamf_version";
        public static final String COL_COMPUTER_BARCODE_1 = "barcode_1";
        public static final String COL_COMPUTER_BARCODE_2 = "barcode_2";
        public static final String COL_COMPUTER_MANAGED = "managed";
        public static final String COL_COMPUTER_MANAGEMENT_USER = "management_username";
        public static final String COL_COMPUTER_MDM_CAPABLE = "mdm_capable";
        public static final String COL_COMPUTER_ASSET = "asset_tag";
        public static final String COL_COMPUTER_SITE = "site";
        public static final String COL_COMPUTER_LOGGED_ITUNES = "itunes_store_account_is_active";
        public static final String COL_COMPUTER_LAST_INV = "report_date_utc";
        public static final String COL_COMPUTER_CHECKIN = "last_contact_time_utc";
        public static final String COL_COMPUTER_LAST_ENROLL = "initial_entry_date_utc";

        // Hardware
        public static final String COL_COMPUTER_MODEL = "model";
        public static final String COL_COMPUTER_MODEL_ID = "model_identifier";
        public static final String COL_COMPUTER_OS_VERSION = "os_version";
        public static final String COL_COMPUTER_OS_BUILD = "os_build";
        public static final String COL_COMPUTER_ACTIVE_DIR_STATUS = "active_directory_status";
        public static final String COL_COMPUTER_PROC_TYPE = "processor_type";
        public static final String COL_COMPUTER_PROC_ARCH = "processor_architecture";
        public static final String COL_COMPUTER_PROC_SPEED_MHZ = "processor_speed_mhz";


    }
}
