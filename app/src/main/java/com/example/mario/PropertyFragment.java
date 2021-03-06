package com.example.mario;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.mario.AddPropertyActivity.KEY_PROPERTY;

public class PropertyFragment extends Fragment
{

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.property_empty_message) TextView emptyText;
    @BindView(R.id.property_list) RecyclerView propertyList;
    @BindView(R.id.incomplete_property_list) RecyclerView incompleteList;
    private Unbinder unbinder;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    private IncompletePropertyAdapter mAdapter;
    private PropertyViewModel mPropViewModel;
    private SharedPreferences sharedPreferences;
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference;
    private String hid;
    private PropertyHolder propertyHolder;



    public PropertyFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mPropViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        mPropViewModel.getAllWords().observe(getActivity(), new Observer<List<IncompleteProperty>>()
        {
            @Override
            public void onChanged(@Nullable List<IncompleteProperty> incompleteProperties)
            {
                mAdapter.setProps(incompleteProperties);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        db = FirebaseFirestore.getInstance();

        View v =  inflater.inflate(R.layout.fragment_property, container, false);

        sharedPreferences=this.getActivity().getSharedPreferences(MainActivity.KEY_SHARED_PREFERENCE,Context.MODE_PRIVATE);

        unbinder = ButterKnife.bind(this, v);
        propertyList.setLayoutManager(new LinearLayoutManager(getActivity()));
        propertyList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


      /**  propertyList.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), propertyList ,new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override public void onItemClick(View view, int position)
                    {
                        Intent i = new Intent(getActivity(),PropertyDetailsActivity.class);
                        startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position)
                    {

                    }
                })
        ); */

        incompleteList.setLayoutManager(new LinearLayoutManager(getActivity()));
        incompleteList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        setupAdapter();
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
        else
            {

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

    @OnClick(R.id.property_fab)
    public void addNewProperty()
    {
        mListener.addNewProperty();
    }

    void insert(IncompleteProperty p)
    {
        mPropViewModel.insert(p);
    }

    void delete(IncompleteProperty p)
    {
        mPropViewModel.delete(p);
    }

    private void setupAdapter()
    {
         hid = sharedPreferences.getString(MainActivity.KEY_UID,"P1");
        Query q = db.collection("properties").whereEqualTo("hid", hid);

        FirestoreRecyclerOptions<Property> res = new FirestoreRecyclerOptions.Builder<Property>()
                .setQuery(q, Property.class).build();

        adapter = new FirestoreRecyclerAdapter<Property, PropertyHolder>(res)
        {

            @Override
            protected void onBindViewHolder(@NonNull PropertyHolder holder, int position, @NonNull final Property model)
            {
                propertyHolder=holder;
                holder.propName.setText(model.getName());
                storageReference=firebaseStorage.getReference().child("/users/PropertyPic/" + hid +"/"+model.getPID()+"/0.jpg");

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri uri)
                    {
                        Glide.with(getActivity()).load(uri.toString()).into(propertyHolder.img);
                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        propertyHolder.img.setImageResource(R.drawable.camera);
                    }
                });

                //Glide.with(getActivity()).load(model.image).into(holder.imgview);
                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //Bundle bundle = new Bundle();
                        //.putParcelable(KEY_PROPERTY, model);
                        mListener.onPropertyClicked(model);
                    }
                });
            }

            @NonNull
            @Override
            public PropertyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                if(emptyText.getVisibility() == View.VISIBLE) emptyText.setVisibility(View.GONE);
                return new PropertyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.property_item, parent, false));
            }

            @Override
            public void onError(@NonNull FirebaseFirestoreException e)
            {
                super.onError(e);
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        propertyList.setAdapter(adapter);

        mAdapter = new IncompletePropertyAdapter(getActivity());
        incompleteList.setAdapter(mAdapter);
    }

    public interface OnFragmentInteractionListener
    {
        void addNewProperty();
        void addOldProperty(IncompleteProperty incompleteProperty);
        void onPropertyClicked(Property property);
    }

    class PropertyHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.property_item_name) TextView propName;
        @BindView(R.id.property_item_image) ImageView img;

        PropertyHolder(View v)
        {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

    private class IncompletePropertyAdapter extends RecyclerView.Adapter<PropertyHolder>
    {

        private Context context;
        private List<IncompleteProperty> props;

        IncompletePropertyAdapter(Context c) { context = c; }

        @NonNull
        @Override
        public PropertyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View v = LayoutInflater.from(context).inflate(R.layout.incomplete_property_item, parent, false);

            v.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = incompleteList.getChildLayoutPosition(v);
                    IncompleteProperty p = props.get(pos);
                    mListener.addOldProperty(p);
                }
            });
            return new PropertyHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull PropertyHolder holder, int position)
        {
            holder.propName.setText(props.get(position).getName());
            holder.img.setImageResource(R.drawable.camera);
        }

        public void setProps(List<IncompleteProperty> a)
        {
            props = a;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount()
        {
            return props == null ? 0 : props.size();
        }
    }
}
