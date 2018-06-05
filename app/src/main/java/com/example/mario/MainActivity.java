package com.example.mario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	private boolean userIsLoggedIn;
	//private Toolbar mToolbar;
	private final int REQUEST_LOGIN=101;
	private final String KEY_SHARED_PREFERENCE="shared_pref_key",KEY_LOGGED_IN="logged_in_key";
	private DrawerLayout mDrawerLayout;
	private RecyclerView mList;
	private DataAdapter mAdapter;
	private SharedPreferences pfm;
	private Toast mToast;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Toolbar mToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		//actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

		mDrawerLayout = findViewById(R.id.drawer_layout);

		NavigationView mNav = findViewById(R.id.navigation_view);
		mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				mDrawerLayout.closeDrawers();
				switch(item.getItemId()) {
					case R.id.menu_add_prop:
						d("Add prop");
						return true;
					case R.id.menu_inbox:
						d("Inbox");
						return true;
				}
				return false;
			}
		});

		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,mToolbar ,  R.string.app_name, R.string.app_name) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				//getActionBar().setTitle(mTitle);
				//invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				//getActionBar().setTitle(mDrawerTitle);
				//invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};

		mDrawerToggle.syncState();
		
		mList = findViewById(R.id.list);
		mList.setLayoutManager(new GridLayoutManager(this,2));
		mAdapter = new DataAdapter();
		mList.setAdapter(mAdapter);
		
		pfm = getSharedPreferences(KEY_SHARED_PREFERENCE,MODE_PRIVATE);
		userIsLoggedIn = pfm.getBoolean(KEY_LOGGED_IN,false); // Check login state here
		
		if(userIsLoggedIn) {
			loadData();
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
				pfm.edit().putBoolean(KEY_LOGGED_IN,false).apply();
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
	
	private void loadData() {
		for(int i=0;i<32;i++)
			mAdapter.add(new Data());
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
					loadData();
					Toast.makeText(this,"Logged in",Toast.LENGTH_SHORT).show();
				}
				else
					finish();
		}
	}
	
}