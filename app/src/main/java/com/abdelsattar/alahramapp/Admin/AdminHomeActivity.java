package com.abdelsattar.alahramapp.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.abdelsattar.alahramapp.R;

public class AdminHomeActivity extends AppCompatActivity {

    RelativeLayout layoutstatistics,layoutmanage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        getSupportActionBar().hide();

        layoutstatistics = (RelativeLayout)findViewById(R.id.layoutstatistics);
        layoutmanage= (RelativeLayout)findViewById(R.id.layoutmanage);

        layoutmanage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AdminHomeActivity.this,DataManagHomeActivity.class));
            }
        });

    }
}
