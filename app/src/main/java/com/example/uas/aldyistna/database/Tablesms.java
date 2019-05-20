package com.example.uas.aldyistna.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sms")

public class Tablesms {

    @DatabaseField(generatedId = true)
    private int id;


    @DatabaseField
    private String number;
    @DatabaseField
    private String smstext;
    @DatabaseField
    private String from;
    @DatabaseField
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSmstext() {
        return smstext;
    }

    public void setSmstext(String smstext) {
        this.smstext = smstext;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
