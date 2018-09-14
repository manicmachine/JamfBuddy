package net.manicmachine.csather.model;

/**
 * Created by csather on 4/24/18.
 */

public class User implements Record {

    private int id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String position;
    private int[] assignedComputers;
    private int[] assignedMobileDevices;

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
