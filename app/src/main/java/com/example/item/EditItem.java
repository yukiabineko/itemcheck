package com.example.item;

import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;



public class EditItem extends AppCompatActivity
{
    private String name;
    private String price;
    private  String info;
    private  String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);


        Typeface font = Typeface.createFromAsset(this.getAssets(), "fontawesome-webfont.ttf");
        EditText nameInput = findViewById(R.id.name_edit);
        EditText priceInput = findViewById(R.id.price_edit);
        EditText infoInput = findViewById(R.id.item_comment);
        ImageView imageView = findViewById(R.id.show_image);




        final Intent intent = getIntent();
        String obj = intent.getStringExtra("obj");


        try {
            JSONObject jsonObject = new JSONObject(obj);
            name = jsonObject.getString("name");
            price = jsonObject.getString("price");
            info = jsonObject.getString("info");
            path = jsonObject.getString("path");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        nameInput.setText(name);
        priceInput.setText(price);
        infoInput.setText(info);
        String url = "http://yukiabineko.sakura.ne.jp/items/" + path;
        Picasso.get().load(url).into(imageView);

/****************************view関連*************************************************/
        TextView title = findViewById(R.id.add_edit_page_title);
        title.setText("商品編集");

        Button pictureButton = findViewById(R.id.image_button);
        pictureButton.setTypeface(font);

        Button cameraButton = findViewById(R.id.image_picture);
        cameraButton.setTypeface(font);



        Button backButton = findViewById(R.id.back_intent);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainPage = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainPage);
            }
        });

        findViewById(R.id.add_button).setVisibility(View.GONE);
        Button editButton = findViewById(R.id.add_page_edit_button);
        editButton.setTypeface(font);
        editButton.setVisibility(View.VISIBLE);

    }
}
