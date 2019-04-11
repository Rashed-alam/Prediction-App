package com.example.rashedalam.callpredictor;

public class SMSdata {



        public String date;
        public String qty;

        // Default constructor required for calls to
        // DataSnapshot.getValue(User.class)
        public SMSdata() {
        }

        public SMSdata(String date, String qty) {
            this.date = date;
            this.qty = qty;
        }
    }

