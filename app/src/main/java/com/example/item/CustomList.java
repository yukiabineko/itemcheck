package com.example.item;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


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
       final ViewItemParam list =  mist.get(position);

        if (convertView == null) {
           convertView = layoutInflater.inflate(R.layout.row,null);
        }

        final int id = list.getId();

        ImageView imageView =convertView.findViewById(R.id.item_image);
        String url = "http://yukiabineko.sakura.ne.jp/items/" + list.getBitmap();
        Picasso.get().load(url).into(imageView);


        TextView name = convertView.findViewById(R.id.name_cont);
        name.setText(list.getName());

        TextView price = convertView.findViewById(R.id.price_cont);
        price.setText(list.getPrice());


        Button deleteButton = convertView.findViewById(R.id.item_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("削除確認")
                        .setMessage(list.getName() +"を削除しますか？")
                        .setPositiveButton("削除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DeleteData task =new DeleteData();
                                task.execute(String.valueOf(id));
                                new AlertDialog.Builder(getContext())
                                        .setTitle("確認")
                                        .setMessage(list.getName() + "を削除しました。")
                                        .setPositiveButton("閉じる",null).show();
                            }
                        }).show();
            }
        });



        return  convertView;
    }

}
