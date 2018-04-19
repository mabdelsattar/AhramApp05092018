package com.abdelsattar.alahramapp.Ui;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.abdelsattar.alahramapp.R;
import com.abdelsattar.alahramapp.adpaters.AddRequestAdpater;
import com.abdelsattar.alahramapp.adpaters.ShowRequestAdapter;
import com.abdelsattar.alahramapp.model.AddRequestModel;
import com.abdelsattar.alahramapp.model.Constant;
import com.abdelsattar.alahramapp.model.RequestModel;
import com.abdelsattar.alahramapp.model.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.abdelsattar.alahramapp.Utilitis.ResponseParser.getRequestData;

public class ShowAllRequestsActivity extends AppCompatActivity {
    RecyclerView requestrecycleview;
    ShowRequestAdapter adapter;
    List<RequestModel> data;
    private ProgressDialog dialog;

    static final String REQ_TAG = "VACTIVITY";
    RequestQueue requestQueue;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allrequests);
        forceRTLIfSupported();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("جميع الطلبات");


        data = new ArrayList<RequestModel>();
        adapter = new ShowRequestAdapter(data,this);
        requestrecycleview = (RecyclerView) findViewById(R.id.allrequest);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        requestrecycleview.setLayoutManager(mLayoutManager);
        requestrecycleview.setItemAnimator(new DefaultItemAnimator());
        requestrecycleview.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider_gray));
        requestrecycleview.addItemDecoration(divider);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowAllRequestsActivity.this,RequestFormActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestQueue = RequestQueueSingleton.getInstance(ShowAllRequestsActivity.this)
                .getRequestQueue();

        dialog = new ProgressDialog(ShowAllRequestsActivity.this);
        dialog.setMessage("جاري التحميل...");
        dialog.show();

        String url = Constant.serversite+"/api/AlAhram/GetAllByUserId?userid";
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        try {
                            data.clear();
                            data.addAll(getRequestData(response));
                            adapter.notifyDataSetChanged();

                        }catch (Exception ex){


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                error.printStackTrace();
            }
        });
        jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);

    }
}
