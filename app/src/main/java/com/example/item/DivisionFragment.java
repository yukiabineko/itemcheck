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
import android.widget.ListView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;


public class DivisionFragment extends Fragment implements OrderDvisionList.OrderDivisionListener {

     private ListView listView;
     private  OrderDvisionList adapter;
     private List<DivisionParams> list = new ArrayList<>();
     private String name,id;
     private View dialogLayout;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.order_division, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        name = args.getString("name");
        id = args.getString("id");

        listView = view.findViewById(R.id.division_listView);
        TextView titleItem = view.findViewById(R.id.order_division_main_title);
        titleItem.setText("商品名:" + name + "集計");

        Button backButton = view.findViewById(R.id.division_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserRequestFragment itemAddFragment = new UserRequestFragment();
                FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ll, itemAddFragment).commit();
            }
        });


        adapter = new OrderDvisionList(getActivity(), 0, list);
        adapter.setListener(this);
        listView.setAdapter(adapter);

        DivisionTask task = new DivisionTask(getActivity(),adapter,list);
        task.execute(id);



    }
    public  void  confirmationView(int itemNumber,  Button confirmButton,  Button backButton,  TextView conf){
        final DivisionParams params = list.get(itemNumber);

        //ダイアログxml読み込み
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialogLayout = layoutInflater.inflate(R.layout.request_dialog,(ViewGroup)getActivity().findViewById(R.id.request_dialog_main));



        //ダイアログのタイトル
        TextView dialogtitle = new TextView(getActivity());
        dialogtitle .setText("確認");
        dialogtitle .setTextSize(24);
        dialogtitle .setTextColor(Color.WHITE);
        dialogtitle .setBackgroundColor(getResources().getColor(R.color.alertBlue));
        dialogtitle .setPadding(20, 20, 20, 20);
        dialogtitle.setGravity(Gravity.CENTER);


        //ダイアログメインメッセージ
        TextView mainMessage = dialogLayout.findViewById(R.id.request_dialog_item_title);
        mainMessage.setText("発注数確定してよろしいですか？");

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
        TextView questionTitle = dialogLayout.findViewById(R.id.memo_title);
        questionTitle.setVisibility(View.GONE);
        TextView question = dialogLayout.findViewById(R.id.request_dialog_memo);
        question.setVisibility(View.GONE);


        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setCustomTitle(dialogtitle)
                .setView(dialogLayout)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        ConfirmTask task = new ConfirmTask(getActivity());
                        task.execute(String.valueOf(params.getUserId()), String.valueOf(params.getItemId()), params.getConfirm());

                        DivisionFragment fragment = new DivisionFragment();
                        Bundle args = new Bundle();
                        args.putString("id", id);
                        args.putString("name", name);
                        fragment.setArguments(args);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                        fragmentTransaction.replace(R.id.ll, fragment).commit();

                    }
                })
                .setNegativeButton("キャンセル",null).show();

        // ボタン
        Button positiveButton =dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.WHITE);
        positiveButton.setTextSize(12);
        positiveButton.setBackgroundColor(getResources().getColor(R.color.alertBlue));
        ViewGroup.LayoutParams lp = positiveButton.getLayoutParams();
        ViewGroup.MarginLayoutParams mp =(ViewGroup.MarginLayoutParams)lp;
        mp.setMargins(0,0,0,10);


        Button nagativeButton =dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        nagativeButton.setTextColor(Color.WHITE);
        nagativeButton.setBackgroundColor(Color.RED);
        ViewGroup.LayoutParams lp2 = nagativeButton.getLayoutParams();
        ViewGroup.MarginLayoutParams mp2 =(ViewGroup.MarginLayoutParams)lp2;
        mp2.setMargins(0,0,20,10);



        /**********************************************************************************************************/

    }
    public void deleteConfirm(int itemNumber){
        DivisionParams params = list.get(itemNumber);
        ConfirmTask task = new ConfirmTask(getActivity());
        task.execute(String.valueOf(params.getUserId()), String.valueOf(params.getItemId()), params.getConfirm());

        DivisionFragment fragment = new DivisionFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("name", name);
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.ll, fragment).commit();
    }
    public void mailSend(int i){
        DivisionParams params = list.get(i);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{params.getEmail()});
        intent.putExtra(Intent.EXTRA_SUBJECT, params.getName() + "ついて。");

        startActivity(intent);
    }

}
