package com.example.rashedalam.callpredictor;


public class NetData {



    public String AM;
    public String PM;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public NetData() {
    }

    public NetData(String AM, String PM) {
        this.AM = AM;
        this.PM = PM;
    }
}

