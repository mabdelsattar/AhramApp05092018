package com.abdelsattar.alahramapp.Utilitis;

/**
 * Created by mohamed_3ntar on 3/4/2018.
 */

public interface OnRequestFinish {
    void onSuccess(String response);

    void onFailure(String message);
}
