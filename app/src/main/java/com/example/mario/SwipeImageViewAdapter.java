package com.example.mario;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.badoualy.stepperindicator.StepperIndicator;

import java.util.ArrayList;
import java.util.List;

public class SwipeImageViewAdapter extends PagerAdapter
{
    private List<Uri> imageUri;
    private Context ctx;
    private LayoutInflater layoutInflater;


    @Override
    public int getCount()
    {
        return imageUri.size();
    }

    public SwipeImageViewAdapter(Context context,List<Uri> uri)
    {
        int i=0;
       // imageUri=new ArrayList<>();
        this.imageUri=uri;
        notifyDataSetChanged();
        if(uri.size()>0)
        {
            for(i = 0; i < uri.size(); i++)
            {
                Log.d("Property Pics Uri:", "" + uri.get(i));

                Log.d("Property Pics Uri:", "" + imageUri.get(i));

            }

        }
        this.ctx = context;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return (view==(ConstraintLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        layoutInflater =(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view =layoutInflater.inflate(R.layout.swipe_image_view,container,false);
        ImageView imageView = (ImageView)item_view.findViewById(R.id.propdetails_propimage_imageview);
        GlideApp.with(ctx).load((imageUri.get(position)).toString()).into(imageView);

        container.addView(item_view);

        return  item_view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {

        container.removeView((ConstraintLayout)object);
    }
}
