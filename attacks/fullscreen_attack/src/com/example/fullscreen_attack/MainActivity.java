package com.example.fullscreen_attack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;


public class MainActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		View v = getWindow().getDecorView();
		v.setBackgroundResource(R.drawable.screenshot);
		v.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | 
				View.SYSTEM_UI_FLAG_FULLSCREEN| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
				View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION );
	}	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();

		Log.d("FULLSCREEN", "Touch detected. X=" + String.valueOf(x) + ", Y=" + String.valueOf(y));
		return false;
	}
}


