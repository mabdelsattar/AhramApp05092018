package com.abdelsattar.alahramapp.Admin;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelsattar.alahramapp.R;
import com.abdelsattar.alahramapp.model.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.List;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class ManageClientsAdpater extends RecyclerView.Adapter<ManageClientsAdpater.CustomViewHolder> {
    private List<ClientViewModel> Userslist;
    Context mcontext;

    public ManageClientsAdpater(Context mcontext, List<ClientViewModel> userslist) {
        this.Userslist = userslist;
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
        return (null != Userslist ? Userslist.size() : 0);
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


    private ProgressDialog dialog;
    static final String REQ_TAG = "DeleteItem";
    RequestQueue requestQueue;



}
