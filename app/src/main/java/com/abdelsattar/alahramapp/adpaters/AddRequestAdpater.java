package com.abdelsattar.alahramapp.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdelsattar.alahramapp.R;
import com.abdelsattar.alahramapp.model.AddRequestModel;

import java.util.List;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class AddRequestAdpater extends RecyclerView.Adapter<AddRequestAdpater.CustomViewHolder> {
    private List<AddRequestModel> Itemlist;
    Context mcontext;

    public AddRequestAdpater(Context mcontext, List<AddRequestModel> itemlist) {
        this.Itemlist = itemlist;
        this.mcontext = mcontext;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        final AddRequestModel item = Itemlist.get(position);
        holder.ordername.setText(item.getOrdername());
        holder.orderprice.setText(item.getOrderprice());

        holder.tvCounter.setText(item.getCounter()+"");
        if (item.getCounter() > 0) {
            holder.tvCounter.setVisibility(View.VISIBLE);
            holder.tvCounter.setText(item.getCounter()+"");
            //notifyDataSetChanged();
        }else{
            holder.tvCounter.setVisibility(View.INVISIBLE);
        }

        holder.add.setOnClickListener(null);
        holder.remove.setOnClickListener(null);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Itemlist.add();
                item.setCounter(item.getCounter() + 1);
                //Toast.makeText(mcontext,"add**",Toast.LENGTH_LONG).show();
                if (item.getCounter() > 0) {
                    holder.tvCounter.setVisibility(View.VISIBLE);
                    holder.tvCounter.setText(item.getCounter() + "");
                    //notifyDataSetChanged();
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Itemlist.remove(item);
              //  final AddRequestModel item = Itemlist.get(position);

                item.setCounter(item.getCounter() - 1);
                if (item.getCounter() == 0) {
                    holder.tvCounter.setVisibility(View.INVISIBLE);
                    // holder.tvCounter.setText(item.getCounter());
                   // notifyDataSetChanged();
                } else {
                    //if(item.getCounter() == 0) {
                    holder.tvCounter.setVisibility(View.VISIBLE);
                    holder.tvCounter.setText(item.getCounter() + "");
                    //notifyDataSetChanged();
                }

                //}
                //  Toast.makeText(mcontext,"gg",Toast.LENGTH_LONG).show();
                //  notifyDataSetChanged();
                //notifyItemRemoved(Itemlist.indexOf(item));
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != Itemlist ? Itemlist.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView ordername, orderprice, tvCounter;
        protected ImageView add, remove;

        public CustomViewHolder(View view) {
            super(view);
            this.ordername = (TextView) view.findViewById(R.id.ordername);
            this.orderprice = (TextView) view.findViewById(R.id.price);
            this.add = (ImageView) view.findViewById(R.id.increase);
            this.remove = (ImageView) view.findViewById(R.id.decrease);
            this.tvCounter = (TextView) view.findViewById(R.id.tvCount);


        }


    }


}
