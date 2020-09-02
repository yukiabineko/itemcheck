package com.example.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

public class ItemAggregate extends AppCompatActivity implements OrderDvisionList.OrderDivisionListener {
    private ListView listView;
    private  OrderDvisionList adapter;
    private List<DivisionParams> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_division);

        Intent intent = getIntent();
        String id = intent.getStringExtra("itemId");
        String name = intent.getStringExtra("name");


        listView = findViewById(R.id.division_listView);
        TextView titleItem = findViewById(R.id.order_division_main_title);
        titleItem.setText("商品名:" + name + "集計");

        Button backButton = findViewById(R.id.division_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });


        adapter = new OrderDvisionList(this, 0, list);
        adapter.setListener(this);
        listView.setAdapter(adapter);

        DivisionTask task = new DivisionTask(this,adapter,list);
        task.execute(id);

    }
    public  void  confirmationView(int itemNumber,  Button confirmButton,  Button backButton,  TextView conf){
        DivisionParams params = list.get(itemNumber);
        ConfirmTask task = new ConfirmTask(this);
        Toast.makeText(this, String.valueOf(params.getItemId()), Toast.LENGTH_LONG).show();
        Toast.makeText(this, String.valueOf(params.getNumber()), Toast.LENGTH_LONG).show();
        Toast.makeText(this, String.valueOf(params.getUserId()), Toast.LENGTH_LONG).show();
        task.execute(String.valueOf(params.getUserId()), String.valueOf(params.getItemId()), params.getConfirm());

        conf.setText("確定済み");
    }

}