package com.example.mario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class SignupActivity extends AppCompatActivity
{
	private EditText mUser,mEmail,mPass,mPhone,mOtp,confPassEditText;
	private Button mBOtp,mBSignup;
	private Toast mToast;
	private boolean male=true,stud=true;
	private int OTP_LENGTH;

	private FirebaseFirestore myDocRef = FirebaseFirestore.getInstance();
	private StorageReference myStorageRef;

	private ProgressBar detailsProgressbar;
	private ProgressBar imageProgressbar;

	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor spEditor;

	private String email_string,fullname_string,phone_string,
					pass_string,confpass_string,otp_string;

	private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
	private FirebaseUser firebaseUser;

	private String picUrl="www.mario.com";
	private Uri picturePathURI =null;
	private Bitmap bitmap;


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
		
		OTP_LENGTH=getResources().getInteger(R.integer.otp_length);
		mToast=Toast.makeText(this,"",Toast.LENGTH_SHORT);
		mUser=findViewById(R.id.s_user);
		mEmail=findViewById(R.id.s_email);
		mPass=findViewById(R.id.s_pass);
		mPhone=findViewById(R.id.s_ph);
		mOtp=findViewById(R.id.s_otp);
		mBSignup=findViewById(R.id.s_otg);
		mBOtp=findViewById(R.id.s_button_send);
		confPassEditText=(EditText)findViewById(R.id.s_cofpass_edittext);
		detailsProgressbar=(ProgressBar)findViewById(R.id.reg_details_progressbar);
		imageProgressbar=(ProgressBar)findViewById(R.id.reg_image_progressbar);

		sharedPreferences=getSharedPreferences(MainActivity.KEY_SHARED_PREFERENCE,MODE_PRIVATE);




		mOtp.addTextChangedListener(new TextWatcher()
		{
			public void beforeTextChanged(CharSequence c,int i1,int i2,int i3) {}
			public void onTextChanged(CharSequence c,int i1, int i2,int i3) {}
			public void afterTextChanged(Editable e)
			{
				if(e.length()==OTP_LENGTH)
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

					email_string=mEmail.getText().toString();
					fullname_string=mUser.getText().toString();
					phone_string=mPhone.getText().toString();
					pass_string=mPass.getText().toString();
					confpass_string=confPassEditText.getText().toString();



					validateDetails();








			}
		});
	}
	
	public void setGender(View v)
	{
		switch(v.getId())
		{
			case R.id.f:
				male=false;
				break;
			case R.id.m:
				male=true;
		}
	}
	
	public void setProf(View v)
	{
		switch(v.getId())
		{
			case R.id.s:
				stud=true;
				break;
			case R.id.p:
				stud=false;
		}
	}
	
	public void send_otp(View v)
	{
		String ph=mPhone.getText().toString();




		if(!android.util.Patterns.PHONE.matcher(ph).matches())
			d("Invalid phone number");
		else {
			//
			d("not yet implemented");
		}
	}

	private void validateDetails()
	{

		if(fullname_string.isEmpty())
		{
			mUser.setError("Name is Required");
			mUser.requestFocus();
			return;
		}

		if(email_string.isEmpty())
		{
			mEmail.setError("Email is Required");
			mEmail.requestFocus();
			return;
		}
		else if(!Patterns.EMAIL_ADDRESS.matcher(email_string).matches())
		{
			mEmail.setError("Please,Enter a vaild  Email.");
			mEmail.requestFocus();
			return;
		}

		if(pass_string.isEmpty())
		{
			mPass.setError("Password is Required");
			mPass.requestFocus();
			return;
		}
		else if(pass_string.length()<6)
		{
			mPass.setError("Min. password length is 6");
			mPass.requestFocus();
			return;
		}

		if(confpass_string.isEmpty())
		{
			confPassEditText.setError("Password not matched");
			confPassEditText.requestFocus();
			return;
		}
		else if(!pass_string.equals(confpass_string))
		{
			confPassEditText.setError("Password not matched");
			confPassEditText.requestFocus();
			return;
		}
		else
		{
			imageProgressbar.setVisibility(View.VISIBLE);
			detailsProgressbar.setVisibility(View.VISIBLE);
			registerUser();

		}
	}
	
	private void d(String s)
	{
		mToast.cancel();
		mToast=Toast.makeText(this,s,Toast.LENGTH_LONG);
		mToast.show();
	}

	private void registerUser()
	{

		firebaseAuth.createUserWithEmailAndPassword(email_string,pass_string).addOnCompleteListener(new OnCompleteListener<AuthResult>()
		{
			@Override
			public void onComplete(@NonNull Task<AuthResult> task)
			{
				if (task.isSuccessful())
				{
					Log.d("Register Page:", "User Id created");
					d("Registered Successfully!");
					firebaseUser = firebaseAuth.getCurrentUser();
					final String uid = firebaseUser.getUid();

					if(picturePathURI!=null)
					{
						final StorageReference storageRef= myStorageRef.child("/users/ProfilePic/"+uid+".jpg");
						ByteArrayOutputStream bytes = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.JPEG,25,bytes);
						String path =MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,picturePathURI.getPath(),null);
						picturePathURI=Uri.parse(path);

						storageRef.putFile(picturePathURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
						{
							@Override
							public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
							{
								Log.d(TAG,"User ProfilePic Saved");

								picUrl=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

								imageProgressbar.setVisibility(View.GONE);



							}
						}).addOnFailureListener(new OnFailureListener()

						{
							@Override
							public void onFailure(@NonNull Exception e)
							{
								Log.d(TAG,"User Profile Failed to save.");
								d("Profile Pic:"+e.getMessage());
								picUrl = "www.mario.com";

							}
						});

					}

					if (firebaseUser != null)
					{

						Log.d("Profile Pic URL :", "" + picUrl);

						UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder().
								setDisplayName(fullname_string).setPhotoUri(Uri.parse(picUrl)).build();

						firebaseUser.updateProfile(changeRequest).addOnCompleteListener(new OnCompleteListener<Void>()
						{
							@Override
							public void onComplete(@NonNull Task<Void> task)
							{
								if (task.isSuccessful())
								{
									Log.d("Profile Pic:", "Url saved");
								}
								else Log.d("Profile Pic: ERROR :", "Url not saved");
							}
						});
					}

					final Uri imgURL = firebaseUser.getPhotoUrl();



					Map<String, Object> dataSave = new HashMap<String, Object>();
					dataSave.put("Uid", uid);
					dataSave.put("FullName", fullname_string);

					dataSave.put("Email", email_string);
					dataSave.put("Phone", phone_string);

					if (male == true) dataSave.put("Gender", "Male");
					else dataSave.put("Gender", "Male");
					if (stud == true) dataSave.put("WorkCategory", "Student");
					else dataSave.put("WorkCategory", "Professional");

					myDocRef.collection("/USERS/").document(uid).set(dataSave).addOnSuccessListener(new OnSuccessListener<Void>()
					{
						@Override
						public void onSuccess(Void aVoid)
						{


							Log.d(TAG, "User Document Saved");
							d("Registration Successful! Please Login using the credentials.");



							spEditor = sharedPreferences.edit();
							spEditor.putString("FULL_NAME", fullname_string);
							spEditor.putString("EMAIL", email_string);
							spEditor.putString("IMG_URL", "" + imgURL);
							spEditor.putString("UID", uid);
							spEditor.putBoolean("IS_USER_LOGGED_IN", true);
							spEditor.apply();

							/*Intent in = new Intent(SignupActivity.this, MainActivity.class);
							startActivity(in);*/
							detailsProgressbar.setVisibility(View.GONE);
							setResult(RESULT_OK);
							finish();

						}
					}).addOnFailureListener(new OnFailureListener()

					{
						@Override
						public void onFailure(@NonNull Exception e)
						{
							Log.d(TAG, "User Document Failed to save.");

						}
					});
				} else
				{
					d("Register Error: " + task.getException().toString());
				}
			}


		});




	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
		{
			picturePathURI = data.getData();


			try
			{
				bitmap =MediaStore.Images.Media.getBitmap(getContentResolver(),picturePathURI);
				CircleImageView imageView = (CircleImageView) findViewById(R.id.ProPic);
				int wh =(int)(bitmap.getHeight()*(512.0/bitmap.getWidth()));
				Bitmap downScaled = Bitmap.createScaledBitmap(bitmap,512,wh,true);
				imageView.setImageBitmap(downScaled);

			} catch (IOException e)
			{
				e.printStackTrace();
				Log.d("Profile Image : ",""+e.getMessage());

				d("Profile Image"+e.getMessage());
			}



		}
	}
}