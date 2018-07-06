package com.alahram.alahramapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class sendbundledata implements Serializable
{

//    List<sendbundle>mydata;
    String ordername,orderprice;
    int counter;

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(String orderprice) {
        this.orderprice = orderprice;
    }

    public sendbundledata(String ordername, String orderprice) {
        this.ordername = ordername;
        this.orderprice = orderprice;
    }

    public int getCounter() {

        if(counter < 0)
            counter = 0;
        return counter;

    }

    public void setCounter(int counter) {
        if(counter < 0)
            counter =0;
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "sendbundledata{" +
                "ordername='" + ordername + '\'' +
                ", orderprice='" + orderprice + '\'' +
                ", counter=" + counter +
                '}';
    }
}
