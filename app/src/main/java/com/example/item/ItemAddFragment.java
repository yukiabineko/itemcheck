package com.example.item;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.Objects;

public class ItemAddFragment extends Fragment
{
    private static final int READ_REQUEST_CODE = 42;
    ImageView imageView;
    private com.example.item.UploadTask task;
    private TextView textView;

    // phpがPOSTで受け取ったwordを入れて作成するHTMLページ(適宜合わせてください)
    String url = "http://192.168.1.7/pass_check.html";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.layout1, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button1 = view.findViewById(R.id.image_button);
        Button button2 = view.findViewById(R.id.add_button);
        imageView = view.findViewById(R.id.show_image);
        final EditText editText = view.findViewById(R.id.name_edit);
        final EditText editText2 = view.findViewById(R.id.price_edit);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String param0 = editText.getText().toString();
                String param1 = editText2.getText().toString();

                if(param0.length() != 0){
                    task = new com.example.item.UploadTask();
                    task.execute(param0, param1);
                }


                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");

                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        super.onActivityResult(requestCode, resultCode, resultData);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri;
            if (resultData != null) {
                uri = resultData.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);


                    new PostBmpAsyncHttpRequest(getActivity()).execute(new Param("http://192.168.1.9/index2.php", bitmap));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
