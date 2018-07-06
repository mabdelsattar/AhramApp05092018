package com.alahram.alahramapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alahram.alahramapp.Ui.ShowAllRequestsActivity;
import com.alahram.alahramapp.model.Constant;
import com.alahram.alahramapp.model.RequestQueueSingleton;
import com.alahram.alahramapp.model.Status;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChangeStatusActivity extends AppCompatActivity {

    TextView
            bindRequestNum,
            bindRequestStatus,
            bindRequestDate,
            bindRecieverName,
            bindRecieverAddress,
            bindRecieverCity,
            bindRecieverPhone,
            bindNotes,
            bindMadeBy;
    LinearLayout bindItemsContainer;

    static final String REQ_TAG = "VACTIVITY";
    RequestQueue requestQueue;
    private ProgressDialog dialog;
    ArrayList<Status>statuses;
    ArrayAdapter<String> statusArrayAdapter;
    int RequestId;
    int statusIndex=0;
    int statusId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);
        bindViews();
        requestQueue = RequestQueueSingleton.getInstance(ChangeStatusActivity.this)
                .getRequestQueue();
        loadStatus();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("التفاصيل");

        bindRequestNum = (TextView) findViewById(R.id.bindRequestNum);
        bindRequestDate = (TextView) findViewById(R.id.bindRequestDate);
        bindRecieverName = (TextView) findViewById(R.id.bindRecieverName);
        bindRecieverAddress = (TextView) findViewById(R.id.bindRecieverAddress);
        bindRecieverCity = (TextView) findViewById(R.id.bindRecieverCity);
        bindRecieverPhone = (TextView) findViewById(R.id.bindRecieverPhone);
        bindItemsContainer = (LinearLayout) findViewById(R.id.bindItemsContainer);
        bindNotes = (TextView) findViewById(R.id.bindNotes);
        bindMadeBy = (TextView) findViewById(R.id.bindMadeBy);
        if (getIntent().hasExtra("jsonObj")) {
            String JsonStr = getIntent().getExtras().getString("jsonObj");
            Log.d("TestStatusId",JsonStr);
            try {
                JSONObject jsonObject = new JSONObject(JsonStr);
                RequestId = jsonObject.getInt("RequestId");
                statusId = jsonObject.getInt("RequestStatusId");
                String RequestStatus = jsonObject.getString("RequestStatus");
                String RequestDate = jsonObject.getString("RequestDate");
                String RecieverName = jsonObject.getString("RecieverName");
                String RecieverAddress = jsonObject.getString("RecieverAddress");
                String RecieverCity = jsonObject.getString("RecieverCity");
                String RecieverPhone = jsonObject.getString("RecieverPhone");
                String notes = jsonObject.getString("Notes");
                String paid = String.valueOf(jsonObject.getInt("Paid"));
                String remain = String.valueOf(jsonObject.getString("Remain"));
                String madeby = jsonObject.getString("MadeBy");

                mPaidAmount.setText(paid);
                mToBePaid.setText(remain);

                bindRequestNum.setText(RequestId + "");
                bindRequestDate.setText(RequestDate);
                bindRecieverName.setText(RecieverName);
                bindRecieverAddress.setText(RecieverAddress);
                bindRecieverCity.setText(RecieverCity);
                bindRecieverPhone.setText(RecieverPhone);
                bindMadeBy.setText(madeby);


                if (!(notes == null || notes.equals("null") || notes.equals("")))
                    bindNotes.setText(notes);


                JSONArray Items = jsonObject.getJSONArray("Items");
                for (int i = 0; i < Items.length(); i++) {

                    TextView valueTV = new TextView(this);
                    valueTV.setText("-" + Items.getString(i));
                    valueTV.setTextColor(Color.GRAY);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                        valueTV.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                    } else {
                        valueTV.setTextAppearance(android.R.style.TextAppearance_Medium);
                    }

                    bindItemsContainer.addView(valueTV);
                }


            } catch (Exception ex) {
int x= 1;
            }
        }
    }

    private void loadStatus()
    {

        dialog = ProgressDialog.show(this, "",
                "جاري التحميل...", true);
        dialog.setCancelable(false);
        dialog.show();
        String url ="http://mabdelsattar1992-001-site1.gtempurl.com/api/AlAhram/GetStatusList";

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        statuses = new ArrayList<>();
                        JSONArray jsonItems = null;
                        try {
                            List<String> statusList = new ArrayList<String>();
                            jsonItems = new JSONArray(response);
                            for (int i = 0; i < jsonItems.length(); i++)
                            {
                                JSONObject jsonObject =jsonItems.getJSONObject(i);
                                String id = jsonObject.getString("Id");
                                String name =  jsonObject.getString("Name");
                                Status status = new Status(id,name);
                                if (id.equals(String.valueOf(statusId)))
                                    statusIndex=i;
                                Log.d("States",status.toString());
                                statusList.add(name);
                                statuses.add(status);
                            }

                            statusArrayAdapter = new ArrayAdapter<String>(ChangeStatusActivity.this, android.R.layout.simple_spinner_item, statusList);
                            mSpinnerStatus.setAdapter(statusArrayAdapter);
                            mSpinnerStatus.setSelection(statusIndex);

                        } catch (JSONException e) {
                            e.printStackTrace();
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








    /** Android Views **/
    Button mSave;
    Button mCancel;
    EditText mPaidAmount;
    EditText mToBePaid;
    Spinner mSpinnerStatus;

    /** Android Views **/

    /**
     * Binds XML views
     * Call this function after layout is ready.
     **/
    private void bindViews(){
        mSave = (Button) findViewById(R.id.save);
        mCancel = (Button) findViewById(R.id.cancel);
        mPaidAmount = (EditText) findViewById(R.id.paidAmount);
        mToBePaid = (EditText) findViewById(R.id.toBePaid);
        mSpinnerStatus = (Spinner) findViewById(R.id.spinner_status);

    }

    public void cancel(View view) {
        onBackPressed();
    }

    public void save(View view) {
        String toBePaid = mToBePaid.getText().toString();
        String paidAmount = mPaidAmount.getText().toString();
        String statusId = statuses.get(mSpinnerStatus.getSelectedItemPosition()).getId();
        Log.d("toBePaid",toBePaid);
        Log.d("paidAmount",paidAmount);
        Log.d("statusId",statusId);
        Log.d("RequestId",""+RequestId);
        if (dialog.isShowing())
            dialog.dismiss();
        dialog.show();

        String url = Constant.serversite + "/api/AlAhram/AddOrUpdateRequest";
        final JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("RequestId",RequestId);
            if (paidAmount.length()>0)
                jsonBody.put("Paid", Integer.parseInt(paidAmount));
            else
                jsonBody.put("Paid", 0);
            if (toBePaid.length()>0)
                jsonBody.put("Remaining", Integer.parseInt(toBePaid));
            else
                jsonBody.put("Remaining", 0);
            jsonBody.put("StatusId", Integer.parseInt(statusId));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Log.i("response", "response :" +response.toString());
                        Toast.makeText(ChangeStatusActivity.this, "تم حفظ البيانات", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(ChangeStatusActivity.this, "حدث خطأ تقني اثناء الاتصال بالخادم", Toast.LENGTH_LONG).show();
                // clientname.setText("Error getting response");
                error.printStackTrace();
            }
        });
        jsonObjectRequest.setTag(REQ_TAG);
        requestQueue.add(jsonObjectRequest);
    }
}
