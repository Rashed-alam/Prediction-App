package com.example.rashedalam.callpredictor;

import android.net.TrafficStats;

/**
 * Created by asifsabir on 6/4/18.
 */

public class InternetUsage extends TrafficStats {
    static long receivedBytes  =  getMobileRxBytes();
   static long sentBytes = getMobileTxBytes();


    public static long getReceivedBytes(){
        return receivedBytes/1000000;
    }
    public static long getSentBytes(){
        return sentBytes/1000000;
    }
}
