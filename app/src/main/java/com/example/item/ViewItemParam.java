package com.example.item;


public class ViewItemParam
{
    private int id;
    private String name;
    private String price;
    private String bitmap;


    public void setId(int i){
        this.id = i;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getBitmap() {
        return bitmap;
    }
}
