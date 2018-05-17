package net.manicmachine.csather.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import net.manicmachine.csather.dao.UserDao;
import net.manicmachine.csather.db.DBHelper;
import net.manicmachine.csather.db.UserContractEntry;
import net.manicmachine.csather.db.UserDevicesContractEntry;
import net.manicmachine.csather.model.User;

import java.util.ArrayList;

public class UserDaoImpl implements UserDao {

    private static final String TAG = "net.manicmachine.csather.dao.impl.UserDaoImpl";
    private static final String GEN_PREFIX = "GEN_";

    private DBHelper mHelper;

    public UserDaoImpl(Context context) {
        this.mHelper = new DBHelper(context);
    }

    @Override
    public User getUser(int userId) {
        User user = new User();

        int[] assignedComputers = null;
        int[] assignedMobileDevices = null;

        SQLiteDatabase db = this.mHelper.getReadableDatabase();
        Cursor cursor = db.query(UserContractEntry.TABLE,
                null,
                UserContractEntry.ID + "=?",
                new String[] {Integer.toString(userId)},
                null,
                null,
                null);

        user.setId(cursor.getInt(cursor.getColumnIndex(UserContractEntry.ID)));
        user.setFullName(cursor.getString(cursor.getColumnIndex(UserContractEntry.COL_USER_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(UserContractEntry.COL_USER_EMAIL)));
        user.setPhoneNumber(cursor.getString(cursor.getColumnIndex(UserContractEntry.COL_USER_PHONE)));
        user.setPosition(cursor.getString(cursor.getColumnIndex(UserContractEntry.COL_USER_POSITION)));

        cursor.close();

        cursor = db.query(UserDevicesContractEntry.TABLE,
                null,
                UserDevicesContractEntry.COL_USER_DEVICES_USER_ID + "=?",
                new String[] {Integer.toString(user.getId())},
                null,
                null,
                null);

        int numComputers = 0;
        int numMobileDevices = 0;

        while(cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(UserDevicesContractEntry.COL_USER_DEVICES_COMPUTER_ID)) != -1) {
                numComputers++;
            } else {
                numMobileDevices++;
            }
        }

        assignedComputers = new int[numComputers];
        assignedMobileDevices = new int[numMobileDevices];

        int compCounter = 0;
        int mdCounter = 0;
        int compId = 0;
        int mdId = 0;

        for (int i = 0; i <= (numComputers + numMobileDevices + 1); i++ ) {
            cursor.moveToPosition(i);
            compId = cursor.getInt(cursor.getColumnIndex(UserDevicesContractEntry.COL_USER_DEVICES_COMPUTER_ID));
            mdId = cursor.getInt(cursor.getColumnIndex(UserDevicesContractEntry.COL_USER_DEVICES_MOBILE_ID));

            if (compId != -1) {
                assignedComputers[compCounter] = compId;
                compCounter++;
            } else {
                assignedMobileDevices[mdCounter] = mdId;
                mdCounter++;
            }
        }

        cursor.close();

        user.setAssignedComputers(assignedComputers);
        user.setAssignedMobileDevices(assignedMobileDevices);

        return user;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        SQLiteDatabase db = this.mHelper.getReadableDatabase();
        Cursor cursor = db.query(UserContractEntry.TABLE,
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            User newUser = new User();

            newUser.setId(cursor.getInt(cursor.getColumnIndex(UserContractEntry.ID)));
            newUser.setFullName(cursor.getString(cursor.getColumnIndex(UserContractEntry.COL_USER_NAME)));
            newUser.setEmail(cursor.getString(cursor.getColumnIndex(UserContractEntry.COL_USER_EMAIL)));
            newUser.setPhoneNumber(cursor.getString(cursor.getColumnIndex(UserContractEntry.COL_USER_PHONE)));
            newUser.setPosition(cursor.getString(cursor.getColumnIndex(UserContractEntry.COL_USER_POSITION)));

            users.add(newUser);
        }

        return users;
    }

    @Override
    public void deleteUser(int userId) {
        SQLiteDatabase db = this.mHelper.getWritableDatabase();
        db.delete(UserContractEntry.TABLE,
                UserContractEntry.ID + " = ?",
                new String[]{Integer.toString(userId)});

        db.close();
    }

    @Override
    public void addUser(User user) {
        SQLiteDatabase db = this.mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UserContractEntry.ID, user.getId());
        values.put(UserContractEntry.COL_USER_NAME, user.getFullName());
        values.put(UserContractEntry.COL_USER_EMAIL, user.getEmail());
        values.put(UserContractEntry.COL_USER_PHONE, user.getPhoneNumber());
        values.put(UserContractEntry.COL_USER_POSITION, user.getPosition());

        db.insertWithOnConflict(UserContractEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }

    @Override
    public int userCount() {
        SQLiteDatabase db = this.mHelper.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, UserContractEntry.TABLE);
        db.close();

        return (int)count;
    }
}
