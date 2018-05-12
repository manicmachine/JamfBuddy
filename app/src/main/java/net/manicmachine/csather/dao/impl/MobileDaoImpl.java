package net.manicmachine.csather.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.manicmachine.csather.dao.MobileDao;
import net.manicmachine.csather.db.MobileDeviceContractEntry;
import net.manicmachine.csather.db.MobileDeviceDBHelper;
import net.manicmachine.csather.model.MobileDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MobileDaoImpl implements MobileDao {

    private static final String TAG = "MobileDaoImpl";
    private MobileDeviceDBHelper mHelper;

    public MobileDaoImpl(Context context) {
        this.mHelper = new MobileDeviceDBHelper(context);
    }

    @Override
    public MobileDevice getMobileDevice(int mobileDeviceId) {
        MobileDevice mobileDevice = new MobileDevice();

        HashMap<String, String> generalInfo = new HashMap<>();
        HashMap<String, String> hardwareInfo = new HashMap<>();
        HashMap<String, String> purchasingInfo = new HashMap<>();
        HashMap<String, String> osInfo = new HashMap<>();
        HashMap<String, String> locInfo = new HashMap<>();
        HashMap<String, String> securityInfo = new HashMap<>();

        SQLiteDatabase db = this.mHelper.getReadableDatabase();
        Cursor cursor = db.query(MobileDeviceContractEntry.TABLE,
                null,
                MobileDeviceContractEntry.ID + "=?",
                new String[]{Integer.toString(mobileDeviceId)},
                null,
                null,
                null);

        String[] columns = cursor.getColumnNames();

        for (String column : columns) {
            if (column.contains("GEN_")) {
                generalInfo.put(column.replace("GEN_",""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains("HW_")) {
                hardwareInfo.put(column.replace("HW_",""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains("OPSYS_")) {
                osInfo.put(column.replace("OPSYS_", ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains("PUR_")) {
                purchasingInfo.put(column.replace("PUR_", ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains("LOC_")) {
                locInfo.put(column.replace("LOC_", ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains("SEC_")) {
                securityInfo.put(column.replace("SEC_", ""), cursor.getString(cursor.getColumnIndex(column)));
            }
        }

        mobileDevice.setGeneralInfo(generalInfo);
        mobileDevice.setHardwareInfo(hardwareInfo);
        mobileDevice.setPurchasingInfo(purchasingInfo);
        mobileDevice.setOsInfo(osInfo);
        mobileDevice.setLocInfo(locInfo);
        mobileDevice.setSecurityInfo(securityInfo);

        return mobileDevice;
    }

    @Override
    public ArrayList<MobileDevice> getAllMobileDevices() {
        ArrayList<MobileDevice> mobileDevices = new ArrayList<>();
        HashMap<String, String> generalInfo = new HashMap<>();
        HashMap<String, String> hardwareInfo = new HashMap<>();

        SQLiteDatabase db = this.mHelper.getReadableDatabase();
        Cursor cursor = db.query(MobileDeviceContractEntry.TABLE,
                new String[]{
                        MobileDeviceContractEntry.ID,
                        MobileDeviceContractEntry.COL_MOBILE_GEN_NAME,
                        MobileDeviceContractEntry.COL_MOBILE_HW_MODEL,
                        MobileDeviceContractEntry.COL_MOBILE_GEN_ASSET,
                        MobileDeviceContractEntry.COL_MOBILE_GEN_LAST_INV},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            MobileDevice newMobileDevice = new MobileDevice();

            generalInfo.put(MobileDeviceContractEntry.ID,
                    Integer.toString(cursor.getInt(cursor.getColumnIndex(MobileDeviceContractEntry.ID))));
            generalInfo.put(MobileDeviceContractEntry.COL_MOBILE_GEN_NAME,
                    cursor.getString(cursor.getColumnIndex(MobileDeviceContractEntry.COL_MOBILE_GEN_NAME)));
            generalInfo.put(MobileDeviceContractEntry.COL_MOBILE_GEN_ASSET,
                    cursor.getString(cursor.getColumnIndex(MobileDeviceContractEntry.COL_MOBILE_GEN_ASSET)));
            generalInfo.put(MobileDeviceContractEntry.COL_MOBILE_GEN_LAST_INV,
                    Integer.toString(cursor.getInt(cursor.getColumnIndex(MobileDeviceContractEntry.COL_MOBILE_GEN_LAST_INV))));

            hardwareInfo.put(MobileDeviceContractEntry.COL_MOBILE_HW_MODEL,
                    cursor.getString(cursor.getColumnIndex(MobileDeviceContractEntry.COL_MOBILE_HW_MODEL)));

            newMobileDevice.setGeneralInfo(generalInfo);
            newMobileDevice.setHardwareInfo(hardwareInfo);

            mobileDevices.add(newMobileDevice);

            generalInfo.clear();
            hardwareInfo.clear();
        }

        return mobileDevices;

    }

    @Override
    public void deleteMobileDevice(int mobileDeviceId) {
        SQLiteDatabase db = this.mHelper.getWritableDatabase();
        db.delete(MobileDeviceContractEntry.TABLE,
                MobileDeviceContractEntry.ID + " = ?",
                new String[]{Integer.toString(mobileDeviceId)});

        db.close();
    }

    @Override
    public void addMobileDevice(MobileDevice mobileDevice) {
        SQLiteDatabase db = this.mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        for (Map.Entry<String, String> entry : mobileDevice.getGeneralInfo().entrySet()) {
            String column = entry.getKey();
            String value = entry.getValue();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : mobileDevice.getHardwareInfo().entrySet()) {
            String column = entry.getKey();
            String value = entry.getValue();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : mobileDevice.getOsInfo().entrySet()) {
            String column = entry.getKey();
            String value = entry.getValue();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : mobileDevice.getPurchasingInfo().entrySet()) {
            String column = entry.getKey();
            String value = entry.getValue();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : mobileDevice.getLocInfo().entrySet()) {
            String column = entry.getKey();
            String value = entry.getValue();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : mobileDevice.getSecurityInfo().entrySet()) {
            String column = entry.getKey();
            String value = entry.getValue();

            values.put(column, value);
        }

        db.insertWithOnConflict(MobileDeviceContractEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }
}
