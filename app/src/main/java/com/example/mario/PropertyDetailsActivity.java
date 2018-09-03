package com.example.mario;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badoualy.stepperindicator.StepperIndicator;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PropertyDetailsActivity extends AppCompatActivity
{

    Property property;

    @BindView(R.id.property_details_toolbar) Toolbar mToolbar;
    @BindView(R.id.property_details_desc) TextView desc;

    @BindView(R.id.chip_lift) ImageView chip_lift;
    @BindView(R.id.chip_parking) ImageView chip_parking;
    @BindView(R.id.chip_cctv) ImageView chip_cctv;
    @BindView(R.id.chip_power) ImageView chip_power;
    @BindView(R.id.chip_playground) ImageView chip_playground;
    @BindView(R.id.chip_pool) ImageView chip_pool;
    @BindView(R.id.chip_garden) ImageView chip_garden;
    @BindView(R.id.chip_gym) ImageView chip_gym;
    @BindView(R.id.chip_tv) ImageView chip_tv;
    @BindView(R.id.chip_refridgerator) ImageView chip_fridge;
    @BindView(R.id.chip_washing_machine) ImageView chip_wash;
    @BindView(R.id.chip_water_purifier) ImageView chip_water;
    @BindView(R.id.chip_wifi) ImageView chip_wifi;
    @BindView(R.id.chip_sofa) ImageView chip_sofa;
    @BindView(R.id.chip_table) ImageView chip_table;

    @BindView(R.id.property_details_in_time) TextView inTime;
    @BindView(R.id.property_details_out_time) TextView outTime;
    @BindView(R.id.property_details_security_deposit) TextView securityDeposit;
    @BindView(R.id.property_details_notice_period) TextView noticePeriod;
    @BindView(R.id.property_details_min_stay) TextView minTime;
    @BindView(R.id.propdetails_image_tablayout) TabLayout tabLayout;
    @BindView(R.id.prodetails_propimage_viewpager) ViewPager viewPager;

    private SwipeImageViewAdapter swipeImageViewAdapter;
    private List<Uri> picPathuri=new ArrayList<>();
    private FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        property = getIntent().getParcelableExtra(AddPropertyActivity.KEY_PROPERTY);
        if(property == null) finish();

        storageReference=firebaseStorage.getReference().child("/users/PropertyPic/" + property.getHID() + "/" +property.getPID()+"/0.jpg");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri uri)
            {
                picPathuri.add(uri);
                swipeImageViewAdapter=new SwipeImageViewAdapter(getBaseContext(),picPathuri);

            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.d("Download Prop Pics : ",e.getMessage());

            }
        });

        viewPager.setAdapter(swipeImageViewAdapter);
        tabLayout.setupWithViewPager(viewPager);




        getSupportActionBar().setTitle(property.getName());
        desc.setText(property.getShortDescription());

        Map<String, Boolean> imap = property.getAmenities();
        chip_lift.setVisibility(imap.get(getString(R.string.chip_text_lift)) ? View.VISIBLE : View.GONE);
        chip_parking.setVisibility(imap.get(getString(R.string.chip_text_parking)) ? View.VISIBLE : View.GONE);
        chip_cctv.setVisibility(imap.get(getString(R.string.chip_text_cctv)) ? View.VISIBLE : View.GONE);
        chip_power.setVisibility(imap.get(getString(R.string.chip_text_power)) ? View.VISIBLE : View.GONE);
        chip_playground.setVisibility(imap.get(getString(R.string.chip_text_playground)) ? View.VISIBLE : View.GONE);
        chip_pool.setVisibility(imap.get(getString(R.string.chip_text_pool)) ? View.VISIBLE : View.GONE);
        chip_garden.setVisibility(imap.get(getString(R.string.chip_text_garden)) ? View.VISIBLE : View.GONE);
        chip_gym.setVisibility(imap.get(getString(R.string.chip_text_gym)) ? View.VISIBLE : View.GONE);
        chip_tv.setVisibility(imap.get(getString(R.string.chip_text_tv)) ? View.VISIBLE : View.GONE);
        chip_fridge.setVisibility(imap.get(getString(R.string.chip_text_refridgerator)) ? View.VISIBLE : View.GONE);
        chip_wash.setVisibility(imap.get(getString(R.string.chip_text_washing_machine)) ? View.VISIBLE : View.GONE);
        chip_water.setVisibility(imap.get(getString(R.string.chip_text_water_purifier)) ? View.VISIBLE : View.GONE);
        chip_wifi.setVisibility(imap.get(getString(R.string.chip_text_wifi)) ? View.VISIBLE : View.GONE);
        chip_sofa.setVisibility(imap.get(getString(R.string.chip_text_sofa)) ? View.VISIBLE : View.GONE);
        chip_table.setVisibility(imap.get(getString(R.string.chip_text_table)) ? View.VISIBLE : View.GONE);

        String temp = property.getInTime();
        inTime.setText(getString(R.string.in_time_flexible, temp == null || temp.length() == 0 || temp.equals("0") ? "Flexible" : temp));
        temp = property.getOutTime();
        outTime.setText(getString(R.string.out_time_flexible, temp == null || temp.length() == 0 || temp.equals("0") ? "Flexible" : temp));
        securityDeposit.setText(getString(R.string.property_details_security_deposit, property.getSecurityMultiplier()));
        noticePeriod.setText(getString(R.string.property_details_notice_period, property.getNoticePeriod()));
        minTime.setText(getString(R.string.property_details_min_stay_time, property.getMinStayTime()));


    }

    @OnClick(R.id.property_details_button_rooms)
    void click()
    {

        Intent intent = new Intent(this, AddRoomsActivity.class);
        intent.putExtra(AddPropertyActivity.KEY_PROPERTY, property);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch(item.getItemId())
        {

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
