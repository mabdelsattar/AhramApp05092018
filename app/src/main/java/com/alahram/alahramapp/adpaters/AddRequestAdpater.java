package com.alahram.alahramapp.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
        CustomViewHolder viewHolder = new CustomViewHolder(view,new MyCustomEditTextListener());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        final AddRequestModel item = Itemlist.get(position);
        holder.ordername.setText(item.getOrdername());
        holder.orderprice.setText(item.getOrderprice());

//        holder.ettroodcount.setText(item.getTrode()+"");
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.ettroodcount.setText(Itemlist.get(holder.getAdapterPosition()).getTrode()+"");


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
        //holder.ettroodcount.addTextChangedListener(null);

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

       /* holder.ettroodcount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() > 0)
                    item.setTrode(Integer.parseInt(s.toString()));
            }
        });*/



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
        protected  EditText ettroodcount;
        protected ImageView add, remove;
        public MyCustomEditTextListener myCustomEditTextListener;


        public CustomViewHolder(View view,MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            this.ordername = (TextView) view.findViewById(R.id.ordername);
            this.orderprice = (TextView) view.findViewById(R.id.price);
            this.add = (ImageView) view.findViewById(R.id.increase);
            this.remove = (ImageView) view.findViewById(R.id.decrease);
            this.tvCounter = (TextView) view.findViewById(R.id.tvCount);
            this.ettroodcount = (EditText) view.findViewById(R.id.ettroodcount);

            this.myCustomEditTextListener = myCustomEditTextListener;
            this.ettroodcount.addTextChangedListener(myCustomEditTextListener);
        }


    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if(charSequence.length() > 0)
                Itemlist.get(position).setTrode(Integer.parseInt(charSequence.toString()));
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
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
