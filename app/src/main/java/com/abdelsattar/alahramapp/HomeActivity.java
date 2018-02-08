package com.abdelsattar.alahramapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btnEmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    /*    getSupportActionBar().setTitle(R.string.lbl_title);
        String string = "#11113353";
        int color = Integer.parseInt(string.replaceFirst("^#",""), 16);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));*/
    getSupportActionBar().hide();

        btnEmp = (Button)findViewById(R.id.btnEmployee);
        btnEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                //Navigate to Login Screen


            }
        });

    }
}
