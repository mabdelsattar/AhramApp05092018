package com.alahram.alahramapp.Admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alahram.alahramapp.R;
import com.alahram.alahramapp.model.RequestModel;
import com.alahram.alahramapp.model.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class ManageClientsAdpater extends RecyclerView.Adapter<ManageClientsAdpater.CustomViewHolder> implements Filterable {
     ArrayList<ClientViewModel> Userslist;
    ArrayList<ClientViewModel> Userslistfilter;
    Context mcontext;

    CustomFilterClients filter;

    public ManageClientsAdpater(Context mcontext, ArrayList<ClientViewModel> userslist) {
        this.Userslist = userslist;
        this.Userslistfilter = userslist;
        this.mcontext = mcontext;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem_admin, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        final ClientViewModel item = Userslist.get(position);
        holder.ordername.setText(item.getName());
        holder.orderprice.setText(item.getPhone());
        holder.tvCounter.setVisibility(View.INVISIBLE);
        holder.trodcount.setVisibility(View.GONE);


        holder.add.setVisibility(View.INVISIBLE);
        holder.remove.setImageResource(R.drawable.clientcontacticon);

        holder.remove.setOnClickListener(null);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", item.getPhone(), null));
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return  Userslist.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView ordername, orderprice, tvCounter,trodcount;
        protected ImageView add, remove;


        public CustomViewHolder(View view) {
            super(view);
            this.ordername = (TextView) view.findViewById(R.id.ordername);
            this.orderprice = (TextView) view.findViewById(R.id.price);
            this.add = (ImageView) view.findViewById(R.id.increase);
            this.remove = (ImageView) view.findViewById(R.id.decrease);
            this.tvCounter = (TextView) view.findViewById(R.id.tvCount);
            this.trodcount = (TextView) view.findViewById(R.id.trodcount);

        }
    }



    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilterClients(Userslistfilter,this);
        }

        return filter;
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    UserslistFiltered = Userslist;
//                } else {
//                    List<ClientViewModel> filteredList = new ArrayList<>();
//                    for (ClientViewModel row : Userslist) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (
//                                row.getPhone().toLowerCase().contains(charString.toLowerCase()) ||
//                                        row.getName().toLowerCase().contains(charString.toLowerCase())
//                                )
//                        {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    UserslistFiltered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = UserslistFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                UserslistFiltered = (ArrayList<ClientViewModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
    }



    private ProgressDialog dialog;
    static final String REQ_TAG = "DeleteItem";
    RequestQueue requestQueue;



}
