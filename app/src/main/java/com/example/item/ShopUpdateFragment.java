package com.example.item;

import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import java.util.regex.Pattern;


public class ShopUpdateFragment extends Fragment{
    private EditText shopInput, emailInput, passwordInput, passwordConfirmationInput, telInput;
    private TextView shopError, emailError, passwordError, confirmationError, telError;
    private String id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.add_shop_view, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        Bundle args = getArguments();
        id = args.getString("id");

        Button newButton = view.findViewById(R.id.add_shop_button);
        newButton.setVisibility(View.GONE);
        Button updateButton = view.findViewById(R.id.add_edit_button);
        updateButton.setTypeface(font);
        updateButton.setVisibility(View.VISIBLE);

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
        telError = view.findViewById(R.id.shop_tel_validation);

        /* 登録作業*/
        Button shopAdd = view.findViewById(R.id.add_shop_button);
        shopAdd.setTypeface(font);
        shopAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shop = shopInput.getText().toString();                                     /*店舗書き込み内容*/
                String email = emailInput.getText().toString();                                   /*email書き込み内容*/
                String password = passwordInput.getText().toString();                            /*password書き込み内容*/
                String passwordConfirmation = passwordConfirmationInput.getText().toString();    /*passwordConfirmation書き込み内容*/
                String tel = telInput.getText().toString();

                /*各種ERROR対応または通信*/

                String pattern = "^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+$"; /*メールパターン*/
                Pattern p = Pattern.compile(pattern);

                String telPattern = "^\\d{2,4}-\\d{2,4}-\\d{4}$";                                            /*電話パターン*/
                Pattern p2 = Pattern.compile(telPattern);

                if(shop.equals("")){ shopError.setVisibility(View.VISIBLE); }
                else{shopError.setVisibility(View.GONE);}


                if(email.equals("")){
                    emailError.setText("(必須)メールアドレスを入力ください。");
                    emailError.setVisibility(View.VISIBLE);
                }
                else if(!p.matcher(email).find()){
                    emailError.setText("メールアドレスが正しくありません。");
                    emailError.setVisibility(View.VISIBLE);
                }
                else{ emailError.setVisibility(View.GONE);}

                if(!p2.matcher(tel).find() && !tel.equals("")){
                    telError.setVisibility(View.VISIBLE);
                }
                else {telError.setVisibility(View.GONE);}



                if(password.equals(passwordConfirmation) && !password.equals("") && !passwordConfirmation.equals("")){
                    passwordError.setVisibility(View.GONE);
                    confirmationError.setVisibility(View.GONE);
                }
                else if(!password.equals("") && passwordConfirmation.equals("")) {
                    confirmationError.setText("(必須)パスワードを入力ください。");
                    confirmationError.setVisibility(View.VISIBLE);
                    passwordError.setVisibility(View.GONE);
                }
                else if(password.equals("") && !passwordConfirmation.equals("")) {
                    passwordError.setText("(必須)パスワードを入力ください。");
                    passwordError.setVisibility(View.VISIBLE);
                    confirmationError.setVisibility(View.GONE);
                }
                else if(password.equals("") && passwordConfirmation.equals("")) {
                    passwordError.setText("(必須)パスワードを入力ください。");
                    passwordError.setVisibility(View.VISIBLE);

                    confirmationError.setText("(必須)パスワードを入力ください。");
                    confirmationError.setVisibility(View.VISIBLE);
                }
                else if(!password.equals(passwordConfirmation)){
                    passwordError.setText("パスワードが異なります。");
                    passwordError.setVisibility(View.VISIBLE);

                    confirmationError.setText("パスワードが異なります。");
                    confirmationError.setVisibility(View.VISIBLE);
                }
                if(password.equals(passwordConfirmation)
                        && p.matcher(email).find()
                        &&  !shop.equals("") && !email.equals("") && !password.equals("") && !passwordConfirmation.equals("")
                        && p2.matcher(tel).find() && !tel.equals("")
                ){
                    ShopUpdataTask task = new ShopUpdataTask(getActivity());
                    task.execute(shop, email, password,tel,id);

                }

               /*
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
                }*/


            }
        });


        /*店舗一覧に戻る*/
        Button back = view.findViewById(R.id.back_add_shop_button);
        back.setTypeface(font);
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
