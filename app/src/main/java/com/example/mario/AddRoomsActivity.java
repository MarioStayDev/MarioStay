package com.example.mario;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddRoomsActivity extends AppCompatActivity implements AddRoomsFragment.OnFragmentInteractionListener, RoomsFragment.OnFragmentInteractionListener {

    Property property;
    private FirebaseFirestore db;
    @BindView(R.id.add_rooms_view_pager) NonSwipeableViewPager vPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rooms);

        db = FirebaseFirestore.getInstance();
        property = getIntent().getParcelableExtra(AddPropertyActivity.KEY_PROPERTY);
        ButterKnife.bind(this);

        setUpViewPager();
    }

    private void setUpViewPager() {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new RoomsFragment());
        pagerAdapter.addFragment(AddRoomsFragment.newInstance(property.getFloors()));
        vPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    public void AddRoom() {
        vPager.setCurrentItem(1);
    }

    @Override
    public void saveRoom(int rNo, int f, int b, int r, Map<String, Boolean> m) {
        vPager.setCurrentItem(0);
        Room room = new Room();
        room.setRoomNo(rNo);
        room.setFloor(f);
        room.setBeds(b);
        room.setRent(r);
        room.setAmenities(m);

        //property.addRooms(room);
        db.collection("properties").document(property.getPID()).collection("rooms").add(room);
        //db.collection("properties").document(property.getPID()).set(property);
        /*

            Now update Property with this room object
            qwerty

         */
    }

    @Override
    public void onBackPressed() {
        if(vPager.getCurrentItem() == 1) vPager.setCurrentItem(0);
        else super.onBackPressed();
    }
}
