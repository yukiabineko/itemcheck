package com.example.item;


import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UserRequestTask extends AsyncTask<Void, Void, String >
{
    private  UserRequestList customList;
    private List<userRequestParams> list;
    Activity activity;

    public UserRequestTask(Activity activity,UserRequestList customList, List<userRequestParams> list){
        super();
        this.activity =activity;
        this.customList = customList;
        this.list = list;
    }


    @Override
    protected String doInBackground(Void ... v)
    {
        String line;
        StringBuilder sb = new StringBuilder();//追加
        try{

            URL url = new URL("http://yukiabineko.sakura.ne.jp/items/userRequestjson.php");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();


            InputStreamReader in = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(in);


            while((line = br.readLine()) != null){
                System.out.println(line);
                if(sb.length() > 0) sb.append('\n');//追加
                sb.append(line);//追加
            }

            br.close();
            in.close();
            http.disconnect();

        }catch(Exception e){
            System.out.println(e);
        }
        return sb.toString();//変更
    }
    protected void onPostExecute(String data){
        super.onPostExecute(data);
        LinearLayout ll = activity.findViewById(R.id.request_content);

        if(data != null){

            try {
                JSONArray jsonArray = new JSONArray(data);
                for(int i=0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id =   jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String shop = jsonObject.getString("shop"); String confirm = jsonObject.getString("confirm");
                    String number = jsonObject.getString("num");
                    String memo = jsonObject.getString("memo");
                    String day = jsonObject.getString("day");
                    String conf = jsonObject.getString("confirm");
                    String price = jsonObject.getString("price");
                    String email = jsonObject.getString("email");


                    userRequestParams param = new userRequestParams();
                    param.setId(Integer.parseInt(id));
                    param.setName(name);
                    param.setShop(shop);
                    param.setNumber(number);
                    param.setMemo(memo);
                    param.setDay(day);
                    param.setConfirm(conf);
                    param.setPrice(price);
                    param.setMail(email);
                    list.add(param);
                    customList.notifyDataSetChanged();
                    ll.setVisibility(View.VISIBLE);
                    activity.findViewById(R.id.not_request_title).setVisibility(View.GONE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            ll.setVisibility(View.INVISIBLE);
            activity.findViewById(R.id.not_request_title).setVisibility(View.VISIBLE);
            Toast.makeText(activity, "データがありません。",Toast.LENGTH_LONG).show();
        }
    }
}
