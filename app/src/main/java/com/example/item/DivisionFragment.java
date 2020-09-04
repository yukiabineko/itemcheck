package com.example.item;

import android.os.Bundle;

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

    }

}
