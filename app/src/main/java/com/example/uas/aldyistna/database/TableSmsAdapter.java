package com.example.uas.aldyistna.database;

import android.content.Context;

import com.example.uas.aldyistna.DatabaseManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.util.List;

public class TableSmsAdapter {
    static private TableSmsAdapter instance;
    private Dao dao;

    static public void init(Context ctx) {
        if (null == instance) {
            instance = new TableSmsAdapter(ctx);
        }
    }

    static public TableSmsAdapter getInstance() {
        return instance;
    }

    private DatabaseManager helper;

    public TableSmsAdapter(Context ctx) {
        helper = new DatabaseManager(ctx);
    }

    private synchronized DatabaseManager getHelper() {
        return helper;
    }

    public List<Tablesms> getAllData() {
        List<Tablesms> tblsatu = null;
        try {
            tblsatu = getHelper().getTableSMSMDAO()
                    .queryBuilder()
                    .orderBy("time", false)
                    .query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tblsatu;
    }

    public List<Tablesms> getListChat2() {
        List<Tablesms> tblsatu = null;
        try {
            dao = helper.getDao(Tablesms.class);
            QueryBuilder<Tablesms, Integer> queryBuilder = dao.queryBuilder();
            queryBuilder.groupBy("number");
            queryBuilder.orderBy("time", false);
            tblsatu = queryBuilder.query();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return(tblsatu);
    }

    public List<Tablesms> getDataCondition(String condition, String param) {
        List<Tablesms> tblsatu = null;
        try {
            dao = helper.getDao(Tablesms.class);
            QueryBuilder<Tablesms, Integer> queryBuilder = dao.queryBuilder();
            Where<Tablesms, Integer> where = queryBuilder.where();
            where.eq(condition, param);
            tblsatu = queryBuilder.query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tblsatu;
    }

    public void insertData(Tablesms tbl, String number, String smstext, String from, String time) {
        try {
            tbl.setNumber(number);
            tbl.setSmstext(smstext);
            tbl.setFrom(from);
            tbl.setTime(time);
            getHelper().getTableSMSMDAO().create(tbl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
