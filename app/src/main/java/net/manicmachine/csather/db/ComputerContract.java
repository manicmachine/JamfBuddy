package net.manicmachine.csather.db;

import android.provider.BaseColumns;

/**
 * Created by csather on 4/26/18.
 */

public class ComputerContract {
    public static final String DB_NAME = "net.manicmachine.csather.jamfbuddy";
    public static final int DB_VERSION = 1;

    public class ComputerEntry implements BaseColumns {

        // Jamf Tab - General
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
        public static final String COL_COMPUTER_LAST_INV = "report_date_utc";
        public static final String COL_COMPUTER_CHECKIN = "last_contact_time_utc";
        public static final String COL_COMPUTER_LAST_ENROLL = "initial_entry_date_utc";

        // Jamf Tab - Hardware
        public static final String COL_COMPUTER_MODEL = "model";
        public static final String COL_COMPUTER_MODEL_ID = "model_identifier";
        public static final String COL_COMPUTER_PROC_TYPE = "processor_type";
        public static final String COL_COMPUTER_PROC_ARCH = "processor_architecture";
        public static final String COL_COMPUTER_PROC_SPEED = "processor_speed";
        public static final String COL_COMPUTER_NUM_PROC = "number_processors";
        public static final String COL_COMPUTER_NUM_CORES = "number_cores";
        public static final String COL_COMPUTER_RAM = "total_ram";
        public static final String COL_COMPUTER_BATTERY_CAP = "battery_capacity";
        public static final String COL_COMPUTER_NIC_SPEED = "nic_speed";

        // Jamf Tab - Operating System
        public static final String COL_COMPUTER_OS = "os_name";
        public static final String COL_COMPUTER_OS_VERSION = "os_version";
        public static final String COL_COMPUTER_OS_BUILD = "os_build";
        public static final String COL_COMPUTER_ACTIVE_DIR_STATUS = "active_directory_status";

        // Jamf Tab - Purchasing
        public static final String COL_COMPUTER_IS_PURCHASED = "is_purchased";
        public static final String COL_COMPUTER_IS_LEASED = "is_leased";
        public static final String COL_COMPUTER_PO_NUMBER = "po_number";
        public static final String COL_COMPUTER_VENDOR = "vendor";
        public static final String COL_COMPUTER_APPLECARE_ID = "applecare_id";
        public static final String COL_COMPUTER_PURCHASE_PRICE = "purchase_price";
        public static final String COL_COMPUTER_PURCHASING_ACCOUNT = "purchasing_account";
        public static final String COL_COMPUTER_PO_DATE = "po_date";
        public static final String COL_COMPUTER_PO_DATE_UTC = "po_date_utc";
        public static final String COL_COMPUTER_WARRANTY_EXPIRES = "warranty_expires";
        public static final String COL_COMPUTER_WARRANTY_EXPIRES_UTC = "warranty_expires_utc";
        public static final String COL_COMPUTER_LEASE_EXPIRES = "lease_expires";
        public static final String COL_COMPUTER_LEASE_EXPIRES_UTC = "lease_expires_utc";
        public static final String COL_COMPUTER_LIFE_EXPECTANCY = "life_expectancy";
        public static final String COL_COMPUTER_PURCHASING_CONTACT = "purchasing_contact";
        public static final String COL_COMPUTER_OS_APPLECARE_ID = "os_applecare_id";
        public static final String COL_COMPUTER_OS_MAINTENANCE_EXPIRES = "os_maintenance_expires";

        // Jamf Tab - Location
        public static final String COL_COMPUTER_DEPARTMENT = "department";
        public static final String COL_COMPUTER_BUILDING = "building";
        public static final String COL_COMPUTER_ROOM = "room";
    }
}
