package com.example.rashedalam.callpredictor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class BroadCastClass extends BroadcastReceiver {
    MediaPlayer mp;
    FirebaseAuth mAuth;

    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Internet Data Sync! ", Toast.LENGTH_LONG).show();
        mAuth = FirebaseAuth.getInstance();

        //enabling offline capabilites...
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mp = MediaPlayer.create(context, R.raw.asd);

        Date date = new Date();
        mp.start();

        // Toast.makeText(context, date.getHours() + " hr & min" + date.getMinutes(), Toast.LENGTH_LONG).show();
        updateInternetDataToServer();

    }


    private void updateInternetDataToServer() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//enabling offline capabalities,

//get day
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_MONTH);
//get prev day data. and delete from it if necessary

        //formatting AM PM
        DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
        String dateString = dateFormat.format(new Date()).toString();


        if (dateString.contains("AM")) {
            DatabaseReference myRef = database.getReference("User").child(mAuth.getCurrentUser().getPhoneNumber())
                    .child("data").child(String.valueOf(day)).child("AM");

            myRef.setValue(
                    String.valueOf(InternetUsage.getSentBytes() + InternetUsage.getReceivedBytes()));

        } else {
            DatabaseReference myRef = database.getReference("User").child(mAuth.getCurrentUser().getPhoneNumber())
                    .child("data").child(String.valueOf(day)).child("PM");

            myRef.setValue(
                    String.valueOf(InternetUsage.getSentBytes() + InternetUsage.getReceivedBytes()));

        }



    }


    private void updatePrevDayInternetData(Context context, String data) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User").child(mAuth.getCurrentUser().getPhoneNumber())
                .child("prevDay");

        myRef.setValue(
                String.valueOf(data));

        Toast.makeText(context, "updatedDB", Toast.LENGTH_SHORT).show();
    }


}


