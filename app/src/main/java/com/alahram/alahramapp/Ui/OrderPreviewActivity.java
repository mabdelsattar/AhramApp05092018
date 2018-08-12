package com.alahram.alahramapp.Ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alahram.alahramapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class OrderPreviewActivity extends AppCompatActivity {



    LinearLayout bindItemsContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderreivew);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("مراجعه الطلب");


        bindItemsContainer = (LinearLayout) findViewById(R.id.bindItemsContainer);



        String JsonStr = getIntent().getExtras().getString("jsonItems");
        try {
            JSONArray jsonArray = new JSONArray(JsonStr);

            for (int i = 0; i < jsonArray.length(); i++) {
JSONObject tempObj = jsonArray.getJSONObject(i);
                TextView valueTV = new TextView(this);
                valueTV.setText("-"+" ("+tempObj.getInt("counter")+")"+tempObj.getString("ordername")+" ("+tempObj.getInt("trode")+" )");
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
