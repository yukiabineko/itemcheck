package com.example.item;


import android.os.AsyncTask;
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

    public UserRequestTask(UserRequestList customList, List<userRequestParams> list){
        super();
        this.customList = customList;
        this.list = list;
    }


    @Override
    protected String doInBackground(Void ... v)
    {
        String line;
        StringBuilder sb = new StringBuilder();//追加
        try{

            URL url = new URL("http://yukiabineko.sakura.ne.jp/items/UserRequestJson.php");
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

        if(data != null){
            try {
                JSONArray jsonArray = new JSONArray(data);
                for(int i=0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id =   jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String price = jsonObject.getString("price");
                    String number = jsonObject.getString("number");
                    String memo = jsonObject.getString("memo");


                    userRequestParams param = new userRequestParams();
                    param.setId(Integer.parseInt(id));
                    param.setName(name);
                    param.setPrice(price);
                    param.setNumber(number);
                    param.setMemo(memo);
                    list.add(param);

                    customList.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{

        }
    }
}
