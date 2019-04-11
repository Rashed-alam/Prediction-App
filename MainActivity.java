package com.example.rashedalam.callpredictor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Inet4Address;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView tvC1, tvC2, tvC3, tvC4, tvC5, tvC6, tvC7, tvC8, tvC9, tvC10,
            tvS1, tvS2, tvS3, tvS4, tvS5, tvS6, tvS7, tvS8, tvS9, tvS10,
            tvD1, tvD2, tvD3, tvD4, tvD5, tvD6, tvD7, tvD8, tvD9, tvD10;

    int callUp = 0, callDown = 0, smsUp = 0, smsDown = 0, dataUp = 0, dataDown = 0;
    Button btnLogOut;
    FirebaseAuth mAuth;
    Button btnLink, btnPredict, btnOffer;
    TextView tvCallsPredict, tvSMSpredict, tvDataPredict;
    //phonecall arryalist
    ArrayList<Integer> callUsageList = new ArrayList<Integer>();
    ArrayList<Integer> smsUsageList = new ArrayList<Integer>();
    ArrayList<Integer> dataUsageList = new ArrayList<Integer>();

    //string of table
    String tableString = "";
    String sAM = "", sPM = "", oAM = "", oPM = "";

    private static final int PERMISSION_READ_STATE = 0;
    // TextView textOut;
    ProgressBar pb1;
    TextView tvShowCallUsageSelfAM, tvShowCallUsageOtherAM,
            tvShowCallUsageSelfPM, tvShowCallUsageOtherPM, tvOperator;
    TextView tvTable, tvSMStable;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLink = (Button) findViewById(R.id.btn_scrap_link);
        btnPredict = (Button) findViewById(R.id.btn_predict);
        btnOffer = (Button) findViewById(R.id.btn_offers);
        mAuth = FirebaseAuth.getInstance();

        ScheduleDailyN();
        getDataHistory();

//        DatabaseReference myRef = FirebaseDatabase.getInstance().
//                getReference("User").child("call pack");
//
//        String key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("5"), String.valueOf("3")));
//         key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("10"), String.valueOf("5")));
//         key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("50"), String.valueOf("15")));
//         key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("100"), String.valueOf("55")));
//         key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("200"), String.valueOf("100")));
//         key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("300"), String.valueOf("120")));
//         key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("400"), String.valueOf("150")));
//         key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("500"), String.valueOf("200")));
//         key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("1000"), String.valueOf("355")));
//         key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("paygo"), String.valueOf("regular price")));
//
//
//
//
//         myRef = FirebaseDatabase.getInstance().
//                getReference("User").child("sms pack");
//
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("100"), String.valueOf("6")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("300"), String.valueOf("10")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("1000"), String.valueOf("15")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("2000"), String.valueOf("20")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("5000"), String.valueOf("50")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("10000"), String.valueOf("100")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("15000"), String.valueOf("150")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("25000"), String.valueOf("200")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("50000"), String.valueOf("500")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("50000+"), String.valueOf("0.12 per sms")));
//
//
//
//
//
//         myRef = FirebaseDatabase.getInstance().
//                getReference("User").child("data pack");
//
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("5"), String.valueOf("1")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("10"), String.valueOf("2")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("50"), String.valueOf("15")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("100"), String.valueOf("19")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("500"), String.valueOf("59")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf(" 1000"), String.valueOf("109")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("1500"), String.valueOf("159")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("2000"), String.valueOf("200")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("5000"), String.valueOf("349")));
//        key = myRef.push().getKey();
//        myRef.child(key).setValue(new Pdata(String.valueOf("50000"), String.valueOf("2750")));


        tvC1 = (TextView) findViewById(R.id.C1);
        tvC2 = (TextView) findViewById(R.id.C2);
        tvC3 = (TextView) findViewById(R.id.C3);
        tvC4 = (TextView) findViewById(R.id.C4);
        tvC5 = (TextView) findViewById(R.id.C5);
        tvC6 = (TextView) findViewById(R.id.C6);
        tvC7 = (TextView) findViewById(R.id.C7);
        tvC8 = (TextView) findViewById(R.id.C8);
        tvC9 = (TextView) findViewById(R.id.C9);
        tvC10 = (TextView) findViewById(R.id.C10);

        tvS1 = (TextView) findViewById(R.id.S1);
        tvS2 = (TextView) findViewById(R.id.S2);
        tvS3 = (TextView) findViewById(R.id.S3);
        tvS4 = (TextView) findViewById(R.id.S4);
        tvS5 = (TextView) findViewById(R.id.S5);
        tvS6 = (TextView) findViewById(R.id.S6);
        tvS7 = (TextView) findViewById(R.id.S7);
        tvS8 = (TextView) findViewById(R.id.S8);
        tvS9 = (TextView) findViewById(R.id.S9);
        tvS10 = (TextView) findViewById(R.id.S10);

        tvD1 = (TextView) findViewById(R.id.d1);
        tvD2 = (TextView) findViewById(R.id.d2);
        tvD3 = (TextView) findViewById(R.id.d3);
        tvD4 = (TextView) findViewById(R.id.d4);
        tvD5 = (TextView) findViewById(R.id.d5);
        tvD6 = (TextView) findViewById(R.id.d6);
        tvD7 = (TextView) findViewById(R.id.d7);
        tvD8 = (TextView) findViewById(R.id.d8);
        tvD9 = (TextView) findViewById(R.id.d9);
        tvD10 = (TextView) findViewById(R.id.d10);


        tvCallsPredict = (TextView) findViewById(R.id.tv_calls_predict);
        tvSMSpredict = (TextView) findViewById(R.id.tv_sms_predict);
        tvDataPredict = (TextView) findViewById(R.id.tv_data_predict);

        //setting up UI
        tvShowCallUsageSelfAM = (TextView) findViewById(R.id.call_info_self_AM);
        tvShowCallUsageOtherAM = (TextView) findViewById(R.id.call_info_other_AM);

        tvShowCallUsageSelfPM = (TextView) findViewById(R.id.call_info_self_PM);
        tvShowCallUsageOtherPM = (TextView) findViewById(R.id.call_info_other_PM);
        tvOperator = (TextView) findViewById(R.id.tv_operator);

        //button
        btnLogOut = (Button) findViewById(R.id.btn_log_out);
        //table view
        tvTable = (TextView) findViewById(R.id.table_view);
        tvSMStable = (TextView) findViewById(R.id.sms_table_view);
        if (Build.VERSION.SDK_INT >= 21) {
            //asking for permissions
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_SMS}, PERMISSION_READ_STATE);
        }

//      //detecting sim card
//        detectSimCard();
//        ScheduleDaily();

        //********************EDIT EDIT EDIT EDIT EDIT*****************     ScheduleDailyN();

        pb1 = (ProgressBar) findViewById(R.id.pb);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, PhoneAuthActivity.class));
                finish();
            }
        });
        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInternetCluster();
                getCallsCluster();
                getSMScluster();
                btnOffer.setVisibility(View.VISIBLE);
            }
        });

        btnOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayOffer();
            }
        });
        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("https://www.robi.com.bd/packages/voice-bundles-sms-bundles-and-magic-packs?lang=eng#AlOHrsZqbyR8cgQf.97"));
//                startActivity(browserIntent);
                Intent i = new Intent(MainActivity.this, ActivityHistoryView.class);
                i.putExtra("STRING_I_NEED", tableString);
                startActivity(i);
                Log.d("po", "called" + tableString);
            }
        });


    }


    private void getCallDetails(Context context) {
        if (Build.VERSION.SDK_INT > 22) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            }
            // Call some material design APIs here
        }

        String OpDigit1 = "", OpDigit2 = "";
        String operator = getSimOperator();
        if (operator.equals("Banglalink")) {
            OpDigit1 = "019";
            OpDigit2 = "+88019";
        } else if (operator.equals("Grameenphone")) {
            OpDigit1 = "017";
            OpDigit2 = "+88017";
        } else if (operator.equals("Robi")) {
            OpDigit1 = "018";
            OpDigit2 = "+88018";
        } else if (operator.equals("Airtel")) {
            OpDigit1 = "016";
            OpDigit2 = "+88016";
        } else if (operator.equals("Teletalk")) {
            OpDigit1 = "015";
            OpDigit2 = "+88015";
        }

        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);

        int cDay = 0, cUse = 0, cMonth = 0;

        //total day looper primer
        int primerDay = 0, primerUsage = 0;

        while (cursor.moveToNext()) {
            String phNumber = cursor.getString(number);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);


            //detecting date of month
            Date callDayTime = new Date(Long.valueOf(callDate));

            //formatting AM PM
            DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
            String dateString = dateFormat.format(callDayTime).toString();


            Calendar c = Calendar.getInstance();
            c.setTime(callDayTime);
            int dayOfWeek = c.get(Calendar.DAY_OF_MONTH);
            cMonth = c.get(Calendar.MONTH) + 1;

            String callDuration = cursor.getString(duration);

            Log.d("okok", callDuration);

            //cluster data insert
            if (primerDay != dayOfWeek) {
                if (primerUsage != 0)
                    callUsageList.add(primerUsage);

                primerDay = dayOfWeek;
                primerUsage = Integer.valueOf(callDuration);
            } else {
                primerUsage += Integer.valueOf(callDuration);
            }

            int dircode = Integer.parseInt(callType);
            if (dircode == CallLog.Calls.OUTGOING_TYPE) {

                //user may enter 121 so check more digits
                if (phNumber.length() > 5) {

                    //own operator
                    if (phNumber.substring(0, 2).equals(OpDigit1) ||
                            phNumber.substring(0, 6).equals(OpDigit2)) {
                        //cheking AM PM

                        if (dateString.contains("AM")) {
                            if (dayOfWeek != cDay) {

                                sAM += cDay + ":" + cUse + "\n";

                                DatabaseReference myRef = FirebaseDatabase.getInstance().
                                        getReference("User").child(mAuth.getCurrentUser().getPhoneNumber())
                                        .child("outgoing").child("self").child("AM").child(String.valueOf(cMonth));

                                String key = myRef.push().getKey();
                                myRef.child(key).setValue(new CallData(String.valueOf(cDay), String.valueOf(cUse)));


                                cDay = dayOfWeek;
                                cUse = 0;
                                cUse += Integer.parseInt(callDuration);
                                //upload server all cDay,cMonth,cUse
                            } else {
                                cUse += Integer.parseInt(callDuration);
                            }
                        } else {
                            if (dayOfWeek != cDay) {
                                sPM += cDay + ":" + cUse + "\n";

                                DatabaseReference myRef = FirebaseDatabase.getInstance().
                                        getReference("User").child(mAuth.getCurrentUser().getPhoneNumber())
                                        .child("outgoing").child("self").child("PM").child(String.valueOf(cMonth));

                                String key = myRef.push().getKey();
                                myRef.child(key).setValue(new CallData(String.valueOf(cDay), String.valueOf(cUse)));


                                cDay = dayOfWeek;
                                cUse = 0;
                                cUse += Integer.parseInt(callDuration);
                                //upload server all cDay,cMonth,cUse
                            } else {
                                cUse += Integer.parseInt(callDuration);
                            }
                        }


                        tableString += phNumber + "  :  " + Integer.parseInt(callDuration) + "  : SELF  :  " + callDayTime + "\n";

                    } else {

                        //others operator########################

                        //checking AMPM
                        if (dateString.contains("AM")) {

                            if (dayOfWeek != cDay) {
                                oAM += cDay + ":" + cUse + "\n";

                                DatabaseReference myRef = FirebaseDatabase.getInstance().
                                        getReference("User").child(mAuth.getCurrentUser().getPhoneNumber())
                                        .child("outgoing").child("others").child("AM").child(String.valueOf(cMonth));

                                String key = myRef.push().getKey();
                                myRef.child(key).setValue(new CallData(String.valueOf(cDay), String.valueOf(cUse)));


                                cDay = dayOfWeek;
                                cUse = 0;
                                cUse += Integer.parseInt(callDuration);
                                //upload server all cDay,cMonth,cUse
                            } else {
                                cUse += Integer.parseInt(callDuration);
                            }

                        } else {
                            if (dayOfWeek != cDay) {
                                oPM += cDay + ":" + cUse + "\n";

                                DatabaseReference myRef = FirebaseDatabase.getInstance().
                                        getReference("User").child(mAuth.getCurrentUser().getPhoneNumber())
                                        .child("outgoing").child("self").child("AM").child(String.valueOf(cMonth));

                                String key = myRef.push().getKey();
                                myRef.child(key).setValue(new CallData(String.valueOf(cDay), String.valueOf(cUse)));


                                cDay = dayOfWeek;
                                cUse = 0;
                                cUse += Integer.parseInt(callDuration);
                                //upload server all cDay,cMonth,cUse
                            } else {
                                cUse += Integer.parseInt(callDuration);
                            }
                        }


                        tableString += phNumber + "  :  " + Integer.parseInt(callDuration) + " :" + getSIMnameByNumber(phNumber) + " :" + callDayTime + "\n";

                    }

//                    stringBuffer.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
//                            + "OUTGOING: " + " \nCall Date:--- " + String.valueOf(dayOfWeek)
//                            + " \nCall duration in sec :--- " + callDuration);
//                    stringBuffer.append("\n----------------------------------");

                }
            }

        }
        cursor.close();
        pb1.setVisibility(View.GONE);
        tvTable.setText(tableString);
//showing in textview
        showCallData();

    }


    public void showCallData() {
//        //lop
//        String kopp="";
//        for (int i=0;i<callUsageList.size();i++)
//            kopp += "\n" + callUsageList.get(i);
//        Toast.makeText(this, kopp, Toast.LENGTH_SHORT).show();
//


        tvShowCallUsageOtherAM.setText(sAM);
        tvShowCallUsageOtherPM.setText(sPM);

        tvShowCallUsageSelfAM.setText(oAM);
        tvShowCallUsageSelfPM.setText(oPM);
//showing 4 strings in 4 scrollview table
    }


    public String getSimOperator() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<SubscriptionInfo> subscription = SubscriptionManager.from(getApplicationContext()).getActiveSubscriptionInfoList();
            for (int i = 0; i < subscription.size(); i++) {
                if (i == 0) continue;
                SubscriptionInfo info = subscription.get(i);
                tvOperator.setText("SIM 2: " + info.getCarrierName());
                return (String) info.getCarrierName();
            }
        } else {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            return telephonyManager.getLine1Number();
        }
        return null;
    }


    //permission results
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted!
                    //checks on app running ; do not Toast anything here.
                    //getting call details and pushing in arrays
                    getCallDetails(this);
                    getSMSdetails();

                } else {
                    // permission denied
                    Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();

                }
                return;
            }

        }
    }

    //getting sim name ADDED LATER
    public String getSIMnameByNumber(String no) {

        if (no.substring(0, 6).trim().equals("+88019") || no.substring(0, 3).trim().equals("019")
                ) {
            return "BL";
        } else if (
                no.substring(0, 6).trim().equals("+88017") || no.substring(0, 3).trim().equals("017")) {
            return "GP";
        } else if (
                no.substring(0, 6).trim().equals("+88016") || no.substring(0, 3).trim().equals("016")) {

            return "Airtel";
        } else if (
                no.substring(0, 6).trim().equals("+88018") || no.substring(0, 3).trim().equals("018")) {
            return "Robi";
        } else if (
                no.substring(0, 6).trim().equals("+88015") || no.substring(0, 3).trim().equals("015")) {
            return "Teletalk";
        } else {
            Log.d("ok", no.substring(0, 6) + "@@" + no.substring(0, 3));
            return "Error";
        }


    }


    public void getSMSdetails() {

        // public static final String INBOX = "content://sms/outbox";
// public static final String SENT = "content://sms/sent";
// public static final String DRAFT = "content://sms/draft";
        String columns[] = new String[]{"date", "body"};

        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/sent"), columns, null, null, "date desc");
        String msgData = "";

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            int ccDay = 0, ccUse = 0;
            do {
                int dayOfWeekX = 0;
                int qty = 0;
                for (int idx = 0; idx < cursor.getColumnCount(); idx++)
                    if (idx == 0) {
                        //detecting date of month
                        Date smsDayTime = new Date(Long.valueOf(cursor.getString(idx)));
                        Calendar c = Calendar.getInstance();
                        c.setTime(smsDayTime);
                        dayOfWeekX = c.get(Calendar.DAY_OF_MONTH);
                    } else {
                        qty = ((Integer.valueOf((cursor.getString(idx).length()) / 250) > 0) ?
                                Integer.valueOf((cursor.getString(idx).length()) / 250) : 1);
                        if (ccDay == dayOfWeekX) {
                            ccUse += qty;
                        } else {
                            if (ccUse != 0) {
                                uploadSMS(String.valueOf(ccDay), String.valueOf(ccUse));
                                msgData += ccDay + ":" + ccUse + "\n";
                            }
                            ccDay = dayOfWeekX;
                            ccUse = qty;
                        }
                    }
                Log.d("SMS", "called");
                // use msgData
            } while (cursor.moveToNext());
            //printing on SMS Section...
            tvSMStable.setText(msgData);

            for (int i = 0; i < smsUsageList.size(); i++)
                Log.d("ok", String.valueOf(smsUsageList.get(i)));


        } else {
            // empty box, no SMS
        }

    }


    public void ScheduleDailyN() {
        Intent intent = new Intent(this, BroadCastClass.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                , 60 * 60 * 1000, pendingIntent);

        Toast.makeText(this, "Server data update scheduled. 1hr interval!", Toast.LENGTH_LONG).show();
    }


    public void uploadSMS(String day, String qty) {

        //filling sms arrayList
        smsUsageList.add(Integer.parseInt(qty));


        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("User").child(mAuth.getCurrentUser().getPhoneNumber()).child("SMS");

        String key = databaseRef.push().getKey();

        SMSdata smSdata = new SMSdata(day, qty);
        databaseRef.child(key).setValue(smSdata);

    }


    public void getCallsCluster() {
        float m1, m2, q = 0.0f, w = 0.0f;
        int k_means;
        int z = 0, i = 0;
        int z1 = 0;
        float avg1, avg2;
//        System.out.println("Enter no of elements in cluster");
        k_means = callUsageList.size();
        int a[] = new int[k_means];
        int c1[] = new int[k_means];
        int c2[] = new int[k_means];
//        System.out.println("Enter elements in cluster");
        for (i = 0; i < callUsageList.size(); i++) {
            a[i] = callUsageList.get(i);
        }
//        System.out.println("Enter value of m1 and m2");
        m1 = callUsageList.get(0);
        m2 = callUsageList.get(callUsageList.size() - 1);
        Operations op = new Operations();
        while (q != m2 && w != m2) {
            for (i = 0; i < k_means; i++) {
                if (Math.abs(a[i] - m1) < Math.abs(a[i] - m2)) {
                    c1[z] = a[i];
                    z++;
                } else {
                    c2[z1] = a[i];
                    z1++;
                }
            }
            z = 0;
            z1 = 0;
//            System.out.print("Cluster 1\t");
//            op.display(c1,k_means);
//            System.out.print("Cluster 2\t");
//            op.display(c2,k_means);
            q = m1;
            w = m2;
            m1 = op.average(c1, k_means);
//            System.out.print("average of cluster1 "+m1);
//            System.out.println();
            m2 = op.average(c2, k_means);
//            System.out.print("average of cluster2 "+m2);
//            System.out.println();
            if(m1>m2){
                int cc =  Math.round(m2);
                m2 = m1;
                m1 = cc;
            }

            tvCallsPredict.setText("Min : " + Math.round(m1 / 60) * 30 + " Min" +
                    "\n" + "Max : " + Math.round(m2 / 60) * 30 + " Min");

            callUp = Math.round(m2 / 60) * 30;
            callDown = Math.round(m1 / 60) * 30;

        }
    }


    public void getDataHistory() {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference InternetDataRef = mDatabase.getReference("User")
                .child(mAuth.getCurrentUser().getPhoneNumber())
                .child("data");


        InternetDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                int prevPM = 0, x = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    NetData netData = dataSnapshot.getValue(NetData.class);

                    int internet = 0;

                    if (netData.PM != null)
                        internet = Integer.parseInt(netData.PM);

                    Log.d("sos", String.valueOf(internet));

                    if (internet > prevPM)
                        x = internet - prevPM;
                    else
                        x = internet;


                    if (x != 0 && x > 0)
                        dataUsageList.add(x);

                    prevPM = internet;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void getInternetCluster() {
        float m1, m2, q = 0.0f, w = 0.0f;
        int k_means;
        int z = 0, i = 0;
        int z1 = 0;
        float avg1, avg2;
//        System.out.println("Enter no of elements in cluster");
        k_means = dataUsageList.size();
        int a[] = new int[k_means];
        int c1[] = new int[k_means];
        int c2[] = new int[k_means];
//        System.out.println("Enter elements in cluster");
        for (i = 0; i < dataUsageList.size(); i++) {
            a[i] = dataUsageList.get(i);
        }
//        System.out.println("Enter value of m1 and m2");
        m1 = dataUsageList.get(0);
        m2 = dataUsageList.get(dataUsageList.size() - 1);
        Operations op = new Operations();
        while (q != m2 && w != m2) {
            for (i = 0; i < k_means; i++) {
                if (Math.abs(a[i] - m1) < Math.abs(a[i] - m2)) {
                    c1[z] = a[i];
                    z++;
                } else {
                    c2[z1] = a[i];
                    z1++;
                }
            }
            z = 0;
            z1 = 0;
//            System.out.print("Cluster 1\t");
//            op.display(c1,k_means);
//            System.out.print("Cluster 2\t");
//            op.display(c2,k_means);
            q = m1;
            w = m2;
            m1 = op.average(c1, k_means);
//            System.out.print("average of cluster1 "+m1);
//            System.out.println();
            m2 = op.average(c2, k_means);
//            System.out.print("average of cluster2 "+m2);
//            System.out.println();
            if(m1>m2){
                int cc =  Math.round(m2);
                m2 = m1;
                m1 = cc;
            }

                String show =   "";
                show = "Min : " + Math.round(m1) * 30 / 1000 + " GB" +
                        "\n" + "Max : " + Math.round(m2) * 30 / 1000 + " GB";


            tvDataPredict.setText(show);
            dataUp = Math.round(m2) * 30 ;
            dataDown = Math.round(m1) * 30;


            for (int xx : dataUsageList)
                Log.d("pop", String.valueOf(xx));

        }
    }


//getting sms cluster

    public void getSMScluster() {
        float m1, m2, q = 0.0f, w = 0.0f;
        int k_means;
        int z = 0, i = 0;
        int z1 = 0;
        float avg1, avg2;
//        System.out.println("Enter no of elements in cluster");
        k_means = smsUsageList.size();
        int a[] = new int[k_means];
        int c1[] = new int[k_means];
        int c2[] = new int[k_means];
        System.out.println("Enter elements in cluster");
        for (i = 0; i < k_means; i++) {
            a[i] = smsUsageList.get(i);
        }
//        System.out.println("Enter value of m1 and m2");
        m1 = smsUsageList.get(0);
        m2 = smsUsageList.size() - 1;
        Operations op = new Operations();
        while (q != m2 && w != m2) {
            for (i = 0; i < k_means; i++) {
                if (Math.abs(a[i] - m1) < Math.abs(a[i] - m2)) {
                    c1[z] = a[i];
                    z++;
                } else {
                    c2[z1] = a[i];
                    z1++;
                }
            }
            z = 0;
            z1 = 0;
//            System.out.print("Cluster 1\t");
//            op.display(c1,k_means);
//            System.out.print("Cluster 2\t");
//            op.display(c2,k_means);
            q = m1;
            w = m2;
            m1 = op.average(c1, k_means);
//            System.out.print("average of cluster1 "+m1);
//            System.out.println();
            m2 = op.average(c2, k_means);
//            System.out.print("average of cluster2 "+m2);
//            System.out.println();
            if(m1>m2){
                int cc =  Math.round(m2);
                m2 = m1;
                m1 = cc;
            }

            tvSMSpredict.setText("Min Usage: " + Math.round(m1) * 30 + " SMS" +
                    "\n" + "Max Usage: " + Math.round(m2) * 30 + " SMS");

            smsUp = Math.round(m2) * 30;
            smsDown = Math.round(m1) * 30;
        }
    }

    public void displayOffer() {
        if (callDown < 5 && callUp > 5)
            tvC1.setBackgroundColor(Color.GREEN);
        else if (callDown < 10 && callUp > 10)
            tvC2.setBackgroundColor(Color.GREEN);
        if (callDown < 50 && callUp > 50)
            tvC3.setBackgroundColor(Color.GREEN);
        else if (callDown < 100 && callUp > 100)
            tvC4.setBackgroundColor(Color.GREEN);
        if (callDown < 200 && callUp > 200)
            tvC5.setBackgroundColor(Color.GREEN);
        else if (callDown < 300 && callUp > 300)
            tvC6.setBackgroundColor(Color.GREEN);
        if (callDown < 400 && callUp > 400)
            tvC7.setBackgroundColor(Color.GREEN);
        else if (callDown < 500 && callUp > 500)
            tvC8.setBackgroundColor(Color.GREEN);
        if (callDown < 1000 && callUp > 1000)
            tvC9.setBackgroundColor(Color.GREEN);
        else
            tvC10.setBackgroundColor(Color.GREEN);

        //--------------
        if ((smsDown < 100 && smsUp > 100) || smsDown < 100)
            tvS1.setBackgroundColor(Color.GREEN);
        else if (smsDown < 300 && smsUp > 300)
            tvS2.setBackgroundColor(Color.GREEN);
        else if (smsDown < 1000 && smsUp > 1000)
            tvS3.setBackgroundColor(Color.GREEN);
        else if (smsDown < 2000 && smsUp > 2000)
            tvS4.setBackgroundColor(Color.GREEN);
        else if (smsDown < 5000 && smsUp > 5000)
            tvS5.setBackgroundColor(Color.GREEN);
        else if (smsDown < 10000 && smsUp > 10000)
            tvS6.setBackgroundColor(Color.GREEN);
        else if (smsDown < 15000 && smsUp > 15000)
            tvS7.setBackgroundColor(Color.GREEN);
        else if (smsDown < 25000 && smsUp > 25000)
            tvS8.setBackgroundColor(Color.GREEN);
        else if (smsDown < 30000 && smsUp > 30000)
            tvS9.setBackgroundColor(Color.GREEN);
        else
            tvS10.setBackgroundColor(Color.GREEN);

        //--------------
        if (dataDown < 5 && dataUp > 5)
            tvD1.setBackgroundColor(Color.GREEN);
        else if (dataDown < 10 && dataUp > 10)
            tvD2.setBackgroundColor(Color.GREEN);
        else if (dataDown < 50 && dataUp > 50)
            tvD3.setBackgroundColor(Color.GREEN);
        else if (dataDown < 100 && dataUp > 100)
            tvD4.setBackgroundColor(Color.GREEN);
        else if (dataDown < 500 && dataUp > 500)
            tvD5.setBackgroundColor(Color.GREEN);
        else if (dataDown < 1000 && dataUp > 1000)
            tvD6.setBackgroundColor(Color.GREEN);
        else if (dataDown < 1500 && dataUp > 1500)
            tvD7.setBackgroundColor(Color.GREEN);
        else if (dataDown < 2000 && dataUp > 2000)
            tvD8.setBackgroundColor(Color.GREEN);
        else if (dataDown < 5000 && dataUp > 5000)
            tvD9.setBackgroundColor(Color.GREEN);
        else
            tvD10.setBackgroundColor(Color.GREEN);


    }

//end of MainActivity Class
}


