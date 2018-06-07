package com.example.mario;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.Context.MODE_PRIVATE;

public class BrowseFragment extends Fragment
{
    //private RecyclerView mList;
    private DataAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    public BrowseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_browse, container, false);
        RecyclerView mList = v.findViewById(R.id.list);
        mList.setLayoutManager(new GridLayoutManager(getContext(),2));
        mAdapter = new DataAdapter();
        mList.setAdapter(mAdapter);
        loadData();
        return v;
    }
    private void loadData()
    {
        //This should be asynchronous load from internet
        for(int i=0;i<32;i++)
            mAdapter.add(new Data());
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
