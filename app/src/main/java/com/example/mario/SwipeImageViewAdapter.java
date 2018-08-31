package com.example.mario;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.badoualy.stepperindicator.StepperIndicator;

public class SwipeImageViewAdapter extends PagerAdapter
{
    private int[] imageUri={R.drawable.camera,R.drawable.bg,R.drawable.wifi};
    private Context ctx;
    private LayoutInflater layoutInflater;


    @Override
    public int getCount()
    {
        return imageUri.length;
    }

    public SwipeImageViewAdapter(Context context/*,int[] uri*/)
    {
        //this.imageUri=uri;
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
        imageView.setImageResource(imageUri[position]);
        container.addView(item_view);

        return  item_view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {

        container.removeView((ConstraintLayout)object);
    }
}
