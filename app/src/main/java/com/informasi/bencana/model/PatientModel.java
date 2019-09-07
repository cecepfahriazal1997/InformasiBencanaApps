package com.informasi.bencana.model;

public class PatientModel {
    private String id, name, doctor, gender, age, stepOne, stepTwo, stepThree;

    public PatientModel() {
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

    public String getStepOne() {
        return stepOne;
    }

    public void setStepOne(String stepOne) {
        this.stepOne = stepOne;
    }

    public String getStepTwo() {
        return stepTwo;
    }

    public void setStepTwo(String stepTwo) {
        this.stepTwo = stepTwo;
    }

    public String getStepThree() {
        return stepThree;
    }

    public void setStepThree(String stepThree) {
        this.stepThree = stepThree;
    }
}
