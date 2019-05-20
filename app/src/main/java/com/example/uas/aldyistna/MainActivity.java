package com.example.uas.aldyistna;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.uas.aldyistna.adapter.ListSMSRecieveAdapter;
import com.example.uas.aldyistna.database.TableSmsAdapter;
import com.example.uas.aldyistna.database.Tablesms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private static RecyclerView recycleRecive;
    private static MainActivity inst;
    private static ListSMSRecieveAdapter listAdapter;
    private SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermissionForM();
        recycleRecive = findViewById(R.id.recycleRecive);
        refreshLayout = findViewById(R.id.swipe);
        new DatabaseManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SendSMS.class));
            }
        });


        showList();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                showList();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showList() {
        List<Tablesms> smsList = new ArrayList<>();
        TableSmsAdapter adapter = new TableSmsAdapter(MainActivity.this);
        smsList = adapter.getListChat2();
        listAdapter = new ListSMSRecieveAdapter(MainActivity.this, smsList);
        LinearLayoutManager layoutParams = new LinearLayoutManager(MainActivity.this);
        recycleRecive.setLayoutManager(layoutParams);
        recycleRecive.setAdapter(listAdapter);
        refreshLayout.setRefreshing(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        showList();
    }

    private void setPermissionForM() {
        String[] PERMISSION = {
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
        };
        if (!hasPermissions(this, PERMISSION)) {
            ActivityCompat.requestPermissions(this, PERMISSION, 1);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Map<String, Integer> perm = new HashMap<>();
        perm.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
        perm.put(Manifest.permission.RECEIVE_SMS, PackageManager.PERMISSION_GRANTED);
        for (int i = 0; i < permissions.length; i++) {
            perm.put(permissions[i], grantResults[i]);
            if (perm.get(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
                    && perm.get(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permision is Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
