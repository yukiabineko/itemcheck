package com.example.item;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
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
    Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");

    public interface shopListener{
        void deleteShop(int shopNO);
        void updateShop(int shopNO);
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
        mail.setTag(position);
        mail.setPaintFlags(mail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mail.setTypeface(null, Typeface.BOLD);

        /*メールアプリ起動*/
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{params.getEmail()});
                getContext().startActivity(intent);
            }
        });







        TextView tel = convertView.findViewById(R.id.shop_tel);
        tel.setText(params.getTel());
        tel.setPaintFlags(tel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tel.setTypeface(font);

        Button delete = convertView.findViewById(R.id.shop_data_delete);
        delete.setTag(position);
        delete.setTypeface(font);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int shopNO = (int) view.getTag();
                listener.deleteShop(shopNO);
            }
        });
        Button update = convertView.findViewById(R.id.shop_update);
        update.setTag(position);
        update.setTypeface(font);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int shopNO = (int) view.getTag();
                listener.updateShop(shopNO);
            }
        });



        return  convertView;
    }

}
