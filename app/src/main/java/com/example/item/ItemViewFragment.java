package com.example.item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ItemViewFragment extends Fragment
{

    CustomList customList;
    List<ViewItemParam> list =new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.itemview_layout, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewItemParam param = new ViewItemParam();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.a);
        param.setBitmap(bitmap);
        param.setName("コーヒー");
        param.setPrice("200");
        list.add(param);


        customList = new CustomList(getContext(),0,list);
        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(customList);




    }
}
