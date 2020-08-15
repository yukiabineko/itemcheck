package com.example.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class UserRequestFragment extends Fragment
{
    UserRequestList userRequestList;
    List<userRequestParams> list = new ArrayList<>();
    ListView listView;


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

        final UserRequestTask task = new UserRequestTask(getActivity(),userRequestList, list);
        task.execute();


        listView = view.findViewById(R.id.request_listView);
        listView.setAdapter(userRequestList);


    }
}
