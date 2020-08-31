package com.example.item;

import android.app.Activity;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;



public class EditItem extends AppCompatActivity
{
    private  int id;
    private String name;
    private String price;
    private  String info;
    private  String path;
    private  ImageView imageView;
    private  Bitmap customise;
    private static final int READ_REQUEST_CODE = 42;
    private TextView nameValidation, priceValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);


        Typeface font = Typeface.createFromAsset(this.getAssets(), "fontawesome-webfont.ttf");
        final EditText nameInput = findViewById(R.id.name_edit);
        final EditText priceInput = findViewById(R.id.price_edit);
        priceInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        final EditText infoInput = findViewById(R.id.item_comment);
        imageView = findViewById(R.id.show_image);
        nameValidation = findViewById(R.id.name_validation);
        priceValidation = findViewById(R.id.price_validation);



        final Intent intent = getIntent();
        String obj = intent.getStringExtra("obj");


        try {
            JSONObject jsonObject = new JSONObject(obj);
            name = jsonObject.getString("name");
            price = jsonObject.getString("price");
            info = jsonObject.getString("info");
            path = jsonObject.getString("path");
            id =Integer.parseInt(jsonObject.getString("id"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        nameInput.setText(name);
        priceInput.setText(price);
        infoInput.setText(info);
        String url = "http://yukiabineko.sakura.ne.jp/items/" + path;
        Picasso.get().load(url).into(imageView);

        customise = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        if(customise !=null){
            imageView.setImageBitmap(customise);
        }


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
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(
                        intent,
                        READ_REQUEST_CODE);
            }
        });



        findViewById(R.id.add_button).setVisibility(View.GONE);
        Button editButton = findViewById(R.id.add_page_edit_button);
        editButton.setTypeface(font);
        editButton.setVisibility(View.VISIBLE);

/*編集ボタン押し下*/
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String param0 = nameInput.getText().toString();
                String param1 = priceInput.getText().toString();
                String param2 = infoInput.getText().toString();
                nameValidation.setVisibility(View.GONE);
                priceValidation.setVisibility(View.GONE);

                if(param0.length() != 0 && param1.length() !=0){
                    ItemUpdateTask task = new ItemUpdateTask(EditItem.this);
                    task.execute(param0, param1, param2, String.valueOf(id));
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else if(param0.length() == 0 && param1.length() == 0){
                    nameValidation.setVisibility(View.VISIBLE);
                    priceValidation.setVisibility(View.VISIBLE);
                }

                else if(param0.length() == 0){
                    nameValidation.setVisibility(View.VISIBLE);
                    priceValidation.setVisibility(View.GONE);
                }
                else if(param1.length() == 0){
                    priceValidation.setVisibility(View.VISIBLE);
                    nameValidation.setVisibility(View.GONE);
                }


                if(customise !=null){

                    new ImageUpdateTask(EditItem.this).execute(new UpdateImageParams("http://yukiabineko.sakura.ne.jp/items/imageUpdate.php?id=" + id, customise));

                }


            }
        });

    }
/**********************************************************************************************************/
@RequiresApi(api = Build.VERSION_CODES.N)
public void onActivityResult(int requestCode, int resultCode,
                             Intent resultData) {

    super.onActivityResult(requestCode, resultCode, resultData);
    if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
        Uri uri;
        if(resultData.getExtras() != null &&
                resultData.getExtras().get("data")!= null){
            Bitmap capturedImage
                    = (Bitmap) resultData.getExtras().get("data");
            customise  = Bitmap.createScaledBitmap(capturedImage, capturedImage.getWidth()/3, capturedImage.getHeight()/3, true);
            imageView.setImageBitmap(customise);
            imageView.setImageBitmap(capturedImage);
        }
        else {
            uri = resultData.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                if(bitmap != null){
                    customise  = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/3, bitmap.getHeight()/3, true);
                    imageView.setImageBitmap(customise);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
}
