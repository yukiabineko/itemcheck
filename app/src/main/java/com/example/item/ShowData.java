package com.example.item;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
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

public class ShowData extends AsyncTask<String, Void, String> {

    private  Activity  activity;
    private  CustomList customList;
    private List<ViewItemParam> list;

    public ShowData(Activity activity) {
        this.activity = activity;
    }


    // 非同期処理
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... params) {

        // 使用するサーバーのURLに合わせる
        String urlSt = "http://yukiabineko.sakura.ne.jp/items/showPost.php";

        HttpURLConnection httpConn;

        String word = "id=17";
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
            } catch (IOException e) {
            }
        } catch (IOException e) {
        }
        return sb.toString();
    }

    // 非同期処理が終了後、結果をメインスレッドに返す
    public void onPostExecute(String result) {

        if(result != null){

            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String id =   jsonObject.getString("id");
                String path = jsonObject.getString("path");
                String name = jsonObject.getString("name");
                String price = jsonObject.getString("price");
                String memo = jsonObject.getString("memo");


                ViewItemParam param = new ViewItemParam();
                param.setId(Integer.parseInt(id));
                param.setBitmap(path);
                param.setName(name);
                param.setPrice(price);
                param.setMemo(memo);
                list.add(param);
                customList.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
           Toast.makeText(activity, "失敗",Toast.LENGTH_LONG).show();
        }
    }
}
