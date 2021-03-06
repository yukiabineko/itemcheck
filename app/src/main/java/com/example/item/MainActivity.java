package com.example.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface font = Typeface.createFromAsset(getBaseContext().getAssets(), "fontawesome-webfont.ttf");
        final Button bt = findViewById(R.id.bt);
        bt.setTypeface(font);
        final Button bt2 = findViewById(R.id.bt2);
        bt2.setTypeface(font);
        final Button bt3 = findViewById(R.id.bt3);
        bt3.setTypeface(font);
        final Button bt4 = findViewById(R.id.bt4);
        bt4.setTypeface(font);


        ItemViewFragment itemViewFragment = new ItemViewFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ll, itemViewFragment).commit();


        bt3.setTypeface(font);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemAddFragment itemAddFragment = new ItemAddFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ll, itemAddFragment).commit();
                bt.setBackgroundColor(Color.parseColor("#3700B3"));
                bt2.setBackgroundColor(Color.parseColor("#6200EE"));
                bt3.setBackgroundColor(Color.parseColor("#6200EE"));
                bt4.setBackgroundColor(Color.parseColor("#6200EE"));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemViewFragment itemViewFragment = new ItemViewFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ll, itemViewFragment).commit();
                bt.setBackgroundColor(Color.parseColor("#6200EE"));
                bt2.setBackgroundColor(Color.parseColor("#3700B3"));
                bt3.setBackgroundColor(Color.parseColor("#6200EE"));
                bt4.setBackgroundColor(Color.parseColor("#6200EE"));
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRequestFragment itemAddFragment = new UserRequestFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ll, itemAddFragment).commit();
                bt.setBackgroundColor(Color.parseColor("#6200EE"));
                bt2.setBackgroundColor(Color.parseColor("#6200EE"));
                bt3.setBackgroundColor(Color.parseColor("#3700B3"));
                bt4.setBackgroundColor(Color.parseColor("#6200EE"));
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopInfoFragment shopInfoFragment = new ShopInfoFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ll, shopInfoFragment).commit();
                bt.setBackgroundColor(Color.parseColor("#6200EE"));
                bt2.setBackgroundColor(Color.parseColor("#6200EE"));
                bt3.setBackgroundColor(Color.parseColor("#6200EE"));
                bt4.setBackgroundColor(Color.parseColor("#3700B3"));

            }
        });

    }
}