package com.example.mhtdoctorapp;

public class ModelRequestList {

    String Name, Id, Time;

    public ModelRequestList() {
    }

    public ModelRequestList(String name, String id, String time) {
        Name = name;
        Id = id;
        Time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
