package com.example.mario;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RefundFragment extends Fragment {

    private Spinner propSpinner;
    private ProgressBar mProgressBar;
    private LinearLayout rView;
    private RecyclerView mRecyclerView;
    private RoomsAdapter mAdapter;
    private OnFragmentInteractionListener mListener;

    public RefundFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_refund, container, false);
        propSpinner = v.findViewById(R.id.refund_spinner_props);
        rView = v.findViewById(R.id.refund_view);
        mProgressBar = v.findViewById(R.id.refund_progress);
        mRecyclerView = v.findViewById(R.id.refund_list_rooms);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new RoomsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
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

    private class FetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.GONE);
            rView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try { Thread.sleep(2000); } catch(InterruptedException e) {}
            return null;
        }
    }

    private class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.viewholder> {
        List<String> mRooms;

        public class viewholder extends RecyclerView.ViewHolder {
            TextView mText;

            public viewholder(View tv) {
                super(tv);


                mText = (TextView)tv.findViewById(R.id.text_refund_room);;
            }
        }

        public RoomsAdapter() {
            mRooms = new ArrayList<>();
            for(int i=0; i<30; i++)
                mRooms.add("Room "+i);
        }

        @NonNull
        @Override
        public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View h = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_refund, parent, false);
            return new viewholder(h);
        }

        @Override
        public void onBindViewHolder(@NonNull viewholder holder, int position) {
            holder.mText.setText(mRooms.get(position));
        }

        @Override
        public int getItemCount() {
            return mRooms.size();
        }
    }
}
