package com.example.mario;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity
{
	public static int SPLASH_TIME_OUT=2000;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		// TODO: Implement this method
		super.onCreate(savedInstanceState);


		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				Intent loginIntent = new Intent(SplashActivity.this,LoginActivity.class);
				startActivity(loginIntent);
				finish();

			}
		},SPLASH_TIME_OUT);
	}
	
}