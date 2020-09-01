package com.example.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class OrderDvisionList extends ArrayAdapter<DivisionParams>
{
    private  LayoutInflater layoutInflater;
    private  List<DivisionParams> orderList;

    public OrderDvisionList(Context context, int i, List<DivisionParams> list){
        super(context, i, list);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        orderList = list;
    }

    public View getView(int position, View convertView, final ViewGroup parent) {
        DivisionParams params = orderList.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.order_division_row,null);
        }
        TextView price = convertView.findViewById(R.id.division_price);
        price.setText(params.getPrice());


        return  convertView;
    }

}
