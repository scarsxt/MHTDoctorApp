package com.example.mhtdoctorapp;

public class ModelDoctorPatientList {

    String NameUser, Time, PatientId;

    public ModelDoctorPatientList() {
    }

    public ModelDoctorPatientList(String nameUser, String time, String patientId) {
        NameUser = nameUser;
        Time = time;
        PatientId = patientId;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPatientId() {
        return PatientId;
    }

    public void setPatientId(String patientId) {
        PatientId = patientId;
    }
}
