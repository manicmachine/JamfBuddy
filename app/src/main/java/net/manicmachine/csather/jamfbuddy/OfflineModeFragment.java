package net.manicmachine.csather.jamfbuddy;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import net.manicmachine.csather.dao.impl.ComputerDaoImpl;
import net.manicmachine.csather.dao.impl.MobileDaoImpl;
import net.manicmachine.csather.dao.impl.UserDaoImpl;

public class OfflineModeFragment extends DialogFragment{

    protected final static String TAG = "net.manicmachine.csather.jamfbuddy.OfflineModeFragment";

    UserInfo userInfo;
    ComputerDaoImpl computerDao;
    MobileDaoImpl mobileDao;
    UserDaoImpl userDao;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        userInfo = UserInfo.get(App.getContext());
        computerDao = new ComputerDaoImpl(App.getContext());
        mobileDao = new MobileDaoImpl(App.getContext());
        userDao = new UserDaoImpl(App.getContext());

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.offlineModeTitle)
                .setMessage(R.string.offlineModeBody)
                .setPositiveButton(R.string.offlineModeOk, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startOffline();
                    }
                })
                .setNegativeButton(R.string.offlineModeNo, null)
                .create();
    }

    public void startOffline() {
        userInfo.setComputers(computerDao.getAllComputers());
        userInfo.setMobileDevices(mobileDao.getAllMobileDevices());
        userInfo.setUsers(userDao.getAllUsers());

        Intent intent = new Intent(App.getContext(), RecordListActivity.class);
        startActivity(intent);
    }

}
