package com.example.item;

import android.content.ClipData;

public class userRequestParams {
    private int userid;
    private int itemid;
    private String name;
    private String shop;
    private String number;
    private String memo;
    private String day;
    private String confirm;
    private String price;
    private String email;

    public void setUserid(int i){this.userid = i;}
    public void setIemId(int i){
        this.itemid = i;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setShop(String shop) {this.shop = shop;}
    public void setNumber(String number) {this.number = number; }
    public void setMemo(String memo){this.memo = memo;}
    public void setDay(String day){this.day = day;}
    public void setConfirm(String confirm){this.confirm = confirm;}
    public void setPrice(String price){this.price = price;}
    public void setMail(String mail){this.email = mail;}

    public int getUserid(){return  userid;}
    public int getItemId(){ return itemid; }
    public String getName() {
        return name;
    }
    public String getShop(){return shop;}
    public String getNumber() {
        return number;
    }
    public  String getMemo(){return  memo;}
    public  String getDay(){return  day;}
    public String getConfirm(){return confirm;}
    public  String  getPrice(){return price;}
    public  String getEmail(){return email;}
}
