package com.example.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

class ShopDataList extends ArrayAdapter<ShopDataParams>
{
    private  LayoutInflater layoutInflater;
    private  List<ShopDataParams> mist;

    public ShopDataList(Context context, int i, List<ShopDataParams> list){
        super(context, i, list);

        mist = list;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, final ViewGroup parent) {
        final ShopDataParams params =  mist.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row,null);
        }

        TextView name = convertView.findViewById(R.id.shop_name);
        name.setText(params.getName());

        TextView mail = convertView.findViewById(R.id.shop_mail);
        mail.setText(params.getName());


        return  convertView;
    }

}
