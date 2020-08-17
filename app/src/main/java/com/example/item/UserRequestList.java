package com.example.item;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class UserRequestList extends ArrayAdapter<userRequestParams>
{

    private  List<userRequestParams> mist;
    private LayoutInflater layoutInflater;
    private RequestListener listener;
    Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");

    public interface RequestListener{
        void confirmationView(int itemNumber);
        void backconfirm(int itemNumber);
        void mailsend(int itemNumber);
    }

    public void setListener(RequestListener listener){
        this.listener = listener;
    }

    public UserRequestList(Context context, int i, List<userRequestParams> list){
        super(context, i, list);

        mist = list;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(int position, View convertView, final ViewGroup parent) {
        final userRequestParams list =  mist.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.request_list_row,null);
        }

        TextView name = convertView.findViewById(R.id.request_name_cont);
        name.setText(list.getName());

        TextView shop = convertView.findViewById(R.id.request_shop_cont);
        shop.setText(list.getShop());

        TextView price = convertView.findViewById(R.id.request_num_cont);
        price.setText(list.getNumber());


        TextView day = convertView.findViewById(R.id.request_day_cont);
        day.setText(list.getDay());


        TextView confirm = convertView.findViewById(R.id.request_confirm_cont);
        String conf = list.getConfirm();


        Button confirmButton = convertView.findViewById(R.id.reuest_send);
        confirmButton.setTypeface(font);
        confirmButton.setTag(position);
        Button backButton = convertView.findViewById(R.id.reuest_edit);
        backButton.setTag(position);
        backButton.setTypeface(font);
        Button mailButton = convertView.findViewById(R.id.request_mail);
        mailButton.setTag(position);
        mailButton.setTypeface(font);


        if(conf.equals("1")){
            confirm.setText("確定済み");
            confirm.setGravity(Gravity.CENTER);
            confirm.setTextColor(Color.WHITE);
            confirm.setBackgroundColor(Color.parseColor("#6200EE"));
            confirmButton.setVisibility(View.GONE);
            backButton.setVisibility(View.VISIBLE);
        }
        else{
            confirm.setText("未確定");
            confirm.setGravity(Gravity.CENTER);
            confirm.setTextColor(Color.WHITE);
            confirm.setBackgroundColor(Color.RED);
            confirmButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.GONE);
        }
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemNumber = (int) view.getTag();
                listener.confirmationView(itemNumber);
                
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemNumber = (int) view.getTag();
                listener.backconfirm(itemNumber);
            }
        });
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemNumber = (int) view.getTag();
                listener.mailsend(itemNumber);
            }
        });


        return  convertView;
    }
}
