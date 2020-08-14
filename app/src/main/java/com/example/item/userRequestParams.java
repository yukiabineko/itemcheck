package com.example.item;

public class userRequestParams {
    private int id;
    private String name;
    private String price;
    private String number;
    private String memo;


    public void setId(int i){
        this.id = i;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setNumber(String number) {this.number = number; }
    public void setMemo(String memo){this.memo = memo;}

    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getNumber() {
        return number;
    }

    public  String getMemo(){return  memo;}
}
