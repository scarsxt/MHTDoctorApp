package com.example.mhtdoctorapp;

public class ModelDoctorPatientList {

    String Name, Email, Time, PatientId;

    public ModelDoctorPatientList() {
    }

    public ModelDoctorPatientList(String name, String email, String time, String patientId) {
        Name = name;
        Email = email;
        Time = time;
        PatientId = patientId;
    }

    public String getPatientId() {
        return PatientId;
    }

    public void setPatientId(String patientId) {
        PatientId = patientId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
