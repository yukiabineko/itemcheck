package com.example.item;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;


class DivisionTask extends AsyncTask<String, Void, StringBuilder> {
    private  Activity activity;
    private  OrderDvisionList adapter;
    private  List<DivisionParams> list;

    public DivisionTask(Activity activity, OrderDvisionList adapter, List<DivisionParams> list){
       this.activity = activity;
       this.adapter = adapter;
       this.list = list;
    }

    // 非同期処理
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected StringBuilder doInBackground(String... params) {

        // 使用するサーバーのURLに合わせる
        String urlSt = "http://yukiabineko.sakura.ne.jp/items/orderDivision.php";

        HttpURLConnection httpConn;

        String word = "item_id=" + params[0];
        StringBuilder sb = new StringBuilder();

        try {
            // URL設定
            URL url = new URL(urlSt);

            // HttpURLConnection
            httpConn = (HttpURLConnection) url.openConnection();

            // request POST
            httpConn.setRequestMethod("POST");

            // no Redirects
            httpConn.setInstanceFollowRedirects(false);

            // データを書き込む
            httpConn.setDoOutput(true);

            // 時間制限
            httpConn.setReadTimeout(10000);
            httpConn.setConnectTimeout(20000);

            // 接続
            httpConn.connect();

            try (// POSTデータ送信処理
                 OutputStream outStream = httpConn.getOutputStream()) {
                outStream.write(word.getBytes(StandardCharsets.UTF_8));
                outStream.flush();
                Log.d("debug", "flush");

                // データを受け取る
                InputStream is = httpConn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line);
                is.close();
            } catch (IOException e) {}
        }
        catch (IOException e){}
        return sb;
    }
    // 非同期処理が終了後、結果をメインスレッドに返す
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onPostExecute(StringBuilder result){
        Toast.makeText(activity,result.toString(),Toast.LENGTH_LONG).show();
        if(result !=null){
            try{

                JSONArray jsonArray = new JSONArray(result.toString());
                for(int i=0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id =   jsonObject.getString("id");
                    String price = jsonObject.getString("price");
                    String memo = jsonObject.getString("memo");
                    String confirm = jsonObject.getString("confirm");

                    DivisionParams params = new DivisionParams();
                    params.setId(Integer.parseInt(id));
                    params.setPrice(price);
                    params.setConfirm(confirm);
                    params.setMemo(memo);
                    list.add(params);
                    adapter.notifyDataSetChanged();

                    if(jsonArray.length() == 0 || jsonArray==null){


                    }
                    else {

                    }


                }

            }
            catch (Exception e){}
        }
    }

}