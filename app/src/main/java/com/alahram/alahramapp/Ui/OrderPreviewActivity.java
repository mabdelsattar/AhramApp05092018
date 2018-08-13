package com.alahram.alahramapp.Ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alahram.alahramapp.CreatePdfActivity;
import com.alahram.alahramapp.NotesActivity;
import com.alahram.alahramapp.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class OrderPreviewActivity extends AppCompatActivity {



    LinearLayout bindItemsContainer;
    TextView tv_deails;
Button btnNext;
   int  totalPrice=0;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderreivew);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#113353"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle("مراجعه الطلب");

TextView tv_deails= (TextView)findViewById(R.id.tv_deails);


        bindItemsContainer = (LinearLayout) findViewById(R.id.bindItemsContainer);




        String JsonStr = getIntent().getExtras().getString("jsonItems");

        try {
             jsonArray = new JSONArray(JsonStr);

            for (int i = 0; i < jsonArray.length(); i++) {
JSONObject tempObj = jsonArray.getJSONObject(i);
                TextView valueTV = new TextView(this);
                valueTV.setText("-"+" ("+tempObj.getInt("counter")+")"+tempObj.getString("ordername")+" ("+tempObj.getInt("trode")+" قطعه)");
                valueTV.setTextColor(Color.GRAY);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    valueTV.setTextAppearance(this, android.R.style.TextAppearance_Medium);
                } else {
                    valueTV.setTextAppearance(android.R.style.TextAppearance_Medium);
                }

                totalPrice+=tempObj.getInt("counter")*Integer.valueOf(tempObj.getString("orderprice"));


                bindItemsContainer.addView(valueTV);
            }
            tv_deails.setText("الاجمالي: "+totalPrice+" ريال سعودي");


        } catch (Exception ex) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setActivated(true);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnNext.setActivated(false);
                Intent intent = new Intent(OrderPreviewActivity.this, NotesActivity.class);
                //Bundle bundle = new Bundle();
                // MyApplication.getApplicationInstance().setNotes(Notes);

              //  bundle.putSerializable("dataList",data);
               // intent.putExtras(bundle);
                intent.putExtra("jsonItems",jsonArray.toString());
                startActivity(intent);

            }
        });
    }
}
