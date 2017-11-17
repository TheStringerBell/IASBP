package com.example.soram.iasbp;

/**
 * Created by sOram on 17. 11. 2017.
 */

public class GetControlData {
    String Status;
    String Mode;
    String lowMin;
    String lowMax;
    String highMin;
    String highMax;

    public String getHighMax() {
        return highMax;
    }

    public String getHighMin() {
        return highMin;
    }

    public String getLowMax() {
        return lowMax;
    }

    public String getLowMin() {
        return lowMin;
    }

    public String getMode() {
        return Mode;
    }

    public String getStatus() {
        return Status;
    }

    public void setHighMax(String highMax) {
        this.highMax = highMax;
    }

    public void setHighMin(String highMin) {
        this.highMin = highMin;
    }

    public void setLowMax(String lowMax) {
        this.lowMax = lowMax;
    }

    public void setLowMin(String lowMin) {
        this.lowMin = lowMin;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public void setStatus(String status) {
        Status = status;
    }

}
