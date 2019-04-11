package com.example.rashedalam.callpredictor;

public class Pdata {



    public String qty;
    public String price;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Pdata() {
    }

    public Pdata(String qty, String price) {
        this.qty = qty;
        this.price = price;
    }
}
