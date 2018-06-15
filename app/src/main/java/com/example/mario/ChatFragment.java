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

public class ChatFragment extends Fragment {

    ChatsAdapter Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        RecyclerView rcv = v.findViewById(R.id.list_chats);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        Adapter = new ChatsAdapter();
        rcv.setAdapter(Adapter);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Adapter = null;
    }

    private class ChatsAdapter extends RecyclerView.Adapter<ChatFragment.ChatsAdapter.ChatsHolder> {
        private List<String> chats;
        private List<Integer> pics;

        ChatsAdapter() {
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

        }

        class ChatsHolder extends RecyclerView.ViewHolder {
            CircleImageView profPic;
            TextView Name, LastTime;

            ChatsHolder(RelativeLayout v) {
                super(v);
                profPic = v.findViewById(R.id.chat_profile);
                Name = v.findViewById(R.id.chat_name);
                LastTime = v.findViewById(R.id.chat_last_time);
            }

        }

        @NonNull
        @Override
        public ChatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_contacts, parent, false);
            return new ChatsHolder((RelativeLayout) v);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatsHolder holder, int position) {
            holder.profPic.setImageResource(pics.get(position));
            holder.Name.setText(chats.get(position));
        }

        @Override
        public int getItemCount() {
            return chats.size();
        }
    }
}
