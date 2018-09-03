package com.example.mario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddPropertyPhotoFragment extends Fragment
{

    private OnFragmentInteractionListener mListener;
    private final int READ_REQUEST_CODE = 105;
    private IncompleteProperty property;
    private Unbinder unbinder;
    private DragNDropAdapter adapter;
    @BindView(R.id.prop_photo_view) RecyclerView img;

    public AddPropertyPhotoFragment() { }

    public static AddPropertyPhotoFragment newInstance(IncompleteProperty p)
    {
        AddPropertyPhotoFragment fragment = new AddPropertyPhotoFragment();
        Bundle args = new Bundle();
        args.putParcelable(AddPropertyActivity.KEY_PROPERTY, p);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) property = getArguments().getParcelable(AddPropertyActivity.KEY_PROPERTY);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_add_property_photo, container, false);


        unbinder = ButterKnife.bind(this, v);

        int i=0;
        List<Uri> imgUri = new ArrayList<>();




            /*for(i = 0; i < property.getPicUri().size(); i++)
            {

                Log.d("Property Pics Uri:", "" + property.getPicUri().get(i));

                if((property.getPicUri().get(i))!=null)
                imgUri.add((property.getPicUri().get(i)));
                imgUri.add((property.getPicUri().get(i)));

                // Log.d("Property Pics Uri:", "" + imageUri.get(i));

            }*/



        if((property.getPicUri_1())!=null)
        imgUri.add(Uri.parse(property.getPicUri_1()));

        if((property.getPicUri_2())!=null)
            imgUri.add(Uri.parse(property.getPicUri_2()));

        if((property.getPicUri_3())!=null)
            imgUri.add(Uri.parse(property.getPicUri_3()));

        if((property.getPicUri_4())!=null)
            imgUri.add(Uri.parse(property.getPicUri_4()));

        adapter = new DragNDropAdapter(getActivity());
        adapter.setPhotos(imgUri);
        Log.d("Property desc Fragment:","Run");



        img.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        img.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback()
        {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
            {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                adapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) { }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(img);
        return v;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;

        }
        else
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

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.prop_photo_photo)
    public void photo()
    {
        //img.setImageResource(R.drawable.camera);
        if(adapter.getItemCount()<4)
        {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setType("image/*");
            startActivityForResult(intent, READ_REQUEST_CODE);

        }
        else d("Only 4 photos can be uploaded!");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {

            case READ_REQUEST_CODE:

                if(resultCode == Activity.RESULT_OK && data != null)
                {

                    System.out.println("Photo received");
                    Uri imageuri= data.getData();





                    int t = adapter.getItemCount();
                    adapter.addPhoto(imageuri);
                    adapter.notifyItemInserted(t);
                    adapter.notifyDataSetChanged();

                    switch (t)
                    {
                        case 0 : property.setPicUri_1(imageuri.toString());
                                    break;

                        case 1 : property.setPicUri_2(imageuri.toString());
                                    break;

                        case 2 : property.setPicUri_3(imageuri.toString());
                            break;

                        case 3 : property.setPicUri_4(imageuri.toString());
                            break;

                            default: d("Only 4 photos can be uploaded!");
                    }




                }
                break;
        }

    }

    @OnClick(R.id.add_prop_next_2)
    public void gotoNext(Button button)
    {
        d("COUNT IN BUTTON: "+property.getPicUri_4());
        mListener.nextFragment();
    }

    public interface OnFragmentInteractionListener
    {
        void nextFragment();
    }

    private void d(String s)
    {
        Toast mToast;

        mToast = Toast.makeText(this.getActivity(), s, Toast.LENGTH_LONG);
        mToast.show();
    }
}
