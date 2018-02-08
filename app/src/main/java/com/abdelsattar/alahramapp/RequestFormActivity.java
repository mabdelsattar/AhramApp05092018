package com.abdelsattar.alahramapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RequestFormActivity extends AppCompatActivity {
EditText clientname,clientphoneksa,clientphoneegy,clientnationalid,recivername,reciverphoneksa,reciverphoneegy,recivernationalid,clientaddressdetail,reciveraddressdetail;
    Preferences mpreferences;
    Spinner spinner_client_address_country,spinner_client_address_city,spinner_Reciever_address_country,spinner_Reciever_address_city  ;

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


        mpreferences=new Preferences(this);
        clientname=(EditText)findViewById(R.id.etClientName);
        clientphoneegy=(EditText)findViewById(R.id.etClientphoneEG);
        clientphoneksa=(EditText)findViewById(R.id.etClientphoneKSA);
        clientnationalid=(EditText)findViewById(R.id.etClientNationID);
        clientaddressdetail=(EditText)findViewById(R.id.etClientAddress);
        reciveraddressdetail=(EditText)findViewById(R.id.etRecieverAddress) ;
        recivername=(EditText)findViewById(R.id.etRecieverName);
        recivernationalid=(EditText)findViewById(R.id.etRecieverNationID);
        reciverphoneegy=(EditText)findViewById(R.id.etClientphoneEG) ;
        reciverphoneksa=(EditText)findViewById(R.id.etRecieverphoneKSA);

        // Spinner element
        Button next=(Button)findViewById(R.id.nextbtn);
        Spinner spinner_nationality = (Spinner) findViewById(R.id.spinner_client_nationality);
         spinner_client_address_country= (Spinner) findViewById(R.id.spinner_client_address_country);
        spinner_client_address_city = (Spinner) findViewById(R.id.spinner_client_address_city);

         spinner_Reciever_address_country= (Spinner) findViewById(R.id.spinner_Reciever_address_country);
         spinner_Reciever_address_city = (Spinner) findViewById(R.id.spinner_Reciever_address_city);
        // Spinner click listener
     //--TODO   spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories_nationality = new ArrayList<String>();
        categories_nationality.add("جنسية العميل");
        categories_nationality.add("مصري");
        categories_nationality.add("سعودي");


        List<String> categories_addressCountry = new ArrayList<String>();
        categories_addressCountry.add("الدولة");
        categories_addressCountry.add("مصر");
        categories_addressCountry.add("السعودية");

        List<String> categories_addressCity = new ArrayList<String>();
        categories_addressCity.add("المدينة");
        categories_addressCity.add("مكة");
        categories_addressCity.add("الرياض");
        categories_addressCity.add("جدة");
        categories_addressCity.add("الطائف");

        List<String> categories_addressCity_client = new ArrayList<String>();
        categories_addressCity_client.add("المدينة");
        categories_addressCity_client.add("القاهرة");
        categories_addressCity_client.add("الاسكندرية");
        categories_addressCity_client.add("الشرقية");
        categories_addressCity_client.add("سوهاج");
        categories_addressCity_client.add("المنيا");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter_nationality = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_nationality);
        ArrayAdapter<String> dataAdapter_client_Address_country = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_addressCountry);
        ArrayAdapter<String> dataAdapter_client_Address_city = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_addressCity);
        ArrayAdapter<String> dataAdapter_client_Address_city_client = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories_addressCity_client);


        // Drop down layout style - list view with radio button
        dataAdapter_nationality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter_client_Address_country.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter_client_Address_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter_client_Address_city_client.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_nationality.setAdapter(dataAdapter_nationality);
        spinner_client_address_country.setAdapter(dataAdapter_client_Address_country);
        spinner_client_address_city.setAdapter(dataAdapter_client_Address_city);


        /*spinner_nationality.setAdapter(dataAdapter_nationality);
        spinner_client_address_country.setAdapter(dataAdapter_client_Address_country);
        spinner_client_address_city.setAdapter(dataAdapter_client_Address_city);*/

        spinner_Reciever_address_country.setAdapter(dataAdapter_client_Address_country);
        spinner_Reciever_address_city.setAdapter(dataAdapter_client_Address_city_client);

          next.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  mpreferences.setclientnae(clientname.getText().toString());
                  mpreferences.setClientphoneEgy(clientphoneegy.getText().toString());
                  mpreferences.setClientphoneKsa(clientphoneksa.getText().toString());
                  mpreferences.setClientNationalId(clientnationalid.getText().toString());
                  mpreferences.setClientAddressDetail1(spinner_client_address_country.getSelectedItem().toString());
                  mpreferences.setClientAddressDetail2(spinner_client_address_city.getSelectedItem().toString());
                  mpreferences.setClientAddressDetail(clientaddressdetail.getText().toString());
                 // mpreferences.setReciverName(recivername.getText().toString());
                  mpreferences.setClientphoneEgy(reciverphoneegy.getText().toString());
                  mpreferences.setReciverphoneEgy(reciverphoneksa.getText().toString());
                  mpreferences.setReciverNationalId(recivernationalid.getText().toString());
                  mpreferences.setReciverAddressDetail1(spinner_Reciever_address_city.getSelectedItem().toString());
                  mpreferences.setReciverAddressDetail2(spinner_Reciever_address_country.getSelectedItem().toString());
                  mpreferences.setReciverAddressDetail(reciveraddressdetail.getText().toString());

                  Intent i=new Intent(RequestFormActivity.this,RequestsActivity.class);
                  startActivity(i);
              }
          });
    }
}
