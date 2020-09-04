package com.example.item;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

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


    }



}
