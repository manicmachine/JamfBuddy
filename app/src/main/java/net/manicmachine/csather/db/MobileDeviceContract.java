package net.manicmachine.csather.db;

import android.provider.BaseColumns;

/**
 * Created by csather on 4/26/18.
 */

public class MobileDeviceContract {
    public static final String DB_NAME = "net.manicmachine.csather.jamfbuddy";
    public static final int DB_VERSION = 1;

    public class MobileEntry implements BaseColumns {

        // Jamf Tab - General
        public static final String TABLE = "mobile_devices";
        public static final String COL_MOBILE_ID = "id";
        public static final String COL_MOBILE_NAME = "name";
        public static final String COL_MOBILE_ASSET = "asset_tag";
        public static final String COL_MOBILE_LAST_INV = "last_inventory_update_utc";
        public static final String COL_MOBILE_OS_VERSION = "os_version";
        public static final String COL_MOBILE_OS_BUILD = "os_build";
        public static final String COL_MOBILE_IP_ADDRESS = "ip_address";
        public static final String COL_MOBILE_MANAGED = "managed";
        public static final String COL_MOBILE_SUPERVISED = "supervised";
        public static final String COL_MOBILE_SHARED = "shared";
        public static final String COL_MOBILE_DEVICE_OWNERSHIP = "device_ownership_level";
        public static final String COL_MOBILE_LAST_ENROLL = "last_enrollment_utc";
        public static final String COL_MOBILE_DEVICE_LOCATE = "device_locator_service_enabled";
        public static final String COL_MOBILE_ICLOUD_BACKUP = "cloud_backup_enabled";
        public static final String COL_MOBILE_ICLOUD_LAST_BACKUP = "last_cloud_backup_date_utc";
        public static final String COL_MOBILE_LOCATION_SERVICES = "location_services_enabled";

        // Jamf Tab - Hardware
        public static final String COL_MOBILE_CAPACITY = "capacity";
        public static final String COL_MOBILE_AVAIL_CAPACITY = "available";
        public static final String COL_MOBILE_PRCT_USED = "percentage_used";
        public static final String COL_MOBILE_BATTERY_LEVEL = "battery_level";
        public static final String COL_MOBILE_SERIAL = "serial_number";
        public static final String COL_MOBILE_UDID = "udid";
        public static final String COL_MOBILE_WIFI_MAC = "wifi_mac_address";
        public static final String COL_MOBILE_MODEL = "model";
        public static final String COL_MOBILE_MODEL_ID = "model_identifier";
        public static final String COL_MOBILE_MODEL_NUMBER = "model_number";

        // Jamf Tab - Purchasing
        public static final String COL_MOBILE_IS_PURCHASED = "is_purchased";
        public static final String COL_MOBILE_IS_LEASED = "is_leased";
        public static final String COL_MOBILE_PO_NUMBER = "po_number";
        public static final String COL_MOBILE_VENDOR = "vendor";
        public static final String COL_MOBILE_APPLECARE_ID = "applecare_id";
        public static final String COL_MOBILE_PURCHASE_PRICE = "purchase_price";
        public static final String COL_MOBILE_PURCHASING_ACCOUNT = "purchasing_account";
        public static final String COL_MOBILE_PO_DATE = "po_date";
        public static final String COL_MOBILE_PO_DATE_UTC = "po_date_utc";
        public static final String COL_MOBILE_WARRANTY_EXPIRES = "warranty_expires";
        public static final String COL_MOBILE_WARRANTY_EXPIRES_UTC = "warranty_expires_utc";
        public static final String COL_MOBILE_LEASE_EXPIRES = "lease_expires";
        public static final String COL_MOBILE_LEASE_EXPIRES_UTC = "lease_expires_utc";
        public static final String COL_MOBILE_LIFE_EXPECTANCY = "life_expectancy";
        public static final String COL_MOBILE_PURCHASING_CONTACT = "purchasing_contact";
        public static final String COL_MOBILE_OS_APPLECARE_ID = "os_applecare_id";
        public static final String COL_MOBILE_OS_MAINTENANCE_EXPIRES = "os_maintenance_expires";

        // Jamf Tab - Security
        public static final String COL_MOBILE_DATA_PROTECTION = "data_protection";
        public static final String COL_MOBILE_PASSCODE_STATUS = "passcode_present";
        public static final String COL_MOBILE_PASSCODE_COMPLIANT = "passcode_compliant";
        public static final String COL_MOBILE_ACTIVATATION_LOCK = "activation_lock_enabled";
        public static final String COL_MOBILE_JAILBREAK_DETECTED = "jailbreak_detected";

        // Jamf Tab - Location
        public static final String COL_MOBILE_DEPARTMENT = "department";
        public static final String COL_MOBILE_BUILDING = "building";
        public static final String COL_MOBILE_ROOM = "room";

    }
}
