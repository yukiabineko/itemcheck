package com.example.item;

import android.graphics.Bitmap;

public class ViewItemParam
{
    private String name;
    private String price;
    private Bitmap bitmap;

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
