package com.alahram.alahramapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by amiraelsayed on 1/7/2018.
 */

public class Preferences
{
    private static final String TAG = Preferences.class.getSimpleName();

    /**
     * The name of the file utilized to store the data.
     */
    private static final String FILE_NAME = "Preferences";

    /**
     * The set of keys utilized.
     */
    /**
     * The key for the user name.
     */
    private static final String CLIENTTNAME= "clientname";
    private static final String CLIENTPHONEKSA = "clientphoneksa";
    private static final String CLIENTPHONEEGY = "clientphoneegy";
    private static final String NATIONALID = "clientnationalid";
    private static final String RECIVERNAME = "recivername";
    private static final String RECIVERPHONEKSA = "reciverphoneksa";
    private static final String RECIVERPHONEEGY = "reciverphoneegy";
    private static final String RECIVERNATIONALID = "recivernationalid";
    private static final String CLIENTADDRESS = "clientaddress";
    private static final String CLIENTDETAILLADDRESS = "clientaddressdetails";
    private static final String CLIENTDETAILLADDRESS1= "clientaddressdeail1";
    private static final String CLIENTDETAILLADDRESS2 = "clientaddressdetail2";
    private static final String RECIVERADDRESS = "reciveraddress";
    private static final String RECIVERDETAILLADDRESS = "reciveraddressdetail";
    private static final String RECIVERDETAILLADDRESS1= "reciveraddressdetail1";
    private static final String RECIVERDETAILLADDRESS2 = "reciveraddressdetail2";
    private static final String USERID = "USERID";
    private final Context context;

    /**
     * The shared preferences to save/restore the data.
     */
    private final SharedPreferences sharedPreferences;

    /**
     * The editor to save the data.
     */
    private final SharedPreferences.Editor editor;

    /**
     * The main constructor.
     * @param context The context passed by any Android's component.
     */
    public Preferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(Preferences.FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public String getclientname()
    {
            String clientname=sharedPreferences.getString(Preferences.CLIENTTNAME,"");
        return  clientname;
    }
    public void setclientnae(String clientname)
    {
        editor.putString(Preferences.CLIENTTNAME, clientname);
        editor.commit();
    }
    public String getClientPhoneEgy()
    {
        String clientphoneEgy=sharedPreferences.getString(Preferences.CLIENTPHONEEGY,"");
        return  clientphoneEgy;
    }
    public void setClientphoneEgy(String clientphoneegy)
    {
        editor.putString(Preferences.CLIENTPHONEEGY, clientphoneegy);
        editor.commit();
    }
    public String getClientPhoneKsa()
    {
        String clientphoneksa=sharedPreferences.getString(Preferences.CLIENTPHONEKSA,"");
        return  clientphoneksa;
    }
    public void setClientphoneKsa(String clientphoneksa)
    {
        editor.putString(Preferences.CLIENTPHONEKSA, clientphoneksa);
        editor.commit();
    }
    public String getClientNationalId()
    {
        String nationalid=sharedPreferences.getString(Preferences.NATIONALID,"");
        return  nationalid;
    }
    public void setClientNationalId(String clientnationalid)
    {
        editor.putString(Preferences.NATIONALID, clientnationalid);
        editor.commit();
    }
    public String getClientaddress()
    {
        String address=sharedPreferences.getString(Preferences.CLIENTADDRESS,"");
        return  address;
    }
    public void setClientAddress(String clientaddress)
    {
        editor.putString(Preferences.CLIENTADDRESS, clientaddress);
        editor.commit();
    }
    public String getClientAddressDetial()
    {
        String clientnameaddress=sharedPreferences.getString(Preferences.CLIENTDETAILLADDRESS,"");
        return  clientnameaddress;
    }
    public void setClientAddressDetail(String clientAddressDetail)
    {
        editor.putString(Preferences.CLIENTDETAILLADDRESS, clientAddressDetail);
        editor.commit();
    }
    public String getClientAddressDetial1()
    {
        String address=sharedPreferences.getString(Preferences.CLIENTDETAILLADDRESS1,"");
        return  address;
    }
    public void setClientAddressDetail1(String clientAddressDetail1)
    {
        editor.putString(Preferences.CLIENTDETAILLADDRESS1, clientAddressDetail1);
        editor.commit();
    }
    public String getClientAddressDetial12()
    {
        String address=sharedPreferences.getString(Preferences.CLIENTDETAILLADDRESS2,"");
        return  address;
    }
    public void setClientAddressDetail2(String clientAddressDetail2)
    {
        editor.putString(Preferences.CLIENTDETAILLADDRESS2, clientAddressDetail2);
        editor.commit();
    }
    public String getRecivername()
    {
        String recivername=sharedPreferences.getString(Preferences.RECIVERNAME,"");
        return  recivername;
    }
    public void setReciverName(String recivername)
    {
        editor.putString(Preferences.RECIVERNAME, recivername);
        editor.commit();
    }
    public String getReciverPhoneKsa()
    {
        String Reciverphoneksa=sharedPreferences.getString(Preferences.RECIVERPHONEKSA,"");
        return  Reciverphoneksa;
    }
    public void setReciverphoneKsa(String Reciverphoneksa)
    {
        editor.putString(Preferences.RECIVERPHONEKSA, Reciverphoneksa);
        editor.commit();
    }
    public String getReciverPhoneEgy()
    {
        String reciverphoneEgy=sharedPreferences.getString(Preferences.RECIVERPHONEEGY,"");
        return  reciverphoneEgy;
    }
    public void setReciverphoneEgy(String reciverphoneegy)
    {
        editor.putString(Preferences.RECIVERPHONEEGY, reciverphoneegy);
        editor.commit();
    }
    public String getReciverNationalId()
    {
        String nationalid=sharedPreferences.getString(Preferences.RECIVERNATIONALID,"");
        return  nationalid;
    }
    public void setReciverNationalId(String reciverNationalId)
    {
        editor.putString(Preferences.RECIVERNATIONALID, reciverNationalId);
        editor.commit();
    }
    public String getReciveraddress()
    {
        String address=sharedPreferences.getString(Preferences.RECIVERADDRESS,"");
        return  address;
    }
    public void setReciverAddress(String reciverAddress)
    {
        editor.putString(Preferences.RECIVERADDRESS, reciverAddress);
        editor.commit();
    }
    public String getReciverAddressDetial()
    {
        String reciveraddress=sharedPreferences.getString(Preferences.RECIVERDETAILLADDRESS,"");
        return  reciveraddress;
    }
    public void setReciverAddressDetail(String reciverAddressDetail)
    {
        editor.putString(Preferences.RECIVERDETAILLADDRESS, reciverAddressDetail);
        editor.commit();
    }
    public String getReciverAddressDetial1()
    {
        String address=sharedPreferences.getString(Preferences.RECIVERDETAILLADDRESS1,"");
        return  address;
    }
    public void setReciverAddressDetail1(String reciverAddressDetail1)
    {
        editor.putString(Preferences.RECIVERDETAILLADDRESS1, reciverAddressDetail1);
        editor.commit();
    }
    public String getReciverAddressDetial12()
    {
        String address=sharedPreferences.getString(Preferences.RECIVERDETAILLADDRESS2,"");
        return  address;
    }
    public void setReciverAddressDetail2(String reciverAddressDetail2)
    {
        editor.putString(Preferences.CLIENTDETAILLADDRESS2, reciverAddressDetail2);
        editor.commit();
    }

    public void setUserId(int UserId)
    {
        editor.putInt(Preferences.USERID, UserId);
        editor.commit();
    }
    public int getUserId()
    {
        int userId=sharedPreferences.getInt(Preferences.USERID,-1);
        return  userId;
    }
}