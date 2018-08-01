package com.example.mario;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddRoomsActivity extends AppCompatActivity implements AddRoomsFragment.OnFragmentInteractionListener, RoomsFragment.OnFragmentInteractionListener
{

    @BindView(R.id.add_rooms_view_pager) NonSwipeableViewPager vPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rooms);

        ButterKnife.bind(this);

        setUpViewPager();
    }

    private void setUpViewPager()
    {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new RoomsFragment());
        pagerAdapter.addFragment(new AddRoomsFragment());
        vPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}
}
