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

    private TextView[] chips;
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
        // Inflate the layout for this fragment
        int noOfChips = getResources().getInteger(R.integer.number_of_chips);
        chips = new TextView[noOfChips];
        selectedChips = new boolean[noOfChips];
        View v = inflater.inflate(R.layout.fragment_property_desc, container, false);
        chips[0] = v.findViewById(R.id.chip1);
        chips[1] = v.findViewById(R.id.chip2);
        chips[2] = v.findViewById(R.id.chip3);
        chips[3] = v.findViewById(R.id.chip4);
        chips[4] = v.findViewById(R.id.chip5);
        chips[5] = v.findViewById(R.id.chip6);
        for(int i = 0; i < noOfChips; i++) {
            selectedChips[i] = false;
            chips[i].setOnClickListener(this);
        }
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
