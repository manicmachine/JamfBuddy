package net.manicmachine.csather.model;

/**
 * Created by csather on 4/24/18.
 */

public class Hdd {
    private String disk;
    private String hddModel;
    private String hddRevision;
    private String hddSerial;
    private String smartStatus;
    private String encrypted;
    private double hddCapacityMB;
    private int hddUsed;
    private int numPartitions;

    class Partition {
        private String partName;
        private String type;
        private String fv2Status;
        private int partUsed;
        private double fv2Percent;
        private double partCapacityMB;

        public String getPartName() {
            return partName;
        }

        public void setPartName(String partName) {
            this.partName = partName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFv2Status() {
            return fv2Status;
        }

        public void setFv2Status(String fv2Status) {
            this.fv2Status = fv2Status;
        }

        public int getPartUsed() {
            return partUsed;
        }

        public void setPartUsed(int partUsed) {
            this.partUsed = partUsed;
        }

        public double getFv2Percent() {
            return fv2Percent;
        }

        public void setFv2Percent(double fv2Percent) {
            this.fv2Percent = fv2Percent;
        }

        public double getPartCapacityMB() {
            return partCapacityMB;
        }

        public void setPartCapacityMB(double partCapacityMB) {
            this.partCapacityMB = partCapacityMB;
        }
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getHddModel() {
        return hddModel;
    }

    public void setHddModel(String hddModel) {
        this.hddModel = hddModel;
    }

    public String getHddRevision() {
        return hddRevision;
    }

    public void setHddRevision(String hddRevision) {
        this.hddRevision = hddRevision;
    }

    public String getHddSerial() {
        return hddSerial;
    }

    public void setHddSerial(String hddSerial) {
        this.hddSerial = hddSerial;
    }

    public String getSmartStatus() {
        return smartStatus;
    }

    public void setSmartStatus(String smartStatus) {
        this.smartStatus = smartStatus;
    }

    public String getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
    }

    public double getHddCapacityMB() {
        return hddCapacityMB;
    }

    public void setHddCapacityMB(double hddCapacityMB) {
        this.hddCapacityMB = hddCapacityMB;
    }

    public int getHddUsed() {
        return hddUsed;
    }

    public void setHddUsed(int hddUsed) {
        this.hddUsed = hddUsed;
    }

    public int getNumPartitions() {
        return numPartitions;
    }

    public void setNumPartitions(int numPartitions) {
        this.numPartitions = numPartitions;
    }
}
