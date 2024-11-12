package com.example.login.medicine;

public class Medicine {
    private int medicineID;
    private String name;
    private int amount;
    private double price;
    private String expiryDate;

    public Medicine(int medicineID, String name, int amount, double price, String expiryDate) {
        this.medicineID = medicineID;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}
