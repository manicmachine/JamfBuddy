package net.manicmachine.csather.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import net.manicmachine.csather.dao.MobileDao;
import net.manicmachine.csather.db.DBHelper;
import net.manicmachine.csather.db.MobileDeviceContractEntry;
import net.manicmachine.csather.model.MobileDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MobileDaoImpl implements MobileDao {

    private static final String TAG = "net.manicmachine.csather.dao.impl.MobileDaoImpl";
    private static final String GEN_PREFIX = "GEN_";
    private static final String HW_PREFIX = "HW_";
    private static final String OPSYS_PREFIX = "OPSYS_";
    private static final String PUR_PREFIX = "PUR_";
    private static final String LOC_PREFIX = "LOC_";
    private static final String SEC_PREFIX = "SOC_";

    private DBHelper mHelper;

    public MobileDaoImpl(Context context) {
        this.mHelper = new DBHelper(context);
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
            if (column.contains(GEN_PREFIX)) {
                generalInfo.put(column.replace(GEN_PREFIX,""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains(HW_PREFIX)) {
                hardwareInfo.put(column.replace(HW_PREFIX,""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains(OPSYS_PREFIX)) {
                osInfo.put(column.replace(OPSYS_PREFIX, ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains(PUR_PREFIX)) {
                purchasingInfo.put(column.replace(PUR_PREFIX, ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains(LOC_PREFIX)) {
                locInfo.put(column.replace(LOC_PREFIX, ""), cursor.getString(cursor.getColumnIndex(column)));
            } else if (column.contains(SEC_PREFIX)) {
                securityInfo.put(column.replace(SEC_PREFIX, ""), cursor.getString(cursor.getColumnIndex(column)));
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
            HashMap<String, String> generalInfo = new HashMap<>();
            HashMap<String, String> hardwareInfo = new HashMap<>();

            generalInfo.put(MobileDeviceContractEntry.ID.replace(GEN_PREFIX, ""),
                    Integer.toString(cursor.getInt(cursor.getColumnIndex(MobileDeviceContractEntry.ID))));
            generalInfo.put(MobileDeviceContractEntry.COL_MOBILE_GEN_NAME.replace(GEN_PREFIX, ""),
                    cursor.getString(cursor.getColumnIndex(MobileDeviceContractEntry.COL_MOBILE_GEN_NAME)));
            generalInfo.put(MobileDeviceContractEntry.COL_MOBILE_GEN_ASSET.replace(GEN_PREFIX, ""),
                    cursor.getString(cursor.getColumnIndex(MobileDeviceContractEntry.COL_MOBILE_GEN_ASSET)));
            generalInfo.put(MobileDeviceContractEntry.COL_MOBILE_GEN_LAST_INV.replace(GEN_PREFIX, ""),
                    Integer.toString(cursor.getInt(cursor.getColumnIndex(MobileDeviceContractEntry.COL_MOBILE_GEN_LAST_INV))));

            hardwareInfo.put(MobileDeviceContractEntry.COL_MOBILE_HW_MODEL.replace(HW_PREFIX, ""),
                    cursor.getString(cursor.getColumnIndex(MobileDeviceContractEntry.COL_MOBILE_HW_MODEL)));

            newMobileDevice.setGeneralInfo(generalInfo);
            newMobileDevice.setHardwareInfo(hardwareInfo);

            mobileDevices.add(newMobileDevice);

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
            String column = GEN_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : mobileDevice.getHardwareInfo().entrySet()) {
            String column = HW_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : mobileDevice.getOsInfo().entrySet()) {
            String column = OPSYS_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : mobileDevice.getPurchasingInfo().entrySet()) {
            String column = PUR_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : mobileDevice.getLocInfo().entrySet()) {
            String column = LOC_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        for (Map.Entry<String, String> entry : mobileDevice.getSecurityInfo().entrySet()) {
            String column = SEC_PREFIX + entry.getKey().toString();
            String value = entry.getValue().toString();

            values.put(column, value);
        }

        db.insertWithOnConflict(MobileDeviceContractEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }

    @Override
    public int mobileCount() {
        SQLiteDatabase db = this.mHelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, MobileDeviceContractEntry.TABLE);
        db.close();

        return (int)count;
    }
}
