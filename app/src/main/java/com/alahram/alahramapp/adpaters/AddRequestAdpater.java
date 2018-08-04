package com.alahram.alahramapp.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.alahram.alahramapp.Admin.ClientViewModel;
import com.alahram.alahramapp.R;
import com.alahram.alahramapp.model.AddRequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class AddRequestAdpater extends RecyclerView.Adapter<AddRequestAdpater.CustomViewHolder>  implements Filterable {
    ArrayList<AddRequestModel> Itemlist;
      ArrayList<AddRequestModel> ItemlistFilter;
    public  CustomFilter filter;

    Context mcontext;

    public AddRequestAdpater(Context mcontext, ArrayList<AddRequestModel> itemlist) {
        this.Itemlist = itemlist;
        this.ItemlistFilter = itemlist;
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
        return Itemlist.size();
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



    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(ItemlistFilter,this);
        }

        return filter;

//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                   // ItemlistFilter = Itemlist;
//                } else {
//                    List<AddRequestModel> filteredList = new ArrayList<>();
//                    for (AddRequestModel row : Itemlist) {
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (
//                                row.getOrdername().toLowerCase().contains(charString.toLowerCase()))
//                        {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    //ItemlistFilter = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = ItemlistFilter;
//                filterResults.count = ItemlistFilter.size();
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                ItemlistFilter = (ArrayList<AddRequestModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
    }




}
