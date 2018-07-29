package com.example.mario;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddPropertyDescriptionFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;
    private IncompleteProperty property;
    /*@BindViews({ R.id.chip_lift, R.id.chip_parking, R.id.chip_cctv, R.id.chip_power,
            R.id.chip_playground, R.id.chip_pool, R.id.chip_garden, R.id.chip_gym,
            R.id.chip_tv, R.id.chip_refridgerator, R.id.chip_washing_machine,
            R.id.chip_water_purifier, R.id.chip_wifi, R.id.chip_sofa, R.id.chip_table})
    List<TextView> chips;*/
    @BindView(R.id.property_desc_edit_description) EditText description;
    //@BindView(R.id.property_desc_map) Map...

    public AddPropertyDescriptionFragment() { }

    public static AddPropertyDescriptionFragment newInstance(IncompleteProperty p) {
        AddPropertyDescriptionFragment fragment = new AddPropertyDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(AddPropertyActivity.KEY_PROPERTY, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) property = getArguments().getParcelable(AddPropertyActivity.KEY_PROPERTY);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_property_description, container, false);
        unbinder = ButterKnife.bind(this, v);

        description.setText(property.getShortDescription());
        if(property.getLift()) v.findViewById(R.id.chip_lift).setBackgroundResource(R.drawable.chip_shape);
        if(property.getParking()) v.findViewById(R.id.chip_parking).setBackgroundResource(R.drawable.chip_shape);
        if(property.getCctv()) v.findViewById(R.id.chip_cctv).setBackgroundResource(R.drawable.chip_shape);
        if(property.getPower()) v.findViewById(R.id.chip_power).setBackgroundResource(R.drawable.chip_shape);
        if(property.getPlayground()) v.findViewById(R.id.chip_playground).setBackgroundResource(R.drawable.chip_shape);
        if(property.getPool()) v.findViewById(R.id.chip_pool).setBackgroundResource(R.drawable.chip_shape);
        if(property.getGarden()) v.findViewById(R.id.chip_garden).setBackgroundResource(R.drawable.chip_shape);
        if(property.getGym()) v.findViewById(R.id.chip_gym).setBackgroundResource(R.drawable.chip_shape);
        if(property.getTv()) v.findViewById(R.id.chip_tv).setBackgroundResource(R.drawable.chip_shape);
        if(property.getFridge()) v.findViewById(R.id.chip_refridgerator).setBackgroundResource(R.drawable.chip_shape);
        if(property.getWashMac()) v.findViewById(R.id.chip_washing_machine).setBackgroundResource(R.drawable.chip_shape);
        if(property.getWater()) v.findViewById(R.id.chip_water_purifier).setBackgroundResource(R.drawable.chip_shape);
        if(property.getWifi()) v.findViewById(R.id.chip_wifi).setBackgroundResource(R.drawable.chip_shape);
        if(property.getSofa()) v.findViewById(R.id.chip_sofa).setBackgroundResource(R.drawable.chip_shape);
        if(property.getTtable()) v.findViewById(R.id.chip_table).setBackgroundResource(R.drawable.chip_shape);

        return v;
    }

    @OnClick({ R.id.chip_lift, R.id.chip_parking, R.id.chip_cctv, R.id.chip_power,
            R.id.chip_playground, R.id.chip_pool, R.id.chip_garden, R.id.chip_gym,
            R.id.chip_tv, R.id.chip_refridgerator, R.id.chip_washing_machine,
            R.id.chip_water_purifier, R.id.chip_wifi, R.id.chip_sofa, R.id.chip_table })
    public void onButtonPressed(View v) {
        boolean b;
        switch(v.getId()) {
            case R.id.chip_lift:
                b = property.getLift();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setLift(!b);
                break;
            case R.id.chip_parking:
                b = property.getParking();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setParking(!b);
                break;
            case R.id.chip_cctv:
                b = property.getCctv();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setCctv(!b);
                break;
            case R.id.chip_power:
                b = property.getPower();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setPower(!b);
                break;
            case R.id.chip_playground:
                b = property.getPlayground();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setPlayground(!b);
                break;
            case R.id.chip_pool:
                b = property.getPool();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setPool(!b);
                break;
            case R.id.chip_garden:
                b = property.getGarden();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setGarden(!b);
                break;
            case R.id.chip_gym:
                b = property.getGym();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setGym(!b);
                break;
            case R.id.chip_tv:
                b = property.getTv();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setTv(!b);
                break;
            case R.id.chip_refridgerator:
                b = property.getFridge();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setFridge(!b);
                break;
            case R.id.chip_washing_machine:
                b = property.getWashMac();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setWashMac(!b);
                break;
            case R.id.chip_water_purifier:
                b = property.getWater();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setWater(!b);
                break;
            case R.id.chip_wifi:
                b = property.getWifi();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setWifi(!b);
                break;
            case R.id.chip_sofa:
                b = property.getSofa();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setSofa(!b);
                break;
            case R.id.chip_table:
                b = property.getTtable();
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                property.setTtable(!b);
                break;
        }
    }

    @OnClick(R.id.prop_desc_next)
    public void gotoNext(Button button) {
        property.setShortDescription(description.getText().toString());
        //map
        if(button != null) mListener.nextFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        void nextFragment();
    }
}
