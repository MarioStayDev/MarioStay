package com.example.mario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InboxFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public InboxFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inbox, container, false);
        TabLayout mTab = v.findViewById(R.id.tab_chat);
        ViewPager mPager = v.findViewById(R.id.pager_chat);
        mPager.setAdapter(new ChatFragmentAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mPager);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private class ChatFragmentAdapter extends FragmentPagerAdapter {

        ChatFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new ChatFragment();
                case 1:
                    return new MembersFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return getResources().getInteger(R.integer.inbox_fragments);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ? "Inbox" : "Current members";
        }
    }
}
