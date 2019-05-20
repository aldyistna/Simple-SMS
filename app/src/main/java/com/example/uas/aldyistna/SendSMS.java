package com.example.uas.aldyistna;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uas.aldyistna.database.TableSmsAdapter;
import com.example.uas.aldyistna.database.Tablesms;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SendSMS extends AppCompatActivity {
    private EditText number, smsTex;
    private Button sendsms;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private String phoneNo, message;

    // contact picker
    private static final int CONTACT_PICKER_RESULT = 1001;

    // phonecontact
    public void doLaunchContactPicker(View view) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, uri);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String phone = "";
        Cursor contacts = null;
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case CONTACT_PICKER_RESULT:

                        // gets the uri of selected contact
                        Uri result = data.getData();
                        // get the contact id from the Uri (last part is contact
                        // id)
                        String id = result.getLastPathSegment();
                        // queries the contacts DB for phone no
                        contacts = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone._ID + "=?",
                                new String[] { id }, null);
                        // gets index of phone no
                        int phoneIdx = contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                        if (contacts.moveToFirst()) {
                            // gets the phone no
                            phone = contacts.getString(phoneIdx);
                            EditText phoneTxt = (EditText) findViewById(R.id.numberphone);
                            // assigns phone no to EditText field phoneno
                            phoneTxt.setText(phone);
                        } else {
                            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
                        }

                        break;

                }

            } else {
                // gracefully handle failure
                Toast.makeText(SendSMS.this, "Kontak belum dipilih",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (contacts != null) {
                contacts.close();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sms);
        number = findViewById(R.id.numberphone);
        smsTex = findViewById(R.id.textsms);
        sendsms = findViewById(R.id.send);

        sendsms.setOnClickListener(sendSMS);
    }

    private View.OnClickListener sendSMS = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (number.getText().toString().trim().length() < 1) {
                Toast.makeText(SendSMS.this, "Please Input Number", Toast.LENGTH_LONG).show();
            } else if (smsTex.getText().toString().trim().length() < 1) {
                Toast.makeText(SendSMS.this, "Please Input SMS", Toast.LENGTH_LONG).show();
            } else {
                sendSMSMessage();
            }
        }
    };

    protected void sendSMSMessage() {
        phoneNo = number.getText().toString();
        message = smsTex.getText().toString();


        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();

                    TableSmsAdapter adapter = new TableSmsAdapter(SendSMS.this);
                    adapter.insertData(new Tablesms(), phoneNo.replace("+62", "0"), message, "s", getCurrentDateandTime());
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    public String getCurrentDateandTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
}
