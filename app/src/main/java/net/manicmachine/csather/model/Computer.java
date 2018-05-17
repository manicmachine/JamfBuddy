package net.manicmachine.csather.model;

import java.util.ArrayList;

/**
 * Created by csather on 4/21/18.
 */

public class Computer extends Device {

    private ArrayList<String> mdmCapableUsers;
    private ArrayList<Hdd> storage;
    private ArrayList<LocalUser> localUsers;

    public Computer(){
        super();
        this.mdmCapableUsers = new ArrayList<>();
        this.storage = new ArrayList<>();
        this.localUsers = new ArrayList<>();
    }

    public ArrayList<String> getMdmCapableUsers() {
        return mdmCapableUsers;
    }

    public void setMdmCapableUsers(ArrayList<String> mdmCapableUsers) {
        this.mdmCapableUsers = mdmCapableUsers;
    }

    public ArrayList<Hdd> getStorage() {
        return storage;
    }

    public void setStorage(ArrayList<Hdd> storage) {
        this.storage = storage;
    }

    public ArrayList<LocalUser> getLocalusers() {
        return localUsers;
    }

    public void setLocalusers(ArrayList<LocalUser> localusers) {
        this.localUsers = localusers;
    }
}
