package com.example.item;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class UserRequestList extends ArrayAdapter<userRequestParams>
{

    private  List<userRequestParams> mist;
    private LayoutInflater layoutInflater;

    public UserRequestList(Context context, int i, List<userRequestParams> list){
        super(context, i, list);

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        mist = list;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(int position, View convertView, final ViewGroup parent) {
        final userRequestParams list =  mist.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row,null);
        }

        TextView name = convertView.findViewById(R.id.request_name_cont);
        name.setText(list.getName());

        TextView price = convertView.findViewById(R.id.request_price_cont);
        price.setText(list.getPrice());




        return  convertView;
    }
}
