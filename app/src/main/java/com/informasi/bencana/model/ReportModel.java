package com.informasi.bencana.model;

public class ReportModel {
    private String id, name, doctor, nurse, gender, age, listProgress, listYear;

    public ReportModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getNurse() {
        return nurse;
    }

    public void setNurse(String nurse) {
        this.nurse = nurse;
    }

    public String getListProgress() {
        return listProgress;
    }

    public void setListProgress(String listProgress) {
        this.listProgress = listProgress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getListYear() {
        return listYear;
    }

    public void setListYear(String listYear) {
        this.listYear = listYear;
    }
}
