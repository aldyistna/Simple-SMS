package com.example.uas.aldyistna;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.uas.aldyistna.database.Tablesms;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by admin on 6/10/2016.
 */
public class DatabaseManager extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_PATH = "/data/data/com.example.uas.aldyistna/databases/";
    private Dao<Tablesms, Integer> tablesmsDAO = null;


    public DatabaseManager(Context context) {
        super(context, DATABASE_PATH + "DBSMS", null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Tablesms.class);
            Log.d("db", "Can't create database");
        } catch (SQLException e) {
            Log.e("this", "Can't create database", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Tablesms.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e("this", "Can't create database", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Dao<Tablesms, Integer> getTableSMSMDAO() {
        if (null == tablesmsDAO) {
            try {
                tablesmsDAO = getDao(Tablesms.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tablesmsDAO;
    }


    /**
     * Close the database connections and clear any cached tableDAO.
     */
    @Override
    public void close() {
        super.close();
        tablesmsDAO = null;

    }
}

