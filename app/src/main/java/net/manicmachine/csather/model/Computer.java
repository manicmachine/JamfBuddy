package net.manicmachine.csather.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by csather on 4/21/18.
 */

public class Computer {

    // General
    private int compId;
    private int barCode1;
    private int barCode2;
    private String compName;
    private String site;
    private String ipAddress;
    private String reportedIpAddress;
    private String jamfBinaryVersion;
    private String platform;
    private String managed;
    private String mdmCapability;
    private String assetTag;
    private String bleCapability;
    private String loggedIntoItunes;
    private ArrayList<String> mdmCapableUsers;
    private ArrayList<ExtensionAttribute> extAttributes;
    private LocalDateTime lastInventory;
    private LocalDateTime lastCheckIn;
    private LocalDateTime lastEnrollment;

    // Hardware
    private int numOfProcessors;
    private int numOfCores;
    private int batteryCapacity;
    private String make;
    private String model;
    private String modelIdentifier;
    private String udid;
    private String serial;
    private String processorSpeed;
    private String processorType;
    private String arcType;
    private String busSpeed;
    private String cacheSize;
    private String primaryMacAddress;
    private String secondaryMacAddress;
    private String totalRam;
    private String availRamSlots;
    private String smcVersion;
    private String nicSpeed;
    private String opticalDrive;
    private String bootRom;

    // Operating System
    private String os;
    private String osVersion;
    private String osBuild;
    private String activeDirStatus;
    private String masterPassSet;
    private String filVaultUsers;

    // User & Location
    private User user;
    private String department;
    private String building;
    private String room;

    // Purchasing
    private double purchasePrice;
    private String purchaseOrLeased;
    private String poNumber;
    private String vendor;
    private String appleCareId;
    private String lifeExpectancy;
    private String purchasingAccount;
    private String purchasingContact;
    private LocalDateTime poDate;
    private LocalDateTime expiration;
    private LocalDateTime leaseExpiration;

    // Hdd
    private ArrayList<Hdd> storage;

    // Local Users
    private ArrayList<LocalUser> localusers;


    private HashMap<String, String> generalInfo;
    private HashMap<String, String> hardwareInfo;
    private HashMap<String, String> osInfo;
    private HashMap<String, String> purchasingInfo;


    public void addProperty(String key, String value) {
        generalInfo.put(key, value);
    }

    public




}
