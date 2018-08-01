package com.example.mario;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PropertyDisplayActivity extends AppCompatActivity
{
    private RecyclerView amenitiesList;
    private  RecyclerView.Adapter amenitiesAdapter;
    private List<String> amenitiesIconList;
    private Button buttonManageRooms;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_display);

        buttonManageRooms= (Button)findViewById(R.id.button_managerooms);
        buttonManageRooms.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(PropertyDisplayActivity.this,ManageRoomsActivity.class);
                startActivity(i);
            }
        });




    }
}

 class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.viewHolder>
{
    private List<String> mData;

    class viewHolder extends RecyclerView.ViewHolder
    {

        private ImageView mImgView;

        viewHolder(LinearLayout t) {
            super(t);

           // mImgView=t.findViewById(R.id.imageview_ammenties_icons);
        }
    }

    AmenitiesAdapter(List<String> list)
    {
        this.mData=list;
    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(ViewGroup p1, int p2)
    {
        LinearLayout ll=(LinearLayout) LayoutInflater.from(p1.getContext()).inflate(R.layout.list_item,p1,false);
        return new viewHolder(ll);
    }

    @Override
    public void onBindViewHolder(viewHolder p1, int p2)
    {

        p1.mImgView.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() { return mData.size(); }

}
