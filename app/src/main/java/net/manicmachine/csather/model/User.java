package net.manicmachine.csather.model;

/**
 * Created by csather on 4/24/18.
 */

public class User {

    private int id;
    private String name;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String position;
    private int[] assignedComputers;
    private int[] assignedMobileDevices;

    public User(int id, String name, String fullName) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int[] getAssignedComputers() {
        return assignedComputers;
    }

    public void setAssignedComputers(int[] assignedComputers) {
        this.assignedComputers = assignedComputers;
    }

    public int[] getAssignedMobileDevices() {
        return assignedMobileDevices;
    }

    public void setAssignedMobileDevices(int[] assignedMobileDevices) {
        this.assignedMobileDevices = assignedMobileDevices;
    }
}
