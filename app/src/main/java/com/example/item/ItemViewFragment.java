package com.example.item;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ItemViewFragment extends Fragment
{

     CustomList customList;
     List<ViewItemParam> list =new ArrayList<>();
     private Button button;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.itemview_layout, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = view.findViewById(R.id.api_button);

        ViewItemParam param = new ViewItemParam();


        param.setName("コーヒー");
        param.setPrice("200");
        list.add(param);


        customList = new CustomList(getContext(),0,list);
        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(customList);

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ListgetData task = new ListgetData(customList,list);
                 task.execute();
             }
         });


    }
}
