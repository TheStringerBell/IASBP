package com.example.soram.iasbp;

/**
 * Created by sOram on 1. 4. 2018.
 */

public class ListModel {
    String hostname;
    String ipAddress;

    public ListModel(String hostname, String ipAddress){
        this.hostname = hostname;
        this.ipAddress = ipAddress;

    }

    public String getHostname() {
        return hostname;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
