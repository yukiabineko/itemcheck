package com.example.item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ItemViewFragment extends Fragment
{

     CustomList customList;
     List<ViewItemParam> list =new ArrayList<>();
     private Button button;
     LinearLayout main,header;
     ListView listView;
     TextView textView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.itemview_layout, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        main = view.findViewById(R.id.main);

        button = view.findViewById(R.id.api_button);
        header = view.findViewById(R.id.table_header);
        textView = view.findViewById(R.id.not_data);

        customList = new CustomList(getContext(),0,list);

        if(customList.isEmpty()){
            header.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView.setY(650);
            textView.setX(200);
        }

        final ListgetData task = new ListgetData(getActivity(),customList,list);
        task.execute();

        listView = view.findViewById(R.id.listView);
        listView.setAdapter(customList);

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 header.setVisibility(View.INVISIBLE);
                 customList.clear();
                 if(customList.isEmpty()){

                     textView.setVisibility(View.VISIBLE);
                     textView.setY(650);
                     textView.setX(200);
                 }
                 else {
                     textView.setVisibility(View.INVISIBLE);
                 }

                     ListgetData task = new ListgetData(getActivity(),customList,list);
                     task.execute();
                 }
         });



    }
}
