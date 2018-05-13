package com.abdelsattar.alahramapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.abdelsattar.alahramapp.R;
import com.abdelsattar.alahramapp.Ui.ShowAllRequestsActivity;

public class StatisticsHomeActivity extends AppCompatActivity {

    Button RequestsLastWeek,RequestsLastMonth,RequestsLastYear,btnRequestsCustomDate,btnRequestsArea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_statistics_home);

        getSupportActionBar().hide();

        RequestsLastWeek = (Button)findViewById(R.id.RequestsLastWeek);
        RequestsLastMonth =(Button)findViewById(R.id.RequestsLastMonth);
        RequestsLastYear =(Button)findViewById(R.id.RequestsLastYear);
        btnRequestsCustomDate =(Button)findViewById(R.id.btnRequestsCustomDate);
        btnRequestsArea =(Button)findViewById(R.id.btnRequestsArea);


        RequestsLastWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send specefic filter type 1
                startActivity(new Intent(StatisticsHomeActivity.this,ShowAllRequestsActivity.class));
            }
        });
        RequestsLastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send specefic filter type 2
                startActivity(new Intent(StatisticsHomeActivity.this,ShowAllRequestsActivity.class));
            }
        });
        RequestsLastYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send specefic filter type 3
                startActivity(new Intent(StatisticsHomeActivity.this,ShowAllRequestsActivity.class));
            }
        });

        btnRequestsCustomDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send specefic filter from ,to (new Activity)
                startActivity(new Intent(StatisticsHomeActivity.this,ShowAllRequestsActivity.class));
            }
        });
        btnRequestsArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send specefic filter with Area Selection (new activity)
                startActivity(new Intent(StatisticsHomeActivity.this,ShowAllRequestsActivity.class));
            }
        });




    }
}
