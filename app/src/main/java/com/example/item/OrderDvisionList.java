package com.example.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import java.util.List;

public class OrderDvisionList extends ArrayAdapter<DivisionParams>
{
    private  LayoutInflater layoutInflater;

    public OrderDvisionList(Context context, int i, List<DivisionParams> list){
        super(context, i, list);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, final ViewGroup parent) {


        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.order_division_row,null);
        }


        return  convertView;
    }

}
