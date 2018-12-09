package com.example.soram.iasbp.db.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "connections")
public class Connections {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "hostname")
    private String hostname;

    @ColumnInfo(name = "ip")
    private String ip;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
