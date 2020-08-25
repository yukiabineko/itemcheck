package com.example.item;

import android.graphics.Bitmap;

public class UpdateImageParams {
    public String uri;
    public Bitmap bmp;



    public UpdateImageParams(String uri, Bitmap bmp) {
        this.uri = uri;
        this.bmp = bmp;


    }
}