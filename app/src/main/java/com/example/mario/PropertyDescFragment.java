package com.example.mario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PropertyDescFragment extends Fragment implements View.OnClickListener {

    //private TextView[] chips;
    private boolean[] selectedChips;

    private OnFragmentInteractionListener mListener;

    public PropertyDescFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int noOfChips = getResources().getInteger(R.integer.number_of_chips);
        selectedChips = new boolean[noOfChips];
        View v = inflater.inflate(R.layout.fragment_property_desc, container, false);
        v.findViewById(R.id.chip1).setOnClickListener(this);
        v.findViewById(R.id.chip2).setOnClickListener(this);
        v.findViewById(R.id.chip3).setOnClickListener(this);
        v.findViewById(R.id.chip4).setOnClickListener(this);
        v.findViewById(R.id.chip5).setOnClickListener(this);
        v.findViewById(R.id.chip6).setOnClickListener(this);
        v.findViewById(R.id.chip7).setOnClickListener(this);
        v.findViewById(R.id.chip8).setOnClickListener(this);
        v.findViewById(R.id.chip9).setOnClickListener(this);
        v.findViewById(R.id.chip10).setOnClickListener(this);
        v.findViewById(R.id.chip11).setOnClickListener(this);
        v.findViewById(R.id.chip12).setOnClickListener(this);
        v.findViewById(R.id.chip13).setOnClickListener(this);
        v.findViewById(R.id.chip14).setOnClickListener(this);
        v.findViewById(R.id.chip15).setOnClickListener(this);
        for(int i = 0; i < noOfChips; i++) selectedChips[i] = false;
        return v;
    }

    @Override
    public void onClick(View v) {
        int i = 0;
        switch(v.getId()) {
            case R.id.chip1:
                i = 0;
                break;
            case R.id.chip2:
                i = 1;
                break;
            case R.id.chip3:
                i = 2;
                break;
            case R.id.chip4:
                i = 3;
                break;
            case R.id.chip5:
                i = 4;
                break;
            case R.id.chip6:
                i = 5;
                break;
            case R.id.chip7:
                i = 6;
                break;
            case R.id.chip8:
                i = 7;
                break;
            case R.id.chip9:
                i = 8;
                break;
            case R.id.chip10:
                i = 9;
                break;
            case R.id.chip11:
                i = 10;
                break;
            case R.id.chip12:
                i = 11;
                break;
            case R.id.chip13:
                i = 12;
                break;
            case R.id.chip14:
                i = 13;
                break;
            case R.id.chip15:
                i = 14;
                break;
        }
        v.setBackgroundResource(selectedChips[i]?R.drawable.chip_shape_deactivated:R.drawable.chip_shape);
        selectedChips[i] = !selectedChips[i];
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
