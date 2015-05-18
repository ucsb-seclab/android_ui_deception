package com.example.imagechooser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IVerificationService;
import android.os.Message;
import android.os.ServiceManager;
import android.view.Display;
import android.graphics.Point;
import android.content.Context;


public class TopWindowService extends Service {

    private WindowManager windowManager;
    private boolean trasparent = true;
    private View myview;
    private View newview;
    private Button button1;
    private ImageView preview;
    ImageAdapter im;
    Handler mHandler;
    
    @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("NewApi")
    @Override public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);        
        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        myview = li.inflate(R.layout.main_grid, null);    
        button1 = (Button) myview.findViewById(R.id.button1);
        preview = (ImageView) myview.findViewById(R.id.imageView1);

        GridView gridView = (GridView) myview.findViewById(R.id.grid_view);
        gridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);
        im = new ImageAdapter(this);
        gridView.setAdapter(im);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                for(int index=0;index<im.selected.length;index++){
                    im.selected[index] = false;
                }
                im.selected[position] = !(im.selected[position]);
                im.notifyDataSetChanged();
                button1.setEnabled(true);
                preview.setImageResource(im.mThumbIds[position]);
                preview.refreshDrawableState();
            }


        });
        button1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startSecondWindow();
            }
        });

        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int type = WindowManager.LayoutParams.TYPE_BOOT_PROGRESS;
        int flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; 
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(width,height+100,type,flags,PixelFormat.OPAQUE); 
        params.x = 0;
        params.y = 0;
        flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | 
                View.SYSTEM_UI_FLAG_FULLSCREEN| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        myview.setSystemUiVisibility(flags);
        windowManager.addView(myview, params);
    }

    @SuppressLint("NewApi")
    private void startSecondWindow(){
        int index=0;
        for(index = 0;index<im.selected.length;index++){
            if(im.selected[index]){
                break;
            }
        }
        final Bitmap bm = BitmapFactory.decodeResource(getResources(),im.mThumbIds[index]);

        LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        newview = li.inflate(R.layout.image_choosen, null);    
        ImageView iv = (ImageView) newview.findViewById(R.id.imageView1);

        final Bitmap lockedImageGreen = generateLockImage(bm,R.drawable.ic_sysbar_trust_green);
        final Bitmap lockedImageYellow = generateLockImage(bm,R.drawable.ic_sysbar_trust_yellow);
        final Bitmap lockedImageRed = generateLockImage(bm,R.drawable.ic_sysbar_trust_red);

        Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.s);
        Bitmap result = overlay2(background, getResizedBitmap(lockedImageGreen, 85,85));        
        
        iv.setImageDrawable(new BitmapDrawable(result));
        Button ok_button = (Button) newview.findViewById(R.id.button1);
        ok_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                updateSecureImages(lockedImageGreen,lockedImageYellow,lockedImageRed);
                windowManager.removeView(myview);
                windowManager.removeView(newview);
                stopSelf();
            }
        });
        
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int type = WindowManager.LayoutParams.TYPE_BOOT_PROGRESS;
        int flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; 
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(width,height+100,type,flags,PixelFormat.OPAQUE); 
        params.x = 0;
        params.y = 0;
        flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | 
                View.SYSTEM_UI_FLAG_FULLSCREEN| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        newview.setSystemUiVisibility(flags);
        windowManager.addView(newview, params);
        
        mHandler = new Handler() {
            public void handleMessage(Message inputMessage) {
                switch (inputMessage.what) {
                case 1:
                    Button b = (Button) newview.findViewById(R.id.button1);
                    b.setVisibility(View.VISIBLE);
                }
            }
        };
        
        new Thread(new Runnable() {
            public void run() {
                buttonEnabler();
            }
        }).start();
    }
    
    
    private void buttonEnabler() {
        sleep(5);
        mHandler.obtainMessage(1).sendToTarget();
    }
    
    private void sleep(double sec){
        try {
            Thread.sleep((int)(sec * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private Bitmap generateLockImage(Bitmap bm, int lock_resource_id){
        Bitmap icon = BitmapFactory.decodeResource(getResources(),lock_resource_id);
        icon = getResizedBitmap(icon, 100,160);
        Bitmap background = bm;
        background = getResizedBitmap(background, 200,200);
        
        Bitmap result = overlay(background, icon);
        
        return result;
    }
    
    public static Bitmap overlay(Bitmap bmp1, Bitmap bmp2){
        try{
            Bitmap bmOverlay = Bitmap.createBitmap(200, 200,  bmp1.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp1, 0, 0, null);
            canvas.drawBitmap(bmp2, 75, 95, null);
            return bmOverlay;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static Bitmap overlay2(Bitmap bmp1, Bitmap bmp2){
        try{
            Bitmap bmOverlay = Bitmap.createBitmap(750, 670,  bmp1.getConfig());
            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp1, 0, 0, null);
            canvas.drawBitmap(bmp2, 388, 65, null);
            return bmOverlay;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }    
    
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }
    
    private void updateSecureImages(Bitmap bm1,Bitmap bm2,Bitmap bm3) {
        
        try{
            IVerificationService VSHandler = IVerificationService.Stub.asInterface(ServiceManager.getService("Verification"));
            VSHandler.newSecureImages(bm1,bm2,bm3);
        }catch(Exception e){
            ;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}




