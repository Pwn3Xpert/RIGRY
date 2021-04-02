package com.rigry;

import android.app.*;
import android.os.*;
import android.content.Intent;

public class SplashActivity extends MainActivity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
		
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				Intent splashIntent = new Intent(SplashActivity.this, MainActivity.class);
				SplashActivity.this.startActivity(splashIntent);
				SplashActivity.this.finish();
			}
		}, 1500);
    }
}
