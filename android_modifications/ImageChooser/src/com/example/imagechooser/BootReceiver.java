package com.example.imagechooser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.IVerificationService;
import android.os.ServiceManager;
import android.os.RemoteException;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {       
        try{
            IVerificationService VSHandler = IVerificationService.Stub.asInterface(ServiceManager.getService("Verification"));
            if(VSHandler.getSecureImages().getParcelable("green_secure_image")!=null){
                return; //secure images already set
            }
        }catch(RemoteException e){}

        context.startService(new Intent(context, TopWindowService.class));
    }
}

