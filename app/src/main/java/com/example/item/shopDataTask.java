package com.example.item;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

public class shopDataTask extends AsyncTask<Void, Void, String > {

    private  Activity  activity;
    private  ShopDataList customList;
    private List<ShopDataParams> list;
    private Dialog dialog;


    public shopDataTask(Activity activity,ShopDataList customList, List<ShopDataParams> list){
        super();
        this.activity = activity;
        this.customList = customList;
        this.list = list;
    }
    @Override
    protected void onPreExecute() {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.progress);
        dialog.setCancelable(false);
        dialog.setTitle("読み込み中");
        dialog.show();
    }

    @Override
    protected String doInBackground(Void ... v){

        String line;
        StringBuilder sb = new StringBuilder();//追加

        try{
            System.out.println("1");

            URL url = new URL("http://yukiabineko.sakura.ne.jp/items/shopJson.php");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setReadTimeout(6000);
            http.setConnectTimeout(5000);
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
                    String name = jsonObject.getString("shop");
                    String email = jsonObject.getString("email");
                    String tel = jsonObject.getString("tel");



                    ShopDataParams param = new ShopDataParams();
                    param.setId(Integer.parseInt(id));
                    param.setName(name);
                    param.setEmail(email);
                    param.setTel(tel);
                    list.add(param);

                    customList.notifyDataSetChanged();
                }

                    activity.findViewById(R.id.shop_list_area).setVisibility(View.VISIBLE);
                    activity.findViewById(R.id.not_list_shop).setVisibility(View.GONE);
                    activity.findViewById(R.id.not_list_shop_button).setVisibility(View.GONE);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{

        }
        dialog.dismiss();
    }

}


