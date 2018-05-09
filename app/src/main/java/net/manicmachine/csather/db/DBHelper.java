package net.manicmachine.csather.db;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DBHelper {

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

                if (i == properties.size() - 1) {
                    createTableSql += ");";
                } else {
                    createTableSql += ", ";
                }

            }

        }

        return createTableSql;

    }

}
