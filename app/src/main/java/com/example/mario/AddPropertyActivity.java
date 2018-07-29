package com.example.mario;

import android.content.Context;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddPropertyActivity extends AppCompatActivity  implements AddPropertyFragment.OnFragmentInteractionListener,
                                                            AddPropertyDescriptionFragment.OnFragmentInteractionListener,
                                                            AddPropertyPhotoFragment.OnFragmentInteractionListener,
                                                            AddPropertyUploadFragment.OnFragmentInteractionListener {

    final static String KEY_PROPERTY = "com.example.mario.AddPropertyActivity.KEY_PROPERTY";
    private NonSwipeableViewPager mViewPager;
    private Toast mToast;
    private Property property;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_property);

        property = new Property();
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
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void nextFragment() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }

    @Override
    public void lastFragment(final ProgressBar p, final Button b) {
        p.setVisibility(View.VISIBLE);
        b.setVisibility(View.GONE);
        db.collection("properties").add(property).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(DocumentReference documentReference)
            {
                b.setVisibility(View.VISIBLE);
                p.setVisibility(View.GONE);
                d("Success");
                //db.collection("user").document()
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
