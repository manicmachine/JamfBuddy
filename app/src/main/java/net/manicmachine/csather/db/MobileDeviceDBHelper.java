package net.manicmachine.csather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MobileDeviceDBHelper extends SQLiteOpenHelper{

    private final static String TAG = "MobileDeviceDBHelper";
    private final static Class CONTRACT = MobileDeviceContractEntry.class;

    public MobileDeviceDBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            System.out.println("Mobile Device DB Helper created.");
            String createTableSql = DBHelper.buildCreateSQL(CONTRACT);
            System.out.println("Mobile SQL: " + createTableSql);
            db.execSQL(createTableSql);
            System.out.println("Table creation command submitted.");

        } catch (IllegalAccessException | NoSuchFieldException ex) {
            Log.d(TAG, "Error: Failed to create the Mobile Device's database.", ex.fillInStackTrace());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MobileDeviceContractEntry.TABLE);
        this.onCreate(db);
    }
}
