package com.example.mario;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRoomsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int floors;
    //private Room room;
    private Map<String, Boolean> imap;

    @BindView(R.id.room_spinner_floorNo) Spinner floorSp;
    @BindView(R.id.add_room_edit_no) EditText rNo;
    @BindView(R.id.room_edit_beds) EditText beds;
    @BindView(R.id.room_edit_rent) EditText rent;

    private OnFragmentInteractionListener mListener;

    public AddRoomsFragment() { }

    public static AddRoomsFragment newInstance(int param1) {
        AddRoomsFragment fragment = new AddRoomsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            floors = getArguments().getInt(ARG_PARAM1);
        }
        imap = new HashMap<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_rooms, container, false);
        ButterKnife.bind(this, v);

        Integer[] floorList = new Integer[floors];
        for(int i = 0; i < floors;) floorList[i++] = i;
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, floorList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorSp.setAdapter(spinnerArrayAdapter);

        return v;
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

    @OnClick(R.id.room_button_save)
    void saveRoom() {
        /*room = new Room();
        room.setRoomNo(Integer.parseInt(rNo.getText().toString()));
        room.setFloor((Integer)floorSp.getSelectedItem());
        room.setBeds(Integer.parseInt(beds.getText().toString()));
        room.setRent(Integer.parseInt(rent.getText().toString()));*/
        mListener.saveRoom(Integer.parseInt(rNo.getText().toString()), (Integer)floorSp.getSelectedItem(), Integer.parseInt(beds.getText().toString()), Integer.parseInt(rent.getText().toString()), imap);
    }

    public interface OnFragmentInteractionListener {
        void saveRoom(int rNo, int f, int b, int r, Map<String, Boolean> m);
    }
}
