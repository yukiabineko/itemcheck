package com.example.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import java.util.List;

class ShopDataList extends ArrayAdapter<ShopDataParams>
{
    private  LayoutInflater layoutInflater;

    public ShopDataList(Context context, int i, List<ShopDataParams> list){
        super(context, i, list);
    }

    public View getView(int position, View convertView, final ViewGroup parent) {


        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row,null);
        }

        return  convertView;
    }

}
