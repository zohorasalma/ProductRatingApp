package org.tensorflow.lite.examples.classification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class myService extends Service
{

   /* public myService(){
        super("My_Worker_Thread");
    }*/

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        //  Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Toast.makeText(this,"Service Stopped",Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
/*
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        synchronized (this){
            int count=0;
            while(count<20){
                try {
                    wait(1500);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }*/
}