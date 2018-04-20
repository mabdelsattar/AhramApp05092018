package com.abdelsattar.alahramapp.model;

import org.json.JSONObject;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class RequestModel
{
    String ordernumber,clientname,clientphone,reciverdate,Notes,paid,remain;


    public String getOrdernumber() {
        return ordernumber;
    }

    public RequestModel(String ordernumber, String clientname, String clientphone, String reciverdate,String object,String notes,String _paid,String _remain) {
        this.ordernumber = ordernumber;
        this.clientname = clientname;
        this.clientphone = clientphone;
        this.reciverdate = reciverdate;
        this.StrObject = object;
        this.Notes = notes;
        this.paid = _paid;
        this.remain = _remain;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String StrObject;

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getStrObject() {
        return StrObject;
    }

    public void setStrObject(String strObject) {
        StrObject = strObject;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getClientname() {
        return clientname;
    }

    public void setClientname(String clientname) {
        this.clientname = clientname;
    }

    public String getClientphone() {
        return clientphone;
    }

    public void setClientphone(String clientphone) {
        this.clientphone = clientphone;
    }

    public String getReciverdate() {
        return reciverdate;
    }

    public void setReciverdate(String reciverdate) {
        this.reciverdate = reciverdate;
    }



}
