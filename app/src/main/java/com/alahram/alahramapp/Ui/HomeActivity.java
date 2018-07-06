package com.alahram.alahramapp.Ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alahram.alahramapp.R;
import com.alahram.alahramapp.VidioActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnEmp,btnVisitor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        btnEmp = (Button)findViewById(R.id.btnEmployee);
        btnEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                //Navigate to Login Screen


            }
        });

        btnVisitor = (Button)findViewById(R.id.btnVisitor);
        btnVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,VidioActivity.class));

            }
        });




    }
}
