package com.example.mario;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	private boolean userIsLoggedIn;
	//private Toolbar mToolbar;
	private final int REQUEST_LOGIN=101;
	private final String KEY_SHARED_PREFERENCE="shared_pref_key",KEY_LOGGED_IN="logged_in_key";
	private RecyclerView mList;
	private DataAdapter mAdapter;
	private SharedPreferences pfm;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);
		
		mList = (RecyclerView)findViewById(R.id.list);
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
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.menu_main,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// TODO: Implement this method
		switch(item.getItemId()) {
			case R.id.menu_logout:
				pfm.edit().putBoolean(KEY_LOGGED_IN,false).apply();
				userIsLoggedIn = false;
				Intent loginIntent = new Intent(this, LoginActivity.class);
				startActivityForResult(loginIntent,REQUEST_LOGIN);
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void loadData() {
		for(int i=0;i<32;i++)
			mAdapter.add(new Data());
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