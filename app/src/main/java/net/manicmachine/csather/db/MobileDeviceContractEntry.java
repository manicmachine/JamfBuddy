package net.manicmachine.csather.db;

import android.provider.BaseColumns;

/**
 * Created by csather on 4/26/18.
 */

public class MobileDeviceContractEntry extends DBContract implements BaseColumns {

    public static final String TABLE = "mobile_devices";

    // Jamf Tab - General
    public static final String ID = "GEN_id";
    public static final String COL_MOBILE_GEN_NAME = "GEN_name";
    public static final String COL_MOBILE_GEN_ASSET = "GEN_asset_tag";
    public static final String COL_MOBILE_GEN_LAST_INV = "GEN_last_inventory_update_epoch";
    public static final String COL_MOBILE_GEN_OS_VERSION = "GEN_os_version";
    public static final String COL_MOBILE_GEN_OS_BUILD = "GEN_os_build";
    public static final String COL_MOBILE_GEN_IP_ADDRESS = "GEN_ip_address";
    public static final String COL_MOBILE_GEN_MANAGED = "GEN_managed";
    public static final String COL_MOBILE_GEN_SUPERVISED = "GEN_supervised";
    public static final String COL_MOBILE_GEN_SHARED = "GEN_shared";
    public static final String COL_MOBILE_GEN_DEVICE_OWNERSHIP = "GEN_device_ownership_level";
    public static final String COL_MOBILE_GEN_LAST_ENROLL = "GEN_last_enrollment_epoch";
    public static final String COL_MOBILE_GEN_DEVICE_LOCATE = "GEN_device_locator_service_enabled";
    public static final String COL_MOBILE_GEN_ICLOUD_BACKUP = "GEN_cloud_backup_enabled";
    public static final String COL_MOBILE_GEN_ICLOUD_LAST_BACKUP = "GEN_last_cloud_backup_date_epoch";
    public static final String COL_MOBILE_GEN_LOCATION_SERVICES = "GEN_location_services_enabled";

    // Jamf Tab - Hardware
    public static final String COL_MOBILE_HW_CAPACITY = "HW_capacity";
    public static final String COL_MOBILE_HW_AVAIL_CAPACITY = "HW_available";
    public static final String COL_MOBILE_HW_PRCT_USED = "HW_percentage_used";
    public static final String COL_MOBILE_HW_BATTERY_LEVEL = "HW_battery_level";
    public static final String COL_MOBILE_HW_SERIAL = "HW_serial_number";
    public static final String COL_MOBILE_HW_UDID = "HW_udid";
    public static final String COL_MOBILE_HW_WIFI_MAC = "HW_wifi_mac_address";
    public static final String COL_MOBILE_HW_MODEL = "HW_model";
    public static final String COL_MOBILE_HW_MODEL_ID = "HW_model_identifier";
    public static final String COL_MOBILE_HW_MODEL_NUMBER = "HW_model_number";

    // Jamf Tab - Purchasing
    public static final String COL_MOBILE_PUR_IS_PURCHASED = "PUR_is_purchased";
    public static final String COL_MOBILE_PUR_IS_LEASED = "PUR_is_leased";
    public static final String COL_MOBILE_PUR_PO_NUMBER = "PUR_po_number";
    public static final String COL_MOBILE_PUR_VENDOR = "PUR_vendor";
    public static final String COL_MOBILE_PUR_APPLECARE_ID = "PUR_applecare_id";
    public static final String COL_MOBILE_PUR_PURCHASE_PRICE = "PUR_purchase_price";
    public static final String COL_MOBILE_PUR_PURCHASING_ACCOUNT = "PUR_purchasing_account";
    public static final String COL_MOBILE_PUR_PO_DATE = "PUR_po_date";
    public static final String COL_MOBILE_PUR_PO_DATE_epoch = "PUR_po_date_epoch";
    public static final String COL_MOBILE_PUR_WARRANTY_EXPIRES = "PUR_warranty_expires";
    public static final String COL_MOBILE_PUR_WARRANTY_EXPIRES_epoch = "PUR_warranty_expires_epoch";
    public static final String COL_MOBILE_PUR_LEASE_EXPIRES = "PUR_lease_expires";
    public static final String COL_MOBILE_PUR_LEASE_EXPIRES_epoch = "PUR_lease_expires_epoch";
    public static final String COL_MOBILE_PUR_LIFE_EXPECTANCY = "PUR_life_expectancy";
    public static final String COL_MOBILE_PUR_PURCHASING_CONTACT = "PUR_purchasing_contact";
    public static final String COL_MOBILE_PUR_OS_APPLECARE_ID = "PUR_os_applecare_id";
    public static final String COL_MOBILE_PUR_OS_MAINTENANCE_EXPIRES = "PUR_os_maintenance_expires";

    // Jamf Tab - Security
    public static final String COL_MOBILE_SEC_DATA_PROTECTION = "SEC_data_protection";
    public static final String COL_MOBILE_SEC_PASSCODE_STATUS = "SEC_passcode_present";
    public static final String COL_MOBILE_SEC_PASSCODE_COMPLIANT = "SEC_passcode_compliant";
    public static final String COL_MOBILE_SEC_ACTIVATATION_LOCK = "SEC_activation_lock_enabled";
    public static final String COL_MOBILE_SEC_JAILBREAK_DETECTED = "SEC_jailbreak_detected";

    // Jamf Tab - Location
    public static final String COL_MOBILE_LOC_DEPARTMENT = "LOC_department";
    public static final String COL_MOBILE_LOC_BUILDING = "LOC_building";
    public static final String COL_MOBILE_LOC_ROOM = "LOC_room";

}
