package com.example.mario;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DragNDropAdapter extends RecyclerView.Adapter<DragNDropAdapter.holder> {

    private Context context;
    private List<Uri> photos;

    DragNDropAdapter(Context c) {
        context = c;
        photos = new ArrayList<>();
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { return new holder(LayoutInflater.from(context).inflate(R.layout.list_item_add_property_photo, parent, false)); }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) { GlideApp.with(context).load(photos.get(position)).placeholder(R.drawable.ic_placeholder_384dp).into(holder.squareImageView); }

    @Override
    public int getItemCount() { return photos.size(); }

    public void addPhoto(Uri uri) { photos.add(uri); }

    static class holder extends RecyclerView.ViewHolder {
        SquareImageView squareImageView;
        holder(View v) {
            super(v);
            squareImageView = v.findViewById(R.id.add_property_photo_holder);
        }
    }
}
