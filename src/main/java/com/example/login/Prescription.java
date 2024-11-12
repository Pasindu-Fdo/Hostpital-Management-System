package com.example.login;

import javafx.scene.control.Button;

public class Prescription {
    private String pid;
    private String sid;
    private String date;
    private String medicine;
    private String note;
    private String updateAction;
    private String deleteAction;

    private Integer patientID;
    private Integer doctorID;
    private String update;
    private String delete;


    public Prescription(Integer patientID, Integer doctorID, String date, String medicine, String note) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.date = date;
        this.medicine = medicine;
        this.note = note;
    }


    // Getters and setters
    public Integer getPid() {
        return patientID;
    }

    public void setPid(Integer pid) {
        this.patientID = pid;
    }

    public Integer getSid() {
        return doctorID;
    }

    public void setSid(Integer sid) {
        this.doctorID = sid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(String updateAction) {
        this.updateAction = updateAction;
    }

    public String getDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(String deleteAction) {
        this.deleteAction = deleteAction;
    }
}
