package com.example.item;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class CustomList extends ArrayAdapter<ViewItemParam>
{
    private  List<ViewItemParam> mist;
    private LayoutInflater layoutInflater;
    private CustomListener listener;
    Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");

    public interface CustomListener{
        void deleteItem(int itemNumber);
        void showItem(int itemNumber);
        void editPage(int itemId);
    }

    public void setListener(CustomListener listener){
        this.listener = listener;
    }

    public CustomList(Context context, int i, List<ViewItemParam> list){
        super(context, i, list);

        mist = list;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, final ViewGroup parent) {
       final ViewItemParam list =  mist.get(position);


        if (convertView == null) {
           convertView = layoutInflater.inflate(R.layout.row,null);
        }



        ImageView imageView =convertView.findViewById(R.id.item_image);
        String url = "http://yukiabineko.sakura.ne.jp/items/" + list.getBitmap();
        Picasso.get().load(url).into(imageView);


        TextView name = convertView.findViewById(R.id.name_cont);
        name.setText(list.getName());

        TextView price = convertView.findViewById(R.id.price_cont);
        price.setText(list.getPrice());


        Button deleteButton = convertView.findViewById(R.id.item_delete);
        deleteButton.setTypeface(font);
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemNumber = (int) view.getTag();
                listener.deleteItem(itemNumber);
            }
        });

        Button showButton = convertView.findViewById(R.id.item_show);
        showButton.setTag(position);
        showButton.setTypeface(font);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemNumber = (int) view.getTag();
                listener.showItem(itemNumber);
            }
        });

        Button editButton = convertView.findViewById(R.id.item_edit_page);
        editButton.setTag(position);
        editButton.setTypeface(font);
        editButton.setPaintFlags(editButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        editButton.setPaintFlags(editButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.editPage(list.getId());
            }
        });

        return  convertView;
    }

}
