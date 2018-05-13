package com.abdelsattar.alahramapp.Admin;

/**
 * Created by codelab on 5/13/2018.
 */

public class ClientViewModel {

    public String Name;
    public String Phone;

    public ClientViewModel(String name, String phone) {
        Name = name;
        Phone = phone;
    }

    public ClientViewModel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
