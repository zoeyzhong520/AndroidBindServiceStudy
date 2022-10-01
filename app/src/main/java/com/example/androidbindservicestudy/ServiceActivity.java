package com.example.androidbindservicestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class ServiceActivity extends AppCompatActivity {
    private BindService bindService;
    private boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        findViewById(R.id.btn_bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isBind) {
                    Intent intentBind = new Intent(ServiceActivity.this, BindService.class);
                    bindService(intentBind, serviceConnection, Context.BIND_AUTO_CREATE);
                    isBind = true;
                }
            }
        });

        findViewById(R.id.btn_unbing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBind) {
                    isBind = false;
                    unbindService(serviceConnection);
                    bindService = null;
                }
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            System.out.println("onServiceConnected" + getClass().getName());
            bindService = ((BindService.LocalBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            System.out.println("onServiceDisconnected" + getClass().getName());
            bindService = null;
        }
    };
}