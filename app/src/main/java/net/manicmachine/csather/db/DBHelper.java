package net.manicmachine.csather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private final static String TAG = "net.manicmachine.csather.db.DBHelper";
    private final static Class COMPUTER_CONTRACT = ComputerContractEntry.class;
    private final static Class MOBILEDEVICE_CONTRACT = MobileDeviceContractEntry.class;
    private final static Class USER_CONTRACT = UserContractEntry.class;

    public DBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DBContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            String createTableSql = buildCreateSQL(COMPUTER_CONTRACT);
            Log.d(TAG, "Database creation string: " + createTableSql.toString());
            db.execSQL(createTableSql.toString());

            createTableSql = buildCreateSQL(MOBILEDEVICE_CONTRACT);
            Log.d(TAG, "Database creation string: " + createTableSql.toString());
            db.execSQL(createTableSql.toString());

            createTableSql = buildCreateSQL(USER_CONTRACT);
            Log.d(TAG, "Database creation string: " + createTableSql.toString());
            db.execSQL(createTableSql.toString());




        } catch (IllegalAccessException | NoSuchFieldException ex) {
            Log.d(TAG, "Error: Failed to create the database. ", ex.fillInStackTrace());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ComputerContractEntry.TABLE);
        this.onCreate(db);
    }

    // Return all the properties of a given class as an ArrayList of Strings.
    static <T> ArrayList<String> getProperties(Class<T> dbContract) throws IllegalAccessException {

        ArrayList<String> properties = new ArrayList<String>();

        Field[] fields = dbContract.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            properties.add((String)field.get((Object)dbContract));
        }

        return properties;
    }

    // Takes in a dbContract class object, get it's properties and uses that to build the initial
    // create Table statement.
    //TODO: PA-5, migrate to JSON for database schema.
    static <T> String buildCreateSQL(Class<T> dbContract) throws IllegalAccessException, NoSuchFieldException {
        ArrayList<String> properties = getProperties(dbContract);

        String createTableSql;

        Field tableField = dbContract.getField("TABLE");
        Field idField = dbContract.getField("ID");

        String tableName = (String) tableField.get((Object) dbContract);
        String idName = (String) idField.get((Object) dbContract);

        createTableSql = "CREATE TABLE " + tableName + " ( ";

        for (int i = 0; i < properties.size(); i++) {
            String property = properties.get(i);
            System.out.println("Goal: " + Integer.toString(properties.size()));
            System.out.println("Property #" + Integer.toString(i) + ": " + property.toString());
            if (!property.equals(tableName)) {

                createTableSql += property;

                // Determine if property is the primary key or epoch timestamp, and if so, set the
                // data type accordingly. Otherwise set it as TEXT.
                if (properties.get(i).equals(idName)) {
                    createTableSql += " INTEGER PRIMARY KEY ";
                } else if (property.contains("_epoch")) {
                    createTableSql += " INTEGER ";
                } else if (property.contains("_serialized")) {
                    createTableSql += " BLOB ";
                } else {
                    createTableSql += " TEXT ";
                }

                // Subtracting 2 instead of 1 as we need to account for the Table property.
                if (i == properties.size() - 2) {
                    createTableSql += ");";
                } else {
                    createTableSql += ", ";
                }

            }

        }

        return createTableSql;

    }

}
