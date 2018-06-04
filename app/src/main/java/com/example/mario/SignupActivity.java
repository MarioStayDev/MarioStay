package com.example.mario;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity
{
	private EditText mUser,mEmail,mPass,mPhone,mOtp;
	private Button mBOtp,mBSignup;
	private Toast mToast;
	private boolean male=true,stud=true;
	private int OTG_LENGTH;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		OTG_LENGTH=getResources().getInteger(R.integer.otp_length);
		mToast=Toast.makeText(this,"",0);
		mUser=(EditText)findViewById(R.id.s_user);
		mEmail=(EditText)findViewById(R.id.s_email);
		mPass=(EditText)findViewById(R.id.s_pass);
		mPhone=(EditText)findViewById(R.id.s_ph);
		mOtp=(EditText)findViewById(R.id.s_otp);
		mBSignup=(Button)findViewById(R.id.s_otg);
		mBOtp=(Button)findViewById(R.id.s_button_send);
		mOtp.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence c,int i1,int i2,int i3) {}
			public void onTextChanged(CharSequence c,int i1, int i2,int i3) {}
			public void afterTextChanged(Editable e) {
				if(e.length()==OTG_LENGTH)
					mBSignup.setEnabled(true);
				else
					mBSignup.setEnabled(false);
			}
		});
		mBSignup.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//new Register(u,p,ph,male,stud).execute();
				d("Not yet implemented");
			}
		});
	}
	
	public void setGender(View v) {
		switch(v.getId()) {
			case R.id.f:
				male=false;
				break;
			case R.id.m:
				male=true;
		}
	}
	
	public void setProf(View v) {
		switch(v.getId()) {
			case R.id.s:
				stud=true;
				break;
			case R.id.p:
				stud=false;
		}
	}
	
	public void send_otp(View v) {
		String u=mUser.getText().toString(),
			p=mPass.getText().toString(),
			ph=mPhone.getText().toString()
			,e=mEmail.getText().toString();
		if("".equals(u))
			d("Username required");
		else if("".equals(e))
			d("Email required");
		else if("".equals(p))
			d("Password required");
		else if(!android.util.Patterns.PHONE.matcher(ph).matches())
			d("Invalid phone number");
		else {
			//
			d("not yet implemented");
		}
	}
	
	private void d(String s) {
		mToast.cancel();
		mToast=Toast.makeText(this,s,Toast.LENGTH_LONG);
		mToast.show();
	}
	
	private class Register extends AsyncTask<Void,Void,Void>
	{
		private String name,pass,phone;
		private char gender,profession;
		
		public Register(String n,String p,String ph,boolean m,boolean s) {
			name=n;pass=p;phone=ph;
			gender=m?'m':'f';
			profession=s?'s':'p';
		}

		@Override
		protected void onPreExecute()
		{
			// TODO: Implement this method
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result)
		{
			// TODO: Implement this method
			super.onPostExecute(result);
		}

		@Override
		protected Void doInBackground(Void[] p1)
		{
			// TODO: Implement this method
			return null;
		}

	}
	
}