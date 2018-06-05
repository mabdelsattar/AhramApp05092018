package com.abdelsattar.alahramapp.Utilitis;

import android.util.Log;

import com.abdelsattar.alahramapp.model.RequestModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mohamed Antar on 17-Feb-18.
 */

public class ResponseParser {
    public static ArrayList<RequestModel> getRequestData(String response) {
        ArrayList<RequestModel> data = new ArrayList<RequestModel>();
        try {
            JSONArray jsonItems = new JSONArray(response);
            Log.d("RequestData",response);
            for (int i = 0; i < jsonItems.length(); i++) {
                JSONObject jsonObject = jsonItems.getJSONObject(i);
                int RequestId = jsonObject.getInt("RequestId");
                String ClientName = jsonObject.getString("ClientName");
                String ClientPhone = jsonObject.getString("ClientPhone");
                String RequestDate = jsonObject.getString("RequestDate");
                String Notes = jsonObject.getString("Notes");
                String MadeBy = jsonObject.getString("MadeBy");
                String paid = String.valueOf(jsonObject.getInt("Paid"));
                String remain = String.valueOf(jsonObject.getInt("Remain"));
                data.add(new RequestModel(RequestId + "", ClientName, ClientPhone, RequestDate, jsonObject.toString(),Notes,paid,remain,MadeBy));
            }
        } catch (Exception ex) {

        }
        return data;
    }
}
