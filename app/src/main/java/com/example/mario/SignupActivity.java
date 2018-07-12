package com.example.mario;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class SignupActivity extends AppCompatActivity
{
	private EditText mUser,mEmail,mPass,mPhone,mOtp;
	private Button mBOtp,mBSignup;
	private Toast mToast;
	private boolean male=true,stud=true;
	private int OTG_LENGTH;
	private FirebaseFirestore myDocRef = FirebaseFirestore.getInstance();
	private StorageReference myStorageRef;
	private String picturePath="no profile pic",picUrl;

	FloatingActionButton searchPic;
	private CircleImageView profilePic;
	private static int RESULT_LOAD_IMAGE=1;

	private static final int REQUEST_WRITE_PERMISSION = 786;

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED)
		{
			Intent i = new Intent(
					Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(i, RESULT_LOAD_IMAGE);
		}
	}

	private void requestPermission()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
		{
			requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
		} else
		{
			Intent i = new Intent(
					Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(i, RESULT_LOAD_IMAGE);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		searchPic = (FloatingActionButton) findViewById(R.id.SearchPic);


		searchPic.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				requestPermission();


			}
		});
		
		OTG_LENGTH=getResources().getInteger(R.integer.otp_length);
		mToast=Toast.makeText(this,"",Toast.LENGTH_SHORT);
		mUser=findViewById(R.id.s_user);
		mEmail=findViewById(R.id.s_email);
		mPass=findViewById(R.id.s_pass);
		mPhone=findViewById(R.id.s_ph);
		mOtp=findViewById(R.id.s_otp);
		mBSignup=findViewById(R.id.s_otg);
		mBOtp=findViewById(R.id.s_button_send);
		mOtp.addTextChangedListener(new TextWatcher()
		{
			public void beforeTextChanged(CharSequence c,int i1,int i2,int i3) {}
			public void onTextChanged(CharSequence c,int i1, int i2,int i3) {}
			public void afterTextChanged(Editable e)
			{
				if(e.length()==OTG_LENGTH)
					mBSignup.setEnabled(true);
				else
					mBSignup.setEnabled(false);
			}
		});

		myStorageRef= FirebaseStorage.getInstance().getReference();




		mBSignup.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Uri myPicUri = Uri.fromFile( new File(picturePath));
				final StorageReference storageRef= myStorageRef.child("/users/ProfilePic/"+mEmail.getText().toString()+".jpg");
				storageRef.putFile(myPicUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
				{
					@Override
					public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
					{
						Log.d(TAG,"User ProfilePic Saved");
						picUrl=storageRef.getDownloadUrl().toString();

					}
				}).addOnFailureListener(new OnFailureListener()

				{
					@Override
					public void onFailure(@NonNull Exception e)
					{
						Log.d(TAG,"User Profile Failed to save.");

					}
				});
				Map<String,Object> dataSave = new HashMap<String, Object>() ;
				dataSave.put("FullName",mUser.getText().toString());
				dataSave.put("Email",mEmail.getText().toString());
				dataSave.put("Password",mPass.getText().toString());
				dataSave.put("Phone",mPhone.getText().toString());
				dataSave.put("PicUrl",picUrl);
				if(male==true) 	dataSave.put("Gender","Male");
				else dataSave.put("Gender","Male");
				if(stud==true) 	dataSave.put("WorkCategory","Student");
				else dataSave.put("WorkCategory","Professional");



				myDocRef.collection("/users").add(dataSave).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
				{
					@Override
					public void onSuccess(DocumentReference documentReference)
					{
						Log.d(TAG,"User Document Saved");
					}
				}).addOnFailureListener(new OnFailureListener()

				{
					@Override
					public void onFailure(@NonNull Exception e)
					{
						Log.d(TAG,"User Document Failed to save.");

					}
				});
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
	
	public void send_otp(View v)
	{
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
		{
			Uri selectedImage = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			picturePath = cursor.getString(columnIndex);			// String picturePath contains the path of selected Image
			cursor.close();

			CircleImageView imageView = (CircleImageView) findViewById(R.id.ProPic);
			imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));


		}
	}
}