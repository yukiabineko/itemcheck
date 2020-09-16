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

public class OrderDvisionList extends ArrayAdapter<DivisionParams>
{
    private  LayoutInflater layoutInflater;
    private  List<DivisionParams> orderList;
    private OrderDivisionListener listener;
    Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");

    public interface  OrderDivisionListener{
        void confirmationView(int itemNumber, Button button, Button button2, TextView confirm);  /*どのボタンか判別するため引数にボタン追加*/
        void deleteConfirm(int itemNumber);
        void mailSend(int itemNO);
    }
    public  void setListener(OrderDivisionListener listener){
        this.listener = listener;
    }


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
        //店舗名
        TextView shop = convertView.findViewById(R.id.order_division_shop);
        shop.setText(params.getShop());


        TextView price = convertView.findViewById(R.id.division_price);
        price.setTypeface(null, Typeface.BOLD);
        price.setGravity(Gravity.CENTER);
        price.setText(params.getNumber());

        final TextView conf = convertView.findViewById(R.id.division_confirm);
        conf.setGravity(Gravity.CENTER);
        String f = params.getConfirm();

        final Button confirmButton = convertView.findViewById(R.id.division_confirm_button);
        confirmButton.setTag(position);
        confirmButton.setTypeface(font);
        final Button backButton = convertView.findViewById(R.id.division_confirm_back);
        backButton.setTypeface(font);
        backButton.setTag(position);

        final  Button mailButton = convertView.findViewById(R.id.division_confirm_mail);
        mailButton.setTypeface(font);
        mailButton.setTag(position);

        /*発注確定か判別*/

        if(f.equals("0")){
           conf.setText("未確定");
           conf.setTextColor(Color.RED);
           confirmButton.setVisibility(View.VISIBLE);
           backButton.setVisibility(View.GONE);
        }
        else {
            conf.setText("確定");
            conf.setTextColor(Color.BLUE);
            confirmButton.setVisibility(View.GONE);
            backButton.setVisibility(View.VISIBLE);
        }
        conf.setTypeface(null,Typeface.BOLD);

        /*発注確定か判別*/

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemNumber = (int) view.getTag();
                listener.confirmationView(itemNumber, confirmButton, backButton, conf);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemNumber = (int) view.getTag();
                listener.deleteConfirm(itemNumber);
            }
        });
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemNumber = (int) view.getTag();
                listener.mailSend(itemNumber);
            }
        });





        return  convertView;
    }
/**/
}
