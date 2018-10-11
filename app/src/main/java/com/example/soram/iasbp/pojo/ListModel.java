package com.example.soram.iasbp.pojo;



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
