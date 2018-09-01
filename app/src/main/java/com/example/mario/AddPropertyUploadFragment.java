package com.example.mario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddPropertyUploadFragment extends Fragment
{

    private IncompleteProperty property;
    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;




    @BindView(R.id.property_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.property_desc)
    TextView desc;

    @BindView(R.id.chip_lift)
    ImageView chip_lift;
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

    @BindView(R.id.property_in_time) TextView inTime;
    @BindView(R.id.property_out_time) TextView outTime;
    @BindView(R.id.property_security_deposit) TextView securityDeposit;
    @BindView(R.id.property_notice_period) TextView noticePeriod;
    @BindView(R.id.property_min_stay) TextView minTime;
    @BindView(R.id.prop_image_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.prop_profimage_viewpager)
    ViewPager viewPager;

    private SwipeImageViewAdapter swipeImageViewAdapter;

    @BindView(R.id.property_progress_upload) ProgressBar progress;

    public AddPropertyUploadFragment() { }

    public static AddPropertyUploadFragment newInstance(IncompleteProperty p)
    {
        AddPropertyUploadFragment fragment = new AddPropertyUploadFragment();
        Bundle args = new Bundle();
        args.putParcelable(AddPropertyActivity.KEY_PROPERTY, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) property = getArguments().getParcelable(AddPropertyActivity.KEY_PROPERTY);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_add_property_upload, container, false);
        unbinder = ButterKnife.bind(this, v);



        mToolbar.setTitle(property.getName());
        swipeImageViewAdapter=new SwipeImageViewAdapter(this.getContext(),property.getPhotosUri());

        viewPager.setAdapter(swipeImageViewAdapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);
        swipeImageViewAdapter.notifyDataSetChanged();


        //Map<String, Boolean> imap = property.getAmenities();

        chip_lift.setVisibility(property.getLift() ? View.VISIBLE : View.GONE);
        chip_parking.setVisibility(property.getParking() ? View.VISIBLE : View.GONE);
        chip_cctv.setVisibility(property.getCctv() ? View.VISIBLE : View.GONE);
        chip_power.setVisibility(property.getPower() ? View.VISIBLE : View.GONE);
        chip_playground.setVisibility(property.getPlayground() ? View.VISIBLE : View.GONE);
        chip_pool.setVisibility(property.getPool() ? View.VISIBLE : View.GONE);
        chip_garden.setVisibility(property.getGarden() ? View.VISIBLE : View.GONE);
        chip_gym.setVisibility(property.getGym() ? View.VISIBLE : View.GONE);
        chip_tv.setVisibility(property.getTv() ? View.VISIBLE : View.GONE);
        chip_fridge.setVisibility(property.getFridge() ? View.VISIBLE : View.GONE);
        chip_wash.setVisibility(property.getWashMac()? View.VISIBLE : View.GONE);
        chip_water.setVisibility(property.getWater()? View.VISIBLE : View.GONE);
        chip_wifi.setVisibility(property.getWifi() ? View.VISIBLE : View.GONE);
        chip_sofa.setVisibility(property.getSofa() ? View.VISIBLE : View.GONE);
        chip_table.setVisibility(property.getTtable() ? View.VISIBLE : View.GONE);

        String temp = property.getInTime();
        inTime.setText(getString(R.string.in_time_flexible, temp == null || temp.length() == 0 || temp.equals("0") ? "Flexible" : temp));
        temp = property.getOutTime();
        outTime.setText(getString(R.string.out_time_flexible, temp == null || temp.length() == 0 || temp.equals("0") ? "Flexible" : temp));
        securityDeposit.setText(getString(R.string.property_details_security_deposit, property.getSecurityMultiplier()));
        noticePeriod.setText(getString(R.string.property_details_notice_period, property.getNoticePeriod()));
        minTime.setText(getString(R.string.property_details_min_stay_time, property.getMinStayTime()));


        return v;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.property_button_upload)
    public void upload(Button b)
    {
        mListener.lastFragment(progress, b);
    }

    public interface OnFragmentInteractionListener
    {
        void lastFragment(ProgressBar progress, Button button);
    }
}
