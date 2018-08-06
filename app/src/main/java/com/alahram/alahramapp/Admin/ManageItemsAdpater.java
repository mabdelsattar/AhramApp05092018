package com.alahram.alahramapp.Admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.alahram.alahramapp.adpaters.ShowRequestAdapter;
import com.alahram.alahramapp.model.AddRequestModel;
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

public class ManageItemsAdpater extends RecyclerView.Adapter<ManageItemsAdpater.CustomViewHolder>  implements Filterable {

     ArrayList<AddRequestModel> Itemlist;
     ArrayList<AddRequestModel> filterList;
    private CustomFilterItems filter;

    Context mcontext;

    public ManageItemsAdpater(Context mcontext, ArrayList<AddRequestModel> itemlist) {
        this.Itemlist = itemlist;
        this.filterList = itemlist;
        this.mcontext = mcontext;
    }

    private void deleteRequest(final AddRequestModel requestModel) {
        new AlertDialog.Builder(mcontext)
                .setTitle("تأكيد الحذف")
                .setMessage("تأكيد حذف العنصر ؟")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d("Delete Item", requestModel.toString());
                        getJsonResponsePost(requestModel);
                    }
                })
                .setNegativeButton("تراجع", null).show();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carditem_admin, null);
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
                Intent intent= new Intent(mcontext,AddEditItemActivity.class);
                intent.putExtra("ItemId",item.getId()+"");
                intent.putExtra("Name",item.getOrdername()+"");
                intent.putExtra("Price",item.getOrderprice()+"");
                mcontext.startActivity(intent);

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Itemlist.remove(item);
              //  final AddRequestModel item = Itemlist.get(position);

                deleteRequest(item);


                //TODO Call Remove Web Service


                //}
                //  Toast.makeText(mcontext,"gg",Toast.LENGTH_LONG).show();
                //  notifyDataSetChanged();
                //notifyItemRemoved(Itemlist.indexOf(item));
            }
        });

    }

    @Override
    public int getItemCount() {
        return Itemlist.size() ;
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

    public void getJsonResponsePost(final AddRequestModel requestModel) {

        requestQueue = RequestQueueSingleton.getInstance(mcontext)
                .getRequestQueue();
        dialog = new ProgressDialog(mcontext);
        dialog.setMessage("جاري التحميل...");
        dialog.setCancelable(false);
        dialog.show();

        String url = "http://mabdelsattar1992-001-site1.gtempurl.com//api/AlAhram/DeleteItem?Id=" + requestModel.getId();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Log.i("respones", response);
                        if (!response.equals("null")) {
                            // .setText("String Response : "+ response.toString());

                            ManageItemsAdpater.this.Itemlist.remove(requestModel);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(mcontext,"لايمكن مسح العنصر حيث يوجد بيانات قديمه مسجله به",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                // clientname.setText("Error getting response");
                error.printStackTrace();
            }
        });
        jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);
    }



    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilterItems(filterList,this);
        }

        return filter;
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    ItemlistFilter = Itemlist;
//                } else {
//                    List<AddRequestModel> filteredList = new ArrayList<>();
//                    for (AddRequestModel row : Itemlist) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (
//                                row.getOrdername().toLowerCase().contains(charString.toLowerCase())
//                                )
//                        {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    ItemlistFilter = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = ItemlistFilter;
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
