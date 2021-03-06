package com.alahram.alahramapp.Ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alahram.alahramapp.R;
import com.itextpdf.text.ImgTemplate;
import com.itextpdf.text.List;
import com.itextpdf.text.pdf.AcroFields;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderdetailsActivity extends AppCompatActivity {


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("التفاصيل");

        bindRequestNum = (TextView) findViewById(R.id.bindRequestNum);
        bindRequestStatus = (TextView) findViewById(R.id.bindRequestStatus);
        bindRequestDate = (TextView) findViewById(R.id.bindRequestDate);
        bindRecieverName = (TextView) findViewById(R.id.bindRecieverName);
        bindRecieverAddress = (TextView) findViewById(R.id.bindRecieverAddress);
        bindRecieverCity = (TextView) findViewById(R.id.bindRecieverCity);
        bindRecieverPhone = (TextView) findViewById(R.id.bindRecieverPhone);
        bindItemsContainer = (LinearLayout) findViewById(R.id.bindItemsContainer);
        bindNotes = (TextView) findViewById(R.id.bindNotes);
        bindMadeBy = (TextView) findViewById(R.id.bindMadeBy);



        String JsonStr = getIntent().getExtras().getString("jsonObj");
        try {
            JSONObject jsonObject = new JSONObject(JsonStr);
            int RequestId = jsonObject.getInt("RequestId");
            String RequestStatus = jsonObject.getString("RequestStatus");
            String RequestDate = jsonObject.getString("RequestDate");
            String RecieverName = jsonObject.getString("RecieverName");
            String RecieverAddress = jsonObject.getString("RecieverAddress");
            String RecieverCity = jsonObject.getString("RecieverCity");
            String RecieverPhone = jsonObject.getString("RecieverPhone");
            String notes = jsonObject.getString("Notes");
            String madeby = jsonObject.getString("MadeBy");

                    bindRequestNum.setText(RequestId+"");
                    bindRequestStatus.setText(RequestStatus);
                    bindRequestDate.setText(RequestDate);
                    bindRecieverName.setText(RecieverName);
                    bindRecieverAddress.setText(RecieverAddress);
                    bindRecieverCity.setText(RecieverCity);
                    bindRecieverPhone.setText(RecieverPhone);
                    bindMadeBy.setText(madeby);

            if(!(notes == null || notes.equals("null") || notes.equals("")))
                    bindNotes.setText(notes);


            JSONArray Items = jsonObject.getJSONArray("Items");
            for (int i = 0; i < Items.length(); i++) {

                TextView valueTV = new TextView(this);
                valueTV.setText("-"+Items.getString(i));
                valueTV.setTextColor(Color.GRAY);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    valueTV.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                } else {
                    valueTV.setTextAppearance(android.R.style.TextAppearance_Medium);
                }

                bindItemsContainer.addView(valueTV);
            }



        } catch (Exception ex) {

        }
    }
}
