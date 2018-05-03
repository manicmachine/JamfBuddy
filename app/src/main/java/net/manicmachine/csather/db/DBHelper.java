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
}
