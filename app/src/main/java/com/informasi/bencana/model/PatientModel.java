package com.informasi.bencana.model;

public class PatientModel {
    private String id, name, doctor, nurse, support, gender, age, date, number, location, phoneDoctor,
            weaknessCondition, threadCondition, remark, stepOne, stepTwo, stepThree, locationLabel, emailDoctor, year;

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

    public String getNurse() {
        return nurse;
    }

    public void setNurse(String nurse) {
        this.nurse = nurse;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeaknessCondition() {
        return weaknessCondition;
    }

    public void setWeaknessCondition(String weaknessCondition) {
        this.weaknessCondition = weaknessCondition;
    }

    public String getThreadCondition() {
        return threadCondition;
    }

    public void setThreadCondition(String threadCondition) {
        this.threadCondition = threadCondition;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }

    public String getPhoneDoctor() {
        return phoneDoctor;
    }

    public void setPhoneDoctor(String phoneDoctor) {
        this.phoneDoctor = phoneDoctor;
    }

    public String getEmailDoctor() {
        return emailDoctor;
    }

    public void setEmailDoctor(String emailDoctor) {
        this.emailDoctor = emailDoctor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
