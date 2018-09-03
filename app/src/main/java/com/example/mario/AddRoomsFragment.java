package com.example.mario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static android.app.Activity.RESULT_OK;
import static com.example.mario.AddPropertyActivity.KEY_PROPERTY;

//import com.abhishek360.mario.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddRoomsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int floors;
    //private Room room;
    private Unbinder unbinder;
    private Map<String, Boolean> imap;

    @BindView(R.id.room_spinner_floorNo) Spinner floorSp;
    @BindView(R.id.add_room_edit_no) EditText rNo;
    @BindView(R.id.room_edit_beds) EditText beds;
    @BindView(R.id.room_edit_rent) EditText rent;

    private final int READ_REQUEST_CODE=105;

    private OnFragmentInteractionListener mListener;
    private DragNDropAdapter adapter;
    private RecyclerView roomPhotos;
    private Room room;
    private RoomsFragment roomsFragment = new RoomsFragment();
    private Map<String,Boolean> roomAmmenities= new HashMap<>();
    private String propertyDoc="BJaAzEDJXgrbGhIiVEcf";

    //Use integer array fro floor numbers not String array
    private Button selectPhotos,saveRoom;
    private EditText roomNo,noOfBeds,monRent;
    private Spinner floorNo;
    private Toast mToast;
    private FirebaseFirestore roomdb;

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
        imap.put(getString(R.string.chip_text_ac), false);
        imap.put(getString(R.string.chip_text_tv), false);
        imap.put(getString(R.string.chip_text_balcony), false);
        imap.put(getString(R.string.chip_text_wardrobe), false);
        imap.put(getString(R.string.chip_text_attached_washroom), false);
        imap.put(getString(R.string.chip_text_gyser), false);
        imap.put(getString(R.string.chip_text_sofa), false);
        imap.put(getString(R.string.chip_text_table), false);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_rooms, container, false);
        unbinder = ButterKnife.bind(this, v);
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_add_rooms, container, false);
        roomNo= (EditText)v.findViewById(R.id.room_edit_no) ;
        floorNo= (Spinner) v.findViewById(R.id.room_spinner_floorNo) ;
        noOfBeds=(EditText)v.findViewById(R.id.room_edit_beds);
        monRent=(EditText)v.findViewById(R.id.room_edit_rent);
        selectPhotos=(Button)v.findViewById(R.id.room_button_selectPhotos);
        saveRoom=(Button)v.findViewById(R.id.room_button_save);
        roomdb= FirebaseFirestore.getInstance();










        roomAmmenities.put("AC",false);
        roomAmmenities.put("TV",false);
        roomAmmenities.put("Balcony",false);
        roomAmmenities.put("Wardrobe",false);
        roomAmmenities.put("Attached Washroom",false);
        roomAmmenities.put("Gyser",false);
        roomAmmenities.put("Sofa",false);
        roomAmmenities.put("Table",false);





        roomPhotos = (RecyclerView)v.findViewById(R.id.room_photo_view);
        adapter = new DragNDropAdapter(getActivity());
        roomPhotos.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        roomPhotos.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                adapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) { }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(roomPhotos);

        saveRoom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                room= new Room();
                room.setRoomId(roomNo.getText().toString());
                room.setNoOfBeds(Integer.parseInt(noOfBeds.getText().toString()));
                room.setNoOfFloors(Integer.parseInt(floorNo.getSelectedItem().toString()));
                room.setMonRent(Integer.parseInt(monRent.getText().toString()));
                room.setRoomAmmenities(roomAmmenities);
                roomdb.collection("properties/"+propertyDoc+"/rooms").add(room).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {

                        d("Success");

                        //setResult(RESULT_CANCELED, r);
                        //setResult(RESULT_OK, r);
                        //finish();
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        d("Failed");
                    }
                });
                d("Uploading...");




            }
        });
        return v;

    }
    public void onButtonPressed(View v) {
        boolean b;
        switch(v.getId()) {
            case R.id.chip_AC:
                b = roomAmmenities.get("AC");
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                roomAmmenities.put("AC",!b);
                break;
            case R.id.chip_TV:
                b = roomAmmenities.get("TV");
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                roomAmmenities.put("TV",!b);
                break;
            case R.id.chip_balcony:
                b = roomAmmenities.get("Balcony");
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                roomAmmenities.put("Balcony",!b);
                break;
            case R.id.chip_washroom:
                b = roomAmmenities.get("Attached Washroom");
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                roomAmmenities.put("Attached Washroom",!b);;
                break;
            case R.id.chip_Gyser:
                b = roomAmmenities.get("Gyser");
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                roomAmmenities.put("Gyser",!b);
                break;
            case R.id.chip_sofa:
                b = roomAmmenities.get("Sofa");
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                roomAmmenities.put("Sofa",!b);
                break;
            case R.id.chip_table:
                b = roomAmmenities.get("Table");
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                roomAmmenities.put("Table",!b);
                break;

        }
    }

        Integer[] floorList = new Integer[floors];
        if(floors == 1) {
            floorList[0] = 1;
            floorSp.setEnabled(false);
        }
        else for(int i = 0; i < floors;) floorList[i++] = i;
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, floorList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorSp.setAdapter(spinnerArrayAdapter);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    @OnClick(R.id.room_button_selectPhotos)
    public void roomPhoto()
    {
        //img.setImageResource(R.drawable.camera);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case READ_REQUEST_CODE:

                if(resultCode == RESULT_OK && data != null)
                {
                    System.out.println("Photo received");
                    int t = adapter.getItemCount();
                    adapter.addPhoto(data.getData());
                    adapter.notifyItemInserted(t);
                    //adapter.notifyDataSetChanged();

                }
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick({ R.id.chip_AC, R.id.chip_TV, R.id.chip_balcony, R.id.chip_wardrobe, R.id.chip_washroom, R.id.chip_Gyser, R.id.chip_sofa, R.id.chip_table })
    public void onButtonPressed(View v) {
        boolean b;
        switch(v.getId()) {
            case R.id.chip_AC:
                b = imap.get(getString(R.string.chip_text_ac));
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                imap.put(getString(R.string.chip_text_ac), !b);
                break;
            case R.id.chip_TV:
                b = imap.get(getString(R.string.chip_text_tv));
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                imap.put(getString(R.string.chip_text_tv), !b);
                break;
            case R.id.chip_balcony:
                b = imap.get(getString(R.string.chip_text_balcony));
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                imap.put(getString(R.string.chip_text_balcony), !b);
                break;
            case R.id.chip_wardrobe:
                b = imap.get(getString(R.string.chip_text_wardrobe));
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                imap.put(getString(R.string.chip_text_wardrobe), !b);
                break;
            case R.id.chip_washroom:
                b = imap.get(getString(R.string.chip_text_attached_washroom));
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                imap.put(getString(R.string.chip_text_attached_washroom), !b);
                break;
            case R.id.chip_Gyser:
                b = imap.get(getString(R.string.chip_text_gyser));
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                imap.put(getString(R.string.chip_text_gyser), !b);
                break;
            case R.id.chip_sofa:
                b = imap.get(getString(R.string.chip_text_sofa));
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                imap.put(getString(R.string.chip_text_sofa), !b);
                break;
            case R.id.chip_table:
                b = imap.get(getString(R.string.chip_text_table));
                v.setBackgroundResource(b ? R.drawable.chip_shape_deactivated : R.drawable.chip_shape);
                imap.put(getString(R.string.chip_text_table), !b);
                break;
        }
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

    private void d(String s)
    {
        mToast.cancel();
        mToast = Toast.makeText(getActivity(), s, Toast.LENGTH_LONG);
        mToast.show();
    }
}
