package com.example.mario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.mario.AddPropertyActivity.KEY_PROPERTY;

public class PropertyFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.property_empty_message) TextView emptyText;
    @BindView(R.id.property_list) RecyclerView propertyList;
    private Unbinder unbinder;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;

    public PropertyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        View v =  inflater.inflate(R.layout.fragment_property, container, false);
        unbinder = ButterKnife.bind(this, v);
        propertyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        propertyList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        setupAdapter();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.property_fab)
    public void addNewProperty() {
        mListener.addNewProperty();
    }

    private void setupAdapter() {
        Query q = db.collection("properties").whereEqualTo("hid", 1);

        FirestoreRecyclerOptions<Property> res = new FirestoreRecyclerOptions.Builder<Property>()
                .setQuery(q, Property.class).build();

        adapter = new FirestoreRecyclerAdapter<Property, PropertyHolder>(res) {

            @Override
            protected void onBindViewHolder(@NonNull PropertyHolder holder, int position, @NonNull final Property model) {
                holder.propName.setText(model.getName());
                holder.img.setImageResource(R.drawable.camera);
                //Glide.with(getActivity()).load(model.image).into(holder.imgview);
                /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(KEY_PROPERTY, model);
                        mListener.onPropertyClicked(bundle);
                    }
                });*/
            }

            @NonNull
            @Override
            public PropertyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if(emptyText.getVisibility() == View.VISIBLE) emptyText.setVisibility(View.GONE);
                return new PropertyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item, parent, false));
            }

            @Override
            public void onError(@NonNull FirebaseFirestoreException e) {
                super.onError(e);
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        propertyList.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        void addNewProperty();
    }

    class PropertyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.property_item_name) TextView propName;
        @BindView(R.id.property_item_image) ImageView img;

        PropertyHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
