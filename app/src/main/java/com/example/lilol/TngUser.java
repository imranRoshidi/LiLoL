package com.example.lilol;

public class TngUser {
    public String phone_num, pin_code, balance, link_user;

    public TngUser(){

    }

    public TngUser(String phone_num, String pin_code, String balance, String link_user){
        this.phone_num = phone_num;
        this.pin_code = pin_code;
        this.balance = balance;
        this.link_user = link_user;
    }
}
