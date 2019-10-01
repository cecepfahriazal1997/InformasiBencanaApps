package com.informasi.bencana.model;

import org.json.JSONArray;

public class PatientProgressModel {
    private String id, name, doctor, nurse, support, gender, age, date, progressId, year, month,
                    week, day, complication, complicationDtl, progress, status, remark,
                    complicationLabel, complicationDtlLabel;
    private JSONArray listProgress;

    public PatientProgressModel() {
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

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getComplication() {
        return complication;
    }

    public void setComplication(String complication) {
        this.complication = complication;
    }

    public String getComplicationDtl() {
        return complicationDtl;
    }

    public void setComplicationDtl(String complicationDtl) {
        this.complicationDtl = complicationDtl;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONArray getListProgress() {
        return listProgress;
    }

    public void setListProgress(JSONArray listProgress) {
        this.listProgress = listProgress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getComplicationLabel() {
        return complicationLabel;
    }

    public void setComplicationLabel(String complicationLabel) {
        this.complicationLabel = complicationLabel;
    }

    public String getComplicationDtlLabel() {
        return complicationDtlLabel;
    }

    public void setComplicationDtlLabel(String complicationDtlLabel) {
        this.complicationDtlLabel = complicationDtlLabel;
    }
}