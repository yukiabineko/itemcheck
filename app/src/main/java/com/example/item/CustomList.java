package com.example.item;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CustomList extends ArrayAdapter<ViewItemParam>
{
    private  List<ViewItemParam> mist;
    private LayoutInflater layoutInflater;

    public CustomList(Context context, int i, List<ViewItemParam> list){
        super(context, i, list);


        mist = list;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
       ViewItemParam list =  mist.get(position);

        if (convertView == null) {
           convertView = layoutInflater.inflate(R.layout.row,null);
        }

        ImageView imageView =convertView.findViewById(R.id.item_image);
        String url = "http://192.168.1.6/" + list.getBitmap();
        Picasso.get().load(url).into(imageView);


        TextView name = convertView.findViewById(R.id.name_cont);
        name.setText(list.getName());

        TextView price = convertView.findViewById(R.id.price_cont);
        price.setText(list.getPrice());


        return  convertView;
    }

}
