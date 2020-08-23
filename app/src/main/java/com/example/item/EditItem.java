package com.example.item;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditItem extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        Typeface font = Typeface.createFromAsset(this.getAssets(), "fontawesome-webfont.ttf");

        final Intent intent = getIntent();
        String sendId = intent.getStringExtra("id");
        Toast.makeText(this, sendId,Toast.LENGTH_LONG).show();

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
