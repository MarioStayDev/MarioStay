package com.example.mario;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Viewholder>
{
	private List<Data> mData;
	
	class Viewholder extends RecyclerView.ViewHolder
	{
		private TextView mTextView;
		private ImageView mImgView;
		
		Viewholder(LinearLayout t) {
			super(t);
			mTextView=t.findViewById(R.id.item_text);
			mImgView=t.findViewById(R.id.item_image);
		}
	}
	
	DataAdapter() {
		mData=new ArrayList<Data>();
	}
	
	public void add(Data d) {
		mData.add(d);
		notifyItemInserted(getItemCount()-1);
	}

	@NonNull
	@Override
	public Viewholder onCreateViewHolder(ViewGroup p1, int p2)
	{
		LinearLayout ll=(LinearLayout)LayoutInflater.from(p1.getContext()).inflate(R.layout.list_item,p1,false);
		return new Viewholder(ll);
	}

	@Override
	public void onBindViewHolder(Viewholder p1, int p2)
	{
		p1.mTextView.setText("Item "+p2);
		p1.mImgView.setImageResource(R.mipmap.ic_launcher);
	}

	@Override
	public int getItemCount() { return mData.size(); }

}