package net.manicmachine.csather.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.manicmachine.csather.dao.ComputerDao;
import net.manicmachine.csather.db.ComputerContractEntry;
import net.manicmachine.csather.db.DBHelper;
import net.manicmachine.csather.model.Computer;
import net.manicmachine.csather.model.Hdd;
import net.manicmachine.csather.model.LocalUser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComputerDaoImpl implements ComputerDao {

    private static final String TAG = "net.manicmachine.csather.dao.impl.ComputerDaoImpl";
    private static final String GEN_PREFIX = "GEN_";
    private static final String HW_PREFIX = "HW_";
    private static final String OPSYS_PREFIX = "OPSYS_";
    private static final String PUR_PREFIX = "PUR_";
    private static final String LOC_PREFIX = "LOC_";

    private DBHelper mHelper;

    public ComputerDaoImpl(Context context) {
        this.mHelper = new DBHelper(context);
    }

    @Override
    public Computer getComputer(int computerId) {
        Computer computer = new Computer();

        HashMap<String, String> generalInfo = new HashMap<>();
        HashMap<String, String> hardwareInfo = new HashMap<>();
        HashMap<String, String> purchasingInfo = new HashMap<>();
        HashMap<String, String> osInfo = new HashMap<>();
        HashMap<String, String> locInfo = new HashMap<>();
        ArrayList<String> mdmCapableUsers = new ArrayList<>();
        ArrayList<Hdd> storage = new ArrayList<>();
        ArrayList<LocalUser> localUsers = new ArrayList<>();

        SQLiteDatabase db = this.mHelper.getReadableDatabase();
        Cursor cursor = db.query(ComputerContractEntry.TABLE,
                null,
                ComputerContractEntry.ID + "=?",
                new String[]{Integer.toString(computerId)},
                null,
                null,
                null);

        String[] columns = cursor.getColumnNames();

        for (String column : columns) {
            if (column.contains(GEN_PREFIX)) {
                generalInfo.put(column.replace(GEN_PREFIX, ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains(HW_PREFIX)) {
                hardwareInfo.put(column.replace(HW_PREFIX, ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains(OPSYS_PREFIX)) {
                osInfo.put(column.replace(OPSYS_PREFIX, ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains(PUR_PREFIX)) {
                purchasingInfo.put(column.replace(PUR_PREFIX, ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains(LOC_PREFIX)) {
                locInfo.put(column.replace(LOC_PREFIX, ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains("_serialized")) {
                try {

                    byte[] buffer = cursor.getBlob(cursor.getColumnIndex(column));
                    ObjectInputStream serializedObject = new ObjectInputStream(new ByteArrayInputStream(buffer));
                    Object deSerializedObject = serializedObject.readObject();

                    switch (column) {
                        case ComputerContractEntry.COL_COMP_MDM_USERS_SERIALIZED:
                            mdmCapableUsers = (ArrayList<String>) deSerializedObject;
                            break;
                        case ComputerContractEntry.COL_COMP_STORAGE_SERIALIZED:
                            storage = (ArrayList<Hdd>) deSerializedObject;
                            break;
                        case ComputerContractEntry.COL_COMP_LOCAL_USERS_SERIALIZED:
                            localUsers = (ArrayList<LocalUser>) deSerializedObject;
                            break;
                    }

                    serializedObject.close();

                } catch (IOException | ClassNotFoundException ex) {
                    Log.d(TAG, "Error: Failed to deserialize the computer object from database.", ex.fillInStackTrace());
                } finally {
                    cursor.close();
                    db.close();
                }
            }
        }

        computer.setGeneralInfo(generalInfo);
        computer.setHardwareInfo(hardwareInfo);
        computer.setPurchasingInfo(purchasingInfo);
        computer.setOsInfo(osInfo);
        computer.setLocInfo(locInfo);
        computer.setMdmCapableUsers(mdmCapableUsers);
        computer.setStorage(storage);
        computer.setLocalusers(localUsers);

        return computer;
    }

    @Override
    public ArrayList<Computer> getAllComputers() {
        ArrayList<Computer> computers = new ArrayList<>();

        SQLiteDatabase db = this.mHelper.getReadableDatabase();
        Cursor cursor = db.query(ComputerContractEntry.TABLE,
                new String[]{
                    ComputerContractEntry.ID,
                    ComputerContractEntry.COL_COMP_GEN_NAME,
                    ComputerContractEntry.COL_COMP_HW_MODEL,
                    ComputerContractEntry.COL_COMP_GEN_ASSET,
                    ComputerContractEntry.COL_COMP_GEN_LAST_INV},
                null, null, null, null, null);

        while (cursor.moveToNext()) {

            Computer newComputer = new Computer();
            HashMap<String, String> generalInfo = new HashMap<>();
            HashMap<String, String> hardwareInfo = new HashMap<>();

            generalInfo.put(ComputerContractEntry.ID.replace(GEN_PREFIX, ""),
                    Integer.toString(cursor.getInt(cursor.getColumnIndex(ComputerContractEntry.ID))));
            generalInfo.put(ComputerContractEntry.COL_COMP_GEN_NAME.replace(GEN_PREFIX, ""),
                    cursor.getString(cursor.getColumnIndex(ComputerContractEntry.COL_COMP_GEN_NAME)));
            generalInfo.put(ComputerContractEntry.COL_COMP_GEN_ASSET.replace(GEN_PREFIX, ""),
                    cursor.getString(cursor.getColumnIndex(ComputerContractEntry.COL_COMP_GEN_ASSET)));
            generalInfo.put(ComputerContractEntry.COL_COMP_GEN_LAST_INV.replace(GEN_PREFIX, ""),
                    Integer.toString(cursor.getInt(cursor.getColumnIndex(ComputerContractEntry.COL_COMP_GEN_LAST_INV))));

            hardwareInfo.put(ComputerContractEntry.COL_COMP_HW_MODEL.replace(HW_PREFIX, ""),
                    cursor.getString(cursor.getColumnIndex(ComputerContractEntry.COL_COMP_HW_MODEL)));

            Log.d(TAG, "General Info: " + generalInfo.toString() + ", " + hardwareInfo.toString());
            newComputer.setGeneralInfo(generalInfo);
            newComputer.setHardwareInfo(hardwareInfo);

            computers.add(newComputer);

        }

        return computers;

    }

    @Override
    public void deleteComputer(int computerId) {
        SQLiteDatabase db = this.mHelper.getWritableDatabase();
        db.delete(ComputerContractEntry.TABLE,
                ComputerContractEntry.ID + " = ?",
                new String[]{Integer.toString(computerId)});

        db.close();
    }

    @Override
    public void addComputer(Computer computer) {
        SQLiteDatabase db = this.mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (Map.Entry<String, String> entry : computer.getGeneralInfo().entrySet()) {
            String column = GEN_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : computer.getHardwareInfo().entrySet()) {
            String column = HW_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : computer.getOsInfo().entrySet()) {
            String column = OPSYS_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : computer.getPurchasingInfo().entrySet()) {
            String column = PUR_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : computer.getLocInfo().entrySet()) {
            String column = LOC_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);

            for (int i = 0; i < 3; i++) {

                if (i == 0) {
                    objectStream.writeObject(computer.getMdmCapableUsers());
                    byte[] mdmUsers = outputStream.toByteArray();
                    values.put(ComputerContractEntry.COL_COMP_MDM_USERS_SERIALIZED, mdmUsers);
                } else if (i == 1) {
                    objectStream.writeObject(computer.getStorage());
                    byte[] storage = outputStream.toByteArray();
                    values.put(ComputerContractEntry.COL_COMP_STORAGE_SERIALIZED, storage);
                } else {
                    objectStream.writeObject(computer.getLocalusers());
                    byte[] localUsers = outputStream.toByteArray();
                    values.put(ComputerContractEntry.COL_COMP_LOCAL_USERS_SERIALIZED, localUsers);
                }

                outputStream.reset();
                objectStream.reset();

            }

            outputStream.flush();
            outputStream.close();
            objectStream.close();

        } catch (IOException ex) {
            Log.d(TAG, "Error: Failed to deserialize the computer object being entered into the database. ", ex.fillInStackTrace());
        }

        db.insertWithOnConflict(ComputerContractEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }

    @Override
    public int computerCount() {
        SQLiteDatabase db = this.mHelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, ComputerContractEntry.TABLE);
        db.close();

        return (int)count;
    }
}
