package com.abdelsattar.alahramapp.adpaters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdelsattar.alahramapp.Ui.OrderdetailsActivity;
import com.abdelsattar.alahramapp.R;
import com.abdelsattar.alahramapp.model.RequestModel;

import java.util.List;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class ShowRequestAdapter extends RecyclerView.Adapter<ShowRequestAdapter.CustomViewHolder>
{
    private List<RequestModel> Itemlist;
    Context mcontext;
    public ShowRequestAdapter(Context mcontext, List<RequestModel> itemlist)
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
                mcontext.startActivity(new Intent(mcontext,OrderdetailsActivity.class));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position)
    {
        final RequestModel item = Itemlist.get(position);
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
            this.ordernumber=(TextView)view.findViewById(R.id.tv_ordernuber);
            this.clientphone=(TextView)view.findViewById(R.id.tv_phone);
            this.clientname=(TextView)view.findViewById(R.id.tv_clientname);
            this.reciverdate=(TextView)view.findViewById(R.id.tv_date);
        }
    }
}
