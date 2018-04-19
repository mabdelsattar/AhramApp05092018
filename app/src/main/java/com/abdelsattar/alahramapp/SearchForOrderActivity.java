package com.abdelsattar.alahramapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdelsattar.alahramapp.Ui.OrderdetailsActivity;
import com.abdelsattar.alahramapp.Utilitis.OnRequestFinish;
import com.abdelsattar.alahramapp.fragments.Fragment1;

import static com.abdelsattar.alahramapp.Utilitis.ConnectionManager.httpRequest;

public class SearchForOrderActivity extends AppCompatActivity {


    private ProgressDialog dialog;
    EditText password,orderNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_order);
        password = (EditText) findViewById(R.id.password);
        orderNumber= (EditText)findViewById(R.id.orderNumber);
        Button submit= (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = ProgressDialog.show(SearchForOrderActivity.this, "",
                        "جاري التحميل...", true);
                dialog.dismiss();
                String passwordString = password.getText().toString();
                String orderNumberString = orderNumber.getText().toString();
                String REGISTER_URL = "http://mabdelsattar1992-001-site1.gtempurl.com/api/AlAhram/GetRequest?requestId="+orderNumberString+"&secretkey="+passwordString;

                dialog.show();

                httpRequest(null, REGISTER_URL, new OnRequestFinish() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(MyApplication.TAG, "response: " + response);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("jsonObj",response);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                    @Override
                    public void onFailure(String message) {
                        Log.i(MyApplication.TAG, "message: " + message);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Toast.makeText(SearchForOrderActivity.this,"تعذر الحصول علي نتائج",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
