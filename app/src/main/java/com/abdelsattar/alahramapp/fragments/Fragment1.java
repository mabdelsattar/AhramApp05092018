package com.abdelsattar.alahramapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.abdelsattar.alahramapp.MyApplication;
import com.abdelsattar.alahramapp.R;
import com.abdelsattar.alahramapp.Ui.OrderdetailsActivity;
import com.abdelsattar.alahramapp.Utilitis.OnRequestFinish;
import com.abdelsattar.alahramapp.model.RequestModel;

import static com.abdelsattar.alahramapp.Utilitis.ConnectionManager.httpRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {


    public Fragment1() {
        // Required empty public constructor
    }

    EditText password,orderNumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_fragment1, container, false);
        password = fragmentView.findViewById(R.id.password);
        orderNumber= fragmentView.findViewById(R.id.orderNumber);
        Button submit= fragmentView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passwordString = password.getText().toString();
                String orderNumberString = orderNumber.getText().toString();
                String REGISTER_URL = "http://mabdelsattar1992-001-site1.gtempurl.com/api/AlAhram/GetRequest?REGISTER_URL="+orderNumberString+"&secretkey="+passwordString;

                httpRequest(null, REGISTER_URL, new OnRequestFinish() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i(MyApplication.TAG, "response: " + response);

                    }
                    @Override
                    public void onFailure(String message) {
                        Log.i(MyApplication.TAG, "message: " + message);

                    }
                });

                RequestModel requestModel = new RequestModel(orderNumberString,"Mohamed Antar","01112688365","22-11-2018","object ","Notes Notes");

                Intent intent = new Intent(Fragment1.this.getActivity(),OrderdetailsActivity.class);
                intent.putExtra("jsonObj",requestModel.getStrObject());
                Fragment1.this.getActivity().startActivity(intent);
            }
        });
        return fragmentView;
    }

}
