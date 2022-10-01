package com.example.androidbindservicestudy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class BindService extends Service {
    // 申明IBinder接口的一个接口变量mBinder
    public final IBinder mBinder = new LocalBinder();
    private NotificationManager mNM;
     private int NOTIFICATION = R.string.local_service_started;
    // LocalBinder是继承Binder的一个内部类
    public class LocalBinder extends Binder {
        public BindService getService() {
            return BindService.this;
        }
    }

    public BindService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        System.out.println("onCreate" + getClass().getName());
        showNotification();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy" + getClass().getName());
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        System.out.println("onBind" + getClass().getName());
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("onUnbind" + getClass().getName());
        return super.onUnbind(intent);
    }

    private void showNotification() {
        CharSequence text = getText(R.string.local_service_started);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, ServiceActivity.class), 0);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(text)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(getText(R.string.local_service_label))
                .setContentText(text)
                .setContentIntent(contentIntent)
                .build();
        mNM.notify(NOTIFICATION, notification);
        System.out.println("通知栏已出" + getClass().getName());
    }
}