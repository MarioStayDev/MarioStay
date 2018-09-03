package com.example.mario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RoomsFragment extends Fragment {

    private static final String PROPERTY = "property";

    private String propID;
    @BindView(R.id.room_view_progress) ProgressBar progressBar;
    @BindView(R.id.room_view_rec) RecyclerView rv;
    private FirebaseFirestore db;
    private Unbinder unbinder;

    private OnFragmentInteractionListener mListener;

    public RoomsFragment() { }

    public static RoomsFragment newInstance(String param1) {
        RoomsFragment fragment = new RoomsFragment();
        Bundle args = new Bundle();
        args.putString(PROPERTY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            propID = getArguments().getString(PROPERTY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rooms, container, false);
        unbinder = ButterKnife.bind(this, v);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(layoutManager);
        //rv.setItemAnimator(new DefaultItemAnimator());

        db = FirebaseFirestore.getInstance();
        setupAdapter();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setupAdapter() {
        System.out.println("Property ID is : "+propID);
        Query q = db.collection("properties").document(propID).collection("rooms").orderBy("roomNo");

        FirestoreRecyclerOptions<Room> res = new FirestoreRecyclerOptions.Builder<Room>()
                .setQuery(q, Room.class).build();

        FirestoreRecyclerAdapter adapter = new FirestoreRecyclerAdapter<Room, RoomHolder>(res) {

            @Override
            protected void onBindViewHolder(@NonNull RoomHolder holder, int position, @NonNull final Room model) {
                progressBar.setVisibility(View.GONE);
                //final int actualPosition = holder.getAdapterPosition();
                holder.propName.setText(String.valueOf(model.getRoomNo()));

                //GlideApp.with(getActivity()).load(R.drawable.bg).placeholder(R.drawable.ic_placeholder_384dp).into(holder.pic);

                //Glide.with(getActivity()).load(model.image).into(holder.imgview);
                /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //d("Clicked pos " + actualPosition);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(KEY_PROPERTY, model);
                        mListener.onPropertyClicked(bundle);
                    }
                });*/
            }

            @NonNull
            @Override
            public RoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RoomHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item, parent, false));
            }

            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
                super.onError(e);
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    class RoomHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.room_det_name) TextView propName;
        //@BindView(R.id.room_det_photo) ImageView pic;

        RoomHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
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
        void AddRoom();
    }

    @OnClick(R.id.rooms_add_rooms)
    void addroom() {
        mListener.AddRoom();
    }

    /*

        --**--  I G N O R E   T H I S   C O D E   S E G M E N T   A S   O F   N O W  --**--

        USE THIS CODE SNIPPET TO GET ALL ROOM DATA

        db.collection("properties").document(property.getPid()).collection("rooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    ALL THE ROOM DATA IS OBTAINED
                    SEPARATE FLOOR WISE ROOMS IN FOR LOOP

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());

                        DOCUMENT.GETDATA() GIVES THE ROOM OBJECT
                    }

                    REMOVE PROGRESSBAR AND SHOW THE ROOMS
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());

                    REMOVE PROGRESS BAR AND SHOW ERROR MESSAGE
                }
            }
        });

     */
}
