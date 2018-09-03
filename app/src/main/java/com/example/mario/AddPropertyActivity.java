package com.example.mario;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.mario.
import com.badoualy.stepperindicator.StepperIndicator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddPropertyActivity extends AppCompatActivity  implements AddPropertyFragment.OnFragmentInteractionListener,
                                                            AddPropertyDescriptionFragment.OnFragmentInteractionListener,
                                                            AddPropertyPhotoFragment.OnFragmentInteractionListener,
                                                            AddPropertyUploadFragment.OnFragmentInteractionListener {

    final static String KEY_PROPERTY = "com.example.mario.AddPropertyActivity.KEY_PROPERTY";
    private NonSwipeableViewPager mViewPager;
    private Toast mToast;
    private IncompleteProperty property;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
    private String hid_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        Intent intent = getIntent();
        property = (intent.hasExtra(KEY_PROPERTY)) ? (IncompleteProperty)intent.getParcelableExtra(KEY_PROPERTY) : new IncompleteProperty();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StepperIndicator indicator = findViewById(R.id.stepper_indicator);
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        indicator.setViewPager(mViewPager, true);

        db = FirebaseFirestore.getInstance();
        mToast = new Toast(this);
    }

    private void setupViewPager(ViewPager pager) {
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(AddPropertyFragment.newInstance(property));
        pagerAdapter.addFragment(AddPropertyDescriptionFragment.newInstance(property));
        pagerAdapter.addFragment(AddPropertyPhotoFragment.newInstance(property));
        pagerAdapter.addFragment(AddPropertyUploadFragment.newInstance(property));
        pager.setAdapter(pagerAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_property, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_add_property_save:
                int c = mViewPager.getCurrentItem();
                Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + c);
                if (page != null) switch(c) {
                    case 0: ((AddPropertyFragment)page).gotoNext(null);
                        break;
                    case 1: ((AddPropertyDescriptionFragment)page).gotoNext(null);
                        break;
                    case 2: ((AddPropertyPhotoFragment)page).gotoNext(null);
                        break;
                }
                Intent r = new Intent().putExtra(KEY_PROPERTY, property);
                setResult(RESULT_CANCELED, r);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void nextFragment() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

    @Override
    public void lastFragment(final ProgressBar p, final Button b) {
        p.setVisibility(View.VISIBLE);
        b.setVisibility(View.GONE);

        Map<String, Boolean> tempMap = new HashMap<>();
        tempMap.put(getString(R.string.chip_text_lift), property.getLift());
        tempMap.put(getString(R.string.chip_text_parking), property.getParking());
        tempMap.put(getString(R.string.chip_text_cctv), property.getCctv());
        tempMap.put(getString(R.string.chip_text_power), property.getPower());
        tempMap.put(getString(R.string.chip_text_playground), property.getPlayground());
        tempMap.put(getString(R.string.chip_text_pool), property.getPool());
        tempMap.put(getString(R.string.chip_text_garden), property.getGarden());
        tempMap.put(getString(R.string.chip_text_gym), property.getGym());
        tempMap.put(getString(R.string.chip_text_tv), property.getTv());
        tempMap.put(getString(R.string.chip_text_refridgerator), property.getFridge());
        tempMap.put(getString(R.string.chip_text_washing_machine), property.getWashMac());
        tempMap.put(getString(R.string.chip_text_water_purifier), property.getWater());
        tempMap.put(getString(R.string.chip_text_wifi), property.getWifi());
        tempMap.put(getString(R.string.chip_text_sofa), property.getSofa());
        tempMap.put(getString(R.string.chip_text_table), property.getTtable());

        Property property1 = new Property(property, tempMap);
        DocumentReference dref = db.collection("properties").document();
        property1.setPID(dref.getId());
        dref.set(property1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                b.setVisibility(View.VISIBLE);
                p.setVisibility(View.GONE);
                d("Success");
                Intent r = new Intent().putExtra(KEY_PROPERTY, property);
                //setResult(RESULT_CANCELED, r);
                setResult(RESULT_OK, r);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                b.setVisibility(View.VISIBLE);
                p.setVisibility(View.GONE);
                d("Failed");
            }
        });
        /*db.collection("properties").add(property1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        hid_string=firebaseUser.getUid();

        Property property1 = new Property(property,tempMap);
        property1.setHID(hid_string);


        db.collection("properties").add(property1).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {


            @Override
            public void onSuccess(DocumentReference documentReference) {
                b.setVisibility(View.VISIBLE);
                p.setVisibility(View.GONE);
                d("Success");
                Intent r = new Intent().putExtra(KEY_PROPERTY, property);
                //setResult(RESULT_CANCELED, r);
                setResult(RESULT_OK, r);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                b.setVisibility(View.VISIBLE);
                p.setVisibility(View.GONE);
                d("Failed");
            }
        });*/
        d("Uploading...");
    }

    @Override
    public void onBackPressed() {
        int currentItem = mViewPager.getCurrentItem();
        if(currentItem == 0)
            super.onBackPressed();
        else
            mViewPager.setCurrentItem(currentItem - 1);
    }

    private void d(String s) {
        mToast.cancel();
        mToast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        mToast.show();
    }
}
