package com.alahram.alahramapp.Ui;

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
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import com.alahram.alahramapp.ChangeStatusActivity;
import com.alahram.alahramapp.R;
import com.alahram.alahramapp.Utilitis.Preferences;
import com.alahram.alahramapp.adpaters.AddRequestAdpater;
import com.alahram.alahramapp.adpaters.ShowRequestAdapter;
import com.alahram.alahramapp.model.AddRequestModel;
import com.alahram.alahramapp.model.Constant;
import com.alahram.alahramapp.model.RequestModel;
import com.alahram.alahramapp.model.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.alahram.alahramapp.Utilitis.ResponseParser.getRequestData;
import static com.alahram.alahramapp.model.Constant.MANAGER_ROLE;

public class ShowAllRequestsActivity extends AppCompatActivity {
    RecyclerView requestrecycleview;
    ShowRequestAdapter adapter;
    ArrayList<RequestModel> data;
    private ProgressDialog dialog;
    int type = -1;

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        SearchView searchView = (SearchView) findViewById(R.id.search);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setHintTextColor(Color.GRAY);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //adapter.getFilter().filter(newText);
                adapter.getFilter().filter(newText);
                adapter.notifyDataSetChanged();
                return false;
            }

        });


        if(getIntent().getExtras() != null)
        {
            type =getIntent().getExtras().getInt("type",-1);
            if(type == 1) {
                getSupportActionBar().setTitle("الطلبات في اخر اسبوع");
fab.setVisibility(View.INVISIBLE);
            }

            else if(type ==2){
                getSupportActionBar().setTitle("الطلبات في اخر شهر");
                fab.setVisibility(View.INVISIBLE);

            }

            else if(type ==3){
                getSupportActionBar().setTitle("الطلبات في اخر عام");
                fab.setVisibility(View.INVISIBLE);

            }

        }

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
        adapter.notifyDataSetChanged();




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowAllRequestsActivity.this,RequestFormActivity.class));
            }
        });
        Preferences preferences = new Preferences(getApplicationContext());
        if (preferences.getRole() == MANAGER_ROLE)
            fab.setVisibility(View.INVISIBLE);
        else
            fab.setVisibility(View.VISIBLE);
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
        if(type == 1)
        {
        url = Constant.serversite+"/api/AlAhram/GetAllBasedOnTime?type=1";
        }
        if(type == 2)
        {
            url = Constant.serversite+"/api/AlAhram/GetAllBasedOnTime?type=2";
        }

        if(type == 3)
        {
            url = Constant.serversite+"/api/AlAhram/GetAllBasedOnTime?type=3";
        }

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

                            if(type == 1)
                            {
                                getSupportActionBar().setTitle("الطلبات في اخر اسبوع "+data.size());
                            }
                            if(type == 2)
                            {
                                getSupportActionBar().setTitle("الطلبات في اخر شهر "+data.size());
                            }

                            if(type == 3)
                            {
                                getSupportActionBar().setTitle("الطلبات في اخر عام "+data.size());
                            }


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
