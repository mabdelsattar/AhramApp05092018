package com.abdelsattar.alahramapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelsattar.alahramapp.Ui.AddRequestsActivity;
import com.abdelsattar.alahramapp.model.AddRequestModel;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {


    Button btnNext;
    EditText tvNotes;
    String Notes = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        tvNotes = (EditText) findViewById(R.id.orderDetails);

        btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fn_permission())
                    openActivity();
            }
        });


    }

    private void openActivity()
    {
        Intent intent = new Intent(NotesActivity.this, CreatePdfActivity.class);
        Bundle bundle = new Bundle();
        Notes = tvNotes.getText().toString();

        bundle.putSerializable("dataList", (ArrayList<AddRequestModel>) getIntent().getExtras().getSerializable("dataList"));
        intent.putExtras(bundle);
        intent.putExtra("more",Notes);
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
