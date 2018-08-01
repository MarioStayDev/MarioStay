package com.example.mario;

import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.badoualy.stepperindicator.StepperIndicator;

public class ManageRoomsActivity extends AppCompatActivity implements AddRoomsFragment.OnFragmentInteractionListener
{
    private Button addRoom;
    private android.support.v4.app.Fragment roomFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rooms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StepperIndicator indicator = findViewById(R.id.stepper_indicator);
        roomFragment = new AddRoomsFragment();


        addRoom= (Button)findViewById(R.id.button_add_rooms);
        addRoom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_manage_rooms,roomFragment).addToBackStack("No rooms Added").commit();

            }
        });




    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
