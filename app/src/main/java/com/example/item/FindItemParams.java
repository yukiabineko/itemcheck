package com.example.item;

public class FindItemParams
{
    private int id;
    private String name;
    private String price;
    private String path;
    private String info;


    public void setId(int i){
        this.id = i;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public void setInfo(String info ){this.info = info;}

    public int getId(){
        return id;
    }
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getPath() {
        return path;
    }

    public  String getInfo(){return  info;}
}