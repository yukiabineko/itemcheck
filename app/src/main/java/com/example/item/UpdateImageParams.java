package com.example.item;

import android.graphics.Bitmap;

public class UpdateImageParams {
    public String uri;
    public Bitmap bmp;
    public int id;


    public UpdateImageParams(String uri, Bitmap bmp, int id) {
        this.uri = uri;
        this.bmp = bmp;
        this.id =id;

    }
}