package com.abdelsattar.alahramapp;

import java.util.List;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class sendbundledata
{
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

    public List<sendbundle> getMydata() {
        return mydata;
    }

    public void setMydata(List<sendbundle> mydata) {
        this.mydata = mydata;
    }

    List<sendbundle>mydata;
    String ordername,orderprice;
    int counter;

}
