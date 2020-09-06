package com.example.item;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;

class ShopDataList extends ArrayAdapter<ShopDataParams>
{
    private  LayoutInflater layoutInflater;
    private  List<ShopDataParams> mist;
    private  shopListener listener;

    public interface shopListener{
        void deleteShop(int shopNO);
    }
    public void setListener(shopListener listener){
        this.listener = listener;
    }

    public ShopDataList(Context context, int i, List<ShopDataParams> list){
        super(context, i, list);

        mist = list;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public View getView(int position, View convertView, final ViewGroup parent) {
        final ShopDataParams params =  mist.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.shop_info_row,null);
        }

        TextView name = convertView.findViewById(R.id.shop_name);
        name.setText(params.getName());
        name.setTypeface(null, Typeface.BOLD);

        TextView mail = convertView.findViewById(R.id.shop_mail);
        mail.setText(params.getEmail());
        name.setTypeface(null, Typeface.BOLD);

        Button delete = convertView.findViewById(R.id.shop_data_delete);
        delete.setTag(position);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int shopNO = (int) view.getTag();
                listener.deleteShop(shopNO);
            }
        });



        return  convertView;
    }

}
