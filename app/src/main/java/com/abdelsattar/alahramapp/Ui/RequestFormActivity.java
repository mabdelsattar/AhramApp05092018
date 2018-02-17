package com.abdelsattar.alahramapp.Ui;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.abdelsattar.alahramapp.Utilitis.Preferences;
import com.abdelsattar.alahramapp.R;
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
import java.util.List;

public class RequestFormActivity extends AppCompatActivity {

    EditText clientname,clientphoneksa,clientphoneegy,clientnationalid,recivername,reciverphoneksa,reciverphoneegy,recivernationalid,clientaddressdetail,reciveraddressdetail;
    Preferences mpreferences;
    Spinner spinner_client_address_country,spinner_client_address_city,spinner_Reciever_address_country,spinner_Reciever_address_city  ;

    public  ArrayList<Integer> clientCities;
    public  ArrayList<Integer> recieverCities;

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
        forceRTLIfSupported();
        setContentView(R.layout.
                activity_request_form);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("انشاء طلب جديد");
        requestQueue = RequestQueueSingleton.getInstance(RequestFormActivity.this)
                .getRequestQueue();
        // forceRTLIfSupported();

        mpreferences=new Preferences(this);
        clientname=(EditText)findViewById(R.id.etClientName);
        clientphoneegy=(EditText)findViewById(R.id.etClientphoneEG);
        clientphoneksa=(EditText)findViewById(R.id.etClientphoneKSA);
        clientnationalid=(EditText)findViewById(R.id.etClientNationID);
        clientaddressdetail=(EditText)findViewById(R.id.etClientAddress);
        reciveraddressdetail=(EditText)findViewById(R.id.etRecieverAddress) ;
        recivername=(EditText)findViewById(R.id.etRecieverName);
        recivernationalid=(EditText)findViewById(R.id.etRecieverNationID);
        reciverphoneegy=(EditText)findViewById(R.id.etRecieverphoneEG) ;
        reciverphoneksa=(EditText)findViewById(R.id.etRecieverphoneKSA);

        // Spinner element
        Button next=(Button)findViewById(R.id.nextbtn);
       final Spinner spinner_nationality = (Spinner) findViewById(R.id.spinner_client_nationality);
         spinner_client_address_country= (Spinner) findViewById(R.id.spinner_client_address_country);
        spinner_client_address_city = (Spinner) findViewById(R.id.spinner_client_address_city);

         spinner_Reciever_address_country= (Spinner) findViewById(R.id.spinner_Reciever_address_country);
         spinner_Reciever_address_city = (Spinner) findViewById(R.id.spinner_Reciever_address_city);
        // Spinner click listener
     //--TODO   spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements

        dialog = new ProgressDialog(RequestFormActivity.this);
        dialog.setMessage("جاري التحميل...");
        dialog.show();


        //Region Load Nationality
        String url = Constant.serversite+"/api/AlAhram/GetCountries";
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        // .setText("String Response : "+ response.toString());
                        Log.i("respones","succed");

                        try {
                            JSONArray jsonArrayCountry = new JSONArray(response);

                            List<String> categories_nationality = new ArrayList<String>();
                            categories_nationality.add("الجنسية");
                            for (int i = 0; i < jsonArrayCountry.length(); i++)
                            {
                                JSONObject jsonObject =jsonArrayCountry.getJSONObject(i);
                                categories_nationality.add(jsonObject.getString("Name"));
                            }

                            ArrayAdapter<String> dataAdapter_nationality = new ArrayAdapter<String>(RequestFormActivity.this, android.R.layout.simple_spinner_item, categories_nationality);
                            dataAdapter_nationality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_nationality.setAdapter(dataAdapter_nationality);

                            //Client and Receiver Country Setting

                            List<String> categories_addressCountry = new ArrayList<String>();
                            categories_addressCountry.add("الدولة");
                            for (int i = 0; i < jsonArrayCountry.length(); i++)
                            {
                                JSONObject jsonObject =jsonArrayCountry.getJSONObject(i);
                                categories_addressCountry.add(jsonObject.getString("Name"));
                            }
                            ArrayAdapter<String> dataAdapter_client_Address_country = new ArrayAdapter<String>(RequestFormActivity.this, android.R.layout.simple_spinner_item, categories_addressCountry);
                            dataAdapter_client_Address_country.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_client_address_country.setAdapter(dataAdapter_client_Address_country);
                            spinner_Reciever_address_country.setAdapter(dataAdapter_client_Address_country);

                            //categories_addressCountry.add("السعودية");

                            spinner_client_address_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                    //Loading Cities
                                    if(position != 0) {
                                        dialog.show();
                                        String url = Constant.serversite + "/api/AlAhram/GetCitiesByCountryId?countryId=" + position;
                                        StringRequest jsonObjectRequest_city = new StringRequest(Request.Method.GET, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (dialog.isShowing()) {
                                                            dialog.dismiss();
                                                        }
                                                        try{

                                                            clientCities= new ArrayList<Integer>();
                                                            clientCities.add(-1);
                                                           ArrayList<String> clientCitiesStr= new ArrayList<String>();
                                                            clientCitiesStr.add("المدينة");
                                                            JSONArray  jsonArray=new JSONArray(response);
                                                            for(int i= 0 ;i<jsonArray.length(); i++)
                                                            {
                                                                JSONObject  jsonObject=jsonArray.getJSONObject(i);
                                                                clientCities.add(jsonObject.getInt("Id"));
                                                                clientCitiesStr.add(jsonObject.getString("Name"));
                                                            }
                                                            ArrayAdapter<String> dataAdapter_client_Address_city = new ArrayAdapter<String>(RequestFormActivity.this, android.R.layout.simple_spinner_item, clientCitiesStr);
                                                            dataAdapter_client_Address_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            spinner_client_address_city.setAdapter(dataAdapter_client_Address_city);



                                                        }

                                                        catch (Exception ex)
                                                        {

                                                        }
                                                        // .setText("String Response : "+ response.toString());
                                                        Log.i("respones", "succed");


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
                                        jsonObjectRequest_city.setTag(REQ_TAG);
                                        requestQueue.add(jsonObjectRequest_city);

                                    }


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parentView) {
                                    // your code here
                                }

                            });

                            spinner_Reciever_address_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                    // your code here

                                    //Loading Cities
                                    if(position != 0) {
                                        dialog.show();
                                        String url = Constant.serversite + "/api/AlAhram/GetCitiesByCountryId?countryId=" + position;
                                        StringRequest jsonObjectRequest_city = new StringRequest(Request.Method.GET, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        if (dialog.isShowing()) {
                                                            dialog.dismiss();
                                                        }
                                                        try{
                                                            recieverCities= new ArrayList<Integer>();
                                                            recieverCities.add(-1);
                                                            ArrayList<String> recieverCitiesStr= new ArrayList<String>();
                                                            recieverCitiesStr.add("المدينة");
                                                            JSONArray  jsonArray=new JSONArray(response);
                                                            for(int i= 0 ;i<jsonArray.length(); i++)
                                                            {
                                                                JSONObject  jsonObject=jsonArray.getJSONObject(i);
                                                                recieverCities.add(jsonObject.getInt("Id"));
                                                                recieverCitiesStr.add(jsonObject.getString("Name"));
                                                            }

                                                            ArrayAdapter<String> dataAdapter_client_Address_city_client = new ArrayAdapter<String>(RequestFormActivity.this, android.R.layout.simple_spinner_item, recieverCitiesStr);
                                                            dataAdapter_client_Address_city_client.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                            spinner_Reciever_address_city.setAdapter(dataAdapter_client_Address_city_client);

                                                        }

                                                        catch (Exception ex)
                                                        {
                                                        }
                                                        // .setText("String Response : "+ response.toString());
                                                        Log.i("respones", "succed");

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
                                        jsonObjectRequest_city.setTag(REQ_TAG);
                                        requestQueue.add(jsonObjectRequest_city);

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parentView) {
                                    // your code here
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




        ArrayList<String> clientCitiesStr= new ArrayList<String>();
        clientCitiesStr.add("المدينة");

        ArrayAdapter<String> dataAdapter_client_Address_city = new ArrayAdapter<String>(RequestFormActivity.this, android.R.layout.simple_spinner_item, clientCitiesStr);
        dataAdapter_client_Address_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_client_address_city.setAdapter(dataAdapter_client_Address_city);



        ArrayList<String> recieverCitiesStr= new ArrayList<String>();
        recieverCitiesStr.add("المدينة");

        ArrayAdapter<String> dataAdapter_client_Address_city_client = new ArrayAdapter<String>(RequestFormActivity.this, android.R.layout.simple_spinner_item, recieverCitiesStr);
        dataAdapter_client_Address_city_client.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Reciever_address_city.setAdapter(dataAdapter_client_Address_city_client);


        next.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

             if("".equals(clientname.getText().toString()))
                 Toast.makeText(RequestFormActivity.this,"يجب ادخال اسم العميل",Toast.LENGTH_LONG).show();
             else  if("".equals(clientphoneksa.getText().toString()))
                 Toast.makeText(RequestFormActivity.this,"يجب ادخال جوال العميل السعودي"  ,Toast.LENGTH_LONG).show();
             else  if("".equals(clientnationalid.getText().toString()))
                 Toast.makeText(RequestFormActivity.this,"يجب ادخال رقم الهوية للعميل"  ,Toast.LENGTH_LONG).show();
             else  if("".equals(recivername.getText().toString()))
                 Toast.makeText(RequestFormActivity.this,"يجب ادخال اسم المستلم"  ,Toast.LENGTH_LONG).show();
             else  if("".equals(reciverphoneegy.getText().toString()))
                 Toast.makeText(RequestFormActivity.this,"يجب ادخال جوال المستلم المصري"  ,Toast.LENGTH_LONG).show();
             else  if("".equals(recivernationalid.getText().toString()))
                 Toast.makeText(RequestFormActivity.this,"يجب ادخال رقم هوية المستلم"  ,Toast.LENGTH_LONG).show();
             else if(spinner_nationality.getSelectedItemPosition() == 0)
                 Toast.makeText(RequestFormActivity.this,"يجب اختيار جنسية العميل"  ,Toast.LENGTH_LONG).show();
             else if(spinner_client_address_country.getSelectedItemPosition() == 0)
                 Toast.makeText(RequestFormActivity.this,"يجب اختيار دولة العميل"  ,Toast.LENGTH_LONG).show();
             else if(spinner_client_address_city.getSelectedItemPosition() == 0)
                 Toast.makeText(RequestFormActivity.this,"يجب اختيار مدينة العميل"  ,Toast.LENGTH_LONG).show();
             else  if("".equals(clientaddressdetail.getText().toString()))
                 Toast.makeText(RequestFormActivity.this,"يجب ادخال العنوان التفصيلي للعميل"  ,Toast.LENGTH_LONG).show();

             else if(spinner_Reciever_address_country.getSelectedItemPosition() == 0)
                 Toast.makeText(RequestFormActivity.this,"يجب اختيار دولة المستلم"  ,Toast.LENGTH_LONG).show();
             else if(spinner_Reciever_address_city.getSelectedItemPosition() == 0)
                 Toast.makeText(RequestFormActivity.this,"يجب اختيار مدينة المستلم"  ,Toast.LENGTH_LONG).show();
             else  if("".equals(reciveraddressdetail.getText().toString()))
                 Toast.makeText(RequestFormActivity.this,"يجب ادخال العنوان التفصيلي للمستلم"  ,Toast.LENGTH_LONG).show();

        else {
                 mpreferences.setclientnae(clientname.getText().toString());
                 mpreferences.setClientphoneEgy(clientphoneegy.getText().toString());
                 mpreferences.setClientphoneKsa(clientphoneksa.getText().toString());
                 mpreferences.setClientNationalId(clientnationalid.getText().toString());
                 mpreferences.setClientAddressDetail1(spinner_client_address_country.getSelectedItem().toString());
                 mpreferences.setClientCountry(spinner_client_address_country.getSelectedItemPosition());
                 mpreferences.setClientAddressDetail2(spinner_client_address_city.getSelectedItem().toString());
                 mpreferences.setClientCity(clientCities.get(spinner_client_address_city.getSelectedItemPosition()));
                 mpreferences.setClientAddressDetail(clientaddressdetail.getText().toString());
                 mpreferences.setReciverName(recivername.getText().toString());
                 mpreferences.setClientphoneEgy(reciverphoneegy.getText().toString());
                 mpreferences.setReciverphoneEgy(reciverphoneksa.getText().toString());
                 mpreferences.setReciverNationalId(recivernationalid.getText().toString());
                 mpreferences.setReciverAddressDetail1(spinner_Reciever_address_city.getSelectedItem().toString());
                 mpreferences.setRecieverCity(recieverCities.get(spinner_Reciever_address_city.getSelectedItemPosition()));
                 mpreferences.setReciverAddressDetail2(spinner_Reciever_address_country.getSelectedItem().toString());
                 mpreferences.setRecieverCountry(spinner_Reciever_address_country.getSelectedItemPosition());
                 mpreferences.setClientNationality(spinner_nationality.getSelectedItemPosition());
                   mpreferences.setReciverAddressDetail(reciveraddressdetail.getText().toString());
                 Intent i = new Intent(RequestFormActivity.this, AddRequestsActivity.class);
                   startActivity(i);

                }
              }
          });
    }
}
