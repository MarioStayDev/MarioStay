package com.example.mario;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class bookingsDataAdapter extends RecyclerView.Adapter<bookingsDataAdapter.Viewholder>
{
    private List<Data> mData;

    class Viewholder extends RecyclerView.ViewHolder
    {
        private TextView amount;
        //private TextView getReceipt;

        private TextView name,paymentType;

        Viewholder(LinearLayout t)
        {
            super(t);
            name=t.findViewById(R.id.booking_text_mem_name);
            paymentType=t.findViewById(R.id.booking_text_payment_type);
            amount=t.findViewById(R.id.booking_text_amount);
            //getReceipt=t.findViewById(R.id.booking_button_get_receipt);

        }
    }

    bookingsDataAdapter()
    {
        mData=new ArrayList<Data>();
    }

    public void add(Data d) {
        mData.add(d);
        notifyItemInserted(getItemCount()-1);
    }

    @NonNull
    @Override
    public bookingsDataAdapter.Viewholder onCreateViewHolder(ViewGroup p1, int p2)
    {
        LinearLayout ll=(LinearLayout) LayoutInflater.from(p1.getContext()).inflate(R.layout.list_items_bookings,p1,false);
        return new bookingsDataAdapter.Viewholder(ll);
    }

    @Override
    public void onBindViewHolder(bookingsDataAdapter.Viewholder p1, int p2)
    {
        p1.name.setText("name/dd/mm-dd/mm");
        p1.amount.setText("Rs xx");
        p1.paymentType.setText("Room No-xyz/deposit");

    }

    @Override
    public int getItemCount() { return 3; }
}
