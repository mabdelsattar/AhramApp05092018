package com.abdelsattar.alahramapp.Admin;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.abdelsattar.alahramapp.R;
import com.abdelsattar.alahramapp.Ui.RequestFormActivity;
import com.abdelsattar.alahramapp.Utilitis.Preferences;
import com.abdelsattar.alahramapp.adpaters.ShowRequestAdapter;
import com.abdelsattar.alahramapp.model.Constant;
import com.abdelsattar.alahramapp.model.RequestModel;
import com.abdelsattar.alahramapp.model.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.abdelsattar.alahramapp.Utilitis.ResponseParser.getRequestData;
import static com.abdelsattar.alahramapp.model.Constant.MANAGER_ROLE;

public class ShowAllRequestsByDateActivity extends AppCompatActivity {
    RecyclerView requestrecycleview;
    ShowRequestAdapter adapter;
    List<RequestModel> data;
    private ProgressDialog dialog;
    int type = -1;

    EditText etFrom;
    EditText etTo;

     Calendar myCalendar_from;
     Calendar myCalendar_to;


    static final String REQ_TAG = "VACTIVITY";
    RequestQueue requestQueue;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etFrom.setText(sdf.format(myCalendar_from.getTime()));
    }
    private void updateLabe2() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etTo.setText(sdf.format(myCalendar_to.getTime()));
    }


    Button btnDateSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allrequests_date);
        forceRTLIfSupported();

        myCalendar_from = Calendar.getInstance();
        myCalendar_to = Calendar.getInstance();

         etFrom= (EditText) findViewById(R.id.etFrom);
        etTo= (EditText) findViewById(R.id.etTo);

        btnDateSearch= (Button) findViewById(R.id.btnDateSearch);



        final DatePickerDialog.OnDateSetListener date_from = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar_from.set(Calendar.YEAR, year);
                myCalendar_from.set(Calendar.MONTH, monthOfYear);
                myCalendar_from.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        final DatePickerDialog.OnDateSetListener date_to = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar_to.set(Calendar.YEAR, year);
                myCalendar_to.set(Calendar.MONTH, monthOfYear);
                myCalendar_to.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabe2();
            }

        };



        etFrom.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new DatePickerDialog(ShowAllRequestsByDateActivity.this, date_from, myCalendar_from
                    .get(Calendar.YEAR), myCalendar_from.get(Calendar.MONTH),
                    myCalendar_from.get(Calendar.DAY_OF_MONTH)).show();
        }
    });

        etTo.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            new DatePickerDialog(ShowAllRequestsByDateActivity.this, date_to, myCalendar_to
                    .get(Calendar.YEAR), myCalendar_to.get(Calendar.MONTH),
                    myCalendar_to.get(Calendar.DAY_OF_MONTH)).show();
        }
    });


        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("جميع الطلبات");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        SearchView searchView = (SearchView) findViewById(R.id.search);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setHintTextColor(Color.GRAY);

        searchView.setVisibility(View.GONE);

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
                startActivity(new Intent(ShowAllRequestsByDateActivity.this,RequestFormActivity.class));
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


        btnDateSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etTo.getText() == null || etTo.getText().toString().equals("") || etTo.getText().toString().equals("null") || etTo.getText().toString().equals(" ")
                        || etFrom.getText() == null || etFrom.getText().toString().equals("") || etFrom.getText().toString().equals("null") || etFrom.getText().toString().equals(" "))
                    Toast.makeText(ShowAllRequestsByDateActivity.this,"يجب اختيار تاريخ من وتاريخ الي",Toast.LENGTH_LONG).show();
                else
                {

                    requestQueue = RequestQueueSingleton.getInstance(ShowAllRequestsByDateActivity.this)
                            .getRequestQueue();

                    dialog = new ProgressDialog(ShowAllRequestsByDateActivity.this);
                    dialog.setMessage("جاري التحميل...");
                    dialog.show();



                    String url = Constant.serversite+"/api/AlAhram/GetAllBasedOnCustomTime?From="+etFrom.getText()+"&To="+etTo.getText();


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
        });


    }
}
