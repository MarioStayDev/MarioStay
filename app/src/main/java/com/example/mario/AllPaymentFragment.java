package com.example.mario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AllPaymentFragment extends Fragment
{
    private bookingsDataAdapter Adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {



        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView rcv = v.findViewById(R.id.chat_list_chatcontacts);
        rcv.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        Adapter = new bookingsDataAdapter();
        rcv.setAdapter(Adapter);
        return v;

    }

}
