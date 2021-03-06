package com.alahram.alahramapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class AddRequestModel implements Serializable
{

//    List<AddRequestAdpater>mydata;
    String ordername,orderprice;
    int counter;
    int Id;
    int trode;


    public int getTrode() {
        if (trode<=0)
            trode =1;
        return trode;
    }

    public void setTrode(int trode) {
        this.trode = trode;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

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

    public AddRequestModel(int id,String ordername, String orderprice,int trood) {
        this.ordername = ordername;
        this.orderprice = orderprice;
        this.Id = id;
        this.trode  =trood;
    }

    public AddRequestModel(int id,String ordername, String orderprice,int count, int trood) {
        this.ordername = ordername;
        this.orderprice = orderprice;
        this.Id = id;
        this.counter = count;
        this.trode  =trood;
    }

    public int getCounter() {
        if(counter <= 0)
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
        return "AddRequestModel{" +
                "ordername='" + ordername + '\'' +
                ", orderprice='" + orderprice + '\'' +
                ", counter=" + counter +
                '}';
    }
}
