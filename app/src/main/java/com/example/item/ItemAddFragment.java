package com.example.item;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;
import java.util.Objects;

public class ItemAddFragment extends Fragment {

    private static final int READ_REQUEST_CODE = 42;
    ImageView imageView;
    private com.example.item.UploadTask task;
    private Bitmap customise;
    private TextView nameValidation, priceValidation;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.layout, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");

        Button fileimage = view.findViewById(R.id.image_button);
        final Button cameraimage = view.findViewById(R.id.image_picture);
        Button button2 = view.findViewById(R.id.add_button);
        fileimage.setTypeface(font);
        cameraimage.setTypeface(font);
        button2.setTypeface(font);
        imageView = view.findViewById(R.id.show_image);
        final EditText editText = view.findViewById(R.id.name_edit);
        final EditText editText2 = view.findViewById(R.id.price_edit);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER);
        final EditText editText3 = view.findViewById(R.id.item_comment);
        nameValidation = view.findViewById(R.id.name_validation);
        priceValidation = view.findViewById(R.id.price_validation);


        fileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");

                startActivityForResult(intent, READ_REQUEST_CODE);


            }
        });
        cameraimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(
                        intent,
                        READ_REQUEST_CODE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String param0 = editText.getText().toString();
                String param1 = editText2.getText().toString();
                String param2 = editText3.getText().toString();
                if ((param0.length() != 0) &&(param1.length() !=0)) {
                    task = new com.example.item.UploadTask(getActivity());
                    task.execute(param0, param1, param2);
                    ItemViewFragment itemViewFragment = new ItemViewFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.ll, itemViewFragment).commit();
                    nameValidation.setVisibility(View.GONE);
                    priceValidation.setVisibility(View.GONE);
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



                if (customise != null) {
                    new PostBmpAsyncHttpRequest().execute(new Param("http://yukiabineko.sakura.ne.jp/items/imagePost.php", customise));

                }

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
            if (resultData.getExtras() != null &&
                    resultData.getExtras().get("data") != null) {
                Bitmap capturedImage
                        = (Bitmap) resultData.getExtras().get("data");
                customise = Bitmap.createScaledBitmap(capturedImage, capturedImage.getWidth() / 1, capturedImage.getHeight() / 1, true);
                imageView.setImageBitmap(customise);
                imageView.setImageBitmap(capturedImage);
            } else {
                uri = resultData.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), uri);
                    if (bitmap != null) {
                        customise = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 5, bitmap.getHeight() / 5, true);
                        imageView.setImageBitmap(customise);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
