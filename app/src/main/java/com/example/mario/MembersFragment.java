package com.example.mario;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MembersFragment extends Fragment {

    MembersAdapter Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView rcv = v.findViewById(R.id.chat_list_chatcontacts);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        Adapter = new MembersAdapter();
        rcv.setAdapter(Adapter);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Adapter = null;
    }

    private class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MembersHolder> {
        private List<String> chats;
        private List<Integer> pics;
        private List<String> stats;

        MembersAdapter() {
            chats = new ArrayList<>();
            chats.add("Steve Jobs");
            chats.add("Barrack Obama");
            chats.add("Douglas Engelbart");
            chats.add("Tim Cook");
            chats.add("Bill Gates");
            chats.add("Sundar Pichai");
            chats.add("Satya Nadella");
            chats.add("Bruce Lee");
            chats.add("Martin L. King");
            chats.add("Ringo Starr");
            chats.add("Michael Schumacher");
            chats.add("Roger Federer");
            chats.add("Michael Jackson");

            pics = new ArrayList<>();
            pics.add(R.drawable.steve_tn);
            pics.add(R.drawable.obama_tn);
            pics.add(R.drawable.engelbart_tn);
            pics.add(R.drawable.tim_tn);
            pics.add(R.drawable.bill_tn);
            pics.add(R.drawable.pichai_tn);
            pics.add(R.drawable.nadella_tn);
            pics.add(R.drawable.ic_account_circle_black_24dp);
            pics.add(R.drawable.martin_tn);
            pics.add(R.drawable.ringo_tn);
            pics.add(R.drawable.michael_tn);
            pics.add(R.drawable.federer_tn);
            pics.add(R.drawable.jackson_tn);

            stats = new ArrayList<>();
            stats.add("Room 101");
            stats.add("Room 102");
            stats.add("Left");
            stats.add("Room 103");
            stats.add("Left");
            stats.add("Room 201");
            stats.add("Room 202");
            stats.add("Wants to join");
            stats.add("Left");
            stats.add("Wants to join");
            stats.add("Left");
            stats.add("Room 301");
            stats.add("Wants to join");

        }

        class MembersHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
            CircleImageView profPic;
            TextView Name, Status;

            MembersHolder(RelativeLayout v) {
                super(v);
                profPic = v.findViewById(R.id.member_profile);
                Name = v.findViewById(R.id.member_name);
                Status = v.findViewById(R.id.member_status);
            }

        }

        @NonNull
        @Override
        public MembersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_members, parent, false);
            return new MembersHolder((RelativeLayout) v);
        }

        @Override
        public void onBindViewHolder(@NonNull MembersHolder holder, int position) {
            holder.profPic.setImageResource(pics.get(position));
            holder.Name.setText(chats.get(position));
            String status = stats.get(position);
            holder.Status.setText(status);
            if("Wants to join".equals(status))
                holder.Status.setTextColor(getResources().getColor(R.color.green));
            else if("Left".equals(status))
                holder.Status.setTextColor(getResources().getColor(R.color.red));
        }

        @Override
        public int getItemCount() {
            return chats.size();
        }
    }

}
