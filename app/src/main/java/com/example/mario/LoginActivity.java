package com.example.mario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONObject;
import org.json.JSONException;

public class LoginActivity extends AppCompatActivity
{
	private EditText mEmail,mPassword;
	private Button buttonLogin,buttonSignup;
	private SharedPreferences pfm;
	private final int REQUEST_SIGNUP=101;
	private Toast mToast;
	private ProgressBar progressBar;
	private String name_string,email_string,uid_string;
	private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
	private FirebaseUser firebaseUser;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		mToast=new Toast(this);
		mEmail = findViewById(R.id.email);
		mPassword = findViewById(R.id.password);
		buttonLogin = findViewById(R.id.button_login);
		buttonSignup = findViewById(R.id.button_signup);
		progressBar=(ProgressBar)findViewById(R.id.login_progressbar);
		
		buttonLogin.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				String n,p;
				n = mEmail.getText().toString();
				p = mPassword.getText().toString();
				if("".equals(n))
					d("Username required");
				else if("".equals(p))
					d("Password required");
				else
					new ValidateLogin(n,p).execute();
			}
		});


		buttonSignup.setOnClickListener(new OnClickListener()
		{
				public void onClick(View v)
				{
					Intent ri = new Intent(LoginActivity.this,SignupActivity.class);
					//startActivity(ri);
					startActivityForResult(ri,REQUEST_SIGNUP);
				}
			});

		pfm = getSharedPreferences(MainActivity.KEY_SHARED_PREFERENCE,MODE_PRIVATE);
		setResult(RESULT_CANCELED);
	}



	public void guest(View v)
	{
		pfm.edit().putBoolean(MainActivity.KEY_LOGGED_IN, false).apply();
		setResult(RESULT_OK);
		finish();
	}




	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode)
		{
			case REQUEST_SIGNUP:

			    if(resultCode == RESULT_OK)
				{
					setResult(RESULT_OK);
					finish();
				}
		}
	}

	

	
	private class ValidateLogin extends AsyncTask<Void,Void,Void>
	{
		private String data,error, email,password;
		private URL url;
		
		public ValidateLogin(String n,String p)
		{
			email = n;
			password=p;


		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
			buttonLogin.setEnabled(false);
			buttonSignup.setEnabled(false);


		}

		@Override
		protected void onPostExecute(Void result)
		{

			super.onPostExecute(result);
			buttonLogin.setEnabled(true);
			buttonSignup.setEnabled(true);
			progressBar.setVisibility(View.GONE);





		}

		@Override
		protected Void doInBackground(Void[] p1)
		{
			firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {

                	if(task.isSuccessful())
                    {
                        firebaseUser=firebaseAuth.getCurrentUser();
                        name_string=firebaseUser.getDisplayName();
                        email_string=firebaseUser.getEmail();
                        uid_string= firebaseUser.getUid();

                        SharedPreferences.Editor editor = pfm.edit();
                        editor.putString("FULL_NAME",name_string);
                        editor.putString("EMAIL",email_string);
                        editor.putString("UID",uid_string);
                        editor.putBoolean("IS_USER_LOGGED_IN",true);
                        editor.apply();

                        /*Intent i = new Intent(LoginActivity.this,MainActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);*/

                        setResult(RESULT_OK);
                        finish();


                    }
                    else
                    {
                        mPassword.requestFocus();
                        d("Login Failed: "+ task.getException().getMessage());
                    }
                }
            });
			return null;
		}

	}

	private void d(String s)
	{
		mToast.cancel();
		mToast = Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT);
		mToast.show();
	}
	
}