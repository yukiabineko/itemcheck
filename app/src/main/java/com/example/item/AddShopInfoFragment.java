package com.example.item;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class AddShopInfoFragment extends Fragment{
     private EditText shopInput, emailInput, passwordInput, passwordConfirmationInput, telInput;
     private TextView shopError, emailError, passwordError, confirmationError;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.add_shop_view, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*editText一覧*/

        shopInput = view.findViewById(R.id.shop_name_edit);
        emailInput = view.findViewById(R.id.add_shop_mail);
        passwordInput = view.findViewById(R.id.add_shop_pass);
        passwordConfirmationInput = view.findViewById(R.id.add_shop_pass_confirmation);
        telInput = view.findViewById(R.id.add_shop_tel);

        /*警告テキスト*/
        shopError = view.findViewById(R.id.shop_name_validation);
        emailError = view.findViewById(R.id.shop_mail_validation);
        passwordError = view.findViewById(R.id.shop_pass_validation);
        confirmationError = view.findViewById(R.id.shop_conf_validation);

        /* 登録作業*/
        Button shopAdd = view.findViewById(R.id.add_shop_button);
        shopAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shop = shopInput.getText().toString();                                     /*店舗書き込み内容*/
                String email = emailInput.getText().toString();                                   /*email書き込み内容*/
                String password = passwordInput.getText().toString();                            /*password書き込み内容*/
                String passwordConfirmation = passwordConfirmationInput.getText().toString();    /*passwordConfirmation書き込み内容*/

                /*各種ERROR対応または通信*/

                if(shop.equals("")){ shopError.setVisibility(View.VISIBLE); }
                else{shopError.setVisibility(View.GONE);}


                if(email.equals("")){ emailError.setVisibility(View.VISIBLE); }
                else{ emailError.setVisibility(View.GONE);}



                if(password.equals("")){
                    passwordError.setText("(必須)パスワードを入力ください。");
                    passwordError.setVisibility(View.VISIBLE);
                }
                else{ passwordError.setVisibility(View.GONE);}

                if(passwordConfirmation.equals("")){
                    confirmationError.setText("(必須)パスワードを入力ください。");
                    confirmationError.setVisibility(View.VISIBLE);
                }
                else { confirmationError.setVisibility(View.GONE);}

                if(!password.equals("") && !password.equals("")){
                    passwordError.setVisibility(View.GONE);
                    confirmationError.setVisibility(View.GONE);
                }


            }
        });




        /*店舗一覧に戻る*/
        Button back = view.findViewById(R.id.back_add_shop_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopInfoFragment shopInfoFragment = new ShopInfoFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ll, shopInfoFragment).commit();
            }
        });

    }


}
