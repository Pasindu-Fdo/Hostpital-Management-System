package com.example.login.Classes;

public class Patient {
    private String name;
    private String age;
    private String hometown;
    private String telephone;

    public Patient(String name, String age, String hometown, String telephone) {
        this.name = name;
        this.age = age;
        this.hometown = hometown;
        this.telephone = telephone;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String gethometown() { return hometown; }
    public void setGender(String gender) { this.hometown = hometown; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}

