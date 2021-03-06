package com.alahram.alahramapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alahram.alahramapp.Ui.AddRequestsActivity;
import com.alahram.alahramapp.model.AddRequestModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {


    Button btnNext;
    EditText tvNotes;
    String Notes = "";
    com.alahram.alahramapp.Utilitis.Preferences pref;
//    Serializable  data;
    String JsonStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


         JsonStr = getIntent().getExtras().getString("jsonItems");

        pref = new com.alahram.alahramapp.Utilitis.Preferences(NotesActivity.this);



        tvNotes = (EditText) findViewById(R.id.orderDetails);


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
                if (fn_permission())
                    openActivity();
            }
        });


    }

    private void openActivity()
    {
        Intent intent = new Intent(NotesActivity.this, CreatePdfActivity.class);
        //Bundle bundle = new Bundle();
        Notes = tvNotes.getText().toString();
        pref.setNotes(Notes);

       // MyApplication.getApplicationInstance().setNotes(Notes);

       // bundle.putSerializable("dataList",data);
        //intent.putExtras(bundle);
        JsonStr = getIntent().getExtras().getString("jsonItems");
        intent.putExtra("jsonItems",JsonStr);
        startActivity(intent);
    }
    public static final int REQUEST_PERMISSIONS = 1;
    private boolean fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(NotesActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(NotesActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(NotesActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(NotesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
            return false;
        }
        else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openActivity();
            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
