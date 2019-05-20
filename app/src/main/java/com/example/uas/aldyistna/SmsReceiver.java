package com.example.uas.aldyistna;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import android.util.Log;
import android.widget.Toast;

import com.example.uas.aldyistna.database.TableSmsAdapter;
import com.example.uas.aldyistna.database.Tablesms;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] object = (Object[]) bundle.get("pdus");
                TableSmsAdapter adapter;
                for (Object anObject : object) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) anObject);
                    String phoneNumber = smsMessage.getDisplayOriginatingAddress();
                    String message = smsMessage.getDisplayMessageBody();

                    Log.d("smsku" , phoneNumber + " " + message);
                    // Calling SMSActivity method to display using TextView
                    String fullSmsInfo = "From: " + phoneNumber + "\n" + message;
                    adapter = new TableSmsAdapter(context);
                    adapter.insertData(new Tablesms(), phoneNumber.replace("+62", "0"), message, "r", getCurrentDateandTime());
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "Exception" + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public String getCurrentDateandTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }


}

