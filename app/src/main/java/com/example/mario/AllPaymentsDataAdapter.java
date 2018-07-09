package com.example.mario;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AllPaymentsDataAdapter extends RecyclerView.Adapter<AllPaymentsDataAdapter.Viewholder>
{
    private List<Data> mData;

    class Viewholder extends RecyclerView.ViewHolder
    {
        private TextView name,rentAmt,starEnd,noticeDate,roomNo;

        //private TextView getReceipt;

        private Button renew;

        Viewholder(LinearLayout t)
        {
            super(t);
            name=t.findViewById(R.id.booking_text_name);
            rentAmt=t.findViewById(R.id.booking_text_rent_amt);
            starEnd=t.findViewById(R.id.booking_text_start_end);
            noticeDate=t.findViewById(R.id.booking_text_release_notice);
            roomNo=t.findViewById(R.id.booking_text_room);
            //getReceipt=t.findViewById(R.id.booking_button_get_receipt);

        }
    }

    AllPaymentsDataAdapter()
    {
        mData=new ArrayList<Data>();
    }

    public void add(Data d) {
        mData.add(d);
        notifyItemInserted(getItemCount()-1);
    }

    @NonNull
    @Override
    public AllPaymentsDataAdapter.Viewholder onCreateViewHolder(ViewGroup p1, int p2)
    {
        LinearLayout ll=(LinearLayout) LayoutInflater.from(p1.getContext()).inflate(R.layout.list_items_all_payments,p1,false);
        return new AllPaymentsDataAdapter.Viewholder(ll);
    }

    @Override
    public void onBindViewHolder(AllPaymentsDataAdapter.Viewholder p1, int p2)
    {
        p1.name.setText("abhishek kumar");
        p1.rentAmt.setText("Rs xx");
        p1.starEnd.setText("DD/MM-DD/MM");
        p1.noticeDate.setText("DD/MM/YYYY");
        p1.roomNo.setText("Room No-xyz");

    }

    @Override
    public int getItemCount() { return 3; }
}
