package com.example.item;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;


public class ShopInfoFragment extends Fragment
{
    private ListView listView;
     List<ShopDataParams> list = new ArrayList<>();
     ShopDataList adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.shop_info_view, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        adapter = new ShopDataList(getContext(),0, list);
        shopDataTask task = new shopDataTask(getActivity(),adapter,list);
        task.execute();
        listView = view.findViewById(R.id.shop_listView);
        listView.setAdapter(adapter);

        /*新規店舗登録*/

        final Button newButton = view.findViewById(R.id.new_shop_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddShopInfoFragment fragment = new AddShopInfoFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ll, fragment).commit();
            }
        });


    }



}
