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
    private int noOfChips;
    private Map<String, Boolean> iMap;
    //private boolean selectedChips[];
    private String[] chipTexts;
    private Property property;
    @BindViews({ R.id.chip1, R.id.chip2, R.id.chip3, R.id.chip4, R.id.chip5, R.id.chip6, R.id.chip7, R.id.chip8, R.id.chip9, R.id.chip10, R.id.chip11, R.id.chip12, R.id.chip13, R.id.chip14, R.id.chip15 })
    List<TextView> chips;
    @BindView(R.id.property_desc_edit_description) EditText description;
    //@BindView(R.id.property_desc_map) Map...

    public AddPropertyDescriptionFragment() { }

    public static AddPropertyDescriptionFragment newInstance(Property p) {
        AddPropertyDescriptionFragment fragment = new AddPropertyDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(AddPropertyActivity.KEY_PROPERTY, p);
        fragment.setArguments(args);
        return fragment;
    }

    private final ButterKnife.Action<TextView> NAME = new ButterKnife.Action<TextView>() {
        @Override public void apply(TextView view, int index) { view.setText(chipTexts[index]); }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) property = getArguments().getParcelable(AddPropertyActivity.KEY_PROPERTY);
        Resources resources = getResources();
        noOfChips = resources.getInteger(R.integer.number_of_chips);
        //selectedChips = new boolean[noOfChips];
        chipTexts = resources.getStringArray(R.array.chip_texts);
        iMap = new HashMap<>();
        for(int i = 0; i < noOfChips; i++)
            iMap.put(chipTexts[i], false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_property_description, container, false);
        unbinder = ButterKnife.bind(this, v);
        ButterKnife.apply(chips, NAME);
        return v;
    }

    @OnClick({ R.id.chip1, R.id.chip2, R.id.chip3, R.id.chip4, R.id.chip5, R.id.chip6, R.id.chip7, R.id.chip8, R.id.chip9, R.id.chip10, R.id.chip11, R.id.chip12, R.id.chip13, R.id.chip14, R.id.chip15 })
    public void onButtonPressed(View v) {
        int i = 15;
        switch(v.getId()) {
            case R.id.chip1: i --;
            case R.id.chip2: i --;
            case R.id.chip3: i --;
            case R.id.chip4: i --;
            case R.id.chip5: i --;
            case R.id.chip6: i --;
            case R.id.chip7: i --;
            case R.id.chip8: i --;
            case R.id.chip9: i --;
            case R.id.chip10: i --;
            case R.id.chip11: i --;
            case R.id.chip12: i --;
            case R.id.chip13: i --;
            case R.id.chip14: i --;
            case R.id.chip15: i --;
        }
        String key = chipTexts[i];
        boolean value = iMap.get(key);
        v.setBackgroundResource(value/*selectedChips[i]*/?R.drawable.chip_shape_deactivated:R.drawable.chip_shape);
        //selectedChips[i] = !selectedChips[i];
        iMap.put(key, !value);
    }

    @OnClick(R.id.prop_desc_next)
    public void gotoNext() {
        property.setShortDescription(description.getText().toString());
        property.setAmenities(iMap);
        mListener.nextFragment();
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
