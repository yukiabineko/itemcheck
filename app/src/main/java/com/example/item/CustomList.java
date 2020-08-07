package com.example.item;

import android.content.Context;
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
    private CustomListener listener;

    public interface CustomListener{
        void deleteItem(int itemNumber);
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
        deleteButton.setTag(position);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemNumber = (int) view.getTag();
                listener.deleteItem(itemNumber);
            }
        });



        return  convertView;
    }

}
