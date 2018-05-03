package net.manicmachine.csather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MobileDeviceDBHelper extends SQLiteOpenHelper{

    private final static String TAG = "MobileDeviceDBHelper";

    public MobileDeviceDBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Build SQL statement to create the mobile devices table.
        String createTableSql = "CREATE TABLE " + MobileDeviceContractEntry.TABLE + " ( ";

        try {

            // Iterate through the properties of the MobileDeviceContractEntry and create an additional
            // line in the sql statement based upon it's content.
            ArrayList<String> properties = DBHelper.getProperties(MobileDeviceContractEntry.class);

            for (int i = 0; i < properties.size(); i++) {
                String property = properties.get(i);

                if (!property.equals(MobileDeviceContractEntry.TABLE)) {

                    createTableSql += property;

                    // Determine if property is the primary key or epoch timestamp, and if so, set the
                    // data type accordingly. Otherwise set it as TEXT
                    if(properties.get(i).equals(MobileDeviceContractEntry.COL_MOBILE_ID)) {
                        createTableSql += " INTEGER PRIMARY KEY ";
                    } else if (property.length() > 5 && property.substring(property.length() - 5).equals("epoch")) {
                        createTableSql += " INTEGER ";
                    } else {
                        createTableSql += " TEXT ";
                    }

                    if (i == properties.size() - 1 ) {
                        createTableSql += ");";
                    } else {
                        createTableSql += ", ";
                    }

                }

            }

            db.execSQL(createTableSql);

        } catch (IllegalAccessException ex) {
            Log.d(TAG, ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MobileDeviceContractEntry.TABLE);
        this.onCreate(db);
    }
}
