package com.abdelsattar.alahramapp.Admin;

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

public class ManageEmpoyeesAdpater extends RecyclerView.Adapter<ManageEmpoyeesAdpater.CustomViewHolder> {
    private List<UserViewModel> Userslist;
    Context mcontext;

    public ManageEmpoyeesAdpater(Context mcontext, List<UserViewModel> userslist) {
        this.Userslist = userslist;
        this.mcontext = mcontext;
    }

    private void deleteRequest(final UserViewModel requestModel) {
        new AlertDialog.Builder(mcontext)
                .setTitle("تأكيد الحظر")
                .setMessage("تأكيد حظر المندوب ؟")
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
        final UserViewModel item = Userslist.get(position);
        holder.ordername.setText(item.getUserName());
        holder.orderprice.setVisibility(View.INVISIBLE);
        holder.tvCounter.setVisibility(View.INVISIBLE);

        holder.add.setOnClickListener(null);
        holder.remove.setOnClickListener(null);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Userslist.add();
                Intent intent= new Intent(mcontext,AddEditUserActivity.class);
                intent.putExtra("Id",item.getUserId()+"");
                intent.putExtra("UserName",item.getUserName()+"");
                intent.putExtra("Password",item.getPassword()+"");
                intent.putExtra("Role",item.getRole()+"");
                mcontext.startActivity(intent);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Userslist.remove(item);
              //  final UserViewModel item = Userslist.get(position);

                deleteRequest(item);


                //TODO Call Remove Web Service


                //}
                //  Toast.makeText(mcontext,"gg",Toast.LENGTH_LONG).show();
                //  notifyDataSetChanged();
                //notifyItemRemoved(Userslist.indexOf(item));
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

    public void getJsonResponsePost(final UserViewModel requestModel) {

        requestQueue = RequestQueueSingleton.getInstance(mcontext)
                .getRequestQueue();
        dialog = new ProgressDialog(mcontext);
        dialog.setMessage("جاري التحميل...");
        dialog.setCancelable(false);
        dialog.show();

        String url = "http://mabdelsattar1992-001-site1.gtempurl.com//api/AlAhram/BlockUserById?userId=" + requestModel.getUserId();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Log.i("respones", response);
                        if (!response.equals("null")) {
                            ManageEmpoyeesAdpater.this.Userslist.remove(requestModel);
                            notifyDataSetChanged();

                        } else {
                            Toast.makeText(mcontext,"لايمكن مسح المندوب حيث يوجد بيانات قديمه مسجله به",Toast.LENGTH_LONG).show();
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
