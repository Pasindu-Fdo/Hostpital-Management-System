package com.example.login.demo1.Models;

public class User {
    private int sid;
    private String name;
    private int mobile;
    private String address;
    private String jobtitle;
    private String password;

    public User(String name, int mobile, String address, String jobtitle, String password) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.jobtitle = jobtitle;
        this.password = password;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
