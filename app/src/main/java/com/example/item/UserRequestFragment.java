package com.example.item;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");

        LinearLayout mainContent = view.findViewById(R.id.request_content);
        mainContent.setVisibility(View.INVISIBLE);
        view.findViewById(R.id.not_request_title).setVisibility(View.VISIBLE);
        view.findViewById(R.id.not_request_button).setVisibility(View.VISIBLE);
        userRequestList = new UserRequestList(getContext(),0,list);
        userRequestList.setListener(this);

        final UserRequestTask task = new UserRequestTask(getActivity(),userRequestList, list);
        task.execute();


        listView = view.findViewById(R.id.request_listView);
        listView.setAdapter(userRequestList);

        Button updateButton = view.findViewById(R.id.request_up_button);
        updateButton.setTypeface(font);


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UserRequestTask task = new UserRequestTask(getActivity(),userRequestList, list);
                task.execute();

            }
        });
        Button hiddenButton = view.findViewById(R.id.not_request_button);
        hiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final UserRequestTask task = new UserRequestTask(getActivity(),userRequestList, list);
                task.execute();

            }
        });

        Button resetButton = view.findViewById(R.id.request_all_delete);
        resetButton.setTypeface(font);

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

       //価格合計
        TextView total = dialogLayout.findViewById(R.id.request_dialog_total);
        int price = Integer.parseInt(params.getPrice());
        int num = Integer.parseInt(params.getNumber());
        int totalmoney = price * num;
        total.setText(String.valueOf(totalmoney));

        //問い合わせ
        TextView question = dialogLayout.findViewById(R.id.request_dialog_memo);
        question.setText(params.getMemo());


        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setCustomTitle(dialogtitle)
                .setView(dialogLayout)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       ConfirmTask task = new ConfirmTask(getActivity());
                       task.execute(String.valueOf(params.getUserid()), String.valueOf(params.getItemId()), params.getConfirm());

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
        task.execute(String.valueOf(params.getUserid()),String.valueOf(params.getItemId()), params.getConfirm());
        listView.findViewById(R.id.reuest_send).setVisibility(View.VISIBLE);
        listView.findViewById(R.id.reuest_edit).setVisibility(View.GONE);
        TextView confirm = listView.findViewById(R.id.request_confirm_cont);
        confirm.setText("未確定");
        confirm.setGravity(Gravity.CENTER);
        confirm.setTextColor(Color.WHITE);
        confirm.setBackgroundColor(Color.RED);
    }
    public void mailsend(int i){
        final userRequestParams params = userRequestList.getItem(i);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{params.getEmail()});
        intent.putExtra(Intent.EXTRA_SUBJECT, params.getName() + "ついて。");

        startActivity(intent);

    }

}
