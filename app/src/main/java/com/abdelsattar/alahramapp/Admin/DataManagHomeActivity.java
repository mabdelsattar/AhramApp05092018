package com.abdelsattar.alahramapp.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.abdelsattar.alahramapp.R;

public class DataManagHomeActivity extends AppCompatActivity {

    Button manageprieces,manageempoyees,manageClients,manageRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manag_home);

        getSupportActionBar().hide();

        manageprieces = (Button)findViewById(R.id.manageprieces);
        manageempoyees =(Button)findViewById(R.id.manageempoyees);
        manageClients =(Button)findViewById(R.id.manageClients);
        manageRequests =(Button)findViewById(R.id.manageRequests);


        manageprieces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataManagHomeActivity.this,ManageItemsActivity.class));
            }
        });

    }
}
