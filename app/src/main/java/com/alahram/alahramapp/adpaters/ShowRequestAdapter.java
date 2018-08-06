package com.alahram.alahramapp.adpaters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alahram.alahramapp.ChangeStatusActivity;
import com.alahram.alahramapp.Ui.LoginActivity;
import com.alahram.alahramapp.Ui.OrderdetailsActivity;
import com.alahram.alahramapp.R;
import com.alahram.alahramapp.Ui.ShowAllRequestsActivity;
import com.alahram.alahramapp.Utilitis.Preferences;
import com.alahram.alahramapp.model.Constant;
import com.alahram.alahramapp.model.RequestModel;
import com.alahram.alahramapp.model.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alahram.alahramapp.model.Constant.MANAGER_ROLE;

/**
 * Created by amiraelsayed on 1/7/2018.
 */


public class ShowRequestAdapter extends RecyclerView.Adapter<ShowRequestAdapter.MyViewHolder>  implements Filterable{
      ArrayList<RequestModel> requestModelList;
    ArrayList<RequestModel> requestModelListfilter;
    CustomFilterRequest filter;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        /**
         * ButterKnife Code
         **/
        @BindView(R.id.deleteBtn)
        ImageView mDeleteBtn;
        @BindView(R.id.orderNumber)
        TextView mOrderNumber;
        @BindView(R.id.clientName)
        TextView mClientName;
        @BindView(R.id.clientPhone)
        TextView mClientPhone;
        @BindView(R.id.orderDate)
        TextView mOrderDate;
        @BindView(R.id.orderNumberHint)
        TextView mOrderNumberHint;
        @BindView(R.id.clientNameHint)
        TextView mClientNameHint;
        @BindView(R.id.clientPhoneHint)
        TextView mClientPhoneHint;
        @BindView(R.id.orderDateHint)
        TextView mOrderDateHint;
        @BindView(R.id.myline)
        View mMyline;
        @BindView(R.id.itemView)
        LinearLayout mItemView;
        /**
         * ButterKnife Code
         **/
        Typeface tf;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tf = Typeface.createFromAsset(view.getResources().getAssets(), "DroidKufi-Bold.ttf");
        }


    }

    @Override
    public Filter getFilter() {

        if(filter==null)
        {
            filter=new CustomFilterRequest(requestModelListfilter,this);
        }

        return filter;

//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    requestModelListFiltered = requestModelList;
//                } else {
//                    List<RequestModel> filteredList = new ArrayList<>();
//                    for (RequestModel row : requestModelList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (
//                                row.getClientname().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getClientphone().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getNotes().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getOrdernumber().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getPaid().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getReciverdate().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getRemain().toLowerCase().contains(charString.toLowerCase()) ||
//                                row.getStrObject().toLowerCase().contains(charString.toLowerCase()) ||
//                                        row.getMadeBy().toLowerCase().contains(charString.toLowerCase())
//                                )
//                                {
//                            filteredList.add(row);
//                        }
//                    }
//
//                    requestModelListFiltered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = requestModelListFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                requestModelListFiltered = (ArrayList<RequestModel>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
    }



    public ShowRequestAdapter(ArrayList<RequestModel> requestModelList, Context context) {
        this.requestModelList = requestModelList;
        this.requestModelListfilter = requestModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_orderlistitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RequestModel requestModel = requestModelList.get(position);


        holder.mOrderNumber.setTypeface(holder.tf);
        holder.mClientName.setTypeface(holder.tf);
        holder.mClientPhone.setTypeface(holder.tf);
        holder.mOrderDate.setTypeface(holder.tf);
        holder.mOrderNumberHint.setTypeface(holder.tf);
        holder.mClientNameHint.setTypeface(holder.tf);
        holder.mClientPhoneHint.setTypeface(holder.tf);
        holder.mOrderDateHint.setTypeface(holder.tf);
        holder.mClientName.setText(requestModel.getClientname());
        holder.mClientPhone.setText(requestModel.getClientphone());
        holder.mOrderNumber.setText(requestModel.getOrdernumber());
        holder.mOrderDate.setText(requestModel.getReciverdate());

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Preferences preferences = new Preferences(holder.mItemView.getContext());
                if (preferences.getRole() == MANAGER_ROLE)
                    intent = new Intent(holder.mItemView.getContext(), ChangeStatusActivity.class);
                else
                    intent = new Intent(holder.mItemView.getContext(), OrderdetailsActivity.class);

                intent.putExtra("jsonObj", requestModel.getStrObject());
                holder.mItemView.getContext().startActivity(intent);

            }
        });

        holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRequest(requestModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestModelList.size();
    }

    private void deleteRequest(final RequestModel requestModel) {
        new AlertDialog.Builder(context)
                .setTitle("الغاء الطلب")
                .setMessage("تأكيد الغاء الطلب ؟")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("موافق", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Log.d("Delete Item", requestModel.toString());
                        getJsonResponsePost(requestModel);
                    }
                })
                .setNegativeButton("تراجع", null).show();
    }


    private ProgressDialog dialog;
    static final String REQ_TAG = "DeleteRequest";
    RequestQueue requestQueue;

    public void getJsonResponsePost(final RequestModel requestModel) {

        requestQueue = RequestQueueSingleton.getInstance(context)
                .getRequestQueue();
        dialog = new ProgressDialog(context);
        dialog.setMessage("جاري التحميل...");
        dialog.setCancelable(false);
        dialog.show();

        String url = "http://mabdelsattar1992-001-site1.gtempurl.com//api/AlAhram/CancelRequest?requestId=" + requestModel.getOrdernumber();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Log.i("respones", response);
                        if (response.equals("true")) {
                            // .setText("String Response : "+ response.toString());

                            ShowRequestAdapter.this.requestModelList.remove(requestModel);
                            notifyDataSetChanged();
                        } else {

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

}
