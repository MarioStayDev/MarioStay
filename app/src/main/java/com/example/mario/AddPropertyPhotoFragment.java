package com.example.mario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddPropertyPhotoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    //private Property property;
    private Unbinder unbinder;
    @BindView(R.id.prop_photo_view) ImageView img;

    public AddPropertyPhotoFragment() { }

    public static AddPropertyPhotoFragment newInstance(Property p) {
        AddPropertyPhotoFragment fragment = new AddPropertyPhotoFragment();
        /*Bundle args = new Bundle();
        args.putParcelable(AddPropertyActivity.KEY_PROPERTY, p);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) property = getArguments().getParcelable(AddPropertyActivity.KEY_PROPERTY);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_property_photo, container, false);
        unbinder = ButterKnife.bind(this, v);
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

    @OnClick(R.id.prop_photo_photo)
    public void photo() {
        img.setImageResource(R.drawable.camera);
    }

    @OnClick(R.id.add_prop_next_2)
    public void gotoNext() {
        mListener.nextFragment();
    }

    public interface OnFragmentInteractionListener {
        void nextFragment();
    }
}
