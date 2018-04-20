package com.abdelsattar.alahramapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdelsattar.alahramapp.MyNotificationHelper.Config;
import com.abdelsattar.alahramapp.MyNotificationHelper.NotificationUtils;
import com.abdelsattar.alahramapp.Ui.OrderdetailsActivity;
import com.abdelsattar.alahramapp.Utilitis.OnRequestFinish;
import com.abdelsattar.alahramapp.fragments.Fragment1;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

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
                SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                //Register Device with Notification
                String regId = pref.getString("regId", null);


                String REGISTER_URL = "http://mabdelsattar1992-001-site1.gtempurl.com/api/AlAhram/GetRequest_New?requestId="+orderNumberString+"&secretkey="+passwordString
                        +"&token="+regId+"&type="+1;

                dialog.show();

                httpRequest(null, REGISTER_URL, new OnRequestFinish() {
                    @Override
                    public void onSuccess(final String response) {
                        Log.i(MyApplication.TAG, "response: " + response);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if(response == null || response.equals("null"))
                            Toast.makeText(getApplicationContext(),"بيانات غير صحيحه ..الرجاء اعادة المحاولة",Toast.LENGTH_LONG).show();
                        else {





                           // if(regId != null){
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("jsonObj", response);
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                           // }


                        }
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
