package com.abdelsattar.alahramapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                Intent intent = new Intent(NotesActivity.this, CreatePdfActivity.class);
                Bundle bundle = new Bundle();
                Notes = tvNotes.getText().toString();

                bundle.putSerializable("dataList", (ArrayList<AddRequestModel>) getIntent().getExtras().getSerializable("dataList"));
                intent.putExtras(bundle);
                intent.putExtra("more",Notes);
                startActivity(intent);
            }
        });


    }
}
