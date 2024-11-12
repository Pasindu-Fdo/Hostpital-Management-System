package com.example.login.Classes;

public class Patient1 {
    private Integer pid;
    private String name;
    private String age;
    private String hometown;
    private String telephone;

    public Patient1(Integer pid, String name, String age, String hometown, String telephone) {
        this.pid = pid;
        this.name = name;
        this.age = age;
        this.hometown = hometown;
        this.telephone = telephone;
    }

    // Getters and Setters
    public Integer getPid() { return pid; }
    public void setPid(int pid) { this.pid = pid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String gethometown() { return hometown; }
    public void setGender(String gender) { this.hometown = hometown; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}

