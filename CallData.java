package com.example.rashedalam.callpredictor;

public class CallData {



    public String date;
    public String qty;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public CallData() {
    }

    public CallData(String date, String qty) {
        this.date = date;
        this.qty = qty;
    }
}

