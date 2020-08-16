package com.example.item;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
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


public class UserRequestFragment extends Fragment implements UserRequestList.RequestListener
{
    UserRequestList userRequestList;
    List<userRequestParams> list = new ArrayList<>();
    ListView listView;
    View dialogLayout;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.user_request, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout mainContent = view.findViewById(R.id.request_content);
        mainContent.setVisibility(View.INVISIBLE);
        view.findViewById(R.id.not_request_title).setVisibility(View.VISIBLE);
        userRequestList = new UserRequestList(getContext(),0,list);
        userRequestList.setListener(this);

        final UserRequestTask task = new UserRequestTask(getActivity(),userRequestList, list);
        task.execute();


        listView = view.findViewById(R.id.request_listView);
        listView.setAdapter(userRequestList);
    }
    public void confirmationView(int i){
        final userRequestParams params = userRequestList.getItem(i);

        //ダイアログxml読み込み
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogLayout = layoutInflater.inflate(R.layout.request_dialog,(ViewGroup)getActivity().findViewById(R.id.request_dialog_main));


        //ダイアログのタイトル
        TextView dialogtitle = new TextView(getActivity());
        dialogtitle .setText(params.getDay()+""+params.getShop()+"依頼状況");
        dialogtitle .setTextSize(24);
        dialogtitle .setTextColor(Color.WHITE);
        dialogtitle .setBackgroundColor(getResources().getColor(R.color.alertBlue));
        dialogtitle .setPadding(20, 20, 20, 20);
        dialogtitle.setGravity(Gravity.CENTER);

        //ダイアログメインメッセージ
        TextView mainMessage = dialogLayout.findViewById(R.id.request_dialog_item_title);
        mainMessage.setText(params.getName() + "について");

        //発注数
        TextView number = dialogLayout.findViewById(R.id.request_dialog_number);
        number.setText(params.getNumber());
        number.setTypeface(Typeface.DEFAULT_BOLD);




        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setCustomTitle(dialogtitle)
                .setView(dialogLayout)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       ConfirmTask task = new ConfirmTask(getActivity());
                       task.execute(String.valueOf(params.getId()), params.getConfirm());

                        TextView confirm = listView.findViewById(R.id.request_confirm_cont);
                        confirm.setText("確定");
                        listView.findViewById(R.id.reuest_send).setVisibility(View.GONE);
                        listView.findViewById(R.id.reuest_edit).setVisibility(View.VISIBLE);
                        confirm.setGravity(Gravity.CENTER);
                        confirm.setTextColor(Color.WHITE);
                        confirm.setBackgroundColor(Color.parseColor("#6200EE"));

                    }
                })
                .setNegativeButton("キャンセル",null).show();

        // ボタン
        Button positiveButton =dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.WHITE);
        positiveButton.setBackgroundColor(getResources().getColor(R.color.alertBlue));
        ViewGroup.LayoutParams lp = positiveButton.getLayoutParams();
        ViewGroup.MarginLayoutParams mp =(ViewGroup.MarginLayoutParams)lp;
        mp.setMargins(40,0,30,0);


        Button nagativeButton =dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        nagativeButton.setTextColor(Color.WHITE);
        nagativeButton.setPadding(30,0,30,0);
        nagativeButton.setBackgroundColor(Color.RED);


    }
    public  void backconfirm(int i){
        final userRequestParams params = userRequestList.getItem(i);
        ConfirmTask task = new ConfirmTask(getActivity());
        task.execute(String.valueOf(params.getId()), params.getConfirm());
        listView.findViewById(R.id.reuest_send).setVisibility(View.VISIBLE);
        listView.findViewById(R.id.reuest_edit).setVisibility(View.GONE);
        TextView confirm = listView.findViewById(R.id.request_confirm_cont);
        confirm.setText("未確定");
        confirm.setGravity(Gravity.CENTER);
        confirm.setTextColor(Color.WHITE);
        confirm.setBackgroundColor(Color.RED);
    }

}
