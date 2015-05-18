package com.example.imagechooser;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;

/*
icons downloaded from: http://www.icojam.com/blog/?p=177
released by Stan Khodjaev in Public Domain
*/


public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startService(new Intent(getBaseContext(), TopWindowService.class));
		finish();
	}
}