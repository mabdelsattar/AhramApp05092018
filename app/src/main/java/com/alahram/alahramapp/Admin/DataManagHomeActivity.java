package com.alahram.alahramapp.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alahram.alahramapp.R;
import com.alahram.alahramapp.Ui.ShowAllRequestsActivity;

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
        manageempoyees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataManagHomeActivity.this,ManageEmpolyeesActivity.class));
            }
        });
        manageClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataManagHomeActivity.this,ManageClientsActivity.class));
            }
        });

        manageRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DataManagHomeActivity.this,ShowAllRequestsActivity.class));
            }
        });


    }
}
