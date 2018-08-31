package com.example.mario;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


public class DashboardFragment extends Fragment
{

    private TextView name_edittext,email_edittext;
    private CircleImageView circleImageView;
    private String name_string,email_string,uid_string;
    private SharedPreferences sharedPreferences;
    private FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    private StorageReference storageReference;

    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser;


    private OnFragmentInteractionListener mListener;

    public DashboardFragment()
    {
        // Required empty public constructor
    }


    public static DashboardFragment newInstance(String param1, String param2)
    {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_dashboard, container, false);

        name_edittext=(TextView)v.findViewById(R.id.dash_username_edittext);
        email_edittext=(TextView)v.findViewById(R.id.dash_email_edittext);
        circleImageView=(CircleImageView)v.findViewById(R.id.dash_proPic);

        sharedPreferences=this.getActivity().getSharedPreferences(MainActivity.KEY_SHARED_PREFERENCE,Context.MODE_PRIVATE);
        name_string=sharedPreferences.getString("FULL_NAME","Mario User");
        email_string=sharedPreferences.getString("EMAIL","User@mario.com");
        uid_string=sharedPreferences.getString("UID","uid");


        storageReference=firebaseStorage.getReference().child("/users/ProfilePic/"+uid_string+".jpg");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri uri)
            {
                Glide.with(getActivity()).load(uri.toString()).into(circleImageView);
            }
        });





        name_edittext.setText("Hello, "+name_string);
        email_edittext.setText(email_string);




        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
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



    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
