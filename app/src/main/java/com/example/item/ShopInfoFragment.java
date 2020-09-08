package com.example.item;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;


public class ShopInfoFragment extends Fragment implements ShopDataList.shopListener
{
    private ListView listView;
     List<ShopDataParams> list = new ArrayList<>();
     ShopDataList adapter;
     NetworkInfo info;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.shop_info_view, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConnectivityManager connectivityManager = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        info = connectivityManager.getActiveNetworkInfo();


        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        adapter = new ShopDataList(getContext(),0, list);
        adapter.setListener(this);
        shopDataTask task = new shopDataTask(getActivity(),adapter,list);
        task.execute();
        listView = view.findViewById(R.id.shop_listView);
        listView.setAdapter(adapter);

        /*新規店舗登録*/

        final Button newButton = view.findViewById(R.id.new_shop_button);
        newButton.setTypeface(font);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddShopInfoFragment fragment = new AddShopInfoFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ll, fragment).commit();
            }
        });
        Button notButton = view.findViewById(R.id.not_list_shop_button);
        notButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopDataTask task = new shopDataTask(getActivity(),adapter,list);
                task.execute();
            }
        });


    }
    @Override
    public void deleteShop(int shopNO) {
        ShopDataParams params = list.get(shopNO);
        final int id = params.getId();

        new AlertDialog.Builder(getActivity())
                .setTitle("削除確認")
                .setMessage("削除しますか？")
                .setPositiveButton("削除する", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       ShopDataDeleteTask task = new ShopDataDeleteTask(getActivity());
                       task.execute(String.valueOf(id));

                        ShopInfoFragment shopInfoFragment = new ShopInfoFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.ll, shopInfoFragment).commit();
                    }
                })
                .setNeutralButton("閉じる",null).show();
    }

    @Override
    public void updateShop(int shopNO) {
        ShopDataParams params = list.get(shopNO);
        final int id = params.getId();
        /*Toast.makeText(getActivity(), String.valueOf(id),Toast.LENGTH_LONG).show();*/
       if(info != null && info.isAvailable()){
           ShopFindTask task = new ShopFindTask(getActivity());
           task.execute(String.valueOf(id));
       }

    }

    @Override
    public void shopList() {
        Toast.makeText(getActivity(), "編集失敗",Toast.LENGTH_LONG).show();
        ShopInfoFragment shopInfoFragment = new ShopInfoFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ll, shopInfoFragment).commit();
    }
}
