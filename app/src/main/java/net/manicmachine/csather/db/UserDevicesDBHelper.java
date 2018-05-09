package net.manicmachine.csather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDevicesDBHelper extends SQLiteOpenHelper {

    private final static String TAG = "UserDBHelper";
    private final static Class CONTRACT = UserContractEntry.class;

    public UserDevicesDBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableSql;

        // Since the UserDevices table is a Composite table, we'll need to construct the SQL
        // differently from the others.
        String tableName = UserDevicesContractEntry.TABLE;
        String userId = UserDevicesContractEntry.COL_USER_DEVICES_USER_ID;
        String compId = UserDevicesContractEntry.COL_USER_DEVICES_COMPUTER_ID;
        String mdId = UserDevicesContractEntry.COL_USER_DEVICES_MOBILE_ID;

        createTableSql = "CREATE TABLE " + tableName + " ( "
                + userId + " INTEGER PRIMARY KEY, "
                + compId + " INTEGER, "
                + mdId + " INTEGER);";

        db.execSQL(createTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MobileDeviceContractEntry.TABLE);
        this.onCreate(db);
    }
}