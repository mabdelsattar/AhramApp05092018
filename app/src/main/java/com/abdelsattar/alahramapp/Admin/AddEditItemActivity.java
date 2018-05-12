package com.abdelsattar.alahramapp.Admin;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdelsattar.alahramapp.CreatePdfActivity;
import com.abdelsattar.alahramapp.Preferences;
import com.abdelsattar.alahramapp.R;
import com.abdelsattar.alahramapp.model.Constant;
import com.abdelsattar.alahramapp.model.RequestQueueSingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class AddEditItemActivity extends AppCompatActivity {

    EditText etprice,etname;
    Button btnsaveitem;
    String Name,Prices;
    RequestQueue requestQueue;
    ProgressDialog dialog;


    String ItemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        btnsaveitem = (Button) findViewById(R.id.btnsaveitem);
        etprice = (EditText) findViewById(R.id.etprice);
        etname = (EditText) findViewById(R.id.etname);

        if(getIntent().getExtras() != null) {
            ItemId = getIntent().getExtras().getString("ItemId", null);
            Name= getIntent().getExtras().getString("Name", null);
            Prices= getIntent().getExtras().getString("Price", null);

            etname.setText(Name);
            etprice.setText(Prices);

        }
        requestQueue = RequestQueueSingleton.getInstance(AddEditItemActivity.this)
                .getRequestQueue();





        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("ادارة الاسعار");

        //check on id to valid on edit or create or both

        btnsaveitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = ProgressDialog.show(AddEditItemActivity.this, "",
                        "جاري التحميل...", true);


                Name = etname.getText().toString();
                Prices = etprice.getText().toString();

                if(Name == null || Name.equals("null") || Name.equals("")
                  || Prices == null || Prices.equals("null") || Prices.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"يجب ملئ كافة الحقول",Toast.LENGTH_LONG).show();
                }else{
                    //Call Web Service That Save or Update

                    try {
                        String url = Constant.serversite + "/api/AlAhram/AddOrUpdateItem";

                        final JSONObject jsonBody = new JSONObject();

                        jsonBody.put("Id",ItemId);
                        jsonBody.put("Name",Name);
                        jsonBody.put("Price",Prices);


                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                        // .setText("String Response : "+ response.toString());
                                        Log.i("respones", "succed");
                                        try {
                                            finish();

                                        }catch (Exception ex){
                                            Toast.makeText(AddEditItemActivity.this,"حدث خطأ تقني",Toast.LENGTH_LONG).show();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                Toast.makeText(AddEditItemActivity.this,"حدث خطأ تقني اثناء الاتصال بالخادم",Toast.LENGTH_LONG).show();
                                // clientname.setText("Error getting response");
                            }
                        });
                        jsonObjectRequest.setTag("Tag Add Request Item");
                        requestQueue.add(jsonObjectRequest);



                    }catch (Exception ex){

                    }
                }


            }
        });





    }
}
