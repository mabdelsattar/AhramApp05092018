package com.alahram.alahramapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class orderListAdapter extends RecyclerView.Adapter<orderListAdapter.CustomViewHolder>
{
    private List<oderlist> Itemlist;
    Context mcontext;
    public  orderListAdapter(Context mcontext, List<oderlist> itemlist)
    {
        this.Itemlist=itemlist;
        this.mcontext=mcontext;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_orderlistitem, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mcontext.startActivity(new Intent(mcontext,Orderdetails.class));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position)
    {
        final oderlist item = Itemlist.get(position);
         holder.clientname.setText(item.getClientname());
        holder.clientphone.setText(item.getClientphone());
        holder.ordernumber.setText(item.getOrdernumber());
        holder.reciverdate.setText(item.getReciverdate());

    }

    @Override
    public int getItemCount() {
        return (null != Itemlist ? Itemlist.size() : 0);
    }
    public class CustomViewHolder extends RecyclerView.ViewHolder
    {
        protected TextView ordernumber,clientname,reciverdate,clientphone;

        public CustomViewHolder(View view) {
            super(view);
          //  this.ordernumber=(TextView)view.findViewById(R.id.tv_ordernuber);
            this.clientphone=(TextView)view.findViewById(R.id.tv_phone);
           // this.clientname=(TextView)view.findViewById(R.id.tv_clientname);
            this.reciverdate=(TextView)view.findViewById(R.id.tv_date);
        }
    }
}
