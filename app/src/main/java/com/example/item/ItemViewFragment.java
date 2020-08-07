package com.example.item;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ItemViewFragment extends Fragment implements CustomList.CustomListener
{

     CustomList customList;
     List<ViewItemParam> list =new ArrayList<>();
     private Button button;
     LinearLayout main,header;
     ListView listView;
     TextView textView;
     View dialogLayout;

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
        customList.setListener(this);

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
         LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         dialogLayout = layoutInflater.inflate(R.layout.show_dialog,(ViewGroup)view.findViewById(R.id.dialog_main));

    }
    public void deleteItem(int itemNumber){
        final ViewItemParam list = customList.getItem(itemNumber);
        new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setMessage(list.getName() + "削除しますか？")
                .setPositiveButton("削除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeleteData task = new DeleteData(getActivity());
                        task.execute(String.valueOf(list.getId()));
                    }
                })
                .setNegativeButton("キャンセル",null).show();
        customList.notifyDataSetChanged();
    }
    public void showItem(int itemNumber){
        new AlertDialog.Builder(getActivity())
                .setTitle("商品")
                .setView(dialogLayout)
                .setNegativeButton("閉じる",null).show();


    }


}
