package com.abdelsattar.alahramapp.Admin;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import com.abdelsattar.alahramapp.R;
import com.abdelsattar.alahramapp.model.AddRequestModel;
import com.abdelsattar.alahramapp.model.Constant;
import com.abdelsattar.alahramapp.model.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManageEmpolyeesActivity extends AppCompatActivity {
    RecyclerView requestrecycleview;
    ManageEmpoyeesAdpater adapter;
    private ProgressDialog dialog;

    static final String REQ_TAG = "VACTIVITY";
    RequestQueue requestQueue;


    public static ArrayList<UserViewModel> data = new ArrayList<>();
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_manage_employees);
        final android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageEmpolyeesActivity.this,AddEditUserActivity.class));
            }
        });

        dialog = new ProgressDialog(ManageEmpolyeesActivity.this);
        dialog.setMessage("جاري التحميل...");
        dialog.show();

        String url = Constant.serversite+"/api/AlAhram/GetUsersByTypeId?type=3";

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        try {
                            data = new ArrayList<>();
                            JSONArray jsonItems = new JSONArray(response);
                            for (int i = 0; i < jsonItems.length(); i++)
                            {
                                JSONObject  jsonObject =jsonItems.getJSONObject(i);
                                int id = jsonObject.getInt("UserId");
                                int Role = 3;//jsonObject.getInt("Role");
                                String UserName =  jsonObject.getString("UserName");
                                String Password =  jsonObject.getString("Password");
                                data.add(new UserViewModel(UserName,Password, Role,id));
                            }
                            // .setText("String Response : "+ response.toString());
                            Log.i("respones", "succed");



                            adapter = new ManageEmpoyeesAdpater(ManageEmpolyeesActivity.this, data);
                            requestrecycleview = (RecyclerView) findViewById(R.id.listview);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ManageEmpolyeesActivity.this);
                            requestrecycleview.setLayoutManager(mLayoutManager);
                            requestrecycleview.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
                            requestrecycleview.setItemAnimator(new DefaultItemAnimator());
                            requestrecycleview.setAdapter(adapter);


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

                        }catch (Exception ex){


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //forceRTLIfSupported();
        setContentView(R.layout.activity_manage_employees);
        requestQueue = RequestQueueSingleton.getInstance(ManageEmpolyeesActivity.this)
                .getRequestQueue();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("ادارة المندوبين");




        final android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageEmpolyeesActivity.this,AddEditUserActivity.class));
            }
        });








    }




    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
