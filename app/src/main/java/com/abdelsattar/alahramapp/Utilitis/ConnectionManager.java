package com.abdelsattar.alahramapp.Utilitis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.abdelsattar.alahramapp.MyApplication;
import com.abdelsattar.alahramapp.R;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by mohamed_3ntar on 2/19/2018.
 */

public class ConnectionManager {


    public static void httpRequest( final JSONObject parameter, String url , final OnRequestFinish listener) {

        if (checkNetworkConnection()) {

            Log.i(MyApplication.TAG, "httpPostRequest URL: " + url);
            if (parameter!=null)
                Log.i(MyApplication.TAG, "httpPostRequest parameter: " + parameter.toString());

            JsonObjectRequest req = new JsonObjectRequest(url, parameter,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.i(MyApplication.TAG, "httpRequest Response: " + response);
                                listener.onSuccess( response.toString(4));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onFailure("Connection Error");
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ","Error");

                    Log.d( MyApplication.TAG, "Error");
                    listener.onFailure("Error");
                }
            });
            // add the request object to the queue to be executed
            MyApplication.getApplicationInstance().addToRequestQueue(req);
        }else
            listener.onFailure("Connection Error");

    }

    public static boolean checkNetworkConnection() {
        return true;
        /*if (isOnline()) {
            return true;
        }
        Toast.makeText(MyApplication.getApplicationInstance().getApplicationContext(), R.string.there_is_no_internet_connection,Toast.LENGTH_LONG).show();
        return false;*/
    }


    public static boolean isOnline() {
        Context context = MyApplication.getApplicationInstance().getApplicationContext();
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;
    }
}
