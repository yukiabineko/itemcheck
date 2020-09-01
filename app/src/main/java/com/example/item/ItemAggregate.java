package com.example.item;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ItemAggregate extends AppCompatActivity {
    private ListView listView;
    private  OrderDvisionList adapter;
    private List<DivisionParams> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_division);

        Intent intent = getIntent();
        String id = intent.getStringExtra("itemId");
        DivisionTask task = new DivisionTask(this,adapter,list);
        task.execute(id);

        DivisionParams params =new DivisionParams();
        params.setPrice("100");


        listView = findViewById(R.id.division_listView);
        adapter = new OrderDvisionList(this, 0, list);
        adapter.add(params);
        listView.setAdapter(adapter);


    }
}