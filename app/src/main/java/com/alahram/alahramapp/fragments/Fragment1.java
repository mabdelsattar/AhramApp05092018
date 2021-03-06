package com.alahram.alahramapp.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alahram.alahramapp.MyApplication;
import com.alahram.alahramapp.MyNotificationHelper.Config;
import com.alahram.alahramapp.R;
import com.alahram.alahramapp.SearchForOrderActivity;
import com.alahram.alahramapp.Ui.AttachActivity;
import com.alahram.alahramapp.Ui.OrderdetailsActivity;
import com.alahram.alahramapp.Utilitis.OnRequestFinish;
import com.alahram.alahramapp.model.RequestModel;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.alahram.alahramapp.Utilitis.ConnectionManager.httpRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {


    public Fragment1() {
        // Required empty public constructor
    }


    RelativeLayout search1;
    LinearLayout search2;
    ScrollView scrollView;

    TextView
            bindRequestNum,
            bindRequestStatus,
            bindRequestDate,
            bindRecieverName,
            bindRecieverAddress,
            bindRecieverCity,
            bindRecieverPhone,
            bindNotes,
            bindMadeBy;
    LinearLayout bindItemsContainer;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_fragment1, container, false);
        search1=fragmentView.findViewById(R.id.search1);
        search2=fragmentView.findViewById(R.id.search2);
        scrollView=fragmentView.findViewById(R.id.scrollView);




        bindRequestNum = fragmentView.findViewById(R.id.bindRequestNum);
        bindRequestStatus = fragmentView.findViewById(R.id.bindRequestStatus);
        bindRequestDate = fragmentView.findViewById(R.id.bindRequestDate);
        bindRecieverName = fragmentView.findViewById(R.id.bindRecieverName);
        bindRecieverAddress = fragmentView.findViewById(R.id.bindRecieverAddress);
        bindRecieverCity = fragmentView.findViewById(R.id.bindRecieverCity);
        bindRecieverPhone = fragmentView.findViewById(R.id.bindRecieverPhone);
        bindItemsContainer = fragmentView.findViewById(R.id.bindItemsContainer);
        bindNotes = fragmentView.findViewById(R.id.bindNotes);
        bindMadeBy = fragmentView.findViewById(R.id.bindMadeBy);

        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });
        search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });
        return fragmentView;
    }
    void openSearchActivity()
    {
        Intent i = new Intent(Fragment1.this.getActivity(), SearchForOrderActivity.class);
        startActivityForResult(i, 1);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("jsonObj");
                Log.d("Result",result);
                search2.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);


                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int RequestId = jsonObject.getInt("RequestId");
                    String RequestStatus = jsonObject.getString("RequestStatus");
                    String RequestDate = jsonObject.getString("RequestDate");
                    String RecieverName = jsonObject.getString("RecieverName");
                    String RecieverAddress = jsonObject.getString("RecieverAddress");
                    String RecieverCity = jsonObject.getString("RecieverCity");
                    String RecieverPhone = jsonObject.getString("RecieverPhone");
                    String notes = jsonObject.getString("Notes");
                    String MadeBy = jsonObject.getString("MadeBy");

                    bindRequestNum.setText(RequestId+"");
                    bindRequestStatus.setText(RequestStatus);
                    bindRequestDate.setText(RequestDate);
                    bindRecieverName.setText(RecieverName);
                    bindRecieverAddress.setText(RecieverAddress);
                    bindRecieverCity.setText(RecieverCity);
                    bindRecieverPhone.setText(RecieverPhone);
                    bindMadeBy.setText(MadeBy);
                    if(!(notes == null || notes.equals("null") || notes.equals("")))
                        bindNotes.setText(notes);


                    JSONArray Items = jsonObject.getJSONArray("Items");
                    for (int i = 0; i < Items.length(); i++) {

                        TextView valueTV = new TextView(this.getActivity());
                        valueTV.setText("-"+Items.getString(i));
                        valueTV.setTextColor(Color.GRAY);
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                            valueTV.setTextAppearance(this.getActivity(), android.R.style.TextAppearance_Medium);
                        } else {
                            valueTV.setTextAppearance(android.R.style.TextAppearance_Medium);
                        }

                        bindItemsContainer.addView(valueTV);
                    }
                } catch (Exception ex) {

                }
            }
        }
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);


        Log.e("nothing", "Firebase reg id: " + regId);
        //TODO Save It to API

        /*if (!TextUtils.isEmpty(regId))
            txtRegId.setText("Firebase Reg Id: " + regId);
        else
            txtRegId.setText("Firebase Reg Id is not received yet!");*/
    }
}
