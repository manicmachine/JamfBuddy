package net.manicmachine.csather.db;

import android.provider.BaseColumns;

/**
 * Created by csather on 4/26/18.
 */

public class UserContractEntry extends DBContract implements BaseColumns {

    public static final String TABLE = "users";

    public static final String ID = "GEN_id";
    public static final String COL_USER_NAME = "GEN_full_name";
    public static final String COL_USER_EMAIL = "GEN_email_address";
    public static final String COL_USER_PHONE = "GEN_phone_number";
    public static final String COL_USER_POSITION = "GEN_position";

}
