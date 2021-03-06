package com.example.item;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemViewFragment extends Fragment implements CustomList.CustomListener
{

     CustomList customList;
     List<ViewItemParam> list =new ArrayList<>();

     Button getbutton,resetbutton,viewButton;
     LinearLayout mainArea,header,listArea;
     private ListView listView;
     private TextView textView, notText;
     View dialogLayout;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.itemview_layout, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainArea = view.findViewById(R.id.main);
        listArea =  view.findViewById(R.id.item_list_area);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");


        getbutton = view.findViewById(R.id.api_button);
        getbutton.setTypeface(font);
        resetbutton = view.findViewById(R.id.all_delete);
        resetbutton.setTypeface(font);
        header = view.findViewById(R.id.table_header);
        viewButton = view.findViewById(R.id.not_list_item_button);
        notText = view.findViewById(R.id.not_list_item);


        customList = new CustomList(getContext(),0,list);
        customList.setListener(this);



        final ListgetData task = new ListgetData(getActivity(),customList,list);
        task.execute();

        listView = view.findViewById(R.id.listView);
        listView.setAdapter(customList);

         getbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo info = cm.getActiveNetworkInfo();
                 if(info == null){
                     listArea.setVisibility(View.GONE);
                     viewButton.setVisibility(View.VISIBLE);
                     notText.setVisibility(View.VISIBLE);

                 }
                 else{
                     header.setVisibility(View.INVISIBLE);
                     customList.clear();

                     ListgetData task = new ListgetData(getActivity(),customList,list);
                     task.execute();
                 }
                 }
         });
         resetbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 new AlertDialog.Builder(getActivity())
                         .setTitle("確認")
                         .setMessage("すべての商品を削除します。よろしいですか？")
                         .setPositiveButton("全削除", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 Allreset task = new Allreset(getActivity());
                                 task.execute();
                                 customList.clear();
                                 customList.notifyDataSetChanged();
                             }
                         }).setNegativeButton("キャンセル",null).show();
             }
         });
         viewButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ListgetData task = new ListgetData(getActivity(),customList,list);
                 task.execute();
             }
         });


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
                        customList.remove(list);
                        customList.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("キャンセル",null).show();

    }
    public void showItem(int itemNumber){
        final ViewItemParam list = customList.getItem(itemNumber);
        ShowData task = new ShowData(getActivity());
        task.execute(String.valueOf(list.getId()));


        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialogLayout = layoutInflater.inflate(R.layout.show_dialog,(ViewGroup)getActivity().findViewById(R.id.dialog_main));
        ImageView dialogimageView =dialogLayout.findViewById(R.id.dialog_img);

        String url = "http://yukiabineko.sakura.ne.jp/items/" + list.getBitmap();
       Picasso.get().load(url).into(dialogimageView);


        TextView dialogmemo = dialogLayout.findViewById(R.id.dialog_memo);
        dialogmemo.setText(list.getMemo());



        //ダイアログのタイトル
        TextView dialogtitle = new TextView(getActivity());
        dialogtitle .setText(list.getName() + "詳細");
        dialogtitle .setTextSize(24);
        dialogtitle .setTextColor(Color.WHITE);
        dialogtitle .setBackgroundColor(getResources().getColor(R.color.alertBlue));
        dialogtitle .setPadding(20, 20, 20, 20);
        dialogtitle.setGravity(Gravity.CENTER);


        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setCustomTitle(dialogtitle)
                .setTitle(list.getMemo() + "詳細")
                .setView(dialogLayout)
                .setPositiveButton("閉じる",null).show();

        // ボタン
        Button positiveButton =dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setWidth(5000);
        positiveButton.setTextColor(Color.WHITE);
        positiveButton.setGravity(Gravity.CENTER);
        positiveButton.setBackgroundColor(getResources().getColor(R.color.alertBlue));

        LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        positiveButtonLL.gravity = Gravity.LEFT;
        positiveButtonLL.setMargins(0,0,0,40);
        positiveButton.setLayoutParams(positiveButtonLL);



    }
    public void editPage(int id){

        EditPageViewTask task = new EditPageViewTask(getActivity());
        task.execute(String.valueOf(id));



    }


}
