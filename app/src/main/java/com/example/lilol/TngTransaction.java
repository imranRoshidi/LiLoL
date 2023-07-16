package com.example.lilol;


public class TngTransaction {
    public String title, type, date_time, status, payee_receiver, amount;
    public long timestamp;

    public TngTransaction(String title, String type, String date_time, String status, String payee_receiver, String amount, Long timestamp){
        this.title = title;
        this.type = type;
        this.date_time = date_time;
        this.status = status;
        this.payee_receiver = payee_receiver;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
