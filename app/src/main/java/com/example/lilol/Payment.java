package com.example.lilol;

public class Payment {

    public String payment_details;
    public Float pay_amount;

    public Payment(){

    }

    public Payment(Float pay_amount, String payment_details){
        this.pay_amount = pay_amount;
        this.payment_details = payment_details;
    }

}
