package com.example.mario;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PropertyFragment.OnFragmentInteractionListener,
																PropertyDescFragment.OnFragmentInteractionListener,
                                                                RefundFragment.OnFragmentInteractionListener,
																com.example.mario.InboxFragment.OnFragmentInteractionListener
{
	private boolean userIsLoggedIn;
	private final int REQUEST_LOGIN=101, REQUEST_ADD_PROPERTY = 102;
	public final static String KEY_SHARED_PREFERENCE = "shared_pref_key", KEY_LOGGED_IN = "logged_in_key", KEY_USER_NAME = "user_name_key", KEY_EMAIL = "email_key";
	private SharedPreferences pfm;
	private Toast mToast;
	private FragmentManager mFragmentManager;
	private PropertyFragment mPropFragment;
	private MenuItem prevMenuItem;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		Toolbar mToolbar = findViewById(R.id.toolbar);
		setSupportActionBar(mToolbar);

        pfm = getSharedPreferences(KEY_SHARED_PREFERENCE,MODE_PRIVATE);
        userIsLoggedIn = pfm.getBoolean(KEY_LOGGED_IN,false); // Fetch and check login state here

        if(!userIsLoggedIn) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent,REQUEST_LOGIN);
        }

		mFragmentManager = getSupportFragmentManager();

		final ViewPager viewPager = findViewById(R.id.frame);
		setupViewPager(viewPager);

		final BottomNavigationView bNavView = findViewById(R.id.bottom_navigation_view);
		bNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				if(!userIsLoggedIn) loginPrompt();
				else switch(item.getItemId()) {
					case R.id.menu_properties:
						viewPager.setCurrentItem(0);
						break;
					case R.id.menu_inbox:
						viewPager.setCurrentItem(1);
						break;
					case R.id.menu_bookings:
						viewPager.setCurrentItem(2);
						break;
					case R.id.menu_refund:
						viewPager.setCurrentItem(3);
						break;
					case R.id.menu_dashboard:
						viewPager.setCurrentItem(4);
						return true;
				}
				return false;
			}
		});

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

			@Override
			public void onPageSelected(int position) {
				if (prevMenuItem != null) prevMenuItem.setChecked(false);
				else bNavView.getMenu().getItem(0).setChecked(false);

				bNavView.getMenu().getItem(position).setChecked(true);
				prevMenuItem = bNavView.getMenu().getItem(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) { }
		});

		mToast = new Toast(this);
    }

	private void setupViewPager(ViewPager v) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(mFragmentManager);
		mPropFragment = new PropertyFragment();
		adapter.addFragment(mPropFragment);
		adapter.addFragment(new InboxFragment());
		adapter.addFragment(new AllPaymentFragment());
		adapter.addFragment(new RefundFragment());
		adapter.addFragment(new Fragment());
		v.setAdapter(adapter);
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
		}
		return super.onOptionsItemSelected(item);
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
				if (resultCode == RESULT_OK) {
					if (userIsLoggedIn = pfm.getBoolean(KEY_LOGGED_IN, false)) d("Logged in");
				}
				else finish();
				break;
			case REQUEST_ADD_PROPERTY:
				if(data != null) {
					IncompleteProperty prop = data.getParcelableExtra(AddPropertyActivity.KEY_PROPERTY);
					if(prop != null) switch (resultCode) {
						case RESULT_CANCELED:
							mPropFragment.insert(prop);
							break;
						case RESULT_OK:
							mPropFragment.delete(prop);
							break;
					}
				}/*
				if(resultCode == RESULT_OK) mPropFragment.delete(prop);
				else if(resultCode == RESULT_CANCELED && data != null) {
					System.out.println("Result is cancelled and data not null");
					IncompleteProperty prop = data.getParcelableExtra(AddPropertyActivity.KEY_PROPERTY);
					if(prop != null) { mPropFragment.insert(prop);System.out.println("Property not null too, insert called"); }
				}*/
		}
	}


	@Override
	public void onFragmentInteraction(Uri uri) { }

    @Override
    public void addNewProperty() { startActivityForResult(new Intent(this, AddPropertyActivity.class), REQUEST_ADD_PROPERTY); }

	@Override
	public void addOldProperty(IncompleteProperty incompleteProperty) {
		Intent intent = new Intent(this, AddPropertyActivity.class).putExtra(AddPropertyActivity.KEY_PROPERTY, incompleteProperty);
        startActivityForResult(intent, REQUEST_ADD_PROPERTY);
	}

	@Override
	public void onPropertyClicked(Property property) {
		Intent intent = new Intent(this, PropertyDetailsActivity.class);
		intent.putExtra(AddPropertyActivity.KEY_PROPERTY, property);
		startActivity(intent);
	}
}