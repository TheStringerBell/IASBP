package com.example.soram.iasbp.pojo;


public class GetHumiData {
    String Date;
    String Time;
    String Value;
    public String getDate(){
        return Date;
    }
    public String getTime(){
        return Time;
    }
    public String getValue(){
        return Value;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }
    public void setTime(String Time) {
        this.Time = Time;
    }
    public void setValue(String Value) {
        this.Value = Value;
    }
}
