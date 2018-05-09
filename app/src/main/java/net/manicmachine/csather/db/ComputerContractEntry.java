package net.manicmachine.csather.db;

import android.provider.BaseColumns;

/**
 * Created by csather on 4/26/18.
 */

public class ComputerContractEntry extends DBContract implements BaseColumns {

    public static final String TABLE = "computers";

    // Jamf Tab - General
    public static final String ID = "GEN_id";
    public static final String COL_COMP_GEN_NAME = "GEN_name";
    public static final String COL_COMP_GEN_MAC = "GEN_mac_address";
    public static final String COL_COMP_GEN_ALT_MAC = "GEN_alt_mac_address";
    public static final String COL_COMP_GEN_IP = "GEN_ip_address";
    public static final String COL_COMP_GEN_REPORTED_IP = "GEN_last_reported_ip";
    public static final String COL_COMP_GEN_SERIAL = "GEN_serial_number";
    public static final String COL_COMP_GEN_UDID = "GEN_udid";
    public static final String COL_COMP_GEN_JAMF_VERSION = "GEN_jamf_version";
    public static final String COL_COMP_GEN_BARCODE_1 = "GEN_barcode_1";
    public static final String COL_COMP_GEN_BARCODE_2 = "GEN_barcode_2";
    public static final String COL_COMP_GEN_MANAGED = "GEN_managed";
    public static final String COL_COMP_GEN_MANAGEMENT_USER = "GEN_management_username";
    public static final String COL_COMP_GEN_MDM_CAPABLE = "GEN_mdm_capable";
    public static final String COL_COMP_GEN_ASSET = "GEN_asset_tag";
    public static final String COL_COMP_GEN_SITE = "GEN_site";
    public static final String COL_COMP_GEN_LAST_INV = "GEN_report_date_epoch";
    public static final String COL_COMP_GEN_CHECKIN = "GEN_last_contact_time_epoch";
    public static final String COL_COMP_GEN_LAST_ENROLL = "GEN_initial_entry_date_epoch";

    // Jamf Tab - Hardware
    public static final String COL_COMP_HW_MODEL = "HW_model";
    public static final String COL_COMP_HW_MODEL_ID = "HW_model_identifier";
    public static final String COL_COMP_HW_PROC_TYPE = "HW_processor_type";
    public static final String COL_COMP_HW_PROC_ARCH = "HW_processor_architecture";
    public static final String COL_COMP_HW_PROC_SPEED = "HW_processor_speed";
    public static final String COL_COMP_HW_NUM_PROC = "HW_number_processors";
    public static final String COL_COMP_HW_NUM_CORES = "HW_number_cores";
    public static final String COL_COMP_HW_RAM = "HW_total_ram";
    public static final String COL_COMP_HW_BATTERY_CAP = "HW_battery_capacity";
    public static final String COL_COMP_HW_NIC_SPEED = "HW_nic_speed";

    // Jamf Tab - Operating System
    public static final String COL_COMP_OPSYS_OS = "OPSYS_os_name";
    public static final String COL_COMP_OPSYS_OS_VERSION = "OPSYS_os_version";
    public static final String COL_COMP_OPSYS_OS_BUILD = "OPSYS_os_build";
    public static final String COL_COMP_OPSYS_ACTIVE_DIR_STATUS = "OPSYS_active_directory_status";

    // Jamf Tab - Purchasing
    public static final String COL_COMP_PUR_IS_PURCHASED = "PUR_is_purchased";
    public static final String COL_COMP_PUR_IS_LEASED = "PUR_is_leased";
    public static final String COL_COMP_PUR_PO_NUMBER = "PUR_po_number";
    public static final String COL_COMP_PUR_VENDOR = "PUR_vendor";
    public static final String COL_COMP_PUR_APPLECARE_ID = "PUR_applecare_id";
    public static final String COL_COMP_PUR_PURCHASE_PRICE = "PUR_purchase_price";
    public static final String COL_COMP_PUR_PURCHASING_ACCOUNT = "PUR_purchasing_account";
    public static final String COL_COMP_PUR_PO_DATE = "PUR_po_date";
    public static final String COL_COMP_PUR_PO_DATE_epoch = "PUR_po_date_epoch";
    public static final String COL_COMP_PUR_WARRANTY_EXPIRES = "PUR_warranty_expires";
    public static final String COL_COMP_PUR_WARRANTY_EXPIRES_epoch = "PUR_warranty_expires_epoch";
    public static final String COL_COMP_PUR_LEASE_EXPIRES = "PUR_lease_expires";
    public static final String COL_COMP_PUR_LEASE_EXPIRES_epoch = "PUR_lease_expires_epoch";
    public static final String COL_COMP_PUR_LIFE_EXPECTANCY = "PUR_life_expectancy";
    public static final String COL_COMP_PUR_PURCHASING_CONTACT = "PUR_purchasing_contact";
    public static final String COL_COMP_PUR_OS_APPLECARE_ID = "PUR_os_applecare_id";
    public static final String COL_COMP_PUR_OS_MAINTENANCE_EXPIRES = "PUR_os_maintenance_expires";

    // Jamf Tab - Location
    public static final String COL_COMP_LOC_DEPARTMENT = "LOC_department";
    public static final String COL_COMP_LOC_BUILDING = "LOC_building";
    public static final String COL_COMP_LOC_ROOM = "LOC_room";

    // Serialized values
    public static final String COL_COMP_MDM_USERS_SERIALIZED = "mdm_users_serialized";
    public static final String COL_COMP_STORAGE_SERIALIZED = "storage_serialized";
    public static final String COL_COMP_LOCAL_USERS_SERIALIZED = "local_users_serialized";

}
