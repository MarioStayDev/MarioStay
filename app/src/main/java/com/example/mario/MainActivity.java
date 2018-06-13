package com.example.mario;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements BrowseFragment.OnFragmentInteractionListener,AddPropFragment.OnFragmentInteractionListener,PropertyDescFragment.OnFragmentInteractionListener,AllPaymentFragment.OnFragmentInteractionListener,
                                                                RefundFragment.OnFragmentInteractionListener
{
	private boolean userIsLoggedIn;
	//private Toolbar mToolbar;
	private final int REQUEST_LOGIN=101;
	public final static String KEY_SHARED_PREFERENCE = "shared_pref_key", KEY_LOGGED_IN = "logged_in_key", KEY_USER_NAME = "user_name_key", KEY_EMAIL = "email_key";
	private DrawerLayout mDrawerLayout;
	//private NavigationView mNavView;
	private SharedPreferences pfm;
	private Toast mToast;
	private FragmentManager mFragmentManager;
	private Fragment mFragment;
	private TextView UserNameText, EmailText;

	//private String UserName, Email;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Toolbar mToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);

		ActionBar actionbar = getSupportActionBar();
		if (actionbar != null) actionbar.setDisplayHomeAsUpEnabled(true);

		mDrawerLayout = findViewById(R.id.drawer_layout);
		NavigationView mNavView = findViewById(R.id.navigation_view);
		mFragmentManager = getSupportFragmentManager();

		View header = mNavView.getHeaderView(0);
		UserNameText = header.findViewById(R.id.username);
		EmailText = header.findViewById(R.id.email);

		mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				mDrawerLayout.closeDrawers();
				switch(item.getItemId()) {
					case R.id.menu_add_prop:
						mFragment = new AddPropFragment();
						break;
					case R.id.menu_inbox:
						mFragment = new BrowseFragment();
						break;
					case R.id.menu_refund:
						mFragment = new RefundFragment();
						break;
				}
				if(!userIsLoggedIn)
					loginPrompt();
				else
					mFragmentManager.beginTransaction().replace(R.id.frame, mFragment).commit();
				return true;
			}
		});

		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,mToolbar ,  R.string.app_name, R.string.app_name);
		mDrawerToggle.syncState();

		pfm = getSharedPreferences(KEY_SHARED_PREFERENCE,MODE_PRIVATE);
		userIsLoggedIn = pfm.getBoolean(KEY_LOGGED_IN,false); // Fetch and check login state here
		
		if(userIsLoggedIn) {
			setUserInfoInDrawer();
			if(findViewById(R.id.frame) != null && savedInstanceState == null) {
				mFragment = new BrowseFragment();
				mFragmentManager.beginTransaction().replace(R.id.frame, mFragment).commit();
			}
		} else {
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivityForResult(loginIntent,REQUEST_LOGIN);
		}

		mToast = Toast.makeText(this, "Init", Toast.LENGTH_LONG);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId()) {
			case R.id.menu_logout:
				pfm.edit().putBoolean(KEY_LOGGED_IN,false).remove(KEY_USER_NAME).remove(KEY_EMAIL).apply();
				userIsLoggedIn = false;
				Intent loginIntent = new Intent(this, LoginActivity.class);
				startActivityForResult(loginIntent,REQUEST_LOGIN);
				return true;
			case android.R.id.home:
				mDrawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void descPage(View v)
	{
		mFragment = new PropertyDescFragment();
		mFragmentManager.beginTransaction().replace(R.id.frame, mFragment).addToBackStack(null).commit();
	}

	private void setUserInfoInDrawer() {
		String UserName = pfm.getString(KEY_USER_NAME, "");
		String Email = pfm.getString(KEY_EMAIL, "");
		UserNameText.setText(UserName);
		EmailText.setText(Email);
	}

	private void loginPrompt() {
		d("You are not logged in");
		startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);
	}

	private void d(String s) {
    	mToast.cancel();
    	mToast = Toast.makeText(this, s, Toast.LENGTH_LONG);
    	mToast.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
			case REQUEST_LOGIN:
				if(resultCode==RESULT_OK) {
					pfm.edit().putBoolean(KEY_LOGGED_IN,true).apply();
					userIsLoggedIn=true;
					if(data !=null && data.getBooleanExtra("GUEST", false)) {
						mFragment = new BrowseFragment();
						mFragmentManager.beginTransaction().replace(R.id.frame, mFragment).commit();
					}
					else {
						setUserInfoInDrawer();
						mFragment = new BrowseFragment();
						mFragmentManager.beginTransaction().replace(R.id.frame, mFragment).commit();
						d("Logged in");
					}
					//Toast.makeText(this,"Logged in",Toast.LENGTH_SHORT).show();
				}
				else
					finish();
		}
	}


	@Override
	public void onFragmentInteraction(Uri uri)
	{

	}
}